package com.tedu.element;

import com.tedu.manager.EnemyManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{
    private boolean isMoving=false;
    private String name;
    private Color color;
    EnemyManager.EnemyDir dir= EnemyManager.EnemyDir.RIGHT;
    private int CurrentHp =5;//血量
    private int MaxHp=5;
    private BloodBar bar=new BloodBar();
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
        drawName(g);
        bar.draw(g);
    }
    private void drawName(Graphics g){
        g.setColor(color);
        g.setFont(new Font("宋体",Font.BOLD,20));
        g.drawString(name,getX()-30,getY()-15);
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
        this.name = EnemyManager.getRandomName();
        this.color = EnemyManager.getRandomColor();
        this.MaxHp=CurrentHp;
        EnemyManager.RegisterEnemy(this);
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
                CurrentHp--;
                //增加计分
                if (CurrentHp <= 0) {
                    this.setLive(false);
                    EnemyManager.score++;
                }
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
    public String getName(){
       return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    //血条
    class BloodBar{
        public static final int BAR_LENGTH=50;
        public static final int BAR_HEIGHT=5;
        public void draw(Graphics g){
            //底色
            g.setColor(Color.RED);
            g.fillRect(getX(),getY()-10,BAR_LENGTH,BAR_HEIGHT);
            //当前血量
            g.setColor(Color.GREEN);
            int w=BAR_LENGTH* CurrentHp /MaxHp;
            g.fillRect(getX(),getY()-10,w,BAR_HEIGHT);
            //边框
            g.setColor(Color.BLACK);
            g.drawRect(getX(),getY()-10,BAR_LENGTH,BAR_HEIGHT);
        }
    }
}