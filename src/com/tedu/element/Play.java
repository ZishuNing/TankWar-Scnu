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

    private static int bias=GameLoad.GameLoadEnum.play2_up.ordinal()-GameLoad.GameLoadEnum.play1_up.ordinal(); // 两个枚举的偏移量
    private int hp=20;//血量
    private int safeValue = 30;//防穿模值
    ;//血量
    // 重构枚举
    public GameLoad.GameLoadEnum dir;

    public Play(){

    }

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        this.obj_type = GameElement.PLAY;
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
        if(dir == GameLoad.GameLoadEnum.play1_left || dir == GameLoad.GameLoadEnum.play2_left) {
            this.setX(this.getX()-2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()-safeValue) this.setX(GameJFrame.GameX-getW()-safeValue);
        }
        if(dir == GameLoad.GameLoadEnum.play1_right || dir == GameLoad.GameLoadEnum.play2_right) {
            this.setX(this.getX()+2);
            if(this.getX()<=0) this.setX(0);
            else if(this.getX()>= GameJFrame.GameX-getW()-safeValue) this.setX(GameJFrame.GameX-getW()-safeValue);
        }
        if(dir == GameLoad.GameLoadEnum.play1_up || dir == GameLoad.GameLoadEnum.play2_up) {
            this.setY(this.getY()-2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()-safeValue) this.setY(GameJFrame.GameY-getH()-safeValue);
        }
        if(dir == GameLoad.GameLoadEnum.play1_down || dir == GameLoad.GameLoadEnum.play2_down) {
            this.setY(this.getY()+2);
            if(this.getY()<=0) this.setY(0);
            else if(this.getY()>= GameJFrame.GameY-getH()-safeValue) this.setY(GameJFrame.GameY-getH()-safeValue);
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

    @Override
    public void collide(GameElement type) {
        switch (type)
        {
            case PLAYFILE:
                setHp(getHp()-1);
                System.out.println("玩家"+getId()+"被子弹打中，血量："+getHp());
                if (getHp()<=0)
                {
                    this.setLive(false);
                }
                break;
            case BOSS:
            case ENEMY:
            case MAPS:
                isMoving = false;
                break;
        }
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
    @Override
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

    public static GameLoad.GameLoadEnum play1ToPlay2(GameLoad.GameLoadEnum dir) {
        switch (dir) {
            case play1_left:
                return GameLoad.GameLoadEnum.play2_left;
            case play1_right:
                return GameLoad.GameLoadEnum.play2_right;
            case play1_down:
                return GameLoad.GameLoadEnum.play2_down;
            case play1_up:
            default:
                return GameLoad.GameLoadEnum.play2_up;
        }
    }

    public static GameLoad.GameLoadEnum play2ToPlay1(GameLoad.GameLoadEnum dir) {
        switch (dir) {
            case play2_left:
                return GameLoad.GameLoadEnum.play1_left;
            case play2_right:
                return GameLoad.GameLoadEnum.play1_right;
            case play2_down:
                return GameLoad.GameLoadEnum.play1_down;
            case play2_up:
            default:
                return GameLoad.GameLoadEnum.play1_up;
        }
    }


    // 重写toString
    public String ToString() {
    	return ""+this.getId()+"," +this.getX()+","+this.getY()+","+this.getLive()+","+this.isMoving+","+this.pkType+","+this.hp+","+this.dir.ordinal();
    }

    public static Play FromString(String str) {
        String[] args = str.split(",");



        GameLoad.GameLoadEnum dir = GameLoad.GameLoadEnum.values()[Integer.parseInt(args[7])+bias];

        Play play = new Play(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 30, 30, GameLoad.ImgMap.get(dir));
        play.setId(Integer.parseInt(args[0]));
        play.setLive(Boolean.parseBoolean(args[3]));
        play.setMoving(Boolean.parseBoolean(args[4]));
        play.setPkType(Boolean.parseBoolean(args[5]));
        play.setHp(Integer.parseInt(args[6]));

//        play.setDir(Play.play1ToPlay2(dir));
        play.setDir(dir);
        return play;

    }


    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(";");
        this.setObj_type(GameElement.PLAY);
        this.dir = GameLoad.GameLoadEnum.play1_up;
        this.setX(Integer.parseInt(arr[0]));
        this.setY(Integer.parseInt(arr[1]));
        this.setW(Integer.parseInt(arr[2]));
        this.setH(Integer.parseInt(arr[3]));
        this.setHp(Integer.parseInt(arr[4]));
        this.setIcon(GameLoad.ImgMap.get(dir));
        return this;
    }

}
