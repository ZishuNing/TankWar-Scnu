package com.tedu.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//窗体：关闭、显示、最大最小化，需嵌入面板，启动主线程
public class GameJFrame extends JFrame {
    public static int GameX = 900;
    public static int GameY = 600;

//    public static int GameMs = 10; // 刷新时间

    private JPanel jPanel =null; //正在显示的面板
    private KeyListener keyListener=null;//键盘监听
    private MouseMotionListener mouseMotionListener=null; //鼠标监听
    private MouseListener mouseListener=null;
    private Thread thead=null;  //游戏主线程
    public GameJFrame() {
        init();
    }
    public void init() {
        this.setSize(GameX, GameY); //窗体大小
        this.setTitle("坦克大战");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出+关闭
        this.setLocationRelativeTo(null);//居中显示
    }

    public void start() {
        if(jPanel!=null) {
            this.add(jPanel);
        }
        if(keyListener !=null) {
            this.addKeyListener(keyListener);
        }
        if(thead !=null) {
            thead.start();//启动线程
        }
//		界面的刷新
        this.setVisible(true);//显示界面
        //如果jPanel是runnable的实体对象子类
        if(this.jPanel instanceof Runnable) {
            Runnable run = (Runnable)this.jPanel;
            Thread th = new Thread(run);
            th.start();
        }
    }

    /*set注入：通过set方法注入配置文件中读取的数据;讲配置文件
     * 中的数据赋值为类的属性
     * 构造注入：需要配合构造方法
     * spring 中ioc 进行对象的自动生成，管理。
     * */
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }
    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }
    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }
    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }
    public void setThead(Thread thead) {
        this.thead = thead;
    }
}
