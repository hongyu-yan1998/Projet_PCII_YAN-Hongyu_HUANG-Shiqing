package view;

import javax.swing.*;

/**
 * @description：
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/8
 */
public class VueArbre extends VueDecors {
    /**
     * Constructeur
     *
     * @param width
     * @param height
     */
    public VueArbre(int width, int height) {
        super(width, height);
        setImg(new ImageIcon("src/image/tree.png").getImage());
    }
}
