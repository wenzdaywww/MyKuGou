package ctrl.viewctrl;

import util.Const;
import model.Group;
import java.io.File;
import model.MusicInfo;
import util.MyFileUtil;
import ctrl.dao.MInfoDao;
import ctrl.dao.GMusicDao;
import util.MusicInfoUtil;
import java.util.ArrayList;
import view.view.TipDialog;
import view.view.MainFrame;
import ctrl.playctrl.Client;
import view.view.MenuDialog;
import view.view.LocalSongPan;
import javax.swing.JFileChooser;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuDialogCtrl {

	private Group group;
	private MenuDialog mDialog;
	private LocalSongPan sPanel;

	public MenuDialogCtrl(MenuDialog mDialog) {
		this.mDialog = mDialog;
		lblBuiltGroupCtrl();
		lblAddOneMusicCtrl();
		lblAddMusicDirCtrl();
		lblClearGroupCtrl();
		lblDelGroupCtrl();
		lblRenameCtrl();
		lblMusicDelCtrl();
		lblMusicLoveCtrl();
		lblMusicRenameCtrl();
	}
	/**
	 * �½��б��ǩ����
	 */
	private void lblBuiltGroupCtrl(){
		mDialog.getLblBuiltGroup().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mDialog.getLblBuiltGroup().setBackground(Const.MENU_MOUSE_IN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblBuiltGroup().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.tipType=Const.GROUP_BUILT;
					mDialog.setVisible(false);
					TipDialog.getTD().showNewName("�½��б�","�б�");
				}
			}
		});
	}
	/**
	 * ��ӱ��ظ�������
	 */
	private void lblAddOneMusicCtrl(){
		mDialog.getLblAddOneMusic().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mDialog.getLblAddOneMusic().isEnabled()) {
					mDialog.getLblAddOneMusic().setBackground(Const.MENU_MOUSE_IN);
				}else {
					mDialog.getLblAddOneMusic().setBackground(Const.MUSIC_MOUSE_IN);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblAddOneMusic().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (mDialog.getLblAddOneMusic().isEnabled()) {
						addMusic();
					}
				}
			}
		});
	}
	/**
	 * ��ӱ��������ļ��б�ǩ����
	 */
	private void lblAddMusicDirCtrl(){
		mDialog.getLblAddMusicDir().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mDialog.getLblAddMusicDir().isEnabled()) {
					mDialog.getLblAddMusicDir().setBackground(Const.MENU_MOUSE_IN);
				}else {
					mDialog.getLblAddMusicDir().setBackground(Const.MUSIC_MOUSE_IN);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblAddMusicDir().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (mDialog.getLblAddMusicDir().isEnabled()) {
						addMusicDir();
					}
				}
			}
		});
	}
	/**
	 * ����б��ǩ����
	 */
	private void lblClearGroupCtrl(){
		mDialog.getLblClearGroup().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mDialog.getLblClearGroup().setBackground(Const.MENU_MOUSE_IN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblClearGroup().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.tipType=Const.GROUP_CLEAR;
					mDialog.setVisible(false);
					TipDialog.getTD().gettCtrl().setGroup(group);
					TipDialog.getTD().showSureMsg("����б�", "ȷ����ա�"+group.getGroupName()+"���б�");
					TipDialog.getTD().getRdbtnDelFile().setVisible(true);
				}
			}
		});
	}
	/**
	 * ɾ���б��ǩ����
	 */
	private void lblDelGroupCtrl(){
		mDialog.getLblDelGroup().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mDialog.getLblDelGroup().isEnabled()) {
					mDialog.getLblDelGroup().setBackground(Const.MENU_MOUSE_IN);
				}else {
					mDialog.getLblDelGroup().setBackground(Const.MUSIC_MOUSE_IN);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblDelGroup().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (mDialog.getLblDelGroup().isEnabled()) {
						Const.tipType=Const.GROUP_DEL;
						mDialog.setVisible(false);
						TipDialog.getTD().gettCtrl().setGroup(group);
						TipDialog.getTD().showSureMsg("ɾ���б�", "ȷ��ɾ����"+group.getGroupName()+"���б�");
						TipDialog.getTD().getRdbtnDelFile().setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * �б���������ǩ����
	 */
	private void lblRenameCtrl(){
		mDialog.getLblRename().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mDialog.getLblRename().isEnabled()) {
					mDialog.getLblRename().setBackground(Const.MENU_MOUSE_IN);
				}else {
					mDialog.getLblRename().setBackground(Const.MUSIC_MOUSE_IN);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblRename().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (mDialog.getLblRename().isEnabled()) {
						Const.tipType=Const.GROUP_RENAME;
						mDialog.setVisible(false);
						TipDialog.getTD().gettCtrl().setGroup(group);
						TipDialog.getTD().showNewName("�������б�","�б�");
					}
				}
			}
		});
	}
	/**
	 * ����ɾ����ǩ����
	 */
	private void lblMusicDelCtrl(){
		mDialog.getLblMusicDel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mDialog.getLblMusicDel().setBackground(Const.MENU_MOUSE_IN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblMusicDel().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mDialog.setVisible(false);
					Const.tipType=Const.MUSIC_DEL;
					TipDialog.getTD().gettCtrl().setsPanel(sPanel);
					TipDialog.getTD().showSureMsg("����ɾ��", "ȷ��ɾ����"+sPanel.getLblName().getText()+"��������");
					TipDialog.getTD().getRdbtnDelFile().setVisible(true);
				}
			}
		});
	}
	/**
	 * �����ղر�ǩ����
	 */
	private void lblMusicLoveCtrl(){
		mDialog.getLblMusicLove().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mDialog.getLblMusicLove().setBackground(Const.MENU_MOUSE_IN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblMusicLove().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mDialog.setVisible(false);
					if (Client.isConnect&&Client.getCL().getUser()!=null) {
						if (sPanel.getmInfo().getNetMusic().equals("��������")) {
							Client.getCL().addCollect(sPanel.getmInfo());
						}
					}else {
						TipDialog.getTD().showSureMsg("��ʾ", "���ȵ����ע���˺š�");
					}
				}
			}
		});
	}
	/**
	 * ������������ǩ����
	 */
	private void lblMusicRenameCtrl(){
		mDialog.getLblMusicRename().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mDialog.getLblMusicRename().setBackground(Const.MENU_MOUSE_IN);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mDialog.getLblMusicRename().setBackground(Const.WHITE_COLOR);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mDialog.setVisible(false);
					Const.tipType=Const.MUSIC_RENAME;
					TipDialog.getTD().gettCtrl().setsPanel(sPanel);
					TipDialog.getTD().showNewName("����������","����");
				}
			}
		});
	}
	/**
	 * ��ӵ��׸���
	 */
	private void addMusic(){
		String filePath = null;
		String type;
		String musicPath;
		String musictypePath = null;
		JFileChooser fileChooser = new JFileChooser(Const.histryPath);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter  filter = new FileNameExtensionFilter("��Ƶ�ļ���*mp3&*wav��", "mp3","wav");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		MenuDialog.getMD().setVisible(false);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION){       
			filePath= fileChooser.getSelectedFile().getAbsolutePath();//���������ѡ����ļ��е�·��
			Const.histryPath=new File(Const.histryPath).getParent();
			musictypePath=MyFileUtil.addOneMusic(filePath);
			type=musictypePath.substring(0, 3);
			musicPath=musictypePath.substring(3, musictypePath.length());
			if (type.equals("mp3")) {//���MP3�ļ�
				MusicInfoUtil.mp3Info(musicPath);
				addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
						musicPath, MusicInfoUtil.getMp3Time(musicPath));
			}else if (type.equals("wav")){//���WAV�ļ�
				MusicInfoUtil.wavInfo(musicPath);
				addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
						musicPath, MusicInfoUtil.getWavTime(musicPath));
			}
			MainFrame.getMF().getMFC().showMusic();
		}
	}
	/**
	 * ��Ӹ����ļ���
	 */
	private void addMusicDir(){
		String dirPath = null;
		JFileChooser fileChooser = new JFileChooser(Const.histryPath);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		MenuDialog.getMD().setVisible(false);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION){       
			dirPath= fileChooser.getSelectedFile().getAbsolutePath();//���������ѡ����ļ��е�·��
			Const.histryPath=dirPath;
			TipDialog.getTD().showRunState("��Ӹ���", "������Ӹ����У����Եȡ�����");
			TipDialog.getTD().getBtnOk().setVisible(false);
			TipDialog.getTD().getBtnCancel().setVisible(false);
			Const.canHide=false;
		}
		if (dirPath!=null) {
			new UpdateMusic(dirPath).start();
		}
	}
	/**
	 * ��Ӹ������������ݿ�
	 * @param groupId
	 * @param musicName
	 * @param singer
	 * @param musicPath
	 * @param musicTime
	 */
	private boolean addToSqlGroup(String groupId,String musicName,String singer,String musicPath,String musicTime){
		boolean isOk=false;
		MInfoDao mDao=new MInfoDao();
		GMusicDao gmDao=new GMusicDao();
		MusicInfo mInfo=new MusicInfo(null, musicName, singer,musicPath, musicTime, "0","��������");
		if (mDao.addMusic(mInfo)) {
			if (gmDao.addGMusic(groupId, mDao.getMaxId())) {
				isOk=true;
			}
		}
		return isOk;
	}
	/**
	 * ��̨��������߳�
	 * @author wWw
	 *
	 */
	private class UpdateMusic extends Thread{
		private  ArrayList<String> mList;
		private String dirPath;
		public UpdateMusic(String dirPath) {
			this.dirPath=dirPath;
		}
		@Override
		public synchronized void run() {
			super.run();
			String type;
			String musicPath;
			mList=MyFileUtil.addMusicDir(dirPath);
			int num=mList.size();
			int no=0;
			for (int i = 0; i < mList.size(); i++) {
				type=mList.get(i).substring(0, 3);
				musicPath=mList.get(i).substring(3, mList.get(i).length());
				if (type.equals("mp3")) {
					MusicInfoUtil.mp3Info(musicPath);
					if (addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
							musicPath, MusicInfoUtil.getMp3Time(musicPath))) {
						no++;
					}
				}else if(type.equals("wav")){
					MusicInfoUtil.wavInfo(musicPath);
					if (addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
							musicPath, MusicInfoUtil.getWavTime(musicPath))) {
						no++;
					}
				}
				TipDialog.getTD().showRunState("��Ӹ���", "��Ӹ������б�"+group.getGroupName()+"���У��Ѿ������"+no+"/"+num+"�����Եȡ�����");
				TipDialog.getTD().getBtnOk().setVisible(false);
				TipDialog.getTD().getBtnCancel().setVisible(false);
			}
			TipDialog.getTD().setVisible(false);
			Const.canHide=true;
			MainFrame.getMF().getMFC().showMusic();
		}
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void setsPanel(LocalSongPan sPanel) {
		this.sPanel = sPanel;
	}

}
