package com.tedu.show;

import javax.swing.*;

//窗体：关闭、显示、最大最小化，需嵌入面板，启动主线程
public class GameJFrame extends JFrame {
    public static int GameX = 900;
    public static int GameY = 600;
    public GameJFrame() {
        init();
    }
    public void init() {
        this.setSize(GameX, GameY); //窗体大小
        this.setTitle("坦克大战");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出+关闭
        this.setLocationRelativeTo(null);//居中显示
    }
}
