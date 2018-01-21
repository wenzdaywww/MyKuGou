package ctrl.viewctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;

import ctrl.client.Client;
import model.MClass;
import util.Const;
import view.view.MainFrame;
import view.view.MusicPanel;
import view.view.TipDialog;

public class MusicPanelCtrl {

	private MusicPanel mPanel;

	/**
	 * 音乐管理的监听构造
	 * @param mPanel
	 */
	public MusicPanelCtrl(MusicPanel mPanel) {
		this.mPanel=mPanel;
		mTabelModelCtr();
	}
	/**
	 * 用户收藏的监听构造
	 * @param mPanel
	 * @param i
	 */
	public MusicPanelCtrl(MusicPanel mPanel, int i) {
		this.mPanel=mPanel;
		uTabelModelCtr();
	}
	private void uTabelModelCtr(){
		mPanel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
				String musicId=(String)(mPanel.getTableModel().getValueAt(row,0));
				if (e.getButton()==MouseEvent.BUTTON3) {
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("删除收藏音乐", MenuItemCtrl.DEL_USER_MUSIC, musicId,false));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
	/**
	 * 歌曲管理的列表监听
	 */
	private void mTabelModelCtr(){
		mPanel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 
				String musicId=(String)(mPanel.getTableModel().getValueAt(row,0));
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						Const.musicId=musicId;
						switch (col) {
						case 1:
							Const.type=Const.RENAME_MUSIC_NAME;
							TipDialog.getTD().showMusicInfo("修改歌曲名",musicId, "歌曲");
							break;
						case 2:
							Const.type=Const.RENAME_SINGER;
							TipDialog.getTD().showMusicInfo("修改歌手名", musicId,"歌手");
							break;
						case 4:
							Const.type=Const.RENAME_MUSIC_TIME;
							TipDialog.getTD().showMusicInfo("修改歌曲时长", musicId,"歌曲时长");
							break;
						case 5:
							Const.type=Const.RENAME_PLAY_COUNT;
							TipDialog.getTD().showMusicInfo("修改播放次数", musicId,"播放次数");
							break;
						default:
							break;
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("删除音乐", MenuItemCtrl.DEL_MUSIC,musicId));
					if (Client.getCL().getmClassList()!=null) {
						ArrayList<MClass> mClassList=Client.getCL().getmClassList();
						for (int i = 0; i < mClassList.size(); i++) {
							MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("添加至“"+mClassList.get(i).getName()+"”歌曲分类", 
									MenuItemCtrl.ADD_MUSIC_TO_CLASS,mClassList.get(i).getClassId(),musicId));
						}
					}
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
