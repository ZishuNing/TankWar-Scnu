package com.tedu.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Replace JPanel Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.add(new JLabel("Panel 1"));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        panel2.add(new JLabel("Panel 2"));

        JButton button = new JButton("Replace");
        button.addActionListener(new ActionListener() {
            private boolean isPanel1 = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                Container contentPane = frame.getContentPane();
                if (isPanel1) {
                    contentPane.remove(panel1);
                    contentPane.add(panel2);
                } else {
                    contentPane.remove(panel2);
                    contentPane.add(panel1);
                }
                isPanel1 = !isPanel1;
                contentPane.revalidate();
                contentPane.repaint();
            }
        });

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}