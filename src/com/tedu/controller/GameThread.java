package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

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

    private int Id;
    private ElementManager em=ElementManager.getManager();

    @Override
    public void run() {// 游戏的run方法，主线程
        // 游戏开始时 读进度条，加载资源

        try {
            gameLoad();
        } catch (IOException | ClassNotFoundException e) {
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

        GameLoad.Init(Id);
    }
    /**
     * 游戏进行
     * 1.玩家的移动、碰撞、死亡
     * 2.npc死亡后爆金币
     * 3.暂停
     */
    private void gameRun() {
        long gameTime = 0;
        while(true){
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            //Set<GameElement> set = all.keySet(); //得到所有的key集合

            List<ElementObj> playfiles = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> plays = em.getElementsByKey(GameElement.PLAY);
            //每一帧对集合中所有的对象进行移动和图片的更新
            moveAndUpdate(all,gameTime);
            //每一帧对对象进行碰撞检测
            ElementPK(enemies,maps);
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
                obj.update(gameTime); //调用所有基类中射出子弹和移动的方法
            }
        }
    }
    /**
     * 游戏结束
     */
    private void gameOver(){

    }


    public void setId(int Id) {
        this.Id = Id;
    }
}
