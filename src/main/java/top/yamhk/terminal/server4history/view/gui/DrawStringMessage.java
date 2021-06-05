package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class DrawStringMessage {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(200, 100);
        panel.setBlackground(Color.YELLOW);

        Graphics g = panel.getGraphics();
        for (int i = 0; i < 8; i++) {
            g.drawString("some like you", i * 5, i * 10 + 10);
        }
    }
}
