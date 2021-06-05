package top.yamhk.terminal.server4history.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AnimatedRectPanel extends JPanel implements ActionListener {
    private final Point p1;
    private final Point p2;
    private final Point p3;
    private final Point p4;
    int weight = 22;
    int height = 22;
    int pweight = 555;
    int pheight = 555;
    private int dx;
    private int dy;

    public AnimatedRectPanel() {
        p1 = new Point(pweight / 2, pheight / 2);
        p2 = new Point(pweight / 2, pheight / 2);
        p3 = new Point(pweight / 2, pheight / 2);
        p4 = new Point(pweight / 2, pheight / 2);
        dx = 5;
        dy = 5;
        Timer timer = new Timer(11, this);
        timer.start();

    }

    public static void main(String[] args) {

        AnimatedRectPanel panel = new AnimatedRectPanel();
        panel.setVisible(true);

        JFrame frame = new JFrame();
        frame.setBounds(111, 111, 555, 555);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(p1.x, p1.y, height, weight);
        g.setColor(Color.BLUE);
        g.fillRect(p2.x, p2.y, height, weight);
        g.setColor(Color.YELLOW);
        g.fillRect(p3.x, p3.y, height, weight);
        g.setColor(Color.GREEN);
        g.fillRect(p4.x, p4.y, height, weight);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        p1.x += dx;
        p2.y += dy;
        p3.x -= dx;
        p4.y -= dy;
        if (p1.x <= 0 || p1.x + 11 >= getWidth()) {
            dx *= -1;
        }
        if (p2.y <= 0 || p2.y + 11 >= getHeight()) {
            dy *= -1;
        }
        repaint();
    }
}
