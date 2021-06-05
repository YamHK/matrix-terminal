package top.yamhk.terminal.server4history.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    private static final long serialVersionUID = -7049917853227972449L;

    public MyFrame() {
        this.setTitle("我的面板");
        this.setBounds(111, 111, 400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        RectPanel panel = new RectPanel();
        frame.add(panel);
    }
}

class RectPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = -7049917853227972449L;
    private final Point p1;
    private final Point p2;
    private int dx;
    private int dy;

    public RectPanel() {

        p1 = new Point(20, 40);
        p2 = new Point(60, 10);
        dx = 5;
        dy = 5;
        Timer timer = new Timer(55, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(p1.x, p1.y, 70, 30);
        g.setColor(Color.BLUE);
        g.fillRect(p2.x, p2.y, 20, 80);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        p1.x += dx;
        p2.y += dy;
        if (p1.x <= 0 || p1.x + 70 >= getWidth()) {
            dx *= -1;
        }
        if (p2.y <= 0 || p2.y + 80 >= getHeight()) {
            dy *= -1;
        }
        repaint();
    }
}