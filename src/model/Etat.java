package model;

import control.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @description definir les etats du vheicule
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Etat {
    /** Vitesse maximum du véhicule */
    public static final int V_MAX = 30;
    /** Déplacement du véhicule */
    public static final int DEPLACE = 10;
    public static final double Y_VEH =  Affichage.HAUT_VEH + Affichage.HEIGHT / 2;
    public  boolean up = false;

    private int position;
    private int ancienPos;
    private double vitesse;
    private double acceleration;
    private int score;
    private int bestScore;
    private int y_veh =  Affichage.HAUT_VEH;
    private long ancienTime = System.currentTimeMillis();
    private Image img = new ImageIcon("src/image/vehicule.png").getImage();
    private Piste piste;
    private Ombre ombre;
    private ArrayList<VueDecors> vueDecors;

    /**
     * Constructeur
     */
    public Etat() {
        this.position = Affichage.LARG/2 - Affichage.WIDTH/2;
        this.ancienPos = this.position;
        this.vitesse = 10;
        this.acceleration = 0;
        this.score = 0;
        this.piste = new Piste();
        this.vueDecors = new ArrayList<>();
        this.ombre = new Ombre(this.position,this.y_veh + 60);
    }

    /**
     * Obetenir l'abcisse du véhicule
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obetenir l'ordonné du véhicule
     * @return
     */
    public int getY_veh() {
        return y_veh;
    }

    /**
     * Changer l'ordonné du véhicule
     * @param y_veh
     */
    public void setY_veh(int y_veh) {
        this.y_veh = y_veh;
    }

    /**
     * obtenir le score du joueur
     * @return
     */
    public int getScore() {
        this.score = (int) (piste.getPosition()/100);
        return score;
    }

    /**
     * Obtenir le meilleur score
     * @return
     */
    public int getBestScore() {
        return bestScore;
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
     * Obetenir l'ombre
     * @return
     */
    public Ombre getOmbre() {
        return ombre;
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
     * Un mécanisme de calcul de l’accélération du véhicule en fonction de la position par rapport à la piste
     */

    public void setAcceleration() {
        // si le véhicule est sur la piste
        if (this.position + Affichage.WIDTH >= piste.getPointSurPiste(Y_VEH) &&
                this.position <= piste.getPointSurPiste(Y_VEH) + Piste.WID_PISTE)
            this.acceleration = 8;
        // si le véhicule en gauche de la piste
        else if (this.position + Affichage.WIDTH < piste.getPointSurPiste(Y_VEH)) {
            if ((piste.getPointSurPiste(Y_VEH) - this.ancienPos) >= (piste.getPointSurPiste(Y_VEH) - this.position))
                this.acceleration = 8;
            else
                this.acceleration = -6;
        }
        // si le véhicule en droite de la piste
        else {
            if ((this.ancienPos - piste.getPointSurPiste(Y_VEH)) >= (this.position - piste.getPointSurPiste(Y_VEH)))
                this.acceleration = 8;
            else
                this.acceleration = -6;
        }
    }

    /**
     * Un mécanisme de calcul de la vitesse en fonction de l’accélération
     */
    public void setVitesse() {
        setAcceleration();
        double t = (double)(System.currentTimeMillis() - ancienTime)/1000;
        this.ancienTime = System.currentTimeMillis();
        double v = this.vitesse + this.acceleration * t;
        if (v >= V_MAX) {
            this.vitesse = V_MAX;
        }
        else if (v >= 0) {
            this.vitesse = v;
        }
        else {
            this.vitesse = 0;
        }
        //System.out.println("acc " + this.acceleration );
        //System.out.println("v " + this.vitesse);
    }

    /**
     * Changer et obetenir la vitesse du véhicule
     * @return
     */
    public double getVitesse() {
        setVitesse();
        return vitesse;
    }

    /**
     * Obetenir la vitesse du véhicule
     * @return
     */
    public double getVitesseV() {
        return vitesse;
    }

    /**
     * Le véhicule se déplace vers la gauche
     */
    public void left() {
        this.ancienPos = this.position;
        //Pour que la voiture ne dépasse pas le bord gauche de la fenetre
        if (this.position > 0) {
            this.position -= DEPLACE;
            ombre.moveLeft();
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
            ombre.moveRight();
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
     * Le véhicule se déplace vers le ciel
     */
    public void up() {
        if (up && this.y_veh >= 550) {
            this.y_veh -= 5;
            ombre.setWidth(-Ombre.W);
            ombre.setX(Ombre.W/2);
        }
    }

    /**
     * Le véhicule se déplace vers la terre
     */
    public void down() {
        if (!up && this.y_veh < Affichage.HAUT_VEH) {
            this.y_veh += 5;
            ombre.setWidth(Ombre.W);
            ombre.setX(-Ombre.W/2);
        }
    }

    public void move() {
        up();
        down();
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
                    if (this.y_veh >= Affichage.HAUT_VEH &&
                            o.getHauteur() <= (Affichage.HAUT_VEH + Affichage.HEIGHT) &&
                            (o.getHauteur() + height) >= Affichage.HAUT_VEH ) {
                        if (o.getPosition() <= this.position+Affichage.WIDTH && o.getPosition()+width >= this.position) {
                            this.vitesse -= 1;
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
        if (this.vitesse == 0 || piste.getTempsres() <=0 )
            return true;
        else
            return false;
    }

    /**
     * Si le jeu termine, on memorise le meilleur score
     */
    public void gameOver() {
        File file = new File("src/score.txt");
        if (!file.exists()) { // si c'est la première fois que le joueur joue le jeu
            try {
                this.bestScore = this.getScore(); // on mémorise le score du premier fois
                file.createNewFile(); // on crée un nouveau fichier pour stocker le meilleur score
                FileWriter out = new FileWriter(file);
                out.write(String.valueOf(this.bestScore)); // on êcrit le score dans le fichier
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader in =new BufferedReader(new FileReader(file));
                String contentLine = in.readLine();
                int scoreCourrent = Integer.parseInt(contentLine);
                this.bestScore = scoreCourrent;
                if (scoreCourrent < this.getScore()) {
                    this.bestScore = this.getScore();
                    FileWriter out = new FileWriter(file);
                    out.write(String.valueOf(this.bestScore)); // on êcrit le score dans le fichier
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
