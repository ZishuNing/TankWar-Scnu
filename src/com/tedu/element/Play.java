package com.tedu.element;
import java.awt.Graphics;

import javax.swing.ImageIcon;
public class Play extends ElementObj{

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }
    /**
     * 面向对象中第1个思想： 对象自己的事情自己做
     */
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }
    //重写键盘输入
    public void keyClick(boolean bl, int key) {
        if (bl) {
            switch (key) {
                case 37:
                    this.setX(this.getX() - 10);
                    break;
                case 38:
                    this.setY(this.getY() - 10);
                    break;
                case 39:
                    this.setX(this.getX() + 10);
                    break;
                case 40:
                    this.setY(this.getY() + 10);
                    break;
                default:
                    break;
            }
        }
    }
}
