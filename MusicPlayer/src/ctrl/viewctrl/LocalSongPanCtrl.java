package ctrl.viewctrl;

import util.Const;
import model.Group;
import model.Collect;
import java.awt.Color;
import java.awt.Cursor;
import view.view.TipDialog;
import view.view.MainFrame;
import java.util.ArrayList;
import ctrl.playctrl.Client;
import view.view.MenuDialog;
import view.view.MusicInfoDlg;
import view.view.LocalSongPan;
import ctrl.playctrl.PlayMusic;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;

public class LocalSongPanCtrl {

	private Group group;
	private Collect collect;
	private LocalSongPan sPanel;
	private String playCount="0";
	/**@see trueΪ���صĸ�������falseΪ�����ղصĸ���*/
	private boolean isLocal=true;
	private String musicStyle="mp3";
	/**
	 * ���ظ�����
	 * @param sPanel
	 * @param group
	 */
	public LocalSongPanCtrl(LocalSongPan sPanel,Group group) {
		this.sPanel=sPanel;
		this.group=group;
		isLocal=true;
		sPanelCtrl();
		lblNameCtrl();
		lblDelCtrl();
		lblLoveCtrl();
		musicStyle=sPanel.getmInfo().getMusicPath().substring(sPanel.getmInfo().getMusicPath().length()-3);
		playCount=sPanel.getmInfo().getPlayCount();
	}
	/**
	 * ���������
	 * @param sPanel
	 * @param collect
	 */
	public LocalSongPanCtrl(LocalSongPan sPanel,Collect collect){
		this.sPanel=sPanel;
		this.collect=collect;
		isLocal=false;
		sPanelCtrl();
		lblNameCtrl();
		lblLoveCtrl();
	}
	/**
	 * ��������ǩ����
	 */
	private void lblNameCtrl(){
		sPanel.getLblName().addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (isLocal) {
					showInfo(e.getXOnScreen(), e.getYOnScreen());
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		sPanel.getLblName().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isLocal) {
					mouseInShow();
				}else {
					isPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (isLocal) {
					mOutOrStopShow();
				}else {
					noPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isLocal) {
					if (e.getButton()==MouseEvent.BUTTON1) {
						if (e.getClickCount()==2) {
							playLocalSong();
						}
						MenuDialog.getMD().setVisible(false);
						if (Const.canHide) {
							TipDialog.getTD().setVisible(false);
						}
					}else if (e.getButton()==MouseEvent.BUTTON3) {
						MenuDialog.getMD().shwoMusicMenu();
						MenuDialog.getMD().setVisible(true);
						MenuDialog.getMD().getmCtrl().setsPanel(sPanel);
						MenuDialog.getMD().setLocation(e.getXOnScreen(), e.getYOnScreen()+5);
					}
					MusicInfoDlg.getMID().setVisible(false);
				}else {	//�����ղ��б��еĸ���
					if (e.getClickCount()==2) {
						Client.getCL().listeningMusic(sPanel.getmInfo());
					}
				}
			}
		});
	}
	/**
	 * ɾ����ǩ����
	 */
	private void lblDelCtrl(){
		sPanel.getLblDel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isLocal) {
					mouseInShow();
					sPanel.getLblDel().setBorder(BorderFactory.createLineBorder(Color.blue));
				}
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (isLocal) {
					mOutOrStopShow();
					sPanel.getLblDel().setBorder(null);
				}
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isLocal) {
					if (e.getButton()==MouseEvent.BUTTON1) {
						Const.tipType=Const.MUSIC_DEL;
						TipDialog.getTD().gettCtrl().setsPanel(sPanel);
						TipDialog.getTD().gettCtrl().setGroup(group);
						TipDialog.getTD().showSureMsg("����ɾ��", "ȷ��ɾ����"+sPanel.getLblName().getText()+"��������");
						TipDialog.getTD().getRdbtnDelFile().setVisible(true);
						MenuDialog.getMD().setVisible(false);
					}
				}
			}
		});
	}
	/**
	 * �ղر�ǩ����
	 */
	private void lblLoveCtrl(){
		sPanel.getLblLove().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isLocal) {
					mouseInShow();
				}else {
					isPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
				sPanel.getLblLove().setBorder(BorderFactory.createLineBorder(Color.blue));
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (isLocal) {
					mOutOrStopShow();
				}else {
					noPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
				sPanel.getLblLove().setBorder(null);
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isLocal) {
					if (e.getButton()==MouseEvent.BUTTON1) {
						MenuDialog.getMD().setVisible(false);
						if (Const.canHide) {
							TipDialog.getTD().setVisible(false);
						}
						if (Client.isConnect&&Client.getCL().getUser()!=null) {
							if (sPanel.getmInfo().getNetMusic().equals("��������")) {
								Client.getCL().addCollect(sPanel.getmInfo());
							}
						}else {
							TipDialog.getTD().showSureMsg("��ʾ", "���ȵ����ע���˺š�");
						}
					}
				} else {
					if (Client.isConnect) {
						Client.getCL().delCollect(collect.getCollectId(), sPanel.getmInfo().getMusicId());
					}
				}
			}
		});
	}
	/**
	 * ����������
	 */
	private void sPanelCtrl(){
		sPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (isLocal) {
					showInfo(e.getXOnScreen(), e.getYOnScreen());
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		//����������
		sPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isLocal) {
					mouseInShow();
				}else {
					isPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (isLocal) {
					mOutOrStopShow();
					MusicInfoDlg.getMID().setVisible(false);
				}else {
					noPlayShow();
					sPanel.getLblDel().setVisible(false);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isLocal) {//�ղظ����ĵ��
					if (e.getButton()==MouseEvent.BUTTON1) {
						if (e.getClickCount()==2) {
							playLocalSong();
						}
						MenuDialog.getMD().setVisible(false);
						if (Const.canHide) {
							TipDialog.getTD().setVisible(false);
						}
					}else if (e.getButton()==MouseEvent.BUTTON3) {
						MenuDialog.getMD().setVisible(true);
						MenuDialog.getMD().shwoMusicMenu();
						MenuDialog.getMD().getmCtrl().setsPanel(sPanel);
						MenuDialog.getMD().setLocation(e.getXOnScreen(), e.getYOnScreen()+5);
					}
					MusicInfoDlg.getMID().setVisible(false);
				}else {	//�����ղ��б��еĸ���
					if (e.getClickCount()==2) {
						Client.getCL().listeningMusic(sPanel.getmInfo());
					}
				}
			}
		});
	}
	/**
	 * �������������ʾ
	 */
	private void mouseInShow(){
		if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {
			isPlayShow();
		}else {
			sPanel.setBackground(Const.MUSIC_MOUSE_IN);
			sPanel.getLblDel().setVisible(true);
			sPanel.getLblLove().setVisible(true);
		}
	}
	/**
	 * ����Ƴ���������ʾ
	 */
	public void mOutOrStopShow(){
		if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {
			isPlayShow();
		}else {
			noPlayShow();
		}
	}
	/**
	 * ��ʾ������Ϣ
	 * @param x
	 * @param y
	 */
	private void showInfo(int x,int y){
		if (MenuDialog.getMD().isVisible()) {
			MusicInfoDlg.getMID().setVisible(false);
		}else {
			MusicInfoDlg.getMID().setVisible(true);
		}
		playCount=sPanel.getmInfo().getPlayCount();
		MusicInfoDlg.getMID().showInfo(x, y+40,
				sPanel.getLblName().getText(), musicStyle, playCount);
	}
	/**
	 * δ���Ÿ�������ʾ
	 */
	public void noPlayShow(){
		sPanel.setBackground(Const.WHITE_COLOR);
		sPanel.getLblDel().setVisible(false);
		sPanel.getLblLove().setVisible(false);
	}
	/**
	 * ���ڲ��Ÿ�������ʾ
	 */
	public void isPlayShow(){
		sPanel.setBackground(Const.MENU_MOUSE_IN);
		sPanel.getLblDel().setVisible(true);
		sPanel.getLblLove().setVisible(true);
	}
	/**
	 * ���ű�������
	 */
	private void playLocalSong(){
		Const.isListening=false;
		if (PlayMusic.getPM().getMusicPath().equals("musicPath")) {//��һ�β���
			PlayMusic.getPM().startPlay(sPanel.getmInfo());
		}else if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {//���ű�������
			if (!Const.isPlaying) {
				PlayMusic.getPM().startPlay(sPanel.getmInfo());
			}
		}else {//ѡ��������������
			//ȡ�����ڲ��ŵĸ�������ʾ
			for (int i = 0; i < Const.localGroupPanel.size(); i++) {
				ArrayList<LocalSongPan> sList =Const.localSongPanel.get(Const.localGroupPanel.get(i).getGroup().getGroupId());
				for (int j = 0; j < sList.size(); j++) {
					if (PlayMusic.getPM().getMusicPath().equals(sList.get(j).getmInfo().getMusicPath())) {
						sList.get(j).getsCtrl().noPlayShow();
					}
				}
			}
			PlayMusic.getPM().closePlay();
			PlayMusic.getPM().startPlay(sPanel.getmInfo());
		}
		MainFrame.getMF().getMFC().addToRecent(sPanel.getmInfo().getMusicId());
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	
}
