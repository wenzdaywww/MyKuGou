package ctrl.viewctrl;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import ctrl.playctrl.Client;
import util.Const;
import view.view.GroupPanel;
import view.view.MainFrame;
import view.view.UserDialog;

public class UserDialogCtrl {
	
	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private UserDialog uDialog;

	public UserDialogCtrl(UserDialog uDialog) {
		this.uDialog = uDialog;
		lblExitCtrl();
		lblTypeCtrl();
		btnOkCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
		textUserIdCtrl();
		pwdLFieldCtrl();
		textRNameCtrl();
		pwdRFieldCtrl();
	}
	/**
	 * ȷ����ť����
	 */
	private void btnOkCtrl(){
		uDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
					if (uDialog.getBtnOk().getText().equals("����")) {
						if (Client.isConnect) {
							Client.getCL().login(uDialog.getTextId().getText(), uDialog.getPwdLField().getText());
						}
					}else if (uDialog.getBtnOk().getText().equals("�˳��˺�")) {
						if (Client.isConnect) {
							Client.getCL().initLogin();
							MainFrame.getMF().addLoveMusicPanel(new GroupPanel("�������ʾ�ղظ���"));
							MainFrame.getMF().addLoveLastPanel(new JPanel());
						}
					}else if (uDialog.getBtnOk().getText().equals("ע��")) {
						if (Client.isConnect) {
							Client.getCL().regiset(uDialog.getTextName().getText(), uDialog.getPwdRField().getText());
							uDialog.getTextName().setText("");
							uDialog.getPwdRField().setText("");
							uDialog.showLogin();
							uDialog.setVisible(false);
						}
					}
				}
			}
		});
	}
	/**
	 * ȡ����ť����
	 */
	private void btnCancelCtrl(){
		uDialog.getBtnCancel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * �رձ�ǩ����
	 */
	private void lblExitCtrl(){
		uDialog.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				uDialog.getLblExit().setForeground(Const.BRIGHT_WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				uDialog.getLblExit().setForeground(Const.DARK_WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * ��ʾ���ͱ�ǩ����
	 */
	private void lblTypeCtrl(){
		uDialog.getLblType().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				uDialog.getLblType().setForeground(new Color(0, 0, 255, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				uDialog.getLblType().setForeground(new Color(0, 0, 255, 180));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (uDialog.getTypeUi()==uDialog.isLoginUi()) {
						uDialog.showRegist();
					}else {
						uDialog.showLogin();
						if (Client.getCL().isLogin()) {
							uDialog.getBtnOk().setText("�˳��˺�");
						}else {
							uDialog.getBtnOk().setText("����");
						}
					}
				}
			}
		});
	}
	/**
	 * ��ʾ������������
	 */
	private void mainPanelCtrl(){
		uDialog.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				uDialog.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		uDialog.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=uDialog.getX();
				jdy=uDialog.getY();
			}
		});
	}
	/**
	 * �û�����ID�ı������
	 */
	private void textUserIdCtrl(){
		uDialog.getTextId().addKeyListener(new KeyAdapter() {
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
	 * �û����������ı������
	 */
	private void pwdLFieldCtrl(){
		uDialog.getPwdLField().addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyTyped(KeyEvent e) {
				if (uDialog.getPwdLField().getText().length()<20) {
				}else {
					e.consume();
				}
			}
		});
	}
	/**
	 * �û�ע���û����ı������
	 */
	private void textRNameCtrl(){
		uDialog.getTextName().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (uDialog.getTextName().getText().length()<20) {
				}else {
					e.consume();
				}
			}
		});
	}
	/**
	 * �û�ע�������ı������
	 */
	private void pwdRFieldCtrl(){
		uDialog.getPwdRField().addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyTyped(KeyEvent e) {
				if (uDialog.getPwdRField().getText().length()<20) {
				}else {
					e.consume();
				}
			}
		});
	}
}
