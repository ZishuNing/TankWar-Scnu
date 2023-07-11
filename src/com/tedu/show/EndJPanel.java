package com.tedu.show;
import javax.swing.*;
import java.awt.*;

public class EndJPanel extends JPanel{
    private JLabel scoreLabel;

    public EndJPanel(int score, boolean win) {
        if(win){
            setLayout(new BorderLayout());
            scoreLabel = new JLabel("游戏结束，您的得分为：" + score, SwingConstants.CENTER);
            scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 32)); // 设置字体
            add(scoreLabel, BorderLayout.CENTER);
        }else{
            setLayout(new BorderLayout());
            scoreLabel = new JLabel("死", SwingConstants.CENTER);
            scoreLabel.setFont(new Font("华文楷体", Font.PLAIN, 200)); // 设置字体
            scoreLabel.setForeground(Color.RED);
            add(scoreLabel, BorderLayout.CENTER);
        }

    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameJFrame.GameX, GameJFrame.GameY);
    }
}
