package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.web.GameClient;
import com.tedu.web.GameServer;

import java.io.*;

public class GameStart {
    //程序入口

    public static boolean isMultiplayer = true;
    public static boolean isServer = true;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GameJFrame gj=new GameJFrame();
        GameMainJPanel jp=new GameMainJPanel();
        //实例化监听
		GameListener listener = new GameListener();



        // 实例化主线程
        GameThread gt=new GameThread(isServer,isMultiplayer);



        if(isMultiplayer && isServer){
            GameServer gs=new GameServer();
            gj.setWeb(gs);
        }else if(isMultiplayer && !isServer){
            GameClient gc=new GameClient();
            gj.setWeb(gc);

        }

        gj.setThead(gt);
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.start();



    }


}
