package control;

import model.Piste;
import view.Affichage;
import view.VueDecors;

import java.awt.*;
import java.util.Random;

/**
 * @description： Animation des decors
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/8
 */
public class Decors implements Runnable{
    // la hauteur des decors
    private int hauteur;
    // l’abscisse des decors
    private int position;
    //private double distance;
    private VueDecors vueDecors;

    private Affichage affichage;

    public Decors(Affichage affichage, VueDecors vueDecors) {

        this.vueDecors = vueDecors;
        // Le hauteur est fixée de manière à ce que les decors soient apparu de l'horizon.
        this.hauteur = Affichage.HORIZON - vueDecors.getWidth();

        // générer aléatoirement la position du decor
        // Les positions de le decors est lié à les positions de la route.
        double x_piste = affichage.getEtat().getPiste().getPointsurHORIZON(Affichage.HORIZON);

        do {
            this.position = new Random().nextInt(300) + Affichage.LARG/2 - 150 ;
        }while ( (this.position + vueDecors.getWidth()) > x_piste && this.position < (x_piste + Piste.WID_PISTE)); // L'abscisse du decors doit être des deux côtés de la route

        //this.distance = this.position - x_piste;
        this.affichage = affichage;
        // si on cree un decors, on l'ajoute dans la liste
        vueDecors.ajouterDecor(this);
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
        boolean flag = true; // pour savoir si le decors sort de le fenetre
        while(flag && !affichage.getEtat().testPerdu()) {
            try {
                Thread.sleep(100);
                // met a jour le hauteur du decors
                this.hauteur += affichage.getEtat().getVitesse();

                // si le decors sort de la fenetre, on l'élimine
                if (this.hauteur > Affichage.HAUT) {
                    vueDecors.eliminerDecors(this);
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
