package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * 单例元素管理器
 * @author NZS
 */
public class ElementManager implements Serializable {
    public static int RefreshTime=5;//刷新时间ms

    //GameElement枚举是key，基类ElementObj是value
    private static Map<GameElement, List<ElementObj>> gameElements;

    private static Lock gameElementsLock = new java.util.concurrent.locks.ReentrantLock();

    public static void AcquireLock() {
        gameElementsLock.lock();
    }

    public static void ReleaseLock() {
        gameElementsLock.unlock();
    }

    public static void destroy() {
        gameElements.clear();
    }


    //map的get方法
    public static Map<GameElement, List<ElementObj>> getGameElements(){
        return gameElements;
    }
    //添加元素
    public static synchronized void addElement(ElementObj obj,GameElement ge) {
        gameElements.get(ge).add(obj);
    }
    //依据key返回list
    public static List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }
    //单例
    private static ElementManager EM=null;//引用
    //synchronized保证只有一个线程实例化
    public static synchronized ElementManager getManager() {
        if(EM == null) {//控制锁定
            EM = new ElementManager();
        }
        return EM;
    }
    private ElementManager() {
        init();
    }
    //实例化方法
    public static void init() {//实例化在这里完成
        gameElements = new HashMap<GameElement, List<ElementObj>>();
//      读取枚举创建集合放入gameElements
		for(GameElement ge:GameElement.values()){
            gameElements.put(ge,new ArrayList<ElementObj>());
        }
    }
}
