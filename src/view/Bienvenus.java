package view;

import model.Etat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * @description Construire l'écran d’accueile
 * @author: Hongyu YAN and Shiqing HUANG
 * @date: 2021/4/1
 */
public class Bienvenus extends JPanel {

    private JLabel playbutton;
    private boolean Commoncer;
    private ScheduledExecutorService service ;


    /**
     * Constructeur
     * @param
     */
    public Bienvenus() {
        //Definit la dimension de la fenêtre
        setPreferredSize(new Dimension(Affichage.LARG, Affichage.HAUT));
        setLayout(null);
        Commoncer = false;

        ImageIcon playImage = new ImageIcon("src/image/button_play.png");

        playbutton = new JLabel(playImage);
        playbutton.setOpaque(false);
        playbutton.setBounds((Affichage.LARG - playImage.getIconWidth())/2, (Affichage.HAUT - playImage.getIconHeight()) / 2 + 50, playImage.getIconWidth(), playImage.getIconHeight());
        playbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playbutton.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playbutton.setCursor(Cursor.getDefaultCursor());
                Commoncer = true;

                removeAll();
                repaint();
                service.shutdown();
                if(!service.isShutdown())
                    service.shutdownNow();
                service = null;
            }
        });
        add(playbutton, JLayeredPane.DRAG_LAYER);

        service = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Dessine dans la fenêtre
     * @param g
     */
    @Override
    /*public void paint(Graphics g) {
        //super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        super.paint(g2);
        g2.drawImage(new ImageIcon("image/bg_day.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

    }*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/image/bg.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(new ImageIcon("src/image/vehicule.png").getImage(), (Affichage.LARG - Affichage.WIDTH) /2 , Affichage.HAUT_VEH, Affichage.WIDTH, Affichage.HEIGHT, this);
        g.drawImage(new ImageIcon("src/image/titre.png").getImage(), 50, Affichage.HORIZON - 150, 600, 150, this);


    }

    public boolean getCommoncer() {
        return Commoncer;
    }


}