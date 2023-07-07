package com.tedu.manager;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.game.GameStart;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.yield;

/**
 * 单例元素管理器
 * @author NZS
 */
public class ElementManager implements Serializable {
    public static int RefreshTime=5;//刷新时间ms

    //GameElement枚举是key，基类ElementObj是value
    private Map<GameElement, List<ElementObj>> gameElements;
    //单例
    private static ElementManager EM=null;//引用

    public static void setGameElements(Map<GameElement, List<ElementObj>> game_elements) {
        EM.gameElements = game_elements;
    }

    public static byte[] Serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(getManager().getGameElements());
        return baos.toByteArray();


    }

    public static void DeSerialize(byte[] serial_data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(serial_data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        getManager().gameElements = (Map<GameElement, List<ElementObj>>) ois.readObject();

    }


    //map的get方法
    public Map<GameElement, List<ElementObj>> getGameElements(){
        return gameElements;
    }
    //添加元素
    public void addElement(ElementObj obj,GameElement ge) {
        gameElements.get(ge).add(obj);
    }
    //依据key返回list
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }


    //synchronized保证只有一个线程实例化
    public static synchronized ElementManager getManager() {
        if(EM == null) {//控制锁定
            EM = new ElementManager();
        }

//        else if(EM == null && GameStart.isMultiplayer && !GameStart.isServer){
//            while(EM == null){ // 直到EM不为空才醒来（等到client完成连接）
//               yield();
//            }
//
//        }
        return EM;
    }
    private ElementManager() {
        init();
    }
    //实例化方法
    public void init() {//实例化在这里完成
        gameElements = new HashMap<GameElement, List<ElementObj>>();
//      读取枚举创建集合放入gameElements
		for(GameElement ge:GameElement.values()){
            gameElements.put(ge,new ArrayList<ElementObj>());
        }



    }

    public synchronized void updatePlayElement(Play play) {
        //更新元素
        List<ElementObj> playList = gameElements.get(GameElement.PLAY);
        int i=0;
        for(;i<playList.size();i++){
            Play play_it_cast = (Play) playList.get(i);
            if(play_it_cast.getId() == play.getId()){

                play_it_cast.setX(play.getX());
                play_it_cast.setY(play.getY());
                play_it_cast.setLive(play.getLive());
                play_it_cast.setDir(play.getDir());
                play_it_cast.setMoving(play.isMoving());
                play_it_cast.setHp(play.getHp());
                play_it_cast.setPkType(play.getPkType());
                break;
            }
        }
        if(i==playList.size()){
            gameElements.get(GameElement.PLAY).add(play);
        }



    }
}
