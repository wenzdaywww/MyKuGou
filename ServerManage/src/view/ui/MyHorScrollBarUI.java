package view.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyHorScrollBarUI extends BasicScrollBarUI {

	@Override
	protected void configureScrollBarColors() {
		trackColor = Color.WHITE;
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		super.paintTrack(g, c, trackBounds);
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		// 这句一定要加上啊，不然拖动就失效了
		g.translate(thumbBounds.x, thumbBounds.y); 
		g.setColor(new Color(191, 191, 191));// 设置滑动条颜色
		g.drawRoundRect(0, 5, thumbBounds.width-1, 5, 5, 5); // 画一个圆角矩形
		// 消除锯齿
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// 半透明
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		// 设置填充颜色，这里设置了渐变，由下往上
//		 g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
		// 填充圆角矩形
		g2.fillRoundRect(0, 5, thumbBounds.width-1, 6, 5, 5);
	}
	/**
	 * 向下按钮重绘
	 */
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}
	/**
	 * 向上按钮重绘
	 */
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}
	
}
