package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Bienvenus extends JPanel {

    /** Largeur de la fenêtre */
    public static final int LARG = 1000;
    /** Hauteur de la fenêtre */
    public static final int HAUT = 700;

    private JLabel playbutton;
    private boolean Commoncer;
    private ScheduledExecutorService service ;

    /**
     * Constructeur
     * @param
     */
    public Bienvenus() {
        //Definit la dimension de la fenêtre
        setPreferredSize(new Dimension(LARG,HAUT));
        setLayout(null);
        Commoncer = false;

        ImageIcon playImage = new ImageIcon("src/image/button_play.png");

        playbutton = new JLabel(playImage);
        playbutton.setOpaque(false);
        playbutton.setBounds((LARG - playImage.getIconWidth())/2, (HAUT - playImage.getIconHeight()) / 2, playImage.getIconWidth(), playImage.getIconHeight());
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
    public void paint(Graphics g) {
        super.paint(g);
    }

    public boolean getCommoncer() {
        return Commoncer;
    }
}