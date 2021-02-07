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
    private ArrayList<Point> points = new ArrayList<>();

    /**
     * Constructeur
     */
    public Piste() {
        //Déterminez la position initiale de la ligne
        int x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
        int y = Affichage.HAUT/3;
        this.points.add(new Point(x,y));
        while (y <= Affichage.HAUT) {
            x = new Random().nextInt(50) + Affichage.LARG/2 - 50;
            //y est plus grand que l'ancien point
            y += new Random().nextInt(30) + 30;
            this.points.add(new Point(x,y));
        }
    }

    /**
     * renvoie un tableau d'une copie de la piste
     * @return
     */
    public Point[] getPoints() {
        Point[] res = new Point[points.size()];
        int i = 0;
        for (Point p: points) {
            res[i] = new Point(p);
            i++;
        }
        return res;
    }

}
