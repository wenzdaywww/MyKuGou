package ctrl.viewctrl;

import util.Const;
import model.Group;
import model.MusicInfo;
import util.MyFileUtil;
import model.GroupMusic;
import ctrl.dao.MInfoDao;
import ctrl.dao.GroupDao;
import ctrl.dao.GMusicDao;
import java.util.ArrayList;
import view.view.MainFrame;
import view.view.TipDialog;
import view.view.LocalSongPan;
import java.awt.event.KeyEvent;
import ctrl.playctrl.PlayMusic;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class TipDialogCtrl {

	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private Group group;
	private TipDialog tDialog;
	private LocalSongPan sPanel;

	public TipDialogCtrl(TipDialog tDialog) {
		this.tDialog = tDialog;
		btnOkCtrl();
		txtNameCtrl();
		lblExitCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
	}
	/**
	 * 确定按钮监听
	 */
	private void btnOkCtrl(){
		tDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					switch (Const.tipType) {
					case Const.GROUP_BUILT:
						groupBuilt();
						break;
					case Const.GROUP_CLEAR:
						clearGroup();
						break;
					case Const.GROUP_DEL:
						delGroup();
						break;
					case Const.GROUP_RENAME:
						renameGroup();
						break;
					case Const.MUSIC_RENAME:
						renameMusic();
						break;
					case Const.MUSIC_DEL:
						deleMusic();
						break;
					case Const.MUSIC_LOVE:

						break;
					case Const.MUSIC_MOVE:

						break;
					case Const.SUCCEED:
						tDialog.setVisible(false);
						break;
					case Const.FAIL:
						tDialog.setVisible(false);
						break;
					case Const.EXIT:
						MainFrame.getMF().deleteTrayIco();
						System.exit(0);
						break;
					default:
						break;
					}
					MainFrame.getMF().getMFC().showMusic();
				}
			}
		});
	}
	/**
	 * 取消按钮监听
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
	 * 命名文本框监听
	 */
	private void txtNameCtrl(){
		tDialog.getTxtName().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((tDialog.getTxtName().getText().length()<16)) {
				} else {
					e.consume(); 
				}
			}
		});
	}
	/**
	 * 关闭标签监听
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
	 * 提示窗体主面板监听
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
	/**
	 * 添加列表
	 */
	private void groupBuilt(){
		GroupDao gDao=new GroupDao();
		if (gDao.addGroup(tDialog.getTxtName().getText())) {
			Const.tipType=Const.SUCCEED;
			tDialog.showSureMsg("列表创建成功", "列表：“"+tDialog.getTxtName().getText()+"”新建成功！");
		}else {
			Const.tipType=Const.FAIL;
			tDialog.showSureMsg("列表创建失败", "列表：“"+tDialog.getTxtName().getText()+"”新建失败！");
		}
		tDialog.getTxtName().setText("");
	}
	/**
	 * 删除列表
	 */
	private void delGroup(){
		tDialog.showSureMsg("删除列表", "正在删除列表，请稍等。。。");
		new DelMusic(group, "删除").start();
	}
	/**
	 * 清空列表歌曲
	 */
	private void clearGroup(){
		tDialog.showSureMsg("清空列表", "正在清空列表，请稍等。。。");
		new DelMusic(group, "清空").start();
	}
	/**
	 * 重命名列表
	 */
	private void renameGroup(){
		GroupDao gDao=new GroupDao();
		if (!tDialog.getTxtName().getText().equals("")) {
			if (gDao.renameGroupName(group.getGroupId(), tDialog.getTxtName().getText())) {
				Const.tipType=Const.SUCCEED;
				tDialog.showSureMsg("重命名列表成功", "列表重命名为“"+tDialog.getTxtName().getText()+"”");
			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg("重命名列表成功", "列表：“"+group.getGroupName()+"”重命名失败！");
			}
			tDialog.getTxtName().setText("");
		}
	}
	/**
	 * 删除歌曲
	 */
	private void deleMusic(){
		if (Const.isPlaying&&!group.getGroupId().equals("2")) {
			if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {
				PlayMusic.getPM().closePlay();
				MainFrame.getMF().getMFC().initPlayShow();
			}
		}
		GMusicDao gmDao=new GMusicDao();
		MInfoDao mDao=new MInfoDao();
		if ((gmDao.deleteGMusic(group.getGroupId(), sPanel.getmInfo().getMusicId()))) {//删除歌曲所在分组
			if (!group.getGroupId().equals("2")) {//不在最近播放列表中则可删除音乐信息
				mDao.deleteMusic(sPanel.getmInfo().getMusicId());
				if (gmDao.isExist("2", sPanel.getmInfo().getMusicId())) {//如果删除的歌曲在最近播放列表中则也从最近播放列表中删除
					gmDao.deleteGMusic("2", sPanel.getmInfo().getMusicId());
				}
			}
			Const.tipType=Const.SUCCEED;
			String strMsg="歌曲“"+sPanel.getLblName().getText()+"”已从列表中删除！";
			if (tDialog.getRdbtnDelFile().isSelected()) {//删除音乐文件
				if (MyFileUtil.delFile(sPanel.getmInfo().getMusicPath())) {
					strMsg="歌曲“"+sPanel.getLblName().getText()+"”文件已删除！";
				}else {
					strMsg="歌曲“"+sPanel.getLblName().getText()+"”文件删除失败！";
				}
			}
			tDialog.showSureMsg("删除歌曲成功", strMsg);
		}else {
			Const.tipType=Const.FAIL;
			tDialog.showSureMsg("删除歌曲成功", "歌曲“"+sPanel.getLblName().getText()+"”从列表中删除失败！");
		}
	}
	/**
	 * 重命名歌曲
	 */
	private void renameMusic(){
		if (!tDialog.getTxtName().getText().equals("")) {
			MInfoDao mDao=new MInfoDao();
			if (mDao.renameMusic(sPanel.getmInfo().getMusicId(), tDialog.getTxtName().getText())) {
				Const.tipType=Const.SUCCEED;
				tDialog.showSureMsg("重命名歌曲成功", "歌曲重命名为“"+tDialog.getTxtName().getText()+"”");
			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg("重命名歌曲成功", "歌曲：“"+sPanel.getLblName().getText()+"”重命名失败！");
			}
			tDialog.getTxtName().setText("");
		}
	}
	/**
	 * 删除列表中音乐文件
	 */
	private boolean delGroupMusicFile(){
		int num=0;
		boolean isOk=false;
		ArrayList<LocalSongPan> sList=Const.localSongPanel.get(group.getGroupId());
		for (int i = 0; i < sList.size(); i++) {
			if (MyFileUtil.delFile(sList.get(i).getmInfo().getMusicPath())) {
				num++;
			}else {
				tDialog.showSureMsg("删除歌曲成功", "歌曲“"+sList.get(i).getLblName().getText()+"”文件删除失败！");
			}
		}
		if (num==sList.size()) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * 歌曲删除线程
	 * @author wWw
	 *
	 */
	private class DelMusic extends Thread{
		private Group group;
		private String type;
		public DelMusic(Group group,String type) {
			this.group=group;
			this.type=type;
		}
		@Override
		public synchronized void run() {
			super.run();
			int no=0,num=0;
			MInfoDao mDao=new MInfoDao();
			GroupDao gDao=new GroupDao();
			GMusicDao gmDao=new GMusicDao();
			String strMsg="列表：“"+group.getGroupName()+"”"+type+"成功，不包括音乐文件！";
			if (gmDao.getMusicNum(group.getGroupId())>0) {
				Const.tipType=Const.SUCCEED;
				ArrayList<GroupMusic> gList=gmDao.getGMusic(group.getGroupId());
				num=gList.size();
				ArrayList<MusicInfo> mList=mDao.getGroupMusic(group.getGroupId());
				//关闭正在播放的音乐
				for (int j = 0; j < mList.size(); j++) {
					if (PlayMusic.getPM().getMusicPath().equals(mList.get(j).getMusicPath())) {
						PlayMusic.getPM().closePlay();
						MainFrame.getMF().getMFC().initPlayShow();
					}
				}
				//删除音乐
				for (int i = 0; i < gList.size(); i++) {
					gmDao.deleteGMusic(group.getGroupId(), gList.get(i).getMusicId());
					if (!group.getGroupId().equals("2")) {//不在最近播放列表中则可删除音乐信息
						mDao.deleteMusic(gList.get(i).getMusicId());
						if (gmDao.isExist("2", gList.get(i).getMusicId())) {//如果删除的歌曲在最近播放列表中则也从最近播放列表中删除
							gmDao.deleteGMusic("2", gList.get(i).getMusicId());
						}
					}
					if (tDialog.getRdbtnDelFile().isSelected()) {//删除音乐文件
						if (delGroupMusicFile()) {
							strMsg="列表：“"+group.getGroupName()+"”"+type+"成功，已删除列表中所有音乐文件！";
						}else {
							strMsg="列表：“"+group.getGroupName()+"”"+type+"成功，列表中部分音乐文件无法删除！";
						}
					}
					no++;
					TipDialog.getTD().showRunState(type+"列表", "列表“"+group.getGroupName()+"”中的歌曲已经删除了"+no+"/"+num+"，请稍等。。。");
					TipDialog.getTD().getBtnOk().setVisible(false);
					TipDialog.getTD().getBtnCancel().setVisible(false);
				}

			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg(type+"列表失败", "列表：“"+group.getGroupName()+"”"+type+"失败！");
			}
			if (type.equals("删除")) {
				gDao.deleteGroup(group.getGroupId());
			}
			tDialog.showSureMsg(type+"列表成功", strMsg);
		}
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void setsPanel(LocalSongPan sPanel) {
		this.sPanel = sPanel;
	}

}
