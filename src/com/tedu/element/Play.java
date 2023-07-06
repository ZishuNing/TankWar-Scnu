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

//    private boolean shot=false;//是否已经发射
    private boolean pkType=false;//攻击状态 true则攻击
    private boolean isMoving=false;

    // 重构枚举
    public GameLoad.GameLoadEnum dir;

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);

        dir = GameLoad.GameLoadEnum.play1_up;
        this.setIcon(icon);
    }

    /**
     * 重写移动方法
     * 每次刷新都会调用一次
     */
    @Override
    public void move(long ... time) {
        if(!isMoving) return;
        if(dir == GameLoad.GameLoadEnum.play1_left) {
            this.setX(this.getX()-2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()) this.setX(GameJFrame.GameX-getW());
        }
        if(dir == GameLoad.GameLoadEnum.play1_right) {
            this.setX(this.getX()+2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()) this.setX(GameJFrame.GameX-getW());
        }
        if(dir == GameLoad.GameLoadEnum.play1_up) {
            this.setY(this.getY()-2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()) this.setY(GameJFrame.GameY-getH());
        }
        if(dir == GameLoad.GameLoadEnum.play1_down) {
            this.setY(this.getY()+2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()) this.setY(GameJFrame.GameY-getH());
        }
    }




    @Override
    public void updateImg(long ... time) {
        if(!isMoving) return;
        this.setIcon(GameLoad.ImgMap.get(dir));
    }
    //添加子弹
    @Override
    protected void add(long ... time) {
        if (!pkType) return;//如果不是攻击状态或射击过了则不添加子弹
        //创建子弹
        pkType=false;
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
                    dir = GameLoad.GameLoadEnum.play1_left;
                    isMoving=true;
                    break;
                case 38:
                    dir = GameLoad.GameLoadEnum.play1_up;
                    isMoving=true;
                    break;
                case 39:
                    dir = GameLoad.GameLoadEnum.play1_right;
                    isMoving=true;
                    break;
                case 40:
                    dir = GameLoad.GameLoadEnum.play1_down;
                    isMoving=true;
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
                    isMoving=false;
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
