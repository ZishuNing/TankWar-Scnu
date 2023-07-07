package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

//玩家子弹类，由玩家调用和创建
public class PlayFile extends ElementObj{
    private int attack;
    private int moveNum;
    private ElementManager em = ElementManager.getManager();
    PlayFile () {}
    //对构造函数封装(普通无图子弹)
    public  ElementObj createPlayFile(ElementObj obj, int attack, int moveNum) {
        this.setObj_type(GameElement.PLAYFILE);
        this.dir = obj.getDir();
        this.attack=attack;
        this.moveNum=moveNum;
        this.setW(10);
        this.setH(10);
        switch (dir) {
            case play1_up:
            case enemy_up:
                this.setX(obj.getX()+20);
                this.setY(obj.getY()-5);
                break;
            case play1_down:
            case enemy_down:
                this.setX(obj.getX()+20);
                this.setY(obj.getY()+40);
                break;
            case play1_left:
            case enemy_left:
                this.setX(obj.getX());
                this.setY(obj.getY()+20);
                break;
            case play1_right:
            case enemy_right:
                this.setX(obj.getX()+40);
                this.setY(obj.getY()+20);
                break;
            default:
                this.setX(obj.getX());
                this.setY(obj.getY());
                break;
        }
        return this;
    }

    @Override
    public void collide(GameElement type) {
        switch (type){
            case PLAY:
            case MAPS:
            case BOSS:
                this.setLive(false);
                break;
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.getX(),this.getY(),this.getW(),this.getH());
    }

    @Override
    public void move(long ... time) {
        if(this.getX()<=0||this.getX()>= GameJFrame.GameX ||this.getY()<=0||this.getY()>=GameJFrame.GameY){
            // 出了边界就死了
            this.setLive(false);
            return;
        }
        switch (dir) {
            case play1_up:
            case enemy_up:
                this.setY(this.getY()-moveNum);
                break;
            case play1_down:
            case enemy_down:
                this.setY(this.getY()+moveNum);
                break;
            case play1_left:
            case enemy_left:
                this.setX(this.getX()-moveNum);
                break;
            case play1_right:
            case enemy_right:
                this.setX(this.getX()+moveNum);
                break;
            default:
                break;
        }
    }

    @Override
    public void die(long ... time) {
        ElementManager em = ElementManager.getManager();
        ElementObj obj = new PlayFileDie().createPlayFileDie(getX(),getY());
        em.addElement(obj, GameElement.DIE);
    }


}
