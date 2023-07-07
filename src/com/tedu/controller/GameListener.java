package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.game.GameStart;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();

    private boolean Flush = false;
    private Set<Integer> set = new HashSet<Integer>();//集合记录按下的键，重复触发就直接结束
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //按下
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(set.contains(key) && !Flush) {

            return;
        }
        if(!set.contains(key)) {
            set.add(key);
        }else{
            Flush = false;
        }
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for(ElementObj obj:play) {
            if(GameStart.isMultiplayer){
                if(obj.getId()== Play.getMainPlayId()){
                    obj.keyClick(true, e.getKeyCode());
                    break;
                }

            }else{
                obj.keyClick(true, e.getKeyCode());
            }

        }
    }
    //松开
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(!set.contains(key)) {
            return;
        }
        Flush = true;
        set.remove(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for(ElementObj obj:play) {
            if(GameStart.isMultiplayer){
                if(obj.getId()== Play.getMainPlayId()){
                    obj.keyClick(false, e.getKeyCode());
                    break;
                }
            }else{
                obj.keyClick(false, e.getKeyCode());
            }
        }
    }
}
