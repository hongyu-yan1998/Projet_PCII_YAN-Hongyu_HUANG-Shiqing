package view;

import control.Nuage;
import control.Tree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/2/8
 */
public class VueTree {
    /** Largeur de l'arbre */
    public static final int WIDTH_TREE = 50;
    /** Hauteur de l'arbre */
    public static final int HEIGHT_TREE = 50;

    private ArrayList<Tree> tree = new ArrayList<>();

    /**
     * Ajouter une arbre dans la liste
     * @param tree
     */
    public void ajouterTree(Tree tree) {
        this.tree.add(tree);
    }

    /**
     * Retirer une arbre
     */
    public void eliminerTree(Tree tree) {
        this.tree.remove(tree);
    }

    /**
     * Dessine des arbres
     * @param g
     */
    public void dessiner(Graphics g) {
        for (Tree tree: tree) {
            //tree.setPosition(100);
            //System.out.println("tree.getHauteur():"+tree.getHauteur() + "tree.getPosition():" + tree.getPosition());
            Image img = new ImageIcon("src/image/tree.png").getImage();
            g.drawImage(img, tree.getPosition(), tree.getHauteur(), WIDTH_TREE, HEIGHT_TREE, null);
        }
    }

    public void setPosition(int position){
        for (Tree tree: tree)
            tree.setPosition(position);
    }
}
