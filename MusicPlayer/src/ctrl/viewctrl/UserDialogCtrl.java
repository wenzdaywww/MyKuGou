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
	 * 确定按钮监听
	 */
	private void btnOkCtrl(){
		uDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
					if (uDialog.getBtnOk().getText().equals("登入")) {
						if (Client.isConnect) {
							Client.getCL().login(uDialog.getTextId().getText(), uDialog.getPwdLField().getText());
						}
					}else if (uDialog.getBtnOk().getText().equals("退出账号")) {
						if (Client.isConnect) {
							Client.getCL().initLogin();
							MainFrame.getMF().addLoveMusicPanel(new GroupPanel("登入后显示收藏歌曲"));
							MainFrame.getMF().addLoveLastPanel(new JPanel());
						}
					}else if (uDialog.getBtnOk().getText().equals("注册")) {
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
	 * 取消按钮监听
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
	 * 关闭标签监听
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
	 * 显示类型标签监听
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
							uDialog.getBtnOk().setText("退出账号");
						}else {
							uDialog.getBtnOk().setText("登入");
						}
					}
				}
			}
		});
	}
	/**
	 * 提示窗体主面板监听
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
	 * 用户登入ID文本框监听
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
	 * 用户登入密码文本框监听
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
	 * 用户注册用户名文本框监听
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
	 * 用户注册密码文本框监听
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
