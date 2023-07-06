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

    public static void Init() throws IOException {

        Load();
        MapLoad(5);
    }

    // 需要加图片就在这里添加，然后在 com/tedu/text/GameData.properties 中加入对应的内容
    public enum GameLoadEnum{
        play1_up,play1_down,play1_left,play1_right,GRASS, BRICK, RIVER, IRON, BOOM
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
        if (maps == null) {
            System.out.println("配置文件读取异常，请重新安装");
            return;
        }

        try {
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            //无序获取
            while (names.hasMoreElements()){
                String key = (String) names.nextElement();
                String value = pro.getProperty(key);
                String[] split = value.split(";");
                for (int i=0;i<split.length;i++){
                    ElementObj element =new MapObj().createElement(key+","+split[i]);
                    em.addElement(element,GameElement.MAPS);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void Load() throws IOException {


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
            System.out.println(e);
        }
    }

}
