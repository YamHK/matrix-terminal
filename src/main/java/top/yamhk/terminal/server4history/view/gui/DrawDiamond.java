package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class DrawDiamond {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(250, 150);
        Graphics g = panel.getGraphics();
        drawDiamond(g, 0, 0);
        drawDiamond(g, 50, 50);
        drawDiamond(g, 100, 0);

    }

    private static void drawDiamond(Graphics g, int x, int y) {
        // TODO Auto-generated method stub
        g.drawRect(x, y, 50, 50);
        g.drawLine(x, y + 25, x + 25, y);
        g.drawLine(x + 25, y, x + 50, y + 25);
        g.drawLine(x + 50, y + 25, x + 25, y + 50);
        g.drawLine(x + 25, y + 50, x, y + 25);
    }
}
