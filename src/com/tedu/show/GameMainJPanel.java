package com.tedu.show;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

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
	private boolean firstTime = true;
	private int enemyNum = 0;
	public GameMainJPanel() {
		init();
	}

	public void init() {
		em = ElementManager.getManager();//得到元素管理器对象
	}

	//问题：先绘画的会覆盖后绘画的
	@Override  //用于绘画的    Graphics 画笔 专门用于绘画的
	public void  paint(Graphics g) {
		super.paint(g);
		
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		//Set<GameElement> set = all.keySet(); //得到所有的key集合
		for(GameElement ge:GameElement.values()) { //迭代器
			List<ElementObj> list = all.get(ge);
			for (int i=0;i<list.size();i++) {
				list.get(i).showElement(g);//调用每个类的自己的show方法完成自己的显示
			}
		}
		// 显示当前得分
		Font font = new Font("宋体",Font.PLAIN,16);
		g.setFont(font);
		g.drawString("当前得分："+ getScore(),650,50);
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

	private   int getScore()
	{
		int score = 0;
		if (firstTime||enemyNum==0)
		{
			firstTime =false;
			java.util.List<ElementObj> elementObjs = em.getElementsByKey(GameElement.ENEMY);
			for (int i=0;i<elementObjs.size();i++)
			{
				enemyNum++;
			}
		}
		else
		{
			int liveEnemy = 0;
			List<ElementObj> elementObjs = em.getElementsByKey(GameElement.ENEMY);
			for (int i=0;i<elementObjs.size();i++)
			{
				liveEnemy++;
			}
			score = enemyNum-liveEnemy;
		}
		return score;
	}

}











