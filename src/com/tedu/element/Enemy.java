package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj {
    private final int random = new Random().nextInt(2000)+500;
    private int safeValue = 30; //防穿模值 即不会把坦克的一大半埋到地图边界了
    private int movingTime = random;
    private boolean isLoop = false;
    private boolean pkType = false;
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(";");
        this.setX(Integer.parseInt(arr[0]));
        this.setY(Integer.parseInt(arr[1]));
        this.setW(Integer.parseInt(arr[2]));
        this.setH(Integer.parseInt(arr[3]));
        this.setHp(Integer.parseInt(arr[4]));
        dir = GameLoad.GameLoadEnum.valueOf(arr[5]);
        this.setObj_type(GameElement.ENEMY);
        this.setIcon(GameLoad.ImgMap.get(dir));
        return this;
    }


    @Override
    /*
     * 重写移动逻辑，不仅可以左右移动，还可以上下移动
     * 根据dir的方向进行移动一定movingTime的时长后，掉头再次移动相同的movingTime时长；
     * 当上述移动完成了一次之后即称为完成一个循环；
     * 完成一个循环后，根据传入的参数time进行求余运算判断奇数或偶数后进行另一条方向轴的运动
     * 可使用Random的函数给予movingTime一个随机值使得敌人移动更加随机
     */
    public void move(long... time) {
        int speed = 1;
        /*
         * 当还剩余movingTime的移动时间时，根据当前的dir方向进行移动，直到movingTime为0
         */
        if (movingTime >0) {
            switch (dir) {
                case enemy_up:
                    this.setY(this.getY() - speed);
                    if (this.getY() <= 0) this.setY(0);
                    else if (this.getY() >= GameJFrame.GameY - getH()-safeValue) this.setY(GameJFrame.GameY - getH()-safeValue);
                    movingTime--;
                    break;
                case enemy_down:
                    this.setY(this.getY() + speed);
                    if (this.getY() <= 0) this.setY(0);
                    else if (this.getY() >= GameJFrame.GameY - getH()-safeValue) this.setY(GameJFrame.GameY - getH()-safeValue);
                    movingTime--;
                    break;
                case enemy_left:
                    this.setX(this.getX()-speed);
                    if(this.getX()<=0) this.setX(0);
                    else if (this.getX()>=GameJFrame.GameX-getW()-safeValue) this.setX(GameJFrame.GameX-getW()-safeValue);
                    movingTime--;
                    break;
                case enemy_right:
                    this.setX(this.getX()+speed);
                    if(this.getX()<=0) this.setX(0);
                    else if (this.getX()>=GameJFrame.GameX-getW()-20) this.setX(GameJFrame.GameX-getW()-20);
                    movingTime--;
                    break;
                default:
                    break;
            }
        }
        /*
         * 当剩余的movingTime时间为0时，重新分配movingTime并且根据是否已经完成循环来决定是否改变方向轴的运动
         */
        else
        {
            movingTime = random;
            if (!isLoop)//未完成一圈循环时
            {
                switch (dir)
                {
                    case enemy_up:
                        dir = GameLoad.GameLoadEnum.enemy_down;
                        isLoop = true;
                        break;
                    case enemy_down:
                        dir = GameLoad.GameLoadEnum.enemy_up;
                        isLoop = true;
                        break;
                    case enemy_left:
                        dir = GameLoad.GameLoadEnum.enemy_right;
                        isLoop = true;
                        break;
                    case enemy_right:
                        dir = GameLoad.GameLoadEnum.enemy_left;
                        isLoop = true;
                        break;
                }
            }
            else//完成一圈循环后
            {
                if(dir==GameLoad.GameLoadEnum.enemy_left||dir==GameLoad.GameLoadEnum.enemy_right)
                {
                    if (time[0] % 2 == 0) {
                        dir = GameLoad.GameLoadEnum.enemy_up;
                    } else {
                        dir = GameLoad.GameLoadEnum.enemy_down;
                    }
                    isLoop = false;
                }
                else if (dir==GameLoad.GameLoadEnum.enemy_up||dir==GameLoad.GameLoadEnum.enemy_down)
                {
                    if(time[0]%2==0)
                    {
                        dir = GameLoad.GameLoadEnum.enemy_left;
                    }else {
                        dir = GameLoad.GameLoadEnum.enemy_right;
                    }
                    isLoop = false;
                }
            }
        }
    }

    @Override
    protected void updateImg(long... time) {
        this.setIcon(GameLoad.ImgMap.get(dir));
    }

    @Override
    protected void add(long... time) {
        int fireTime = 125;//当帧数为这个值的倍数时开火，也可以采用random来获取
        if (time[0]!=0&&time[0]%fireTime==0)
        {
            pkType = true;
        }
        if (pkType)
        {
            ElementObj playFile = new PlayFile().createPlayFile(this, 1, 10);
            ElementManager.getManager().addElement(playFile, GameElement.PLAYFILE);
            pkType = false;
        }
    }

    @Override//碰到玩家、地图、Boss时将改变方向
    public void collide(GameElement type) {
        switch (type)
        {
            case PLAY:
            case MAPS:
            case BOSS:
                    changeDir();
                    break;
            case PLAYFILE:
                this.setHp(this.getHp()-1);
                if (this.getHp()<=0)
                {
                    this.setLive(false);
                }
                break;
        }
    }

    private void changeDir() {
        switch (dir)
        {
            case enemy_up:
                dir = GameLoad.GameLoadEnum.enemy_down;
                break;
            case enemy_down:
                dir = GameLoad.GameLoadEnum.enemy_up;
                break;
            case enemy_left:
                dir = GameLoad.GameLoadEnum.enemy_right;
                break;
            case enemy_right:
                dir = GameLoad.GameLoadEnum.enemy_left;
                break;
        }
    }
}