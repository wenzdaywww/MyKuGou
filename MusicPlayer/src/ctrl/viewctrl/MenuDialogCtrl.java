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
	 * 新建列表标签监听
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
					TipDialog.getTD().showNewName("新建列表","列表");
				}
			}
		});
	}
	/**
	 * 添加本地歌曲监听
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
	 * 添加本地音乐文件夹标签监听
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
	 * 清空列表标签监听
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
					TipDialog.getTD().showSureMsg("清空列表", "确定清空“"+group.getGroupName()+"”列表？");
					TipDialog.getTD().getRdbtnDelFile().setVisible(true);
				}
			}
		});
	}
	/**
	 * 删除列表标签监听
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
						TipDialog.getTD().showSureMsg("删除列表", "确定删除“"+group.getGroupName()+"”列表？");
						TipDialog.getTD().getRdbtnDelFile().setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * 列表重命名标签监听
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
						TipDialog.getTD().showNewName("重命名列表","列表");
					}
				}
			}
		});
	}
	/**
	 * 歌曲删除标签监听
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
					TipDialog.getTD().showSureMsg("歌曲删除", "确定删除“"+sPanel.getLblName().getText()+"”歌曲？");
					TipDialog.getTD().getRdbtnDelFile().setVisible(true);
				}
			}
		});
	}
	/**
	 * 歌曲收藏标签监听
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
						if (sPanel.getmInfo().getNetMusic().equals("网络音乐")) {
							Client.getCL().addCollect(sPanel.getmInfo());
						}
					}else {
						TipDialog.getTD().showSureMsg("提示", "请先登入或注册账号。");
					}
				}
			}
		});
	}
	/**
	 * 歌曲重命名标签监听
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
					TipDialog.getTD().showNewName("歌曲重命名","歌曲");
				}
			}
		});
	}
	/**
	 * 添加单首歌曲
	 */
	private void addMusic(){
		String filePath = null;
		String type;
		String musicPath;
		String musictypePath = null;
		JFileChooser fileChooser = new JFileChooser(Const.histryPath);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter  filter = new FileNameExtensionFilter("音频文件（*mp3&*wav）", "mp3","wav");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		MenuDialog.getMD().setVisible(false);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION){       
			filePath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径
			Const.histryPath=new File(Const.histryPath).getParent();
			musictypePath=MyFileUtil.addOneMusic(filePath);
			type=musictypePath.substring(0, 3);
			musicPath=musictypePath.substring(3, musictypePath.length());
			if (type.equals("mp3")) {//添加MP3文件
				MusicInfoUtil.mp3Info(musicPath);
				addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
						musicPath, MusicInfoUtil.getMp3Time(musicPath));
			}else if (type.equals("wav")){//添加WAV文件
				MusicInfoUtil.wavInfo(musicPath);
				addToSqlGroup(group.getGroupId(), MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
						musicPath, MusicInfoUtil.getWavTime(musicPath));
			}
			MainFrame.getMF().getMFC().showMusic();
		}
	}
	/**
	 * 添加歌曲文件夹
	 */
	private void addMusicDir(){
		String dirPath = null;
		JFileChooser fileChooser = new JFileChooser(Const.histryPath);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		MenuDialog.getMD().setVisible(false);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		if(returnVal == JFileChooser.APPROVE_OPTION){       
			dirPath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径
			Const.histryPath=dirPath;
			TipDialog.getTD().showRunState("添加歌曲", "正在添加歌曲中，请稍等。。。");
			TipDialog.getTD().getBtnOk().setVisible(false);
			TipDialog.getTD().getBtnCancel().setVisible(false);
			Const.canHide=false;
		}
		if (dirPath!=null) {
			new UpdateMusic(dirPath).start();
		}
	}
	/**
	 * 添加歌曲到本地数据库
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
		MusicInfo mInfo=new MusicInfo(null, musicName, singer,musicPath, musicTime, "0","本地音乐");
		if (mDao.addMusic(mInfo)) {
			if (gmDao.addGMusic(groupId, mDao.getMaxId())) {
				isOk=true;
			}
		}
		return isOk;
	}
	/**
	 * 后台导入歌曲线程
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
				TipDialog.getTD().showRunState("添加歌曲", "添加歌曲到列表“"+group.getGroupName()+"”中，已经添加了"+no+"/"+num+"，请稍等。。。");
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
