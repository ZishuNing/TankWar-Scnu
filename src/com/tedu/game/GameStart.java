package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.web.GameClient;
import com.tedu.web.GameServer;
import com.tedu.web.GameWebHelper;
import com.tedu.show.GameMenuJPanel;

import java.io.*;
import java.util.Scanner;
/**
 * 在GameStart类所在的main线程new游戏需要的主线程、监听、面板等，然后其他面板需要调用时通过get函数获取
 * 为了防止在某个面板上按下某个按钮而导致持续new主线程而导致线程爆炸
 */


public class GameStart {
    private static final GameMainJPanel  jPanel = new GameMainJPanel();
    private static final GameThread gameThread = new GameThread();
    private static final GameMenuJPanel jp=new GameMenuJPanel();
    private static final GameListener listener = new GameListener();
    //程序入口

    public static boolean isMultiplayer = false;
    public static boolean isServer = false;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 有三个游戏开始的入口，分别对应单人游戏，多人游戏的服务器端，多人游戏的客户端
        // 多人游戏的服务器端和客户端需要在同一个局域网内
        // 多人游戏的服务端要开在前面，客户端要后开
        // 服务端开启后，客户端会广播一个信息到11451端口，服务端受到后会传输地图信息给客户端，并进行一系列的初始化操作，客户端接收到后会开始游戏

        GameJFrame gj=new GameJFrame();
        gj.setjPanel(jp);
        gj.start();
    }

    public static GameMainJPanel getjPanel() {
        return jPanel;
    }

    public static GameThread getGameThread() {
        return gameThread;
    }

    public static GameMenuJPanel getJp() {
        return jp;
    }

    public static GameListener getListener() {
        return listener;
    }
}
