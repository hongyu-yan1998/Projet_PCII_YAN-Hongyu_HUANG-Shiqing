import control.Conduire;
import model.Etat;
import model.Piste;
import view.Affichage;

import javax.swing.*;

/**
 * @description： Commencer le jeu!
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class FenetrePrincipal {
    public static void main(String[] args) {
        Etat etat = new Etat();
        Affichage affichage = new Affichage(etat);
        Conduire conduire = new Conduire(etat,affichage);


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
