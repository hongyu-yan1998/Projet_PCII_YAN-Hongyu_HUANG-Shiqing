package view;

import model.Etat;
import model.Piste;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

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
    /** Longeur du véhicule */
    public static final int WIDTH = 40;
    /** Largeur du véhicule */
    public static final int HEIGHT = 80;
    /** Hauteur du véhicule */
    public static final int HAUT_VEH = HAUT - 100;
    /** Hauteur de l'horizon */
    public static final int HORIZON = HAUT/3;

    private Etat etat;
    private VueNuage vueNuage;
    //private VueDecors vueDecors;
    //private ArrayList<VueDecors> vueDecors;

    /**
     * Constructeur
     * @param etat
     */
    public Affichage(Etat etat, VueNuage vueNuage) {
        this.etat = etat;
        this.vueNuage = vueNuage;
        //this.vueDecors = new ArrayList<>();
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
        //dessine le point de controle
        g2.draw(new Line2D.Double(etat.getPiste().getPointControle().getX(), etat.getPiste().getPointControle().getY() - etat.getVitesse(), etat.getPiste().getPointControle().getX()+Piste.WID_PISTE, etat.getPiste().getPointControle().getY()- etat.getVitesse()));
        //change la couleur du background
        setFondColor(g2);
        //afficher le score du joueur
        g.drawString("Score: " + etat.getPiste().getPosition(), LARG - 100, 20);
        //afficher le temps restant
        g.drawString("Temps restant: " + (etat.getPiste().temps - (System.currentTimeMillis() - Piste.DEBUT)/1000), LARG - 120, 40);
        //dessine le véhicule
        g2.drawImage(etat.getImg(), etat.getPosition(), HAUT_VEH,WIDTH,HEIGHT, null);
        //dessine les nuages
        vueNuage.dessiner(g2);
        //dessine les decors
        for (VueDecors d: etat.getVueDecors()) {
            d.dessiner(g2);
        }
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
