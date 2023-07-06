package com.tedu.element;

//元素基类
import com.tedu.manager.GameElement;

import java.awt.*;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * @说明 所有元素的基类。
 * @author ZRH
 *
 */
public abstract class ElementObj implements Serializable {

    // 改成protected，子类可以直接访问
    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected ImageIcon icon;

    protected GameElement obj_type;//元素类型

    protected int id;// obj id



    private boolean live=true;//是否死亡,可以使用枚举加入多种状态，如死亡，无敌，隐身等等



    /**
     * @说明 无参构造方法
     */

    public ElementObj() {
        super();
    }
    /**
     * @说明 带参数的构造方法; 可以由子类传输数据到父类
     * @param x    左上角X坐标
     * @param y    左上角y坐标
     * @param w    w宽度
     * @param h    h高度
     * @param icon  图片
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }
    /**
     * @说明 抽象方法，显示元素
     * @param g  画笔 用于进行绘画
     */
    public abstract void showElement(Graphics g);

    /**
     *需要创建元素时，例如创建子弹、敌人时，重写这个方法
     * @param str 对象数据
     * @return 返回ElementObj的对象实体
     */
    public ElementObj createElement(String str){
        return null;
    }

    /**
     *为对象生成用于碰撞检测的碰撞矩形
     * @return 返回该对象的碰撞矩形
     */
    public Rectangle getRectangle() {
        return new Rectangle(x,y,w,h);
    }


    /**
     * collide 碰撞后会调用这个函数，表明碰撞的对象类型
     */
    public void collide(GameElement type) {

    }

    /**
     *碰撞检测
     * 将传入的ElementObj对象与调用该方法的对象进行碰撞检测
     * @param obj 用于碰撞检测的对象
     * @return true 为已碰撞；false 为未碰撞
     */
    public boolean isCollide(ElementObj obj) {
        return this.getRectangle().intersects(obj.getRectangle());
    }
    /**
     * 使用父类定义接收键盘事件的方法
     * 需要实现键盘监听的子类，重写这个方法
     * bl true为按下，false为松开
     */
    public void keyClick(boolean bl, int key) {

    }

    /**
     * 更新方法，每一帧都会更新一次，子类不需要重写
     */
    public final void update(long ... time){
        // ... 表示可变参数，可以传入0个或多个long类型的参数
        updateImg(time);
        move(time);
        add(time);
    }

    /**
     * 移动方法，需要移动的子类需重写
     */
    protected void move(long ... time){}

    /**
     * 更新图片方法，需要更新图片的子类需重写
     */
    protected void updateImg(long ... time){}

    /**
     * 额外方法，需要添加子弹子类需重写，不知道干什么的
     */
    protected void add(long ... time){}

    /**
     * 死亡方法，需要死亡的子类需重写
     * 死亡也是一个对象
     */

    public void die(long ... time){}

    /**
     * 只要是 VO类 POJO 就要为属性生成 get和set方法
     */
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public ImageIcon getIcon() {
        return icon;
    }
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean getLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public GameElement getObj_type() {
        return obj_type;
    }

    public void setObj_type(GameElement obj_type) {
        this.obj_type = obj_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}










