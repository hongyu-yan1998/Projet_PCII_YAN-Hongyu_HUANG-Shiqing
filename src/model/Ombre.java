package model;

import view.Affichage;

import javax.swing.*;
import java.awt.*;

/**
 * @description：
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

    public Ombre(int x, int y) {
        this.width = Affichage.WIDTH;
        this.height = Affichage.HEIGHT/3;
        this.img = new ImageIcon("src/image/ombre.png").getImage();
        this.x = x;
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width += width;
    }
    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x += x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        this.x -= Etat.DEPLACE;
    }

    public void moveRight() {
        this.x += Etat.DEPLACE;
    }
}
