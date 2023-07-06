package com.tedu.element;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;
import jdk.nashorn.internal.runtime.Debug;

import javax.swing.*;
import java.awt.*;

//玩家子弹类，由玩家调用和创建
public class PlayFile extends ElementObj{
    private int attack;
    private int moveNum;


    GameLoad.GameLoadEnum dir;//子弹方向
    PlayFile () {}
    //对构造函数封装(普通无图子弹)
    public  ElementObj createPlayFile(Play play, int attack, int moveNum) {
        this.dir = play.dir;
        this.attack=attack;
        this.moveNum=moveNum;
        this.setW(10);
        this.setH(10);
        switch (dir) {
            case play1_up:
                this.setX(play.getX()+20);
                this.setY(play.getY()-5);
                break;
            case play1_down:
                this.setX(play.getX()+20);
                this.setY(play.getY()+40);
                break;
            case play1_left:
                this.setX(play.getX());
                this.setY(play.getY()+20);
                break;
            case play1_right:
                this.setX(play.getX()+40);
                this.setY(play.getY()+20);
                break;
            default:
                this.setX(play.getX());
                this.setY(play.getY());
                break;
        }
        return this;
    }
    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.getX(),this.getY(),this.getW(),this.getH());
    }

    @Override
    protected void move(long ... time) {
        if(this.getX()<=0||this.getX()>= GameJFrame.GameX ||this.getY()<=0||this.getY()>=GameJFrame.GameY){
            // 出了边界就死了
            this.setLive(false);
            return;
        }
        switch (dir) {
            case play1_up:
                this.setY(this.getY()-moveNum);
                break;
            case play1_down:
                this.setY(this.getY()+moveNum);
                break;
            case play1_left:
                this.setX(this.getX()-moveNum);
                break;
            case play1_right:
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
