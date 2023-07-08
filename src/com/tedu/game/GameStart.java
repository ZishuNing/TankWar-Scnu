package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;
import com.tedu.show.GameMenuJPanel;

import java.io.*;
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
