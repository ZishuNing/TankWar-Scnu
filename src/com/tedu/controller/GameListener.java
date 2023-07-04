package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();
    //private Set<Integer> set = new HashSet<Integer>();//集合记录按下的键，重复触发就直接结束
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //按下
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
//        if(set.contains(key)) {
//            //如果包含，直接结束
//            return;
//        }
//        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for(ElementObj obj:play) {
            obj.keyClick(true, e.getKeyCode());
        }
    }
    //松开
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
//        if(set.contains(key)) {
//            return;
//        }
//        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for(ElementObj obj:play) {
            obj.keyClick(false, e.getKeyCode());
        }
    }
}
