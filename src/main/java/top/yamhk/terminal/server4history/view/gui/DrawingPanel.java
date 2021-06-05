package top.yamhk.terminal.server4history.view.gui;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DrawingPanel extends MouseInputAdapter implements ActionListener {

    private final JFrame frame;
    private final JPanel panel;
    private final JLabel statusBar;
    private final Graphics g;

    // construts a drawing panel of given size
    public DrawingPanel(int width, int height) {
        BufferedImage image;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = image.getGraphics();
        g.setColor(Color.BLACK);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(label);

        statusBar = new JLabel("    ");

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        frame = new JFrame("Drawing Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // frame.setResizable ( true);
        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(250, this);
        timer.start();
    }

    public Graphics getGraphics() {
        return g;
    }

    public void setBlackground(Color c) {
        panel.setBackground(c);
    }

    public void setVisible(Boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        statusBar.setText("(" + e.getX() + "," + e.getY() + ")");
    }
}
