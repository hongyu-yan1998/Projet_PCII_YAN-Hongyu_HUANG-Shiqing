package model;

import control.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @description definir les etats du vheicule
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Etat {
    /** Vitesse maximum du véhicule */
    public static final int V_MAX = 60;
    /** Déplacement du véhicule */
    public static final int DEPLACE = 10;

    private int position;
    private int ancienPos;
    private double vitesse;
    private double acceleration;
    private long ancienTime = System.currentTimeMillis();
    private Image img = new ImageIcon("src/image/vehicule.png").getImage();
    private Piste piste;
    private ArrayList<VueDecors> vueDecors;

    /**
     * Constructeur
     */
    public Etat() {
        this.position = Affichage.LARG/2 - Affichage.WIDTH/2;
        this.ancienPos = this.position;
        this.vitesse = 30;
        this.acceleration = 0;
        this.piste = new Piste();
        this.vueDecors = new ArrayList<>();
    }

    /**
     * Obetenir l'abcisse du véhicule
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obetenir la piste
     * @return
     */
    public Piste getPiste() {
        return piste;
    }

    /**
     * Obetenir l'etat du véhicule
     * @return
     */
    public Image getImg() {
        return img;
    }

    /**
     * obtenir les decors/obstacles
     * @return
     */
    public ArrayList<VueDecors> getVueDecors() {
        return vueDecors;
    }

    /**
     * ajouter des nouveaux decors/obstacles
     * @param vueDecors
     */
    public void ajouterDecors(VueDecors vueDecors) {
        this.vueDecors.add(vueDecors);
    }


    /**
     * Obtenir l'abscisse gauche de la piste
     * @return
     */
    public double positionPiste() {
        Point2D p0 = null;
        Point2D p1 = null;
        int y_veh = Affichage.HAUT - Affichage.HEIGHT / 2;
        Point2D[] points = getPiste().getPoints();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].getY() >= y_veh && points[i+1].getY() <= y_veh) {
                p0 = points[i];
                p1 = points[i+1];
            }
        }
        double pente = (p1.getY() - p0.getY())*1.0/(p1.getX() - p0.getX());
        double b = (p1.getX()*p0.getY() - p0.getX()*p1.getY())*1.0/(p1.getX() - p0.getX());
        double x_piste = (y_veh - b)/pente;
        return x_piste;
    }

    /**
     * Un mécanisme de calcul de l’accélération du véhicule en fonction de la position par rapport à la piste
     */

    public void setAcceleration() {
        // si le véhicule est sur la piste
        if (this.position + Affichage.WIDTH >= positionPiste() && this.position <= positionPiste() + Piste.WID_PISTE)
            this.acceleration = 3;
        // si le véhicule en gauche de la piste
        else if (this.position + Affichage.WIDTH < positionPiste()) {
            if ((positionPiste() - this.ancienPos) >= (positionPiste() - this.position))
                this.acceleration = 1;
            else
                this.acceleration = -1;
        }
        // si le véhicule en droite de la piste
        else {
            if ((this.ancienPos - positionPiste()) >= (this.position - positionPiste()))
                this.acceleration = 1;
            else
                this.acceleration = -1;
        }
    }


    /**
     * Un mécanisme de calcul de la vitesse en fonction de l’accélération
     */
    public void setVitesse() {
        setAcceleration();
        double t = (System.currentTimeMillis() - ancienTime)/1000;
        System.out.println("ancient t " + this.ancienTime);
        System.out.println("courrent t " + this.ancienTime);
        this.ancienTime = System.currentTimeMillis();
        double v = this.vitesse + this.acceleration * t;
        if (v >= V_MAX) {
            this.vitesse = V_MAX;
        }
        else if (v >= 0) {
            this.vitesse = v;
        }
        //System.out.println("acc " + this.acceleration );
        //System.out.println("v " + this.vitesse);
        //System.out.println("t " + t );
    }


    /**
     * Le véhicule se déplace vers la gauche
     */
    public void left() {
        this.ancienPos = this.position;
        //Pour que la voiture ne dépasse pas le bord gauche de la fenetre
        if (this.position > 0) {
            this.position -= DEPLACE;
        }

        if (this.piste.BordDroit()) {
            //La piste se déplace vers la droite
            this.piste.left();
            for (VueDecors d: vueDecors) {
                d.setPosition(DEPLACE);
            }
        }
    }

    /**
     * Le véhicule se déplace vers la droite
     */
    public void right() {
        this.ancienPos = this.position;
        //Pour que la voiture ne dépasse pas le bord droit de la fenetre
        if (this.position < Affichage.LARG - Affichage.WIDTH) {
            this.position += DEPLACE;
        }

        if(this.piste.BordGauche()){
            //La piste se déplace vers la gauche
            this.piste.right();
            for (VueDecors d: vueDecors) {
                d.setPosition(-DEPLACE);
            }
        }
    }

    /**
     * Obetenir la vitesse du véhicule
     * @return
     */
    public double getVitesse() {
        setVitesse();
        return vitesse;
    }

    /**
     * Détection de la collision
     */
    public void collision() {
        // Parcourir la liste de decors
        for (VueDecors decors: this.vueDecors) {
            // si il est un obstacle
                // on obtient width et height de l'obstacle
                int width = decors.getWidth();
                int height = decors.getHeight();
                for (Decors o: decors.getDecors()) {
                    // on détecte s'il y a une collision
                    if (o.getHauteur() <= (Affichage.HAUT_VEH + Affichage.HEIGHT) &&
                            (o.getHauteur() + height) >= Affichage.HAUT_VEH ) {
                        if (o.getPosition() <= this.position+Affichage.WIDTH && o.getPosition()+width >= this.position) {
                            if (this.vitesse > 0) { // s'il y a une collision, on perd une vitesse fixe
                                this.vitesse -= 1;
                                System.out.println(this.vitesse);
                            } else {
                                System.out.println("game over");
                            }
                        }
                    }
                }
        }
    }

    /**
     * Tester si le jeu est perdu
     * @return
     */
    public boolean testPerdu() {
        // S'il ne reste pas de temps ou la vitesse est 0, le jeu perd
        if (this.vitesse == 0 || ((System.currentTimeMillis() - Piste.DEBUT)/1000 >= piste.getTemps()))
            return true;
        else
            return false;
    }
}
