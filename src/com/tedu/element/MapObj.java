package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }
    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(",");
        ImageIcon icon = null;
        switch (arr[0]) {
            // 设置图片信息，图片还未加载到内存中
            case "GRASS":
                icon = new ImageIcon("image/wall/grass.png");
                break;
            case "BRICK":
                icon = new ImageIcon("image/wall/brick.png");
                break;
            case "RIVER":
                icon = new ImageIcon("image/wall/river.png");
                break;
            case "IRON":
                icon = new ImageIcon("image/wall/iron.png");
                break;
            default:
                return null;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setH(icon.getIconHeight());
        this.setW(icon.getIconWidth());
        this.setIcon(icon);
        // TODO: create and return ElementObj using the icon
        return this;
    }
}
