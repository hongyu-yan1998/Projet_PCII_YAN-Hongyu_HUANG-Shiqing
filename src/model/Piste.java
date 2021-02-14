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
    //la valeur de l’incrément de la position
    public static final int AVANCE = 10;

    private int position;
    private ArrayList<Point> points = new ArrayList<>();

    /**
     * Constructeur
     */
    public Piste() {
        position = 0;
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
    public void setPosition() {
        this.position += AVANCE;
    }

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
        if (res[res.length - 1].y > Affichage.HORIZON) {
            int x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
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
        int widthPiste = 40;
        //Pour que la piste ne dépasse pas le bord droit de la fenetre;
        //Si il y a un point arrivé au bord droit, alors la piste entier ne peut plus déplacer.
        boolean flag = true;
        for (Point p: points) {
            if (p.x + widthPiste + Etat.DEPLACE > Affichage.LARG) {
                flag = false;
            }
            widthPiste += 5;
        }
        widthPiste = 40;
        for (Point p: points) {
            if (p.x + widthPiste + Etat.DEPLACE <= Affichage.LARG && flag) {
                p.x += Etat.DEPLACE;
            }
            widthPiste += 5;
        }
    }

    /**
     * La piste se déplace vers la gauche
     */
    public void right() {
        //Pour que la piste ne dépasse pas le bord gauche de la fenetre;
        //Si il y a un point arrivé au bord droit, gauche la piste entier ne peut plus déplacer.
        boolean flag = true;
        for (Point p: points) {
            if (p.x - Etat.DEPLACE < 0) {
                flag = false;
            }
        }

        for (Point p: points) {
            if (p.x - Etat.DEPLACE >= 0 && flag) {
                p.x -= Etat.DEPLACE;
            }
        }
    }



}
