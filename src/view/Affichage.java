package view;

import model.Etat;
import model.Piste;

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
    /** Hauteur de l'horizon */
    public static final int HORIZON = HAUT/3;

    private Etat etat;
    private VueNuage vueNuage;
    private VueTree vueTree;

    /**
     * Constructeur
     * @param etat
     */
    public Affichage(Etat etat, VueNuage vueNuage, VueTree vueTree) {
        this.etat = etat;
        this.vueNuage = vueNuage;
        this.vueTree = vueTree;
        //Definit la dimension de la fenêtre
        setPreferredSize(new Dimension(LARG,HAUT));
    }

    /**
     * Obtenir les nuages
     * @return
     */
    public VueNuage getVueNuage() {
        return vueNuage;
    }

    /**
     * Obtenir les arbres
     * @return
     */
    public VueTree getVueTree() {
        return vueTree;
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
        g2.drawLine(0,HAUT/3,LARG,HORIZON);
        //dessine la piste
        paintLine(g2);
        //change la couleur du background
        setFondColor(g2);
        //afficher le score du joueur
        //g.drawString("Score: " + etat.getPiste().getPosition(), LARG - 100, 20);
        //dessine le véhicule
        g2.drawImage(etat.getImg(), etat.getPosition(), HAUT-100,WIDTH,HEIGHT, null);
        //dessine les nuages
        vueNuage.dessiner(g2);
        //dessine les arbres
        vueTree.dessiner(g2);
    }

    /**
     * Changer la couleur du ciel (en dessus de l'horizon)
     * @param g
     */
    public void setFondColor(Graphics g) {
        Color c = g.getColor(); // Enregistrer la couleur actuelle pour la récupération
        g.setColor(Color.decode("#87CEEB")); //Changer la couleur
        g.fillRect(0,0,LARG,HORIZON); //Définir la position et la taille de remplissage
        g.setColor(c); // remettre la couleur d'avant
    }

    /**
     * Dessine la ligne brisé(piste)
     * @param g
     */
    public void paintLine(Graphics g) {
        // Width de la piste
        int widthPiste = Piste.WID_PISTE;
        Point[] points = etat.getPiste().getPoints();
        for (int i = 0; i < points.length - 1; i++) {
            g.drawLine(points[i].x,
                    points[i].y,
                    points[i+1].x,
                    points[i+1].y);
            //Générer une route avec largeur widthPiste
            g.drawLine(points[i].x + widthPiste,
                    points[i].y,
                    points[i+1].x + widthPiste,
                    points[i+1].y);

            //Élargissez la route près de chez vous
            //widthPiste -= 6;
        }
    }

    public Etat getEtat() {
        return etat;
    }
}
