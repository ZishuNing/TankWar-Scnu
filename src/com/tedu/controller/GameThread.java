package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.EnemyManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @说明： 游戏线程类, 用于控制游戏加载，游戏的自动化，游戏判定
 * @author:  ZRH
 * @继承 继承Thread
 */
public class GameThread extends Thread {

    private ElementManager em=ElementManager.getManager();
    private EnemyManager enemyManager = EnemyManager.getEnemyManager();

    @Override
    public void run() {// 游戏的run方法，主线程
        // 游戏开始时 读进度条，加载资源

        try {
            gameLoad();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 游戏进行时
        gameRun();
        // 游戏结束时 资源回收
        gameOver();


    }

    /**
     * 游戏加载
     */
    private void gameLoad() throws IOException, ClassNotFoundException {

        GameLoad.Init();
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
            long gameTime = 0;
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            //Set<GameElement> set = all.keySet(); //得到所有的key集合

            List<ElementObj> playfiles = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);

            moveAndUpdate(all,gameTime);
            //碰撞检测
            ElementPK(enemies,playfiles);
            ElementPK(playfiles,maps);
            ElementPK(plays, maps);
            ElementPK(plays, playfiles);
            ElementPK(plays, enemies);

            gameTime++;// 开游戏到现在经过的帧数
            try {
                Thread.sleep(ElementManager.RefreshTime);//休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }

    /**
     * pk 函数
     * @param listA
     * @param listb
     */
    public void ElementPK(List<ElementObj> listA, List<ElementObj> listb)
    {
        for(ElementObj objA:listA)
        {
            for(ElementObj objB:listb)
            {
                if (objA.isCollide(objB))
                {
                    objA.collide(objB.getObj_type());
                    objB.collide(objA.getObj_type());
                    break;
                }
            }
        }
    }


    /**
     *对游戏内某一帧中的元素集合进行遍历生存状态，若不为生存状态则调用死亡方法
     * @param all 元素管理器中的元素集合
     * @param gameTime 某一帧
     */
    private void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime){
        for(GameElement ge:GameElement.values()) { //迭代器
            List<ElementObj> list = all.get(ge);
            // 正确的在循环中的删除方法
            Iterator<ElementObj> it = list.iterator();
            while(it.hasNext()){
                ElementObj obj = it.next();
                if(!obj.getLive()){
                    // 需要调用死亡方法
                    obj.die(gameTime);
                    it.remove();
                    continue;
                }
                obj.update(gameTime);
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

        ElementObj obj=new Play(0,0,50,50,GameLoad.ImgMap.get(GameLoad.GameLoadEnum.play1_up));//实例化对象
//		讲对象放入到 元素管理器中
//		em.getElementsByKey(GameElement.PLAY).add(obj);
        em.addElement(obj, GameElement.PLAY);//直接添加

        // 采用for循环方式向元素集合中添加敌人,默认设置为10个敌人,str的内容为敌人数据
            for (int i=0;i<3;i++)
            {
                em.addElement(new Enemy().createElement(""),GameElement.ENEMY);
            }
    }
}
