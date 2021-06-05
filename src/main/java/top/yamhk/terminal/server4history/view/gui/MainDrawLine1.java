package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class MainDrawLine1 {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(200, 100);
        Graphics g = panel.getGraphics();
        g.drawLine(25, 75, 100, 25);
        g.drawLine(100, 25, 175, 75);
        g.drawLine(25, 75, 175, 75);
    }
}
