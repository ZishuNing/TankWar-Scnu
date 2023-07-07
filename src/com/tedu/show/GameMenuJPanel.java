package com.tedu.show;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameMenuJPanel extends JPanel {
    private CenterJpanel centerJp = null;//中间图形面板
    private JPanel bottomJp = null;//底部按钮面板
    private Image image = null;//背景图片
    private JButton startBtn = new JButton("开始游戏");
    private JButton map1Btn = new JButton("进入地图1");
    private JButton map2Btn = new JButton("进入地图2");
    private JButton map3Btn = new JButton("进入地图3");

    /**
     * 构造方法生成面板
     */
    public GameMenuJPanel(){
        try {
            image = ImageIO.read( new File("image/login_background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bottomJp = new JPanel();
        bottomJp.add(startBtn);
        bottomJp.add(map1Btn);
        bottomJp.add(map2Btn);
        bottomJp.add(map3Btn);
        startBtn.setVisible(true);
        map1Btn.setVisible(false);
        map2Btn.setVisible(false);
        map3Btn.setVisible(false);
        addListener();


        centerJp = new CenterJpanel();

        setLayout(new BorderLayout());
        add(centerJp,BorderLayout.CENTER);
        add(bottomJp,BorderLayout.SOUTH);
    }

    class CenterJpanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(image,0,0,null);
        }
    }

    private void addListener(){
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.setVisible(false);
                map1Btn.setVisible(true);
                map2Btn.setVisible(true);
                map3Btn.setVisible(true);
            }
        });
        map1Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread gameThread = new GameThread();
                gameThread.setId(1);
                GameMainJPanel jP = new GameMainJPanel();
                GameListener listener = new GameListener();
                JButton button = (JButton) e.getSource();
                 GameJFrame gameJFrame  = (GameJFrame) button.getRootPane().getParent();
                 gameJFrame.setjPanel(jP);
                 gameJFrame.setThread(gameThread);
                 gameJFrame.setKeyListener(listener);
                 gameJFrame.start();
            }
        });
        map2Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread gameThread = new GameThread();
                gameThread.setId(2);
                GameMainJPanel jP = new GameMainJPanel();
                GameListener listener = new GameListener();
                JButton button = (JButton) e.getSource();
                GameJFrame gameJFrame  = (GameJFrame) button.getRootPane().getParent();
                gameJFrame.setjPanel(jP);
                gameJFrame.setThread(gameThread);
                gameJFrame.setKeyListener(listener);
                gameJFrame.start();
            }
        });
        map3Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread gameThread = new GameThread();
                gameThread.setId(3);
                GameMainJPanel jP = new GameMainJPanel();
                GameListener listener = new GameListener();
                JButton button = (JButton) e.getSource();
                GameJFrame gameJFrame  = (GameJFrame) button.getRootPane().getParent();
                gameJFrame.setjPanel(jP);
                gameJFrame.setThread(gameThread);
                gameJFrame.setKeyListener(listener);
                gameJFrame.start();
            }
        });
    }
}
