package com.tedu.show;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.game.GameStart;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameMenuJPanel extends JPanel {
    private CenterJpanel centerJp = null;//中间图形面板
    private JPanel bottomJp = null;//底部按钮面板//
    private Image image = null;//背景图片
    private JButton startBtn = new JButton("开始游戏");
    private JButton map1Btn = new JButton("进入地图1");
    private JButton map2Btn = new JButton("进入地图2");
    private JButton map3Btn = new JButton("进入地图3");

    /**
     * 构造方法生成面板
     * 通过IO流获取图片
     * 初始时使开始按钮可见，而进入其他地图的按钮不可见
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
/**
 * 按钮的监听事件，将主窗口的面板换为游戏面板，同时注意设置线程的ID值，防止无法读入配置文件
 * 注意当按下按钮时，要把焦点设置回窗口，不然GameListener监听的对象为按钮
 */

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
                //获取线程、监听、面板等并设置线程的入口值
                GameThread gameThread = GameStart.getGameThread();
                GameMainJPanel gameMainJPanel = GameStart.getjPanel();
                GameListener gameListener = GameStart.getListener();
                gameThread.setId(1);
                //通过获取按钮而获取父窗口
                JButton button = (JButton) e.getSource();
                GameJFrame gameJFrame = (GameJFrame) button.getRootPane().getParent();
                //将焦点设置为父窗口并且设置然后启动父窗口
                gameJFrame.requestFocus();
                gameJFrame.setjPanel(gameMainJPanel);
                gameJFrame.setThread(gameThread);
                gameJFrame.setKeyListener(gameListener);
                gameJFrame.start();
            }
        });
        map2Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread gameThread = GameStart.getGameThread();
                GameMainJPanel gameMainJPanel = GameStart.getjPanel();
                GameListener gameListener = GameStart.getListener();
                gameThread.setId(2);

                JButton button = (JButton) e.getSource();
                GameJFrame gameJFrame = (GameJFrame) button.getRootPane().getParent();

                gameJFrame.requestFocus();
                gameJFrame.remove(GameStart.getJp());
                gameJFrame.setjPanel(gameMainJPanel);
                gameJFrame.setThread(gameThread);
                gameJFrame.setKeyListener(gameListener);
                gameJFrame.start();
            }
        });
        map3Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread gameThread = GameStart.getGameThread();
                GameMainJPanel gameMainJPanel = GameStart.getjPanel();
                GameListener gameListener = GameStart.getListener();
                gameThread.setId(3);

                JButton button = (JButton) e.getSource();
                GameJFrame gameJFrame = (GameJFrame) button.getRootPane().getParent();

                gameJFrame.requestFocus();
                gameJFrame.remove(GameStart.getJp());
                gameJFrame.setjPanel(gameMainJPanel);
                gameJFrame.setThread(gameThread);
                gameJFrame.setKeyListener(gameListener);
                gameJFrame.start();
            }
        });
    }
}
