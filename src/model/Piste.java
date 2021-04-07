package model;

import view.Affichage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * @description： Générer la route
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Piste {
    // Largeur de la piste
    public static final int WID_PISTE = 100;
    // Heure de début du jeu
    public static long DEBUT;

    //temps restant
    private long tempsres;
    private double position;
    private double vitesse;
    private ArrayList<Point2D> points = new ArrayList<>();

    private PointControle pointControle;

    /**
     * Constructeur
     */
    public Piste() {
        DEBUT = System.currentTimeMillis();
        position = 0;
        //vitesse = 0;
        //Déterminez la position initiale de la piste
        int x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
        int y = Affichage.HAUT;
        this.points.add(new Point2D.Double(x,y));
        while (y >= Affichage.HORIZON) {
            x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
            //y est plus grand que l'ancien point
            y -= new Random().nextInt(30) + 30;
            this.points.add(new Point2D.Double(x,y));
        }
        this.pointControle = new PointControle();
        this.tempsres = pointControle.getTemps();
    }

    /**
     * getter pour récupérer la valeur courante de la position
     * @return
     */
    public double getPosition() {
        return position;
    }

    /**
     * setter pour faire avancer la position de quelques pixels
     */
    public void setPosition(double position) {
        this.position += position;
        this.vitesse = position;

        //Si le joueur franchit les points, on le donner un bonus de temps
        if(this.position % PointControle.INTERVALLE < position){
            pointControle.setTemps();
            //Faiblir le bonus
            pointControle.setBonus(this.position / PointControle.INTERVALLE);
        }
    }

    /**
     * renvoie un tableau de la piste
     * @return
     */
    public Point2D[] getPoints() {
        Point2D[] res = new Point2D[points.size()];
        // les coordonnées ont été calculées en ajoutant la valeur de position à leur valeur d’abscisse.
        int i = 0;

        for (Point2D p: points) {
            res[i] = new Point2D.Double(p.getX(), p.getY() + this.position);
            i++;
        }

        // générer un point supplémentaire Lorsque le dernier point depasse l'horizon
        if (res[res.length - 2].getY() > Affichage.HORIZON) {
            double x = new Random().nextInt(Affichage.LARG);
            double y = points.get(points.size()-1).getY() - 100;
            this.points.add(new Point2D.Double(x,y));
        }

        // retirer le premier point lorsque le deuxième point sur de la zone visible
        if (res[1].getY() >= Affichage.HAUT) {
            points.remove(0);
        }

        return res;
    }

    /**
     * renvoie un tableau de la piste
     * @return
     */
    public Point2D[] getPointsStable() {
        Point2D[] res = new Point2D[points.size()];
        // les coordonnées ont été calculées en retirant la valeur de position à leur valeur d’abscisse.
        int i = 0;
        for (Point2D p: points) {
            res[i] = new Point2D.Double(p.getX(), p.getY());
            i++;
        }
        return res;
    }

    /**
     * Obetenir le temps restant
     * @return
     */
    public long getTempsres() {
        return tempsres;
    }

    public void setTempsres() {
        this.tempsres = pointControle.getTemps() - (System.currentTimeMillis() - Piste.DEBUT)/1000;
    }

    /**
     * La piste se déplace vers la droite
     */
    public void left() {
        //Pour que la piste ne dépasse pas le bord droit de la fenetre;
        //Nous limitons la fenêtre à au moins deux points sur le côté v de la route pour apparaître dans la fenêtre.
        for (Point2D p: points) {
            p.setLocation(p.getX()+Etat.DEPLACE, p.getY());
        }
        //Déplacer le point de contrôle avec la piste
        pointControle.setX(pointControle.getX()+Etat.DEPLACE);
    }

    /**
     * La piste se déplace vers la gauche
     */
    public void right() {
        //Pour que la piste ne dépasse pas le bord gauche de la fenetre;
        //Nous limitons la fenêtre à au moins deux points sur le côté droit de la route pour apparaître dans la fenêtre.
        for (Point2D p: points) {
            p.setLocation(p.getX()-Etat.DEPLACE, p.getY());
        }
        //Déplacer le point de contrôle avec la piste
        pointControle.setX(pointControle.getX() - Etat.DEPLACE);
    }

    /**
     * Si la piste a arrivée au bord gauche
     */
    public boolean BordGauche(){
        int cpt = 0;
        for (Point2D p: points) {
            if (p.getX() + WID_PISTE >= 0) {
                cpt ++;
            }
        }
        //System.out.println(cpt + "**************************" + (cpt >= 2));
        return cpt >= 2 ;
    }

    /**
     * Si la piste a arrivée au bord droit
     */
    public boolean BordDroit(){
        int cpt = 0;
        for (Point2D p: points) {
            if (p.getX() <= Affichage.LARG) {
                cpt ++;
            }
        }
        //System.out.println(cpt + "**************************" + (cpt >= 2));
        return cpt >= 2 ;
    }

    /**
     * Trouvez l'abscisse sur la route selon l'ordonnée
     * @param y
     * @return
     * */
    public double getPointSurPiste(double y){
        double debut0_x = this.points.get(0).getX() + 30;
        double debut0_y = this.points.get(0).getY() + 30;

        Point2D[] pointlist = new Point2D[points.size() + 1];

        // les coordonnées ont été calculées en retirant la valeur de position à leur valeur d’abscisse.
        pointlist[0] = new Point2D.Double(debut0_x, debut0_y);
        int j = 1;
        for (Point2D p : points) {
            pointlist[j] = new Point2D.Double(p.getX(), p.getY() + position);
            j++;
        }
        double x1 = 0.0, y1 = 0.0, x2 = 0.0, y2 = 0.0, x3 = 0.0, y3 = 0.0, t, x_piste;

        for(int i = 0;i<pointlist.length - 2; i++){
//            if(y == Affichage.HORIZON)
//                System.out.println("***" + ((pointlist[i].getY() + pointlist[i+1].getY())*1.0/2) + "***"  + y + "***" +((pointlist[i+1].getY() + pointlist[i+2].getY())*1.0/2));
            if((pointlist[i].getY() + pointlist[i+1].getY())*1.0/2 >= y && (pointlist[i+1].getY() + pointlist[i+2].getY())*1.0/2 <= y){

                //(x1, y1),(x3, y3) sont 2 points sur le côté gauche de la route, y est entre ces deux points
                //(x2, y2) est le point de control
                x1 = (pointlist[i].getX() + pointlist[i+1].getX())*1.0/2;
                y1 = (pointlist[i].getY() + pointlist[i+1].getY())*1.0/2;

                x2 = pointlist[i+1].getX();
                y2 = pointlist[i+1].getY();

                x3 = (pointlist[i+1].getX() + pointlist[i+2].getX())*1.0/2;
                y3 = (pointlist[i+1].getY() + pointlist[i+2].getY())*1.0/2;
                break;
            }
        }

        Point2D p0 = new Point2D.Double(x1, y1);
        Point2D p1 = new Point2D.Double(x2, y2);
        Point2D p2 = new Point2D.Double(x3, y3);

        //Calculez la valeur t de la courbe de Bézier en fonction de la valeur y
        t = computeT(p0, p1, p2, y);
        //Calculer la valeur x de la courbe de Bézier en fonction de la valeur t
        x_piste = getPointOnQuadraticCurve(p0, p1, p2, t);
//        if(y == Affichage.HORIZON)
//            System.out.println("(x1=" + x1 + "y1=" + y1 + ") (x2=" + x2 + "y2=" + y2 + ") (x3=" + x3 + "y3=" + y3 + ") (x=" + x_piste + "y=" + y + ")");
        return x_piste;
    }

    /**
     * Calculez la valeur t de la courbe de Bézier
     * @param p
     * @param p0
     * @param p1
     * @param p2
     * @return
     * */
    public double computeT(Point2D p0, Point2D p1,Point2D p2, double p) {
        //Résolvez une équation quadratique pour trouver t
        double[] tt = equation2(p0.getY(),p1.getY(),p2.getY(),p);
        if(tt[0] != -1 && tt[1] != -1) {
            //Si l'équation a deux solutions,
            // les deux valeurs t sont placées respectivement dans la formule de la courbe de Bézier.
            // Comparez la valeur y obtenue et prenez une solution plus proche de la valeur y du point cible p.
            double pointTest = getPointOnQuadraticCurve(p0, p1, p2, tt[0]);
            if (Math.abs(pointTest - p) < 0.01)
                return tt[0];
            else
                return tt[1];
        }
        else
            return tt[0];
    }

    /**
     * Résolvez des équations quadratiques en une seule variable
     * yP = (1-t)2 yP0 2t(1-t) yP1 t2 * yP2
     * @param yp
     * @param y0
     * @param y1
     * @param y2
     * @return
     * */
    public double[] equation2(double y0, double y1, double y2, double yp){
        double a = y0 - y1 * 2 + y2,
                b = 2*(y1 - y0),
                c = y0 - yp;
        double[] tt = new double[2];

        //Si a = 0, la solution est - c / b
        if(a == 0 && b != 0){
            tt[0] = - c / b;
            tt[1] = - 1;
        } else {
            double sq = Math.sqrt( b * b - 4 * a * c );
            tt[0] = (sq - b)/ (2 * a);
            tt[1] = (-sq - b) / (2 * a);

            //Déterminer si t atteint la limite [0,1]
            if((tt[0] <= 1 && tt[0]>= 0) && (tt[1] <= 1 && tt[1]>= 0)){
                return tt;
            }else if(tt[0] <= 1 && tt[0]>= 0){
                tt[1] = -1;
            }else {
                tt[0] = tt[1];
                tt[1] = -1;
            }
        }
        return tt;
    }

    /**
     * Calculer la valeur x de la courbe de Bézier en fonction de la valeur t
     * @param t
     * @param p0
     * @param p1
     * @param p2
     * @return
     * */
    public double getPointOnQuadraticCurve(Point2D p0, Point2D p1, Point2D p2, double t){
        return  (1-t) * (1-t) * p0.getX() + 2 * t * (1-t) * p1.getX() + t * t * p2.getX();
    }
    /**
     * Mettre à jour les données du point de contrôle
     * @param y
     */
    public void NouveauPoint(double y){
        pointControle.setX(getPointSurPiste(y));
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
