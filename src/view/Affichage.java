package view;

import model.Etat;
import model.Piste;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    public static final int WIDTH = 50;
    /** Largeur du véhicule */
    public static final int HEIGHT = 50;
    /** Hauteur du véhicule */
    public static final int HAUT_VEH = HAUT - 50;
    /** Hauteur de l'horizon */
    public static final int HORIZON = HAUT/3;

    private Etat etat;
    private VueNuage vueNuage;
    private VueOiseau vueOiseau;

    /**
     * Constructeur
     * @param etat
     */
    public Affichage(Etat etat, VueNuage vueNuage, VueOiseau vueOiseau) {
        this.etat = etat;
        this.vueNuage = vueNuage;
        this.vueOiseau = vueOiseau;
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
     * Obtenir les oiseaux
     * @return
     */
    public VueOiseau getVueOiseau() {
        return vueOiseau;
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
        //change la couleur de la terre
        setGroundColor(g2);
        //dessine la piste
        paintLine(g2);
        //dessine le point de controle


        g2.drawImage(etat.getPiste().getPointControle().getImg(), (int)etat.getPiste().getPointControle().getX() + Piste.WID_PISTE, (int)etat.getPiste().getPointControle().getY() - 45, 35, 45, null);
        g2.draw(new Line2D.Double(etat.getPiste().getPointControle().getX(), etat.getPiste().getPointControle().getY(), etat.getPiste().getPointControle().getX()+Piste.WID_PISTE, etat.getPiste().getPointControle().getY()));
        //change la couleur du background
        setFondColor(g2);

        //afficher le score du joueur
        //g.drawString("Score: " + etat.getPoint(), LARG - 100, 20);

        //afficher la vitesse du véhicule
        //g.drawString("Vitesse: " + (int)(etat.getVitesseV()*5) + "km/h", LARG - 150, 40);

        //afficher le temps restant
        /*
        if(!etat.testPerdu())
            etat.getPiste().setTempsres();
        g.drawString("Temps restant: " + etat.getPiste().getTempsres() , LARG - 150, 60);
         */
        //dessine le véhicule
        g2.drawImage(etat.getImg(), etat.getPosition(), HAUT_VEH, WIDTH, HEIGHT, null);
        //dessine les nuages
        vueNuage.dessiner(g2);
        //dessine les oiseaux
        vueOiseau.dessiner(g2);
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
        g.fillRect(0,0, LARG, HORIZON); //Définir la position et la taille de remplissage
        g.setColor(c); // remettre la couleur d'avant
    }

    public void setGroundColor(Graphics g) {
        Color c = g.getColor(); // Enregistrer la couleur actuelle pour la récupération
        g.setColor(Color.decode("#90E2B2")); //Changer la couleur
        g.fillRect(0, HORIZON, LARG,HAUT-HORIZON); //Définir la position et la taille de remplissage
        g.setColor(c); // remettre la couleur d'avant
    }

    /**
     * Dessine la piste en courbe de Bézier
     * @param g
     */

    public void paintLine(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        QuadCurve2D courbe = new QuadCurve2D.Double();
        Point2D[] points = etat.getPiste().getPoints();

        // Dessine une cube avec 3 points
        // le premier point d'extrémité
        double debut0_x = points[0].getX() + 30;
        double debut0_y = points[0].getY() + 30;
        // le deuxième point d'extrémité
        double fin0_x = (points[0].getX() + points[1].getX())*1.0/2;
        double fin0_y = (points[0].getY() + points[1].getY())*1.0/2;

        courbe.setCurve(new Point2D.Double(debut0_x, debut0_y),
                new Point2D.Double(points[0].getX(), points[0].getY()),
                new Point2D.Double(fin0_x,fin0_y));
        g2.draw(courbe);

        courbe.setCurve(new Point2D.Double(debut0_x + Piste.WID_PISTE, debut0_y),
                new Point2D.Double(points[0].getX() + Piste.WID_PISTE, points[0].getY()),
                new Point2D.Double(fin0_x + Piste.WID_PISTE,fin0_y));
        g2.draw(courbe);

        for (int i = 0; i < points.length - 2; i++) {
            double debut_x = (points[i].getX() + points[i+1].getX())*1.0/2;
            double debut_y = (points[i].getY() + points[i+1].getY())*1.0/2;
            double fin_x = (points[i+1].getX() + points[i+2].getX())*1.0/2;
            double fin_y = (points[i+1].getY() + points[i+2].getY())*1.0/2;
            Point2D.Double debut = new Point2D.Double(debut_x, debut_y);
            Point2D.Double ctrl = new Point2D.Double(points[i+1].getX(), points[i+1].getY());
            Point2D.Double fin = new Point2D.Double(fin_x,fin_y);
            courbe.setCurve(debut,ctrl,fin);
            g2.draw(courbe);

            //Générer une route avec largeur Piste.WID_PISTE
            Point2D.Double debut2 = new Point2D.Double(debut_x + Piste.WID_PISTE, debut_y);
            Point2D.Double ctrl2 = new Point2D.Double(points[i+1].getX() + Piste.WID_PISTE, points[i+1].getY());
            Point2D.Double fin2 = new Point2D.Double(fin_x + Piste.WID_PISTE,fin_y);
            courbe.setCurve(debut2,ctrl2,fin2);
            g2.draw(courbe);
        }
    }

    /*
    public void paintLine(Graphics g) {
        // Width de la piste
        Graphics2D g2 = (Graphics2D)g;
        int widthPiste = Piste.WID_PISTE;
        Point2D[] points = etat.getPiste().getPoints();
        for (int i = 0; i < points.length - 1; i++) {
            g2.draw(new Line2D.Double(points[i].getX(),
                    points[i].getY(),
                    points[i+1].getX(),
                    points[i+1].getY()));
            //Générer une route avec largeur widthPiste
            g2.draw(new Line2D.Double(points[i].getX() + widthPiste,
                    points[i].getY(),
                    points[i+1].getX() + widthPiste,
                    points[i+1].getY()));

            //Élargissez la route près de chez vous
            //widthPiste -= 6;
        }
    }

     */

    public Etat getEtat() {
        return etat;
    }
}
