package com.tedu.element;

import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{



    private GameLoad.GameLoadEnum type;
    private int hp;

    @Override
    public void collide(GameElement type) {
        switch (type){
            case ENEMY:
            case PLAY:
            case BOSS:
                break;
            case PLAYFILE:
                hp--;
                if(hp<=0) this.setLive(false);
                break;
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }
    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(",");
        ImageIcon icon = null;
        this.obj_type= GameElement.MAPS;
        switch (arr[0]) {
            // 设置图片信息，图片还未加载到内存中
            case "GRASS":
                type = GameLoad.GameLoadEnum.GRASS;
                hp = 1;
                icon = GameLoad.ImgMap.get(type);
                break;
            case "BRICK":
                type = GameLoad.GameLoadEnum.BRICK;
                hp = 5;
                icon = GameLoad.ImgMap.get(type);
                break;
            case "RIVER":
                type = GameLoad.GameLoadEnum.RIVER;
                hp = 1;
                icon = GameLoad.ImgMap.get(type);
                break;
            case "IRON":
                type = GameLoad.GameLoadEnum.IRON;
                hp = 10;
                icon = GameLoad.ImgMap.get(type);
                break;
            default:
                return null;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setH(icon.getIconHeight());
        this.setW(icon.getIconWidth());
        this.setIcon(icon);

        return this;
    }

    /**
     * 扣血
     * @param live
     */
    @Override
    public void setLive(boolean live) {
        if(!live){
            switch (type){
                case GRASS:
                case RIVER:
                    break;
                case BRICK:
                case IRON:
                    hp--;
                    if(hp==0){
                        super.setLive(live);
                    }
                    break;
            }
        }


        super.setLive(live);
    }
}
