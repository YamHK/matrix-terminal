package top.yamhk.terminal.server4history.view.gui;

import java.awt.*;

public class MainDrawLine {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        DrawingPanel panel = new DrawingPanel(200, 100);
        Graphics g = panel.getGraphics();
        g.drawLine(25, 75, 175, 25);
    }

}
