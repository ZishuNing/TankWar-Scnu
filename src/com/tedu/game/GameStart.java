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
    private static GameMainJPanel jPanel = new GameMainJPanel();
    private static GameThread gameThread = new GameThread();
    private static GameMenuJPanel jp=new GameMenuJPanel();
    private static GameListener listener = new GameListener();
    //程序入口
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
