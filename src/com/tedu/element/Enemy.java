package com.tedu.element;

import com.tedu.manager.EnemyManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{
    private boolean isMoving=false;
    EnemyManager.EnemyDir dir= EnemyManager.EnemyDir.RIGHT;
    private int hp=5;//血量
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
        //EnemyManager.getEnemyManager().RegisterEnemy(this);
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
                //增加计分
                if (hp <= 0) this.setLive(false);
                break;
        }
    }
    @Override
    public void move(long... time) {
        int speed = 1; // 移动速度
        if (isMoving) {
            int newX = getX() + speed; // 计算新的 x 坐标
            dir= EnemyManager.EnemyDir.RIGHT;
            if (newX >= GameJFrame.GameX - getW()) {
                newX = GameJFrame.GameX - getW(); // 超出屏幕边界时，修正 x 坐标
                isMoving = false; // 停止移动
            }
            setX(newX); // 更新 x 坐标
        } else {
            int newX = getX() - speed; // 计算新的 x 坐标
            dir= EnemyManager.EnemyDir.LEFT;
            if (newX <= 0) {
                newX = 0; // 超出屏幕边界时，修正 x 坐标
                isMoving = true; // 开始移动
            }
            setX(newX); // 更新 x 坐标
        }
    }

    @Override
    protected void updateImg(long... time) {
        if (dir == EnemyManager.EnemyDir.LEFT) {
            this.setIcon(new ImageIcon("image/tank/bot/bot_left.png"));
        } else if (dir == EnemyManager.EnemyDir.RIGHT) {
            this.setIcon(new ImageIcon("image/tank/bot/bot_right.png"));
        } else if (dir == EnemyManager.EnemyDir.UP) {
            this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
        } else if (dir == EnemyManager.EnemyDir.DOWN) {
            this.setIcon(new ImageIcon("image/tank/bot/bot_down.png"));
        }
    }
}