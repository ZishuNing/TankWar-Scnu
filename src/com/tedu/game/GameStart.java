package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
    //程序入口
    public static void main(String[] args) {
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
}
