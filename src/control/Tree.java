package control;

import model.Piste;
import view.Affichage;
import view.VueNuage;
import view.VueTree;

import java.awt.*;
import java.util.Random;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class Tree implements Runnable {

    // la hauteur de l'arbre
    private int hauteur;
    // l’abscisse de l'arbre
    private int position;

    private double distance;

    private Affichage affichage;

    public Tree(Affichage affichage) {

        // Le hauteur est fixée de manière à ce que l'arbre soit apparu de l'horizon.
        this.hauteur = Affichage.HORIZON - (VueTree.HEIGHT_TREE - 4);

        // générer aléatoirement la position de l'arbre
        // Les positions de l'arbre est lié à les positions de la route.
        Point[] points = affichage.getEtat().getPiste().getPoints();

        double x1 = 0.0, y1 = 0.0, x2 = 0.0, y2 = 0.0, x_piste;
        double k, b;

        for(int i=0;i<points.length - 1;i++){

            if(points[i].y >= Affichage.HORIZON && points[i+1].y <= Affichage.HORIZON){
                //(x1, y1),(x2, y2) sont 2 points sur le côté gauche de la route, y est entre ces deux points
                x1 = points[i].x;
                x2 = points[i+1].x;
                y1 = points[i].y;
                y2 = points[i+1].y;
            }
        }

        k = (y2 - y1) / (x2 -x1);
        b = (x2*y1 - x1*y2)/(x2 - x1);

        x_piste = (Affichage.HORIZON - b)/k;

        do {
            this.position = new Random().nextInt(300) + Affichage.LARG/2 - 150 ;
        }while ( (this.position + VueTree.WIDTH_TREE) > x_piste && this.position < x_piste + Piste.WID_PISTE); // L'abscisse de l'arbre doit être des deux côtés de la route

        this.distance = this.position - x_piste;
        this.affichage = affichage;
        // si on cree une arbre, on l'ajoute dans la liste
        this.affichage.getVueTree().ajouterTree(this);
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position += position;
    }

    @Override
    public void run() {
        boolean flag = true; // pour savoir si l'arbre sort de le fenetre
        while(flag) {
            try {
                Thread.sleep(500);
                // met a jour le hauteur de l'arbre
                this.hauteur += affichage.getEtat().getVitesse();

                // si l'arbre sort de la fenetre, on l'élimine
                if (this.hauteur > Affichage.HAUT) {
                    affichage.getVueTree().eliminerTree(this);
                    flag = false;
                }

                affichage.revalidate(); // forcer le dessin
                affichage.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
