package com.tedu.element;

import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj{

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
        return this;
    }


}