package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class DrawColorGradient {
    public static final int RECTS = 32;

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(256, 256);
        panel.setBlackground(new Color(255, 128, 0));//orange
        Graphics g = panel.getGraphics();
        for (int i = 0; i < RECTS; i++) {
            int shift = i * 256 / RECTS;
            g.setColor(new Color(shift, shift, shift));
            g.fillRect(shift, shift, 20, 20);
        }
    }
}
