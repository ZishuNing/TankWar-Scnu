package com.tedu.element;

import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import java.awt.*;

//玩家子弹类，由玩家调用和创建
public class PlayFileDie extends ElementObj{

    PlayFileDie() {}

    @Override
    public void collide(GameElement type) {
        // play碰撞之后在这里获取金币
    }

    public  ElementObj createPlayFileDie(int X, int Y){
        this.obj_type = GameElement.DIE;
        this.setX(X);
        this.setY(Y);
        this.setIcon(GameLoad.ImgMap.get(GameLoad.GameLoadEnum.BOOM));
        setW(35);
        setH(35);
        return this;
    }
    @Override
    public void showElement(Graphics g) {
//        System.out.println("PlayFileDie");
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
        setLive(false);
    }


}
