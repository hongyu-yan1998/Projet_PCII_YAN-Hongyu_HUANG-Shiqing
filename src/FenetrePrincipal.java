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
        VueNuage vueNuage = new VueNuage();
        VueObstacles vueObstacles = new VueObstacles(50,50);
        VueArbre vueArbre = new VueArbre(40,50);
        Etat etat = new Etat();
        Affichage affichage = new Affichage(etat, vueNuage);
        // Ajouer des decors dans l'etat
        etat.ajouterDecors(vueObstacles);
        etat.ajouterDecors(vueArbre);
        Conduire conduire = new Conduire(etat,affichage);

        // Un thread qui permet d'avancer la piste
        new Thread(new Avancer(etat,affichage)).start();

        JFrame fenetre = new JFrame("course de voiture");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
