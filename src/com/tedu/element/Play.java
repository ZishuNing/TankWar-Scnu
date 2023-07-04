package com.tedu.element;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.manager.GameLoad;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
public class Play extends ElementObj{
//    private int dir_x=2,dir_y=2;
    /**
     * 移动属性：
     * 1.单属性 配合 方向枚举类型使用；一次只能移动一个方向
     * 2.双属性 上写 和 左右 bool， 例如true 代表上，需要另一个变量确定是否按下方向键
     * 3.4属性，上下左右都可以 bool true代表向上，false不移动，后按的会重置先按的
     *
     * 问题 图片什么时候加载到内存，图片放在哪里
     */

    public enum Dir{
        UP,DOWN,LEFT,RIGHT,STOP
    }
    Dir dir;


    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);

        dir = Dir.STOP;
        this.setIcon(icon);

    }

    /**
     * 重写移动方法
     * 每次刷新都会调用一次
     */
    @Override
    public void move() {
        if(dir == Dir.LEFT) {
            this.setX(this.getX()-2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()) this.setX(GameJFrame.GameX-getW());
        }
        if(dir == Dir.RIGHT) {
            this.setX(this.getX()+2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()) this.setX(GameJFrame.GameX-getW());
        }
        if(dir == Dir.UP) {
            this.setY(this.getY()-2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()) this.setY(GameJFrame.GameY-getH());
        }
        if(dir == Dir.DOWN) {
            this.setY(this.getY()+2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()) this.setY(GameJFrame.GameY-getH());
        }
    }




    @Override
    public void updateImg() {
        if(dir == Dir.STOP) return;
        this.setIcon(GameLoad.playImgMap.get(dir));
    }

    /**
     * 面向对象中第1个思想： 对象自己的事情自己做
     */
    @Override
    public void showElement(Graphics g) {
//        move(); // 每一帧都move

        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }
    //重写键盘输入
    public void keyClick(boolean bl, int key) {
        if (bl) {
            switch (key) {
                case 37:
                    dir = Dir.LEFT;
                    break;
                case 38:
                    dir = Dir.UP;
                    break;
                case 39:
                    dir = Dir.RIGHT;
                    break;
                case 40:
                    dir = Dir.DOWN;
                    break;
                default:
                    break;
            }
        }else{
            switch (key) {
                case 37:
                case 38:
                case 39:
                case 40:

                    dir = Dir.STOP;
                    break;
                default:
                    break;
            }
        }


    }
}
