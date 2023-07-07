package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.show.GameMenuJPanel;

import java.io.*;

public class GameStart {
    //程序入口
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GameJFrame gj=new GameJFrame();
        GameMainJPanel jp=new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();
        GameThread thread = new GameThread();
        thread.setId(2);
        // 实例化主线程
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.setThread(thread);
        gj.start();
    }
}
