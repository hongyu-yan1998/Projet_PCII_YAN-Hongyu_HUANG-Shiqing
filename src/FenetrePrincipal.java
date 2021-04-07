import control.Avancer;
import control.Conduire;
import model.Etat;
import view.*;

import javax.swing.*;

/**
 * @description： Commencer le jeu!
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class FenetrePrincipal {
    public static void main(String[] args) {

        //un écran d’accueil
        Bienvenus bienvenus = new Bienvenus();

        JFrame fenetre = new JFrame("course de voiture");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fenetre.add(bienvenus);

        fenetre.pack();
        //rendre la fenetre visible
        fenetre.setVisible(true);
        //mettre la fenêtre au centre de l'écran
        fenetre.setLocationRelativeTo(null);


        while (!bienvenus.getCommoncer()){
            System.out.println(" ");
        }


        fenetre.remove(bienvenus);

        //Générer des nuages
        VueNuage vueNuage = new VueNuage();
        //Générer des oiseaux
        VueOiseau vueOiseau = new VueOiseau();
        //Générer des adversaires
        VueAdversaire vueAdversaire = new VueAdversaire();

        VueBarrage vueBarrage = new VueBarrage(50,50);
        VueArbre vueArbre = new VueArbre(40,50);
        Etat etat = new Etat();
        Affichage affichage = new Affichage(etat, vueNuage, vueOiseau, vueAdversaire);
        // Ajouer des decors dans l'etat
        etat.ajouterDecors(vueBarrage);
        etat.ajouterDecors(vueArbre);
        // Ajouter des adversaires
        etat.ajouterVueAdversaires(vueAdversaire);
        Conduire conduire = new Conduire(etat,affichage);

        // Un thread qui permet d'avancer la piste
        new Thread(new Avancer(etat,affichage)).start();

        // JFrame fenetre = new JFrame("course de voiture");
        // fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.add(affichage);
        fenetre.addKeyListener(conduire);
        //une fois que les composants ont été ajoutés, il faut l’assembler
        fenetre.pack();
        //rendre la fenetre visible
        fenetre.setVisible(true);
        //mettre la fenêtre au centre de l'écran
        fenetre.setLocationRelativeTo(null);
    }
}
