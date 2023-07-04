package com.tedu.manager;

import com.tedu.element.Enemy;
import com.tedu.element.Play;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @说明 游戏加载类，用于加载游戏资源，作为一个全局类，将图片唯一地加载到内存里面，方便复用
 */
public class GameLoad {

    public static Map<Play.Dir, ImageIcon> playImgMap;

    public static Map<Enemy.Dir, ImageIcon> enemyImgMap;
    static {
        playImgMap = new HashMap<>() ;
        playImgMap.put(Play.Dir.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        playImgMap.put(Play.Dir.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));
        playImgMap.put(Play.Dir.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        playImgMap.put(Play.Dir.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));

        enemyImgMap = new HashMap<>() ;
        enemyImgMap.put(Enemy.Dir.UP, new ImageIcon("image/tank/play2/player2_up.png"));
        enemyImgMap.put(Enemy.Dir.DOWN, new ImageIcon("image/tank/play2/player2_down.png"));
        enemyImgMap.put(Enemy.Dir.LEFT, new ImageIcon("image/tank/play2/player2_left.png"));
        enemyImgMap.put(Enemy.Dir.RIGHT, new ImageIcon("image/tank/play2/player2_right.png"));
    }


}
