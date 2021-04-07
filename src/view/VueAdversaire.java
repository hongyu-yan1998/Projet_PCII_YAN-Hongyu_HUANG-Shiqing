package view;

import control.Adversaire;
import control.Decors;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description： Affichage des adversaires
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/25
 */

public class VueAdversaire  {

    private Image img = new ImageIcon("src/image/adversaire.png").getImage();

    /** Longeur du adversaire */
    public static final int WIDTH_ADV = 80;
    /** Largeur du adversaire */
    public static final int HEIGHT_ADV = 60;
    private CopyOnWriteArrayList<Adversaire> adversaires = new CopyOnWriteArrayList<>();
    //private ArrayList<Decors> decors = new ArrayList<>();


    /**
     * Ajouter un adversaire dans la liste
     * @param adversaire
     */
    public void ajouterAdversaire(Adversaire adversaire) {
        this.adversaires.add(adversaire);
    }

    /**
     * Retirer un adversaire
     */
    public void eliminerAdversaire(Adversaire adversaire) {

        this.adversaires.remove(adversaire);
    }

    /**
     * Obtenir les adversaires
     * @return
     */
    public CopyOnWriteArrayList<Adversaire> getAdversaires() {
        return adversaires;
    }

    /**
     * Dessine des decors
     * @param g
     */
    public void dessiner(Graphics g) {
        System.out.println();
        for (Adversaire adversaire: adversaires) {
            if(adversaire.getHauteur() > Affichage.HORIZON - VueAdversaire.HEIGHT_ADV)
                g.drawImage(img, adversaire.getPosition(), adversaire.getHauteur(), WIDTH_ADV, HEIGHT_ADV, null);
//            System.out.println(adversaire.getPosition());
        }
    }

    /**
     * Change la position des adversaires
     * @param position
     */
    public void setPosition(int position){
        for (Adversaire adversaire: adversaires) {
            adversaire.setPosition(position);
            System.out.println(adversaire.getPosition());
        }
    }
}
