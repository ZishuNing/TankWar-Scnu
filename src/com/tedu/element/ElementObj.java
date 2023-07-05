package com.tedu.element;

//元素基类
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @说明 所有元素的基类。
 * @author ZRH
 *
 */
public abstract class ElementObj {

    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;
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
     * 使用父类定义接收键盘事件的方法
     * 需要实现键盘监听的子类，重写这个方法
     * bl true为按下，false为松开
     */
    public void keyClick(boolean bl, int key) {

    }

    /**
     * 更新方法，每一帧都会更新一次，子类不需要重写
     */
    public final void update(){
        updateImg();
        move();
        add();
    }

    /**
     * 移动方法，需要移动的子类需重写
     */
    protected void move(){}

    /**
     * 更新图片方法，需要更新图片的子类需重写
     */
    protected void updateImg(){}

    /**
     * 额外方法，需要添加子弹子类需重写，不知道干什么的
     */
    protected void add(){}

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

}










