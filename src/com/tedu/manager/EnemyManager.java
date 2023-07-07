package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;

import java.util.List;

public class EnemyManager {
    private static EnemyManager em;
    // 敌人集合
    public List<ElementObj> enemies;
    //当前得分
    public static int score = 0;
    // 敌人方向枚举
    public enum EnemyDir{
        LEFT,RIGHT,UP,DOWN
    }
    private EnemyManager() {
        enemies = ElementManager.getManager().getElementsByKey(GameElement.ENEMY);
    }
//    public void RegisterEnemy(Enemy enemy) {
//        enemies.add(enemy);
//    }
    public static synchronized EnemyManager getEnemyManager() {
        if(em == null) {//控制锁定
            em = new EnemyManager();
        }
        return em;
    }
//    public void run() {
//            for (ElementObj enemy : enemies) {
//                enemy.move(); // 让敌人左右移动
//            }
//            try {
//                Thread.sleep(ElementManager.RefreshTime); // 等待一段时间
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//    }
}
