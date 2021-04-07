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

                // générer aléatoirement les adversaires
                if (new Random().nextInt(150) == 10) {
                    System.out.println("hello");
                    new Thread(new Adversaire(affichage)).start();
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

                etat.setAcceleration();
                etat.setVitesse();
                etat.getPiste().setPosition(etat.getVitesse());
                etat.setScore();


                // Si le véhicule s’éloigne de la piste, il va perdre de la vitesse

                if (etat.getY_veh() == Etat.Y_ciel) {
                    System.out.println("vitesse: " + etat.getVitesse());
                    etat.setVitesse(-1);
                    // Il redescend tout seul vers le sol lorsqu’il perd de la vitesse.
                    if (etat.getVitesse() < 8) {
                        etat.setY_veh(Affichage.HAUT_VEH);
                        etat.getOmbre().setWidth(Affichage.WIDTH - etat.getOmbre().getWidth());
                        etat.getOmbre().setX(etat.getPosition() - etat.getOmbre().getX());
                    }
                    System.out.println("vitesse ------------- :" + etat.getVitesse());
                }

                etat.move();

                etat.collision(); // détection de la collision
                affichage.revalidate(); //forcer le dessin
                affichage.repaint();
                Thread.sleep(100); // Mettre une pause de quelques millisecondes entre chaque avance
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        etat.gameOver();
        JOptionPane.showMessageDialog(null, "Score : "+ etat.getScore() + "\nBest Score : " +  etat.getBestScore(), "Terminer", -1);
    }
}
