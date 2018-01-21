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
	 * 歌曲条监听
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
	 * 歌曲名标签监听
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
	 * 收藏监听
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
							TipDialog.getTD().showSureMsg("收藏失败", "请先登入或注册账号。");
						}
					}
				}
			}
		});
	}
	/**
	 * 试听标签监听
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
	 * 下载标签监听
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
	 * 时长标签监听
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
	 * 鼠标进入歌曲条显示
	 */
	private void mouseIn(){
		nPan.setBackground(Const.MENU_MOUSE_IN);
	}
	/**
	 * 鼠标退出歌曲条显示
	 */
	private void mouseOut(){
		nPan.setBackground(Const.WHITE_COLOR);
		
	}
}
