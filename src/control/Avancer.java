package control;

import model.Etat;
import model.PointControle;
import view.*;

import javax.swing.*;
import java.util.Random;

/**
 * @description： Animation de la piste et les decors
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
        while (!etat.testPerdu()) {
            try {
                // générer aléatoirement les decors du fond
                if (new Random().nextInt(40) == 10) {
                    new Thread(new Nuage(affichage)).start();
                    new Thread(new Oiseau(affichage)).start();
                }
                // générer aléatoirement les obstacles
                if (new Random().nextInt(30) == 10) {
                    for (VueDecors decors: etat.getVueDecors()) {
                        if (decors instanceof VueBarrage) {
                            new Thread(new Barrage(affichage,decors)).start();
                        }
                    }
                }
                // générer aléatoirement les arbres
                if (new Random().nextInt(30) == 10) {
                    for (VueDecors decors: etat.getVueDecors()) {
                        if (decors instanceof VueArbre) {
                            new Thread(new Arbre(affichage,decors)).start();
                        }
                    }
                }
                //Déterminer si le point de contrôle atteint la plage visible de la fenêtre
                if((etat.getPiste().getPosition() % PointControle.INTERVALLE >= PointControle.INTERVALLE - (Affichage.HAUT - Affichage.HORIZON + etat.getVitesse())
                        && etat.getPiste().getPosition() % PointControle.INTERVALLE < PointControle.INTERVALLE - (Affichage.HAUT - Affichage.HORIZON)))
                    etat.getPiste().NouveauPoint(Affichage.HAUT- (PointControle.INTERVALLE - etat.getPiste().getPosition() % PointControle.INTERVALLE));

                //Mettre à jour les données du point de contrôle si le point de contrôle est dans la plage visible de la fenêtre
                if(etat.getPiste().getPointControle().getY() > Affichage.HORIZON - etat.getVitesse())
                    etat.getPiste().getPointControle().setY(etat.getPiste().getPointControle().getY() + etat.getVitesse());

                etat.getPiste().setPosition(etat.getVitesse());

                etat.collision(); // détection de la collision
                affichage.revalidate(); //forcer le dessin
                affichage.repaint();
                Thread.sleep(100); // Mettre une pause de quelques millisecondes entre chaque avance
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "Score : "+ etat.getPoint(), "Terminer", -1);
    }
}
