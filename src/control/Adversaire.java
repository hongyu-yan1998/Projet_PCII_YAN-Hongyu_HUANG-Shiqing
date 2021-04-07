package control;

import model.Etat;
import model.Piste;
import view.Affichage;
import view.VueAdversaire;

import java.util.Random;

/**
 * @description： Animation des adversaires
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/25
 */

public class Adversaire implements Runnable{
    // la hauteur des adversaire
    private int hauteur;
    // l’abscisse des adversaire
    private int position;

    private Affichage affichage;

    /**
     * Constructeur
     * @param affichage
     * */
    public Adversaire(Affichage affichage) {

        // Le hauteur est fixée de manière à ce que les adversaires soient apparu de l'horizon.
        this.hauteur = Affichage.HORIZON - VueAdversaire.WIDTH_ADV;

        // générer aléatoirement la position du adversaire
        // Les positions de l'adversaire est lié à les positions de la route.
        double x_piste = affichage.getEtat().getPiste().getPointSurPiste(Affichage.HORIZON);

        do {
            this.position = new Random().nextInt(300) + Affichage.LARG/2 - 150 ;

            //L'adversaire doit apparaître au milieu ou près de la route
        }while ( (this.position + VueAdversaire.WIDTH_ADV + 10) < x_piste && this.position > (x_piste + Piste.WID_PISTE + 10));

        //System.out.println((this.position + vueDecors.getWidth()) + "***" + x_piste + "***" + this.position + "***" +(x_piste + Piste.WID_PISTE));
        this.affichage = affichage;
        // si on cree un adversaire, on l'ajoute dans la liste
        this.affichage.getVueAdversaire().ajouterAdversaire(this);
    }

    /**
     * Obtenir l'hauteur de l'adversaire
     * */
    public int getHauteur() {

        return hauteur;
    }

    /**
     * Obtenir la position de l'adversaire
     * */
    public int getPosition() {

        this.position = (int)affichage.getEtat().getPiste().getPointSurPiste(this.hauteur);

//        int n = new Random().nextInt(20);
//        if( n == 10)
//            this.position += Etat.DEPLACE;
//        else if ( n == 1)
//            this.position -= Etat.DEPLACE;

        return position;
    }

    /**
     * setter pour faire avancer la position
     * */
    public void setPosition(int position) {
        this.position += position;
    }

    public void run(){
        boolean flag = true; // pour savoir si l'adversaire sort de le fenetre
        while(flag && !affichage.getEtat().testPerdu()) {
            try {

                // met a jour le hauteur de l'adversaire
                this.hauteur += affichage.getEtat().getVitesse() - 25;

                //System.out.println("!!!!!!!!!!!!!!!!");
                // si l'adversaire sort de la fenetre, on l'élimine
                if (this.hauteur > Affichage.HAUT) {
                    //vueAdversaire.eliminerAdversaire(this);
                    System.out.println("!!!!!!!!!!!!!!!!");
                    this.affichage.getEtat().setBonus(10);
                    this.affichage.getVueAdversaire().eliminerAdversaire(this);

                    flag = false;
                }
                affichage.revalidate(); // forcer le dessin
                affichage.repaint();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
