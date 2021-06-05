package top.yamhk.terminal.server4history.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SanJiaoXingMianJi {

    private final JFrame frame;
    private final JLabel title;
    private final JTextField bian1;
    private final JTextField bian2;
    private final JTextField bian3;
    private final JButton clear;
    private final JButton computeButton;
    private final JTextArea res;

    public SanJiaoXingMianJi() {
        // TODO Auto-generated constructor stub
        title = new JLabel("输入三角形的三边：");
        bian1 = new JTextField(5);
        bian2 = new JTextField(5);
        bian3 = new JTextField(5);
        res = new JTextArea(5, 5);
        computeButton = new JButton("计算");
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (bian1.getText().equals("") || bian2.getText().equals("")
                        || bian3.getText().equals("")) {
                    res.setText("输入有误");
                    return;
                }
                double a = Double.parseDouble(bian1.getText());
                double b = Double.parseDouble(bian2.getText());
                double c = Double.parseDouble(bian3.getText());
                double p = (a + b + c) / 2;
                if (a + b > c && a + c > b && b + c > a) {
                    double resn = Math.sqrt(p * (p - a) * (p - b) * (p - c));
                    res.setText("面积为：" + resn);
                } else {
                    res.setText("输入有误");
                }
            }
        });
        clear = new JButton("清除");
        clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                bian1.setText(null);
                bian2.setText(null);
                bian3.setText(null);
                res.setText(null);
            }
        });
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(bian1);
        panel.add(bian2);
        panel.add(bian3);
        panel.add(computeButton);
        panel.add(clear);

        frame = new JFrame("计算三角形面积：");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(res, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        SanJiaoXingMianJi test = new SanJiaoXingMianJi();
    }
}