package view;

import javax.swing.*;
import java.awt.*;

/**
 * @description： obstacles qui ralentissent le véhicule
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/3/8
 */
public class VueBarrage extends VueDecors {
    /**
     * Constructeur
     * @param width
     * @param height
     */
    public VueBarrage(int width, int height) {
        super(width, height);
        setImg(new ImageIcon("src/image/barrage.png").getImage());
    }
}
