package ctrl.viewctrl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ctrl.client.Client;
import util.Const;
import view.view.TipDialog;
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
		btnOkCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
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
					if (Client.isConnect) {
						if (!uDialog.getTextName().getText().equals("")&&!uDialog.getPwdRField().getText().equals("")) {
							Client.getCL().addUser(uDialog.getTextName().getText(), uDialog.getPwdRField().getText());
						}else {
							TipDialog.getTD().showSureMsg("注册失败", "用户名或密码不能为空");
						}
					}else {
						TipDialog.getTD().showSureMsg("操作失败", "请确认网络是否已连接");
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
