package ctrl.viewctrl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ctrl.client.Client;
import util.Const;
import view.view.TipDialog;

public class TipDialogCtrl {

	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;
	private TipDialog tDialog;

	public TipDialogCtrl(TipDialog tDialog) {
		this.tDialog = tDialog;
		btnOkCtrl();
		txtNameCtrl();
		lblExitCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
	}
	/**
	 * ȷ����ť����
	 */
	private void btnOkCtrl(){
		tDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					switch (Const.type) {
					case Const.SUCCEED:
						tDialog.setVisible(false);
						break;
					case Const.FAIL:
						tDialog.setVisible(false);
						break;
					case Const.MAIN_EXIT:
						System.exit(0);
						Const.type=Const.MAIN_EXIT;
						break;
					case Const.RENAME_MUSIC_NAME:
						renameMusicInfo();
						break;
					case Const.RENAME_SINGER:
						renameMusicInfo();
						break;
					case Const.RENAME_MUSIC_TIME:
						renameMusicInfo();
						break;
					case Const.RENAME_PLAY_COUNT:
						renameMusicInfo();
						break;
					case Const.ADD_CLASS:
						addClass();
						break;
					case Const.DEL_CLASS:
						delClass();
						break;
					case Const.RENAME_CLASS:
						renameClass();
						break;
					case Const.DEL_MUSIC:
						delMusic();
						break;
					case Const.RENAME_USER_NAME:
						renameUser();
						break;
					case Const.RENAME_PWD:
						renameUser();
						break;
					case Const.DEL_USER_MUSIC:
						Const.type=Const.SUCCEED;
						if (Client.isConnect) {
							Client.getCL().delCollectMusic(Const.collectId, Const.musicId);
							Client.getCL().getUserMusic(Const.collectId);
						}else {
							TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
						}
						break;
					default:
						break;
					}
					tDialog.setVisible(false);
					tDialog.getTxtName().setText("");
				}else if(e.getButton()==MouseEvent.BUTTON3){
					
				}
			}
		});
	}
	/**
	 * �޸��û���Ϣ
	 */
	private void renameUser(){
		tDialog.setVisible(false);
		Const.type=Const.SUCCEED;
		String renameType="name";
		if (Const.type==Const.RENAME_PWD) {
			renameType="pwd";
		}
		if (Client.isConnect) {
			if (!tDialog.getTxtName().getText().equals("")) {
				Client.getCL().renameUser(Const.userId, renameType, tDialog.getTxtName().getText());
			}else {
				Const.type=Const.FAIL;
				TipDialog.getTD().showSureMsg("����ʧ��", "�޸��û�����Ϣ����Ϊ��");
			}
		}else {
			TipDialog.getTD().showSureMsg("��������", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * ɾ������
	 */
	private void delMusic(){
		Const.type=Const.SUCCEED;
		if (Client.isConnect) {
			Client.getCL().delMusic(Const.musicId);
		}else {
			TipDialog.getTD().showSureMsg("��������", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * ������ַ���
	 */
	private void addClass(){
		Const.type=Const.SUCCEED;
		if (Client.isConnect) {
			if (!tDialog.getTxtName().getText().equals("")) {
				Client.getCL().addClass(tDialog.getTxtName().getText());
			}else {
				TipDialog.getTD().showSureMsg("����ʧ��", "�޸ĸ�������Ϣ����Ϊ��");
			}
		}else {
			TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * 
	 */
	private void delClass(){
		Const.type=Const.SUCCEED;
		if (Client.isConnect) {
			Client.getCL().delClass(Const.classId);
		}else {
			TipDialog.getTD().showSureMsg("��������", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * �޸ķ�������
	 */
	private void renameClass(){
		Const.type=Const.SUCCEED;
		if (Client.isConnect) {
			if (!tDialog.getTxtName().getText().equals("")) {
				Client.getCL().renameClass(Const.classId, tDialog.getTxtName().getText());
			}else {
				TipDialog.getTD().showSureMsg("����ʧ��", "�޸ĸ����������Ϣ����Ϊ��");
			}
		}else {
			TipDialog.getTD().showSureMsg("�ϴ�����", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * �޸ĸ�����
	 */
	private void renameMusicInfo(){
		tDialog.setVisible(false);
		String renameType="musicId";
		switch (Const.type) {
		case Const.RENAME_MUSIC_NAME:
			renameType="musicName";
			break;
		case Const.RENAME_SINGER:
			renameType="singer";
			break;
		case Const.RENAME_MUSIC_TIME:
			renameType="musicTime";
			break;
		case Const.RENAME_PLAY_COUNT:
			renameType="playCount";
			break;
		default:
			break;
		}
		if (Client.isConnect) {
			if (!tDialog.getTxtName().getText().equals("")) {
				Client.getCL().reviseMusic(Const.musicId, renameType, tDialog.getTxtName().getText());
			}else {
				Const.type=Const.FAIL;
				TipDialog.getTD().showSureMsg("����ʧ��", "�޸ĸ�������Ϣ����Ϊ��");
			}
		}else {
			TipDialog.getTD().showSureMsg("��������", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * ȡ����ť����
	 */
	private void btnCancelCtrl(){
		tDialog.getBtnCancel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					tDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * �����ı������
	 */
	private void txtNameCtrl(){
		tDialog.getTxtName().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tDialog.getTxtName().getText().length()<20) {
					
				}else {
					e.consume();
				}
			}
		});
	}
	/**
	 * �رձ�ǩ����
	 */
	private void lblExitCtrl(){
		tDialog.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tDialog.getLblExit().setForeground(Const.BRIGHT_WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tDialog.getLblExit().setForeground(Const.DARK_WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					tDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * ��ʾ������������
	 */
	private void mainPanelCtrl(){
		tDialog.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				tDialog.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		tDialog.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=tDialog.getX();
				jdy=tDialog.getY();
			}
		});
	}

}
