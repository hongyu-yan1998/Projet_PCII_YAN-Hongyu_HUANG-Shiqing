package view;

import control.Decors;

import java.awt.*;
import java.util.ArrayList;

/**
 * @description： Affichage des deors
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class VueDecors {
    private int width;
    private int height;
    private Image img;
    private ArrayList<Decors> decors = new ArrayList<>();

    /**
     * Constructeur
     * @param width
     * @param height
     */
    public VueDecors(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Largeur du decors
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Longeur du decors
     * @return
     */
    public int getHeight() {
        return height;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    /**
     * Ajouter un decors dans la liste
     * @param decors
     */
    public void ajouterDecor(Decors decors) {
        this.decors.add(decors);
    }

    /**
     * Retirer un decors
     */
    public void eliminerDecors(Decors decors) {
        this.decors.remove(decors);
    }

    /**
     * Obtenir les decors
     * @return
     */
    public ArrayList<Decors> getDecors() {
        return decors;
    }

    /**
     * Dessine des decors
     * @param g
     */
    public void dessiner(Graphics g) {
        for (Decors decors: decors) {
            g.drawImage(img, decors.getPosition(), decors.getHauteur(), width, height, null);
        }
    }

    /**
     * Change la position des decors
     * @param position
     */
    public void setPosition(int position){
        for (Decors decors: decors)
            decors.setPosition(position);
    }
}