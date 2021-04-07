package control;

import model.Etat;
import view.Affichage;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @description： le déplacement du vehicule
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

        if (!etat.testPerdu()) {
            int code = e.getKeyCode();
            //Quand on appuie sur le bouton gauche, la voiture se déplace vers la gauche
            if (code == KeyEvent.VK_LEFT) {
                etat.setImg("src/image/vehiculeL.png");
                etat.left();
            }
            //Quand on appuie sur le bouton droit, la voiture se déplace vers la droite
            else if (code == KeyEvent.VK_RIGHT) {
                etat.setImg("src/image/vehiculeR.png");
                etat.right();
            }
            else if (code == KeyEvent.VK_UP) {
                etat.up = true;
            }
            affichage.repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            etat.setImg("src/image/vehicule.png");
        }
        //Quand on appuie sur le bouton droit, la voiture se déplace vers la droite
        else if (code == KeyEvent.VK_RIGHT) {
            etat.setImg("src/image/vehicule.png");
        }
        else if (code == KeyEvent.VK_UP) {
            etat.up = false;
        }
    }
}
