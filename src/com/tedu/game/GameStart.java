package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.web.GameClient;
import com.tedu.web.GameServer;
import com.tedu.web.GameWebHelper;

import java.io.*;
import java.util.Scanner;

public class GameStart {
    //程序入口

    public static boolean isMultiplayer = true;
    public static boolean isServer = true;

    private static void ClientMultiStart() {
        GameJFrame gj = new GameJFrame();
        GameMainJPanel jp = new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();
        isMultiplayer = true;
        isServer = false;


        // 实例化主线程
        GameThread gt = new GameThread();


        GameClient gc = new GameClient();

        GameWebHelper.setGameClient(gc);
        gj.setWeb(gc);


        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();
    }


    private static void ServerMultiStart() {

        isMultiplayer = true;
        isServer = true;


        GameJFrame gj = new GameJFrame();
        GameMainJPanel jp = new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();


        // 实例化主线程
        GameThread gt = new GameThread();

        if (isServer) {
            GameServer gs = new GameServer();
            GameWebHelper.setGameServer(gs);

            gj.setWeb(gs);
        }
        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();
    }

    private static void SingleStart() {
        isMultiplayer = false;
        isServer = false;

        GameJFrame gj = new GameJFrame();
        GameMainJPanel jp = new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();


        // 实例化主线程
        GameThread gt = new GameThread();


        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 有三个游戏开始的入口，分别对应单人游戏，多人游戏的服务器端，多人游戏的客户端
        // 多人游戏的服务器端和客户端需要在同一个局域网内
        // 多人游戏的服务端要开在前面，客户端要后开
        // 服务端开启后，客户端会广播一个信息到11451端口，服务端受到后会传输地图信息给客户端，并进行一系列的初始化操作，客户端接收到后会开始游戏

        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            int i = sc.nextInt();
            if (i == 0) {
                SingleStart();
            } else if (i == 1) {
                ServerMultiStart();
            } else if (i == 2) {
                ClientMultiStart();
            } else {
                System.out.println("输入错误");
                return;
            }
        }





    }


}
