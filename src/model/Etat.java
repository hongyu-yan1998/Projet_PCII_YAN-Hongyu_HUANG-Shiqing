package model;

import control.*;
import view.*;

import javax.swing.*;
import java.awt.*;
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
    private int vitesse;
    private double acceleration;
    private Image img = new ImageIcon("src/image/vehicule.png").getImage();
    private Piste piste;
    private ArrayList<VueDecors> vueDecors;

    /**
     * Constructeur
     */
    public Etat() {
        this.position = Affichage.LARG/2 - Affichage.WIDTH/2;
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
        Point p0 = null;
        Point p1 = null;
        int y_veh = Affichage.HAUT - Affichage.HEIGHT / 2;
        Point[] points = getPiste().getPoints();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].y >= position && points[i+1].y <= position) {
                p0 = points[i];
                p1 = points[i+1];
            }
        }
        double pente = (p1.y - p0.y)*1.0/(p1.x - p0.x);
        double b = (p1.x*p0.y - p0.x*p1.y)*1.0/(p1.x - p0.x);
        double x_piste = (y_veh - b)/pente;
        return x_piste;
    }

    /**
     * Un mécanisme de calcul de l’accélération du véhicule en fonction de la position par rapport à la piste
     */
    /*
    public void setAcceleration() {
        if (this.position + Affichage.WIDTH >= positionPiste() && this.position <= positionPiste() + Piste.WID_PISTE)
            this.acceleration = 3;
        else if (this.position + Affichage.WIDTH < positionPiste())
            this.acceleration = 2;
            //this.acceleration = positionPiste() - this.position - Affichage.WIDTH;
        else
            this.acceleration = 2;
            //this.acceleration = this.position - positionPiste() - Piste.WID_PISTE;

    }

     */

    /**
     * Un mécanisme de calcul de la vitesse en fonction de l’accélération
     */
    /*
    public void setVitesse() {
        setAcceleration();
        if (this.vitesse + this.acceleration <= V_MAX && this.vitesse + acceleration >= 0) {
            this.vitesse += this.acceleration;
        }
        System.out.println("acc " + this.acceleration );
        System.out.println("v " + this.vitesse);
    }

     */

    /**
     * Le véhicule se déplace vers la gauche
     */
    public void left() {
        //Pour que la voiture ne dépasse pas le bord gauche de la fenetre
        if (this.position > 0) {
            this.position -= DEPLACE;
        }

        if (this.piste.BordDroit()) {
            //La piste se déplace vers la droite
            this.piste.left();
            //Le décors se déplace vers la droite
            //this.decors.left();
            for (VueDecors d: vueDecors) {
                d.setPosition(DEPLACE);
            }
        }

        /*
        // si le vehicule est sur le côté gauche de la piste
        if (this.position + Affichage.WIDTH <= positionPiste()) {
            this.acceleration = (-1) * Math.abs(this.acceleration);
            System.out.println(1);
        }

        else{
            this.acceleration = Math.abs(this.acceleration);
            System.out.println(2);
        }
        setVitesse();
         */
    }

    /**
     * Le véhicule se déplace vers la droite
     */
    public void right() {
        //Pour que la voiture ne dépasse pas le bord droit de la fenetre
        if (this.position < Affichage.LARG - Affichage.WIDTH) {
            this.position += DEPLACE;
        }

        if(this.piste.BordGauche()){
            //La piste se déplace vers la gauche
            this.piste.right();
            //Le décors se déplace vers la gauche
            //this.decors.right();
            for (VueDecors d: vueDecors) {
                d.setPosition(-DEPLACE);
            }
        }

        /*
        // si le vehicule est sur le côté droit de la piste
        if (this.position >= positionPiste() + Piste.WID_PISTE)
            this.acceleration = (-1) * Math.abs(this.acceleration);
        else
            this.acceleration = Math.abs(this.acceleration);
        setVitesse();
         */
    }

    /**
     * Obetenir la vitesse du véhicule
     * @return
     */
    public int getVitesse() {
        //setVitesse();
        return vitesse;
    }

    /**
     * Détection de la collision
     */
    public void collision() {
        // Parcourir la liste de decors
        for (VueDecors decors: this.vueDecors) {
            // si il est un obstacle
            if (decors instanceof VueObstacles) {
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
    }

    /**
     * Tester s'il reste du temps
     * @return
     */
    public boolean testTemps(){
        if ((System.currentTimeMillis() - Piste.DEBUT)/1000 == piste.temps)
            return false;
        else
            return true;
    }

    /**
     * Tester si la vitesse est de 0
     * @return
     */
    public boolean testVitesse(){
        if (this.vitesse == 0)
            return false;
        else
            return true;
    }
}
