package com.tedu.element;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.manager.GameLoad;
import com.tedu.web.GameWebHelper;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
public class Play extends ElementObj implements Serializable {
    public static int mainPlayId=0;
//    private boolean shot=false;//是否已经发射
    private boolean pkType=false;//攻击状态 true则攻击
    private boolean isMoving=false;

    private int hp=100;//血量
    // 重构枚举
    public GameLoad.GameLoadEnum dir;

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        this.obj_type = GameElement.PLAY;
        dir = GameLoad.GameLoadEnum.play1_up;
        this.setIcon(icon);
    }


    @Override
    public void collide(GameElement type) {
        switch (type){
            case MAPS:
            case ENEMY:
            case PLAY:
            case BOSS:
                this.isMoving = false;
                break;
            case PLAYFILE:
                hp--;
                if(hp<=0) this.setLive(false);
                break;

        }


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
        GameWebHelper.boardCast(this);
    }

    public void setDir(GameLoad.GameLoadEnum dir) {
        this.dir = dir;
    }

    public GameLoad.GameLoadEnum getDir() {
        return dir;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean getPkType() {
        return pkType;
    }

    public void setPkType(boolean pkType) {
        this.pkType = pkType;
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public static void setMainPlayId(int mainPlayId) {
        Play.mainPlayId = mainPlayId;
    }

    public static int getMainPlayId() {
    	return Play.mainPlayId;
    }

    // 重写toString

    public String ToString() {
    	return ""+this.getId()+"," +this.getX()+","+this.getY()+","+this.getLive()+","+this.isMoving+","+this.pkType+","+this.hp+","+this.dir.ordinal();
    }

    public static Play FromString(String str) {
        String[] args = str.split(",");



        GameLoad.GameLoadEnum dir = GameLoad.GameLoadEnum.values()[Integer.parseInt(args[7])];

        Play play = new Play(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 50, 50, GameLoad.ImgMap.get(dir));
        play.setId(Integer.parseInt(args[0]));
        play.setLive(Boolean.parseBoolean(args[3]));
        play.setMoving(Boolean.parseBoolean(args[4]));
        play.setPkType(Boolean.parseBoolean(args[5]));
        play.setHp(Integer.parseInt(args[6]));
        play.setDir(GameLoad.GameLoadEnum.values()[Integer.parseInt(args[7])]);
        return play;

    }

}
