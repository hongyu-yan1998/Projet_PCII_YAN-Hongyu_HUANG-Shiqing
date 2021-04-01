package control;

import view.*;

import java.util.Random;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/27
 */
public class Oiseau implements Runnable{
    /** la valeur de l’incrément de la position */
    public static final int AVANCE = 5;
    // le temps (en millisecondes) entre chaque mise à jour de l’affichage pour l’oiseau
    private int delai;
    // position de l’oiseau
    private int etat;
    // la hauteur de l’oiseau
    private int hauteur;
    // l’abscisse de l’oiseau
    private int position;
    private Affichage affichage;

    public Oiseau(Affichage affichage) {
        // générer aléatoirement le temps entre chaque affichage de l'oiseau
        this.delai = new Random().nextInt(300) + 200;
        this.etat = 0;
        // générer aléatoirement le hauteur de l'oiseau
        this.hauteur = new Random().nextInt(Affichage.HORIZON - VueOiseau.HEIGHT_OISEAU);
        this.position = Affichage.LARG;
        this.affichage = affichage;
        // si on cree un oiseau, on l'ajoute dans la liste
        this.affichage.getVueOiseau().ajouterOiseau(this);
    }

    public int getEtat() {
        return etat;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getPosition() {
        return position;
    }

    public void setEtat() {
        if(this.etat == 5)
            this.etat = 0;
        else
            this.etat ++;
    }

    @Override
    public void run() {
        boolean flag = true; // pour savoir si l'oiseau sort de le fenetre
        while(flag) {
            try {
                Thread.sleep(delai);
                // met a jour la position de l'oiseau
                this.position -= AVANCE;
                // met a jour l'etat de l'oiseau
                this.setEtat();
                // si l'oiseau sort de la fenetre, on l'élimine
                if (this.position + VueOiseau.WIDTH_OISEAU < 0) {
                    affichage.getVueOiseau().eliminerOiseau(this);
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
