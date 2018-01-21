package ctrl.viewctrl;

import util.Const;
import java.awt.Color;
import java.awt.Cursor;
import model.MusicInfo;
import view.view.TipDialog;
import view.view.MainFrame;
import ctrl.playctrl.Client;
import view.view.NetSongPan;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class NetSongPanCtrl {
	
	private NetSongPan nPan;
	private MusicInfo mInfo;

	public NetSongPanCtrl(NetSongPan nPan,MusicInfo mInfo) {
		this.nPan = nPan;
		this.mInfo=mInfo;
		nPanelCtrl();
		lblNameCtrl();
		lblTimeCtrl();
		lblListenCtrl();
		lblDownloadCtrl();
		lblCollectCtrl();
	}
	/**
	 * ����������
	 */
	private void nPanelCtrl(){
		nPan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
	/**
	 * ��������ǩ����
	 */
	private void lblNameCtrl(){
		nPan.getLblName().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
			}
		});
	}
	/**
	 * �ղؼ���
	 */
	private void lblCollectCtrl(){
		nPan.getLblCollect().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				nPan.getLblCollect().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				nPan.getLblCollect().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						if (Client.getCL().getUser()!=null) {
							Client.getCL().addCollect(Client.getCL().getCollect().getCollectId(), mInfo.getMusicId());
						}else {
							TipDialog.getTD().showSureMsg("�ղ�ʧ��", "���ȵ����ע���˺š�");
						}
					}
				}
			}
		});
	}
	/**
	 * ������ǩ����
	 */
	private void lblListenCtrl(){
		nPan.getLblListen().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				nPan.getLblListen().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				nPan.getLblListen().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().listeningMusic(mInfo);
					}
				}
			}
		});
	}
	/**
	 * ���ر�ǩ����
	 */
	private void lblDownloadCtrl(){
		nPan.getLblDownload().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				nPan.getLblDownload().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				nPan.getLblDownload().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().downloadMusic(mInfo);
					}
				}
			}
		});
	}
	/**
	 * ʱ����ǩ����
	 */
	private void lblTimeCtrl(){
		nPan.getLblTime().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIn();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseOut();
			}
		});
	}
	/**
	 * �������������ʾ
	 */
	private void mouseIn(){
		nPan.setBackground(Const.MENU_MOUSE_IN);
	}
	/**
	 * ����˳���������ʾ
	 */
	private void mouseOut(){
		nPan.setBackground(Const.WHITE_COLOR);
		
	}
}
