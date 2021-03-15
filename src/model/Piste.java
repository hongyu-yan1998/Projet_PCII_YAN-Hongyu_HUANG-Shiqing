package model;

import view.Affichage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @description： Générer la route
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Piste {
    // Largeur de la piste
    public static final int WID_PISTE = 60;
    // Heure de début du jeu
    public static long DEBUT;

    //temps restant
    public long temps = 30 ;
    private int position;
    private ArrayList<Point> points = new ArrayList<>();

    private PointControle pointControle;
    private int vitesse;

    /**
     * Constructeur
     */
    public Piste() {
        DEBUT = System.currentTimeMillis();
        position = 0;
        vitesse = 0;
        //Déterminez la position initiale de la piste
        int x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
        int y = Affichage.HAUT;
        this.points.add(new Point(x,y));
        while (y >= Affichage.HORIZON) {
            x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
            //y est plus grand que l'ancien point
            y -= new Random().nextInt(30) + 30;
            this.points.add(new Point(x,y));
        }
        this.pointControle = new PointControle();
    }

    /**
     * getter pour récupérer la valeur courante de la position
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * setter pour faire avancer la position de quelques pixels
     */
    public void setPosition(int position) {
        this.vitesse = position;
        this.position += this.vitesse;

        //Si le joueur franchit les points, on le donner un bonus de temps
        //System.out.println("*******" + this.position % PointControle.INTERVALLE + "*********" + Etat.DEPLACE*3);
        if(this.position % PointControle.INTERVALLE < Etat.DEPLACE*3){

            //System.out.println("!!!!!!!!!!!!!!!" + temps);
            temps += pointControle.getBonus();
            //System.out.println("!!!!!!!!!!!!!!!" + temps);

            //Faiblir le bonus
            pointControle.setBonus(this.position / PointControle.INTERVALLE);
        }
    }
    /*
    public void setPosition() {
        this.position += AVANCE;
    }
    */


    /**
     * renvoie une liste de la piste
     * @return
     */
    public Point[] getPoints() {
        Point[] res = new Point[points.size()];
        // les coordonnées ont été calculées en retirant la valeur de position à leur valeur d’abscisse.
        int i = 0;
        for (Point p: points) {
            res[i] = new Point(p.x, p.y + this.position);
            i++;
        }

        // générer un point supplémentaire Lorsque le dernier point depasse l'horizon
        if (res[res.length - 2].y > Affichage.HORIZON) {
            int x = new Random().nextInt(Affichage.LARG);
            int y = points.get(points.size()-1).y - 100;
            this.points.add(new Point(x,y));
        }

        // retirer le premier point lorsque le deuxième point sur de la zone visible
        if (res[1].y >= Affichage.HAUT) {
            points.remove(0);
        }

        return res;
    }

    /**
     * La piste se déplace vers la droite
     */
    public void left() {
        //Pour que la piste ne dépasse pas le bord droit de la fenetre;
        //Si il y a un point arrivé au bord droit, alors la piste entier ne peut plus déplacer.
        for (Point p: points) {
            if (p.x + WID_PISTE + Etat.DEPLACE <= Affichage.LARG) {
                p.x += Etat.DEPLACE;
            }
        }
        //Déplacer le point de contrôle avec la piste
        pointControle.setX(pointControle.getX()+Etat.DEPLACE);
    }

    /**
     * La piste se déplace vers la gauche
     */
    public void right() {
        //Pour que la piste ne dépasse pas le bord gauche de la fenetre;
        //Si il y a un point arrivé au bord droit, gauche la piste entier ne peut plus déplacer.
        for (Point p: points) {
            if (p.x - Etat.DEPLACE >= 0) {
                p.x -= Etat.DEPLACE;
            }
        }
        //Déplacer le point de contrôle avec la piste
        pointControle.setX(pointControle.getX()-Etat.DEPLACE);
    }

    /**
     * Si la piste a arrivée au bord gauche
     */
    public boolean BordGauche(){
        for (Point p: points) {
            if (p.x - Etat.DEPLACE <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Si la piste a arrivée au bord droit
     */
    public boolean BordDroit(){
        for (Point p: points) {
            if (p.x + WID_PISTE + Etat.DEPLACE >= Affichage.LARG) {
                return false;
            }
        }
        return true;
    }

    /**
     * Trouvez l'abscisse sur la route selon l'ordonnée
     * @param y
     * @return
     */
    public double getPointsurHORIZON(double y){
        Point[] pointlist = this.getPoints();
        double x1 = 0.0, y1 = 0.0, x2 = 0.0, y2 = 0.0, x_piste;
        double k, b;

        for(int i =0;i<pointlist.length - 1;i++){

            if(pointlist[i].y >= y && pointlist[i+1].y <= y){
                //(x1, y1),(x2, y2) sont 2 points sur le côté gauche de la route, y est entre ces deux points
                x1 = pointlist[i].x;
                x2 = pointlist[i+1].x;
                y1 = pointlist[i].y;
                y2 = pointlist[i+1].y;
            }
        }

        k = (y2 - y1) / (x2 -x1);
        b = (x2*y1 - x1*y2)/(x2 - x1);

        x_piste = (y - b)/k;
        return x_piste;
    }

    /**
     * Mettre à jour les données du point de contrôle
     * @param y
     */
    public void NouveauPoint(double y){
        pointControle.setX(getPointsurHORIZON(y));
        pointControle.setY(y);
    }

    /**
     * Obtenir l'object PointControle
     * @return
     */
    public PointControle getPointControle() {
        return pointControle;
    }

}
