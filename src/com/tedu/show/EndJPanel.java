package com.tedu.show;
import javax.swing.*;
import java.awt.*;

public class EndJPanel extends JPanel{
    private JLabel scoreLabel;

    public EndJPanel(int score) {
        setLayout(new BorderLayout());
        scoreLabel = new JLabel("游戏结束，您的得分为：" + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 32)); // 设置字体
        add(scoreLabel, BorderLayout.CENTER);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameJFrame.GameX, GameJFrame.GameY);
    }
}
