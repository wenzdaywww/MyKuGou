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
	 * ȷ����ť����
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
							TipDialog.getTD().showSureMsg("ע��ʧ��", "�û��������벻��Ϊ��");
						}
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
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
