package model;

import view.Affichage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @description: Générer la decoration
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */

public class Decors {
    /** Largeur de l'arbre */
    public static final int WIDTH_TREE = 50;
    /** Hauteur de l'arbre */
    public static final int HEIGHT_TREE = 50;
    private ArrayList<Point> tree = new ArrayList<>();
    private Piste piste;

    /**
     * Constructeur
     */
    public Decors(Piste piste){

        this.piste = piste;
        //int y = new Random().nextInt(Affichage.HAUT-Affichage.HAUT/3-100)+Affichage.HAUT/3;
        int y = new Random().nextInt(Affichage.HAUT/2 - Affichage.HORIZON) + Affichage.HORIZON;
        ajouterTree(y);

        while (y <= Affichage.HAUT - 100) {
            y += new Random().nextInt(HEIGHT_TREE) + HEIGHT_TREE;
            ajouterTree(y);
        }

    }

    /**
     * Ajouter une arbre
     */
    public void ajouterTree(int y){

        Point[] points = piste.getPoints();

        double x1 = 0.0, y1 = 0.0, x2 = 0.0, y2 = 0.0, x_piste;
        double xd1 = 0.0, yd1 = 0.0, xd2 = 0.0, yd2 = 0.0, x_dpiste;
        double k, b;
        double kd, bd;
        int x;

        int widthPiste = 40;

        for(int i=0;i<points.length - 1;i++){
            if(points[i].y <= y && points[i+1].y >= y){
                //(x1, y1),(x2, y2) sont 2 points sur le côté gauche de la route, y est entre ces deux points
                x1 = (double)points[i].x;
                x2 = (double)points[i+1].x;
                y1 = (double)points[i].y;
                y2 = (double)points[i+1].y;

                //(xd1, yd1),(xd2, yd2) sont 2 points sur le côté droit de la route,
                xd1 = (double)points[i].x + widthPiste;
                xd2 = (double)points[i+1].x + widthPiste+5;
                yd1 = (double)points[i].y;
                yd2 = (double)points[i+1].y;
            }
            widthPiste += 5;
        }

        k = (y2 - y1) / (x2 -x1);
        b = (x2*y1 - x1*y2)/(x2 - x1);

        kd = (yd2 - yd1) / (xd2 -xd1);
        bd = (xd2*yd1 - xd1*yd2)/(xd2 - xd1);

        x_piste = (y - b)/k;
        x_dpiste = (y - bd)/kd;

        do {
            x = new Random().nextInt(300) + Affichage.LARG/2 - 150 ;

        }while ( (x + WIDTH_TREE) > x_piste && x < x_dpiste); // L'abscisse de l'arbre doit être des deux côtés de la route

        this.tree.add(new Point(x,y));
    }

    /**
     * renvoie un tableau d'une copie de la decoration
     * @return
     */
    public Point[] getTree() {

        Point[] res = new Point[tree.size()];
        int i = 0;
        for (Point p: tree) {
            res[i] = new Point(p.x, p.y + piste.getPosition());
            i++;
        }

        // générer un point supplémentaire Lorsque le dernier point depasse l'horizon
        if (res[res.length - 1].y > Affichage.HORIZON) {
            int y = tree.get(tree.size()-1).y - 100;
            ajouterTree(y);
        }

        // retirer le premier point lorsque le deuxième point sur de la zone visible
        if (res[1].y >= Affichage.HAUT) {
            tree.remove(0);
        }

        return res;
    }

    /**
     * La décoration se déplace vers la droite
     */
    public void left() {
        Point[] points = piste.getPoints();
        int widthPiste = 40;
        //Pour que la décoration ne dépasse pas le bord droite de la fenetre;
        //Si il y a un point arrivé au bord droit, alors la décoration ne peut plus déplacer.
        boolean flag = true;
        for (Point p: points) {

            if (p.x + widthPiste + Etat.DEPLACE > Affichage.LARG) {
                flag = false;
            }
            widthPiste += 5;
        }

        widthPiste = 40;

        for (Point p: tree) {
            if (flag) {
                p.x += Etat.DEPLACE;
            }
            widthPiste += 5;
        }
    }

    /**
     * La décoration se déplace vers la gauche
     */
    public void right() {
        //Pour que la décoration ne dépasse pas le bord gauche de la fenetre;
        //Si il y a un point arrivé au bord gauche, alors la décoration ne peut plus déplacer.
        Point[] points = piste.getPoints();
        boolean flag = true;
        for (Point p: points) {
            if (p.x - Etat.DEPLACE < 0) {
                flag = false;
            }
        }

        for (Point p: tree) {
            if (flag) {
                p.x -= Etat.DEPLACE;
            }
        }
    }
}
