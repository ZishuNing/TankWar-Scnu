package com.tedu.element;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import jdk.nashorn.internal.runtime.Debug;

import javax.swing.*;
import java.awt.*;

//玩家子弹类，由玩家调用和创建
public class PlayFile extends ElementObj{
    private int attack;
    private int moveNum;
    Play.Dir dir;//子弹方向
    PlayFile () {}
    //对构造函数封装(普通无图子弹)
    public  ElementObj createPlayFile(Play play, int attack, int moveNum) {
        this.dir = play.dir;
        this.attack=attack;
        this.moveNum=moveNum;
        this.setW(10);
        this.setH(10);
        switch (dir) {
            case UP:
                this.setX(play.getX()+20);
                this.setY(play.getY()-5);
                break;
            case DOWN:
                this.setX(play.getX()+20);
                this.setY(play.getY()+40);
                break;
            case LEFT:
                this.setX(play.getX());
                this.setY(play.getY()+20);
                break;
            case RIGHT:
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
    protected void move() {
        switch (dir) {
            case UP:
                this.setY(this.getY()-moveNum);
                break;
            case DOWN:
                this.setY(this.getY()+moveNum);
                break;
            case LEFT:
                this.setX(this.getX()-moveNum);
                break;
            case RIGHT:
                this.setX(this.getX()+moveNum);
                break;
            default:
                break;
        }
    }
}
