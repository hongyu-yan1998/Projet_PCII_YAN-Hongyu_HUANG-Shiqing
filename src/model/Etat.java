package model;

import view.Affichage;

import javax.swing.*;
import java.awt.*;

/**
 * @description definir les etats du vheicule
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Etat {
    /** Vitesse maximum du véhicule */
    public static final int V_MAX = 240;
    /** Vitesse minimum du véhicule */
    public static final int V_MIN = 40;
    /** Déplacement du véhicule */
    public static final int DEPLACE = 10;

    private int position;
    private int vitesse;
    private Image img = new ImageIcon("src/image/car.png").getImage();
    private Image imgTree = new ImageIcon("src/image/tree.png").getImage();
    private Piste piste;
    private Decors decors;

    /**
     * Constructeur
     */
    public Etat() {
        this.position = Affichage.LARG/2 - Affichage.WIDTH/2;
        this.vitesse = V_MIN;
        this.piste = new Piste();
        this.decors = new Decors(this.piste);
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
     * Obetenir l'image de l'arbre
     * @return
     */
    public Image getImgTree(){ return imgTree; }

    /**
     * Obetenir l'etat du décors
     * @return
     */
    public Point[] getDecors() {
        return decors.getTree();
    }

    /**
     * Le véhicule se déplace vers la gauche
     */
    public void left() {
        //Pour que la voiture ne dépasse pas le bord gauche de la fenetre
        if (this.position > 0) {
            this.position -= DEPLACE;
        }
        //La piste se déplace vers la droite
        this.piste.left();
        //Le décors se déplace vers la droite
        this.decors.left();
    }

    /**
     * Le véhicule se déplace vers la droite
     */
    public void right() {
        //Pour que la voiture ne dépasse pas le bord droit de la fenetre
        if (this.position < Affichage.LARG - Affichage.WIDTH) {
            this.position += DEPLACE;
        }
        //La piste se déplace vers la gauche
        this.piste.right();
        //Le décors se déplace vers la gauche
        this.decors.right();
    }
}
