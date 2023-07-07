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


    private static void MultiStart(boolean is_server){

        GameStart.isServer = is_server;

        GameJFrame gj=new GameJFrame();
        GameMainJPanel jp=new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();



        // 实例化主线程
        GameThread gt=new GameThread();

        if(is_server){
            GameServer gs=new GameServer();
            GameWebHelper.setGameServer(gs);

            gj.setWeb(gs);
        }else{
            GameClient gc=new GameClient();

            GameWebHelper.setGameClient(gc);
            gj.setWeb(gc);
        }

        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();
    }

    private static void SingleStart() {
        GameJFrame gj=new GameJFrame();
        GameMainJPanel jp=new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();



        // 实例化主线程
        GameThread gt=new GameThread();



        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        Scanner sc = new Scanner(System.in);
        if(sc.hasNextInt()){
            int i = sc.nextInt();
            if(i==0){
                isMultiplayer = false;
                isServer = false;
            }else if(i==1){
                isMultiplayer = true;
                isServer = true;
            }else if(i==2){
                isMultiplayer = true;
                isServer = false;
            }else{
                System.out.println("输入错误");
                return;
            }
        }
        if(isMultiplayer){
            MultiStart(isServer);
        }else{
            SingleStart();
        }


    }


}
