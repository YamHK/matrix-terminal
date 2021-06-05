package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class MainDrawLoops {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(200, 110);
        panel.setBlackground(Color.WHITE);

        Graphics g = panel.getGraphics();
        for (int i = 0; i < 4; i++) {
            g.drawRect(i * 50, i * 25, 50, 25);
            g.fillOval(i * 50, i * 25, 50, 25);

            g.drawRect(i * 50, 25, 50, 25);
            g.fillOval(i * 50, 25, 50, 25);
        }
    }
}
