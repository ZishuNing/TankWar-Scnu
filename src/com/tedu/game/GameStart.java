package com.tedu.game;

import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
    //程序入口
    public static void main(String[] args) {
        GameJFrame gj=new GameJFrame();
        GameMainJPanel jp=new GameMainJPanel();

        gj.setjPanel(jp);
        gj.start();
    }
}
