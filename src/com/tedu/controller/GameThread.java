package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @说明： 游戏线程类, 用于控制游戏加载，游戏的自动化，游戏判定
 * @author:  ZRH
 * @继承 继承Thread
 */
public class GameThread extends Thread {

    private ElementManager em=ElementManager.getManager();


    @Override
    public void run() {// 游戏的run方法，主线程
        // 游戏开始时 读进度条，加载资源

        gameLoad();
        // 游戏进行时
        gameRun();
        // 游戏结束时 资源回收
        gameOver();


    }

    /**
     * 游戏加载
     */
    private void gameLoad() {
        loadPlay();
    }
    /**
     * 游戏进行
     * 1.玩家的移动、碰撞、死亡
     * 2.npc死亡后爆金币
     * 3.暂停
     */
    private void gameRun() {
        while(true){
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            //Set<GameElement> set = all.keySet(); //得到所有的key集合
            for(GameElement ge:GameElement.values()) { //迭代器
                List<ElementObj> list = all.get(ge);
                for(int i=0;i<list.size();i++) {
                    ElementObj obj=list.get(i);
                    obj.update();//调用每个类的自己的show方法完成自己的显示
                }
            }
            try {
                Thread.sleep(ElementManager.RefreshTime);//休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }

    /**
     * 游戏结束
     */
    private void gameOver(){

    }

    public void loadPlay() {
//		图片导入
        ImageIcon icon=new ImageIcon("image/tank/play1/player1_up.png");
        ElementObj obj=new Play(100,100,50,50,icon);//实例化对象
//		讲对象放入到 元素管理器中
//		em.getElementsByKey(GameElement.PLAY).add(obj);
        em.addElement(obj, GameElement.PLAY);//直接添加

        // 添加一个敌人，并实现敌人的移动
        ImageIcon icon1=new ImageIcon("image/tank/play2/player2_up.png");
        ElementObj obj1=new Enemy(200,200,50,50,icon1);//实例化对象
        em.addElement(obj1, GameElement.ENEMY);//直接添加
    }
}
