package com.tedu.show;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class EndJPanel extends JPanel{
    private JLabel scoreLabel;

    public EndJPanel(int score) {
        setLayout(new BorderLayout());
        scoreLabel = new JLabel("过关！得分为：" + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("宋体", Font.BOLD, 28)); // 设置字体
        add(scoreLabel, BorderLayout.CENTER);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameJFrame.GameX, GameJFrame.GameY);
    }
}
