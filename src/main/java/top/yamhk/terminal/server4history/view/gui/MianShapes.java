package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class MianShapes {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(200, 100);
        panel.setBlackground(Color.CYAN);

        Graphics g = panel.getGraphics();
        g.drawRect(25, 50, 20, 20);
        // g.drawRect ( 150 , 10 , 40 , 20 );
        g.fillRect(150, 10, 40, 20);

        g.setColor(Color.WHITE);
        g.drawOval(50, 25, 20, 20);
        // g.drawOval ( 150 , 50 , 40 , 20 );
        g.fillOval(150, 50, 40, 20);
    }
}
