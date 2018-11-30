package ctrl.viewctrl;

import util.Const;
import model.Collect;
import java.awt.Cursor;
import util.PictureUtil;
import javax.swing.JPanel;
import java.util.ArrayList;
import view.view.TipDialog;
import view.view.MainFrame;
import ctrl.playctrl.Client;
import view.view.GroupPanel;
import view.view.MenuDialog;
import view.view.LocalSongPan;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GroupPanelCtrl {

	private Collect collect;
	private GroupPanel groupPanel;
	/**
	 * �����б��չ��
	 * @param groupPanel
	 * @param down
	 */
	public GroupPanelCtrl(GroupPanel groupPanel,int down){
		this.groupPanel=groupPanel;
		openDownloadCtrl();
	}
	/**
	 * �����б�������
	 * @param groupPanel
	 */
	public GroupPanelCtrl(GroupPanel groupPanel) {
		this.groupPanel=groupPanel;
		grouPanelCtrl();
		lblOpenCtrl();
		lblNameCtrl();
		lblOpetionCtrl();
	}
	/**
	 * �����ղ��б����
	 * @param groupPanel
	 * @param collect
	 */
	public GroupPanelCtrl(GroupPanel groupPanel,Collect collect){
		this.groupPanel=groupPanel;
		this.collect=collect;
		openCollect();
	}
	/**
	 * ����������
	 */
	private void grouPanelCtrl(){
		groupPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						clickGroup();
					}
					MenuDialog.getMD().setVisible(false);
					if (Const.canHide) {
						TipDialog.getTD().setVisible(false);
					}
				}else if (e.getButton()==MouseEvent.BUTTON3) {
					MenuDialog.getMD().shwoGroupMenu();
					openMenuDialog(e.getXOnScreen(), e.getYOnScreen()+5);
				}
			}
		});
	}
	/**
	 * չ����ǩ����
	 */
	private void lblOpenCtrl(){
		groupPanel.getLblOpen().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					clickGroup();
					MenuDialog.getMD().setVisible(false);
					if (Const.canHide) {
						TipDialog.getTD().setVisible(false);
					}
				}
			}
		});
	}
	/**
	 * ��������ǩ����
	 */
	private void lblNameCtrl(){
		groupPanel.getLblName().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					MenuDialog.getMD().setVisible(false);
					if (Const.canHide) {
						TipDialog.getTD().setVisible(false);
					}
				}
			}
		});
	}
	/**
	 * �ղ��б��չ����ǩ����
	 */
	private void openCollect(){
		groupPanel.getLblOpen().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					MainFrame.getMF().getLovePanel().removeAll();
					MainFrame.getMF().getLovePanel().repaint();
					if (groupPanel.isOpen()) {
						groupPanel.setOpen(false);
						MainFrame.getMF().addLoveMusicPanel(groupPanel);
						groupPanel.getLblOpen().setIcon(PictureUtil.OPEN_ICO);
					}else {
						MainFrame.getMF().addLoveMusicPanel(groupPanel);
						for (int i = 0; i < Client.getCL().getCollectMusicList().size(); i++) {
							LocalSongPan sPanel=new LocalSongPan(Client.getCL().getCollectMusicList().get(i), collect);
							MainFrame.getMF().addLoveMusicPanel(sPanel);
						}
						groupPanel.setOpen(true);
						groupPanel.getLblOpen().setIcon(PictureUtil.CLOSE_ICO);
					}
					MainFrame.getMF().addLoveLastPanel(new JPanel());
				}
			}
		});
	}
	/**
	 * �����б�չ������
	 */
	private void openDownloadCtrl(){
		groupPanel.getLblOpen().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (groupPanel.isOpen()) {
						Client.getCL().closeDownloadList();
					}else {
						Client.getCL().openDownloadList();
					}
				}
			}
		});
	}
	/**
	 * ѡ���ǩ����
	 */
	private void lblOpetionCtrl(){
		groupPanel.getLblOption().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					MenuDialog.getMD().shwoGroupMenu();
					if (Const.canHide) {
						TipDialog.getTD().setVisible(false);
					}
					openMenuDialog(e.getXOnScreen()-20, e.getYOnScreen()+10);
				}
			}
		});
	}
	/**
	 * ����������
	 */
	private void clickGroup(){
		MainFrame.getMF().getListPanel().removeAll();
		MainFrame.getMF().repaint();
		if (!groupPanel.isOpen()) {	//չ����ʾ����
			Const.openGroupId=groupPanel.getGroup().getGroupId();//��ȡչ�������Id
			groupPanel.setOpen(true);
			groupPanel.getLblOpen().setIcon(PictureUtil.CLOSE_ICO);
			//��ȡ�÷����е����и�����
			ArrayList<LocalSongPan> slist=Const.localSongPanel.get(groupPanel.getGroup().getGroupId());
			for (int i = 0; i < Const.localGroupPanel.size(); i++) {
				//������������չ����ͼ��
				if (!groupPanel.getGroup().getGroupId().equals(Const.localGroupPanel.get(i).getGroup().getGroupId())) {
					Const.localGroupPanel.get(i).getLblOpen().setIcon(PictureUtil.OPEN_ICO);
					Const.localGroupPanel.get(i).setOpen(false);
				}
				//��������б����͸�����
				MainFrame.getMF().addLocalMusicPanel(Const.localGroupPanel.get(i));
				if (groupPanel.getGroup().getGroupId().equals(Const.localGroupPanel.get(i).getGroup().getGroupId())) {
					for (int j = 0; j < slist.size(); j++) {
						slist.get(j).getsCtrl().mOutOrStopShow();
						MainFrame.getMF().addLocalMusicPanel(slist.get(j));
					}
				}
			}
		}else {
			Const.openGroupId="0";
			groupPanel.setOpen(false);
			groupPanel.getLblOpen().setIcon(PictureUtil.OPEN_ICO);
			for (int i = 0; i < Const.localGroupPanel.size(); i++) {
				MainFrame.getMF().addLocalMusicPanel(Const.localGroupPanel.get(i));
			}
		}
		MainFrame.getMF().addLocalLastPanel(new JPanel());
	}
	/**
	 * ��ʾ�˵��Ի���
	 * @param X
	 * @param Y
	 */
	private void openMenuDialog(int X,int Y){
		if (groupPanel.isCanDelete()) {
			MenuDialog.getMD().getLblDelGroup().setEnabled(true);
			MenuDialog.getMD().getLblRename().setEnabled(true);
		}else {
			MenuDialog.getMD().getLblDelGroup().setEnabled(false);
			MenuDialog.getMD().getLblRename().setEnabled(false);
		}
		if (groupPanel.getGroup().getGroupId().equals("2")) {
			MenuDialog.getMD().getLblAddOneMusic().setEnabled(false);
			MenuDialog.getMD().getLblAddMusicDir().setEnabled(false);
		}else {
			MenuDialog.getMD().getLblAddOneMusic().setEnabled(true);
			MenuDialog.getMD().getLblAddMusicDir().setEnabled(true);
		}
		MenuDialog.getMD().setLocation(X, Y);
		MenuDialog.getMD().getmCtrl().setGroup(groupPanel.getGroup());
		MenuDialog.getMD().setVisible(true);
	}
}
