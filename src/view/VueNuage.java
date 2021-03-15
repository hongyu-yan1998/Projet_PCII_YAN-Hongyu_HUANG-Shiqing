package view;

import control.Nuage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @description： Affichage des nuages
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class VueNuage {
    /** Largeur du nuage */
    public static final int WIDTH_NUAGE = 100;
    /** Hauteur du nuage */
    public static final int HEIGHT_NUAGE = 100;

    private ArrayList<Nuage> nuages = new ArrayList<>();

    /**
     * Ajouter un nuage dans la liste
     * @param nuage
     */
    public void ajouterNuage(Nuage nuage) {
        this.nuages.add(nuage);
    }

    /**
     * Retirer un nuage
     */
    public void eliminerNuage(Nuage nuage) {
        this.nuages.remove(nuage);
    }

    /**
     * Dessine des nuages
     * @param g
     */
    public void dessiner(Graphics g) {
        for (Nuage nuage: nuages) {
            Image img = new ImageIcon("src/image/nuage.png").getImage();
            g.drawImage(img, nuage.getPosition(), nuage.getHauteur(), WIDTH_NUAGE, HEIGHT_NUAGE, null);
        }
    }
}
