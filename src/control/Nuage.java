package control;

import view.Affichage;
import view.VueNuage;

import java.util.Random;

/**
 * @description： Animation des nuages
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class Nuage implements Runnable {
    /** la valeur de l’incrément de la position */
    public static final int AVANCE = 5;
    // le temps (en millisecondes) entre chaque mise à jour de l’affichage pour le nuage
    private int delai;
    // la hauteur du nuage
    private int hauteur;
    // l’abscisse du nuage
    private int position;

    private Affichage affichage;

    public Nuage(Affichage affichage) {
        // générer aléatoirement le temps entre chaque affichage du nuage
        this.delai = new Random().nextInt(500) + 100;
        // générer aléatoirement le hauteur du nuage
        this.hauteur = new Random().nextInt(Affichage.HORIZON - VueNuage.HEIGHT_NUAGE);
        // La position est fixée de manière à ce que le nuage soit complètement à droite, au delà de la fenêtre visible.
        this.position = Affichage.LARG;

        this.affichage = affichage;
        // si on cree un nuage, on l'ajoute dans la liste
        this.affichage.getVueNuage().ajouterNuage(this);
    }

    /**
     * Obtenir le hauteur du nuage
     * @return
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Obtenir la position du nuage
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Un mécanisme de contrôle du mouvement des nuages
     */
    @Override
    public void run() {
        boolean flag = true; // pour savoir si le nuage sort de le fenetre
        while(flag) {
            try {
                Thread.sleep(delai);
                // met a jour la position de l'oiseau
                this.position -= AVANCE;
                // si le nuage sort de la fenetre, on l'élimine
                if (this.position + VueNuage.WIDTH_NUAGE < 0) {
                    affichage.getVueNuage().eliminerNuage(this);
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
