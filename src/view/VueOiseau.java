package view;

import control.Nuage;
import control.Oiseau;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/27
 */
public class VueOiseau extends JPanel {
    /** Largeur du nuage */
    public static final int WIDTH_OISEAU = 100;
    /** Hauteur du nuage */
    public static final int HEIGHT_OISEAU = 100;
    private CopyOnWriteArrayList<Oiseau> oiseaux = new CopyOnWriteArrayList<>();

    /**
     * Ajouter un oiseau dans la liste
     * @param oiseau
     */
    public void ajouterOiseau(Oiseau oiseau) {
        this.oiseaux.add(oiseau);
    }

    /**
     * Retirer un oiseau
     */
    public void eliminerOiseau(Oiseau oiseau) {
        this.oiseaux.remove(oiseau);
    }

    /**
     * Dessine des oiseaux
     * @param g
     */
    public void dessiner(Graphics g) {
        for (Oiseau o: oiseaux) {
            Image img = new ImageIcon("src/image/oiseau/"+ o.getEtat()+".png").getImage();
            g.drawImage(img, o.getPosition(), o.getHauteur(),80,80, null);
        }
    }
}

