package com.tedu.element;
import com.tedu.show.GameJFrame;
import com.tedu.manager.GameLoad;
import java.awt.Graphics;
import javax.swing.ImageIcon;
public class Enemy extends ElementObj{

    /**
     * 移动属性：方向
     */
    public enum Dir{
        UP,LEFT,DOWN,RIGHT,STOP
    }
    Dir dir;
    int frames=0;

    public Enemy(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);

        dir = Dir.STOP;
        this.setIcon(icon);

    }

    /**
     * 重写移动方法
     * 每次刷新都会调用一次
     */
    @Override
    public void move(long ... time) {
        synchronized (this) {
//            System.out.println("move");
            if (dir == Dir.LEFT) {
                this.setX(this.getX() - 2);
                if (this.getX() <= 0) this.setX(0);
                else if (this.getX() >= GameJFrame.GameX - getW()) this.setX(GameJFrame.GameX - getW());
            }
            if (dir == Dir.RIGHT) {
                this.setX(this.getX() + 2);
                if (this.getX() <= 0) this.setX(0);
                else if (this.getX() >= GameJFrame.GameX - getW()) this.setX(GameJFrame.GameX - getW());
            }
            if (dir == Dir.UP) {
                this.setY(this.getY() - 2);
                if (this.getY() <= 0) this.setY(0);
                else if (this.getY() >= GameJFrame.GameY - getH()) this.setY(GameJFrame.GameY - getH());
            }
            if (dir == Dir.DOWN) {
                this.setY(this.getY() + 2);
                if (this.getY() <= 0) this.setY(0);
                else if (this.getY() >= GameJFrame.GameY - getH()) this.setY(GameJFrame.GameY - getH());
            }
        }
    }




    @Override
    public void updateImg(long ... time) {
        frames++;
        if(frames>60){

            synchronized (this){
//                System.out.println("切换图片");
                // 循环移动
                dir = Dir.values()[dir.ordinal()+1>Dir.values().length-2?0:dir.ordinal()+1];
                this.setIcon(GameLoad.enemyImgMap.get(dir));
            }

            frames=0;
        }


    }

    /**
     * 面向对象中第1个思想： 对象自己的事情自己做
     */
    @Override
    public void showElement(Graphics g) {
//        move(); // 每一帧都move
        synchronized (this){
//            System.out.println("show");
            g.drawImage(this.getIcon().getImage(),
                    this.getX(), this.getY(),
                    this.getW(), this.getH(), null);
        }

    }
    //重写键盘输入
    public void keyClick(boolean bl, int key) {
        // 不需要
    }
}
