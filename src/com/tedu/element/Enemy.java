package com.tedu.element;

import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{


    private boolean isMoving=false;
    private int hp=10;//血量

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);

    }

    @Override
    public ElementObj createElement(String str) {
        Random random = new Random();
        int x = random.nextInt(new GameJFrame().getX());
        int y = random.nextInt(new GameJFrame().getY());
        this.setX(x);
        this.setY(y);
        this.setW(50);
        this.setH(50);
        this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
        this.obj_type = GameElement.ENEMY;
        return this;
    }

    @Override
    public void collide(GameElement type) {
        switch (type) {
            case MAPS:
            case ENEMY:
            case PLAY:
            case BOSS:
                this.isMoving = false;
                break;
            case PLAYFILE:
                hp--;
                if (hp <= 0) this.setLive(false);
                break;

        }
    }

}