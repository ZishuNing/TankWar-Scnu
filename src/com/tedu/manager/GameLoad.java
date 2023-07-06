package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.element.Play;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @说明 游戏加载类，用于加载游戏资源，作为一个全局类，将图片唯一地加载到内存里面，方便复用
 */
public class GameLoad {
    private static ElementManager em = ElementManager.getManager();

    public static Map<Play.Dir, ImageIcon> playImgMap;

    static {
        playImgMap = new HashMap<>() ;
        playImgMap.put(Play.Dir.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        playImgMap.put(Play.Dir.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));
        playImgMap.put(Play.Dir.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        playImgMap.put(Play.Dir.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));
    }
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
}
