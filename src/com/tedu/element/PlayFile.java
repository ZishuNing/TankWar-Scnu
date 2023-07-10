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
//    GameLoad.GameLoadEnum dir;//子弹方向
    BulletDir bulletDir;
    public enum BulletDir{
        UP,DOWN,LEFT,RIGHT
    }
    PlayFile () {}
    //对构造函数封装(普通无图子弹)
    public  ElementObj createPlayFile(Play obj, int attack, int moveNum) {

        this.attack=attack;
        this.moveNum=moveNum;
        this.obj_type = GameElement.PLAYFILE;
        this.setW(10);
        this.setH(10);
        switch (obj.dir) {
            case play1_up:
            case play2_up:
            case enemy_up:
                this.setX(obj.getX()+obj.getW()/2);
                this.setY(obj.getY()-5);
                bulletDir=BulletDir.UP;
                break;
            case play1_down:
            case play2_down:
            case enemy_down:
                this.setX(obj.getX()+obj.getW()/2);
                this.setY(obj.getY()+obj.getH()+5);
                bulletDir=BulletDir.DOWN;
                break;
            case play1_left:
            case play2_left:
            case enemy_left:
                this.setX(obj.getX());
                this.setY(obj.getY()+obj.getH()/2);
                bulletDir=BulletDir.LEFT;
                break;
            case play1_right:
            case play2_right:
            case enemy_right:
                this.setX(obj.getX()+obj.getW());
                this.setY(obj.getY()+obj.getH()/2);
                bulletDir=BulletDir.RIGHT;
                break;
            default:
                this.setX(obj.getX());
                this.setY(obj.getY());
                bulletDir=BulletDir.RIGHT;
                break;
        }
        return this;
    }
    public  ElementObj createEnemyBullet(Enemy enemy, int attack, int moveNum) {
        this.attack=attack;
        this.moveNum=moveNum;
        this.obj_type = GameElement.PLAYFILE;
        this.setW(10);
        this.setH(10);
        switch (enemy.dir) {
            case UP:
                bulletDir=BulletDir.UP;
                this.setX(enemy.getX()+20);
                this.setY(enemy.getY()-15);
                break;
            case DOWN:
                bulletDir=BulletDir.DOWN;
                this.setX(enemy.getX()+20);
                this.setY(enemy.getY()+50);
                break;
            case LEFT:
                bulletDir=BulletDir.LEFT;
                this.setX(enemy.getX()-10);
                this.setY(enemy.getY()+20);
                break;
            case RIGHT:
                bulletDir=BulletDir.RIGHT;
                this.setX(enemy.getX()+50);
                this.setY(enemy.getY()+20);
                break;
            default:
                bulletDir=BulletDir.RIGHT;
                this.setX(enemy.getX());
                this.setY(enemy.getY());
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
            case PLAYFILE:
                break;
            default:
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
        switch (bulletDir) {
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

    @Override
    public void die(long ... time) {
        ElementManager em = ElementManager.getManager();
        ElementObj obj = new PlayFileDie().createPlayFileDie(getX(),getY());
        em.addElement(obj, GameElement.DIE);
    }
}
