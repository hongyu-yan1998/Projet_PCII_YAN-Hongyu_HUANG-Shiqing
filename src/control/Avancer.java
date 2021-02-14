package control;

import model.Etat;
import view.Affichage;

import java.util.Random;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class Avancer implements Runnable {
    private Etat etat;
    private Affichage affichage;

    /**
     * Constructeur
     * @param etat
     * @param affichage
     */
    public Avancer(Etat etat, Affichage affichage) {
        this.etat = etat;
        this.affichage = affichage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (new Random().nextInt(20) == 10) {
                    new Thread(new Nuage(affichage)).start();
                }
                Thread.sleep(500); // Mettre une pause de quelques millisecondes entre chaque avance
                etat.getPiste().setPosition();
                affichage.revalidate(); //forcer le dessin
                affichage.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
