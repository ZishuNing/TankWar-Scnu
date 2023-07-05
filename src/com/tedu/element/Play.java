package com.tedu.element;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.manager.GameLoad;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
public class Play extends ElementObj{
//    private int dir_x=2,dir_y=2;
    private boolean pkType=false;//攻击状态 true则攻击
    //移动枚举
    public enum Dir{
        UP,DOWN,LEFT,RIGHT,STOP
    }
    public Dir dir;

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
    //添加子弹
    @Override
    protected void add() {
        if (!pkType) return;//如果不是攻击状态则不添加子弹
        //创建子弹
        ElementObj playFile = new PlayFile().createPlayFile(this, 1, 10);
        //装入集合
        ElementManager.getManager().addElement(playFile, GameElement.PLAYFILE);
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
                case 32://空格键攻击
                    this.pkType=true;//开启攻击状态
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
                case 32://空格键攻击
                    this.pkType=false;//关闭攻击状态
                    break;
                default:
                    break;
            }
        }
    }
}
