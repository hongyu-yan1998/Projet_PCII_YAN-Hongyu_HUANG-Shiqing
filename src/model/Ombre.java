package model;

import view.Affichage;

import javax.swing.*;
import java.awt.*;

/**
 * @description： L'ombre du véhicule
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/29
 */
public class Ombre {
    /** Valeur de witdth perdu/augmantant lorsque le véhicule se déplace vers le ciel/la terre */
    public static final int W = 4;
    private int width;
    private int height;
    private int x;
    private int y;
    private Image img;

    /**
     * Constructeur
     * @param x
     * @param y
     */
    public Ombre(int x, int y) {
        this.width = Affichage.WIDTH;
        this.height = Affichage.HEIGHT/3;
        this.img = new ImageIcon("src/image/ombre.png").getImage();
        this.x = x;
        this.y = y;
    }

    /**
     * Obtenir l'image du véhicule
     * @return
     */
    public Image getImg() {
        return img;
    }

    /**
     * Largeur du ombre
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Changer le largeur du ombre
     * @param width
     */
    public void setWidth(int width) {
        this.width += width;
    }

    /**
     * Longeur du ombre
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * La position du ombre
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Changer la position du ombre
     * @param x
     */
    public void setX(int x) {
        this.x += x;
    }

    /**
     * L'ordonée du ombre
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Quand le véhicule se déplace vers le gauche
     */
    public void moveLeft() {
        this.x -= Etat.DEPLACE;
    }

    /**
     * Quand le véhicule se déplace vers la droite
     */
    public void moveRight() {
        this.x += Etat.DEPLACE;
    }
}
