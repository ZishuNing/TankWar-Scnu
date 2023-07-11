package com.tedu.show;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.EnemyManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主要面板
 * @author renjj
 * @功能说明 主要进行元素的显示，同时进行界面的刷新(多线程)
 * 
 * @题外话 java开发实现思考的应该是：做继承或者是接口实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
//	联动管理器
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}

	public void init() {
		em = ElementManager.getManager();//得到元素管理器对象
	}

	//问题：先绘画的会覆盖后绘画的
	@Override  //用于绘画的    Graphics 画笔 专门用于绘画的
	public void paint(Graphics g) {
		super.paint(g);


		ElementManager.AcquireReadLock();
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		//Set<GameElement> set = all.keySet(); //得到所有的key集合
		for(GameElement ge:GameElement.values()) { //迭代器
			List<ElementObj> list = all.get(ge);
			for (ElementObj obj : list) {
				obj.showElement(g);//调用每个类的自己的show方法完成自己的显示
			}
		}
		ElementManager.ReleaseReadLock();
		// 显示当前得分
		String scoreStr = "当前得分：" + EnemyManager.GetScore();
		Font font = new Font("宋体", Font.BOLD, 20);
		g.setFont(font);
		g.drawString(scoreStr, 750, 20);
	}


	@Override
	public void run() {
		while(true) {
			this.repaint();
			//一般情况下，多线程都会使用一个休眠，控制速度
			try {
				Thread.sleep(ElementManager.RefreshTime);//休眠
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}











