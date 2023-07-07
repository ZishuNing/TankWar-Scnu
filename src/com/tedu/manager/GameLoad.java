package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Play;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @说明 游戏加载类，用于加载游戏资源，作为一个全局类，将图片唯一地加载到内存里面，方便复用
 */
public class GameLoad {
    private static ElementManager em = ElementManager.getManager();

    public static void Init(int Id) throws IOException {
        Load();//读取文件
        PlayLoad(Id);
        MapLoad(Id);
        EnemyLoad(Id);
    }

    // 需要加图片就在这里添加，然后在 com/tedu/text/GameData.properties 中加入对应的内容
    public enum GameLoadEnum{
        play1_up,play1_down,play1_left,play1_right,enemy_up,enemy_down,enemy_left,enemy_right,GRASS, BRICK, RIVER, IRON, BOOM
    }

    // 重构全局枚举类，用于存储图片
    public static Map<GameLoadEnum, ImageIcon> ImgMap = new HashMap<GameLoadEnum, ImageIcon>();


    //读取文件的类
    private static Properties pro = new Properties();
    //传入地图ID加载地图
    public static void MapLoad(int mapId) {
        // 获取文件路径
        String mapName = "com/tedu/text/" + mapId + ".map";
        // 使用IO流来获取文件对象
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        pro.clear();
        if (maps == null) {
            System.out.println("地图配置文件读取异常，请重新安装");
            return;
        }

        try {
            pro.load(maps);//地图配置文件内容装载进properties中
            Enumeration<?> names = pro.propertyNames();
            //无序获取
            while (names.hasMoreElements()){
                String key = (String) names.nextElement();//键
                String value = pro.getProperty(key);//值，为‘400；200；300’形式的字符串
                String[] split = value.split(";");//将上述字符串分解成’400‘、‘200’、‘300’的值
                for (int i=0;i<split.length;i++){//
                    ElementObj element =new MapObj().createElement(key+","+split[i]);//传入字符串创建对象并加进管理器中
                    em.addElement(element,GameElement.MAPS);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void PlayLoad(int playId){
        String playName =  "com/tedu/text/" + playId + ".play";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream play = classLoader.getResourceAsStream(playName);
        pro.clear();
        if(play==null) {
            System.out.println("玩家配置文件读取异常，请重新安装");
            return;
        }

        try {
            pro.load(play);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()){
                String key = (String) names.nextElement();
                String value = pro.getProperty(key);
                ElementObj element = new Play().createElement(value);
                em.addElement(element,GameElement.PLAY);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void EnemyLoad(int enemyId) {
        String enemyName = "com/tedu/text/"+enemyId+".enemy";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream enemy = classLoader.getResourceAsStream(enemyName);
        pro.clear();
        if(enemy==null) {
            System.out.println("敌人配置文件读取异常，请重新安装");
            return;
        }

        try {
            pro.load(enemy);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()){
                String key = (String) names.nextElement();
                String value = pro.getProperty(key);
                ElementObj element = new Enemy().createElement(value);
                em.addElement(element,GameElement.ENEMY);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Load() throws IOException {

        //以字节流的方式获取配置配置文件中的内容并存入图片集ImgMap中
        try{
            Properties prop = new Properties();
            ClassLoader classLoader = GameLoad.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream("com/tedu/text/GameData.properties");
            prop.load(in);     ///加载属性列表
            for (String key : prop.stringPropertyNames()) {
                System.out.println(key + ":" + prop.getProperty(key));
                ImgMap.put(GameLoadEnum.valueOf(key), new ImageIcon(prop.getProperty(key)));
            }
            assert in != null;
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
