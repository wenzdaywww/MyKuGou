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

public class MyVerScrollBarUI extends BasicScrollBarUI {

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
		// ���һ��Ҫ���ϰ�����Ȼ�϶���ʧЧ��
		g.translate(thumbBounds.x, thumbBounds.y); 
		g.setColor(new Color(191, 191, 191));// ���û�������ɫ
		g.drawRoundRect(5, 0, 5, thumbBounds.height-1, 5, 5); // ��һ��Բ�Ǿ���
		// �������
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);
		// ��͸��
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		// ���������ɫ�����������˽��䣬��������
//		 g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY, c.getWidth() / 2, c.getHeight(), Color.GRAY));
		// ���Բ�Ǿ���
		g2.fillRoundRect(5, 0, 6, thumbBounds.height-1, 5, 5);
	}
	/**
	 * ���°�ť�ػ�
	 */
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}
	/**
	 * ���ϰ�ť�ػ�
	 */
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setBorder(null);
		return button;
	}
	
}
