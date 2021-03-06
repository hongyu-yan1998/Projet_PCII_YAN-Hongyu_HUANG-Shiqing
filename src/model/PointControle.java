package model;

import view.Affichage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @description definir les etats du point de contrôle
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/14
 */
public class PointControle {
    //intervalles réguliers apparaissent des points de contrôles
    public static final int INTERVALLE = 500;
    //le bonus de temps est de plus en plus faible
    public static final int FAIBLE = 5;
    //temps supplémentaire
    private int bonus;
    private double x;
    private double y;
    //le temps total
    private long temps = 20 ;

    private Image img = new ImageIcon("src/image/drapeau.png").getImage();

    /**
     * Constructeur
     */
    public PointControle() {
        this.bonus = 10;
    }

    /**
     * Change l'ordonnée du point de contrôle
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Change l'abcisse du point de contrôle
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Obetenir l'abcisse du véhicule
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Obetenir l'ordonnée du point de contrôle
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Faiblir le temps supplémentaire
     * */
    public void setBonus(double No) {
        if(this.bonus <= FAIBLE)
            this.bonus = 1;
        else
            this.bonus -= No * FAIBLE;
    }

    public long getTemps() {
        return temps;
    }
    public void setTemps() {
        this.temps += this.bonus;
    }

    /**
     * Obetenir le temps supplémentaire
     * @return
     */
    public int getBonus() {
        return bonus;
    }

    public Image getImg() {
        return img;
    }
}
