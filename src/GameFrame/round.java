package GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class round extends JPanel {
    private GamePage gamePage;

    public round(GamePage gamePage){
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.gamePage = gamePage;

        setLayout(null);
        this.setSize(420,630);
        this.setLocation(0,0);
        this.setOpaque(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(String.format("Round: %d",gamePage.getChessboard().getRound()/2),210,500);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED){
            repaint();
        }
    }
}
