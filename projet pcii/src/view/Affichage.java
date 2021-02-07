package view;

import model.Etat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @description Construire l'interface du jeu
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Affichage extends JPanel {
    /** Largeur de la fenêtre */
    public static final int LARG = 1000;
    /** Hauteur de la fenêtre */
    public static final int HAUT = 700;
    /** Largeur du véhicule */
    public static final int WIDTH = 100;
    /** Hauteur du véhicule */
    public static final int HEIGHT = 100;

    private Etat etat;

    /**
     * Constructeur
     * @param etat
     */
    public Affichage(Etat etat) {
        this.etat = etat;
        //Definit la dimension de la fenêtre
        setPreferredSize(new Dimension(LARG,HAUT));
    }

    /**
     * Dessine dans la fenêtre
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paint(g2);
        //dessine un horizon
        g2.drawLine(0,HAUT/3,LARG,HAUT*1/3);
        //dessine la piste
        paintLine(g2);
        //dessine le véhicule
        g2.drawImage(etat.getImg(), etat.getPosition(), HAUT-100,WIDTH,HEIGHT, null);
    }

    /**
     * Dessine la ligne brisé(piste)
     * @param g
     */
    public void paintLine(Graphics g) {
        // Width de la piste
        int widthPiste = 40;
        Point[] points = etat.getPiste();
        for (int i = 0; i < points.length - 1; i++) {
            g.drawLine(points[i].x,
                    points[i].y,
                    points[i+1].x,
                    points[i+1].y);
            //Générer une route avec largeur widthPiste
            g.drawLine(points[i].x + widthPiste,
                    points[i].y,
                    points[i+1].x + widthPiste + 5,
                    points[i+1].y);
            //Élargissez la route près de chez vous
            widthPiste += 5;
        }
    }
}
