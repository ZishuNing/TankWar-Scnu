package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private static EnemyManager em;
    // 敌人集合
    public static List<ElementObj> enemies;
    //当前得分
    public static int score = 0;
    // 敌人方向枚举
    public enum EnemyDir{
        LEFT,RIGHT,UP,DOWN
    }
    //敌人行为枚举
    public enum EnemyAction{
        HORIZONTALMOVE,VERTICALMOVE,STOP
    }
    private static final String[] NAMES = {
            "行人", "乐园", "花草", "人才", "左手", "目的", "课文", "优点", "年代", "灰尘", "沙子", "小说", "儿女", "难题", "明星", "本子", "彩色", "水珠", "路灯", "把握", "房屋", "心愿", "左边", "新闻", "早点", "市场", "雨点", "细雨", "书房", "毛巾", "画家", "元旦", "绿豆", "本领", "起点", "青菜", "土豆", "总结", "礼貌", "右边", "老虎", "老鼠", "猴子", "树懒", "斑马", "小狗", "狐狸", "狗熊", "黑熊", "大象", "豹子", "麝牛", "狮子", "熊猫", "疵猪", "羚羊", "驯鹿", "考拉", "犀牛", "给翔", "猩猩", "海牛", "水獭", "海豚", "海象", "刺猬", "袋鼠", "犹徐", "河马", "海豹", "海狮", "编蝠", "白虎", "狸猫", "水牛", "山羊", "绵羊", "牦牛", "猿猴", "松鼠", "野猪", "豪猪", "廉鹿", "花豹", "野狼", "灰狼", "蜂猴", "熊猴", "叶猴", "紫貂", "貂熊", "熊狸", "云豹", "雪豹", "黑鹿", "野马", "惬鹿", "坡鹿", "豚鹿", "野牛", "藏羚", "河狸", "驼鹿", "黄羊", "繁羚", "斑羚", "岩羊", "盘羊", "雪免"
    };
    private static final String[] MODIFIY = {"可爱", "傻傻", "萌萌", "羞差", "笨笨", "呆呆", "美丽", "聪明", "伶俐", "狡猾", "胖乎乎", "粉嫩嫩", "白胖胖", "漂亮", "可爱", "聪明", "懂事", "乖巧", "淘气", "淘气", "顽劣", "调皮", "顽皮", "天真", "可爱", "无邪", "单纯", "纯洁", "无暇", "纯真", "稚气", "温润", "好奇"};
    public   int ENEMY_AI_INTERVAL = 5000;
    public static final double ENEMY_FIRE_RATE = 0.02;
    private EnemyManager() {
        enemies = new ArrayList<ElementObj>();
    }
    public static void RegisterEnemy(ElementObj enemy) {
        if(enemies == null){
            enemies = new ArrayList<ElementObj>();
        }
        enemies.add(enemy);
    }

    public static synchronized EnemyManager getEnemyManager() {
        if(em == null) {//控制锁定
            em = new EnemyManager();
        }
        return em;
    }
    public static String getRandomName() {
        return MODIFIY[getRandomNumber(0, MODIFIY.length)] + "的" + NAMES[getRandomNumber(0, NAMES.length)];
    }
    public static Color getRandomColor() {
        return new Color(getRandomNumber(0, 255), getRandomNumber(0, 255), getRandomNumber(0, 255));
    }
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int getENEMY_AI_INTERVAL() {
        return ENEMY_AI_INTERVAL;
    }

}
