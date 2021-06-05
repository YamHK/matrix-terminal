package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class DrawPyramids {
    public static final int SIZE = 100;

    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(350, 250);
        Graphics g = panel.getGraphics();
        drawPyramids(g, Color.WHITE, 0, 0, 10);
        drawPyramids(g, Color.RED, 80, 140, 5);
        drawPyramids(g, Color.BLUE, 220, 50, 20);
    }

    static void drawPyramids(Graphics g, Color c, int x, int y, int stairs) {
        // TODO Auto-generated method stub
        g.drawRect(x, y, SIZE, SIZE);
        for (int i = 0; i < stairs; i++) {
            int stairsHeight = SIZE / stairs;
            int stairsWidth = stairsHeight * (i + 1);
            int staitsX = x + (SIZE - stairsWidth) / 2;
            int stairsY = y + stairsHeight * i;
            g.setColor(c);
            g.fillRect(staitsX, stairsY, stairsWidth, stairsHeight);
            g.setColor(Color.BLACK);
            g.drawRect(staitsX, stairsY, stairsWidth, stairsHeight);
        }
    }

}
