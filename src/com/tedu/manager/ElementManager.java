package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 单例元素管理器
 * @author NZS
 */
public class ElementManager {
    //GameElement枚举是key，基类ElementObj是value
    private Map<GameElement, List<ElementObj>> gameElements;
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
    public void init() {//实例化在这里完成
        gameElements = new HashMap<GameElement, List<ElementObj>>();
//      将每种元素集合都放入到map中
		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
    }
}
