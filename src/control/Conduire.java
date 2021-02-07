package control;

import model.Etat;
import view.Affichage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/1
 */
public class Conduire implements KeyListener {
    private Etat etat;
    private Affichage affichage;

    /**
     * Constructeur
     * @param etat
     * @param affichage
     */
    public Conduire(Etat etat, Affichage affichage) {
        this.etat = etat;
        this.affichage = affichage;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Un mécanisme de contrôle au clavier pour déplacer le véhicule à droite et à gauche
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Renvoie le code de caractère appuyé sur le clavier
        int code = e.getKeyCode();
        //Quand on appuie sur le bouton gauche, la voiture se déplace vers la gauche
        if (code == KeyEvent.VK_LEFT) {
            etat.left();
        }
        //Quand on appuie sur le bouton droit, la voiture se déplace vers la droite
        else if (code == KeyEvent.VK_RIGHT) {
            etat.right();
        }
        affichage.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
