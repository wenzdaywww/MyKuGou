package ctrl.viewctrl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.LineBorder;

import ctrl.client.BackThread;
import ctrl.client.Client;
import util.Const;
import view.view.LoginFrame;
import view.view.TipDialog;

public class LoginFrameCtrl {
	
	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private LoginFrame lFrame;

	public LoginFrameCtrl(LoginFrame uFrame) {
		this.lFrame = uFrame;
		Client.getCL();
		BackThread.getBT();
		lblMinCtrl();
		lblLoginCtrl();
		mainPanelCtrl();
		lblExitCtrl();
		textIdCtrl();
	}
	/**
	 * ���밴ť����
	 */
	private void lblLoginCtrl(){
		lFrame.getLblLogin().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lFrame.getLblLogin().setForeground(Const.BRIGHT_WHITE);
				lFrame.getLblLogin().setBorder(new LineBorder(Color.white));
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lFrame.getLblLogin().setForeground(Const.DARK_WHITE);
				lFrame.getLblLogin().setBorder(null);
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().login(lFrame.getTextId().getText(), lFrame.getPwdField().getText());
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "����ȷ�������Ƿ�������");
					}
				}
			}
		});
	}
	/**
	 * �˳���ť����
	 */
	private void lblExitCtrl(){
		lFrame.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lFrame.getLblExit().setForeground(Const.BRIGHT_WHITE);
				lFrame.getLblExit().setBorder(new LineBorder(Color.white));
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lFrame.getLblExit().setForeground(Const.DARK_WHITE);
				lFrame.getLblExit().setBorder(null);
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					System.exit(0);
				}
			}
		});
	}
	/**
	 * ��С����ǩ����
	 */
	private void lblMinCtrl(){
		lFrame.getLblMin().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lFrame.getLblMin().setForeground(Const.BRIGHT_WHITE);
				lFrame.getLblMin().setBorder(new LineBorder(Color.yellow));
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lFrame.getLblMin().setForeground(Const.DARK_WHITE);
				lFrame.getLblMin().setBorder(null);
				lFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					lFrame.setExtendedState(Frame.ICONIFIED);
				}
			}
		});
	}
	/**
	 * �û�����ID�ı������
	 */
	private void textIdCtrl(){
		lFrame.getTextId().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()>=KeyEvent.VK_0&&e.getKeyChar()<=KeyEvent.VK_9) {
				}else {
					e.consume();
				}
			}
		});
	}
	/**
	 * ��ʾ������������
	 */
	private void mainPanelCtrl(){
		lFrame.getMainPane().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lFrame.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		lFrame.getMainPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=lFrame.getX();
				jdy=lFrame.getY();
			}
		});
	}
}
