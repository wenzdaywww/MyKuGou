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
	 * ���ֹ���ļ�������
	 * @param mPanel
	 */
	public MusicPanelCtrl(MusicPanel mPanel) {
		this.mPanel=mPanel;
		mTabelModelCtr();
	}
	/**
	 * �û��ղصļ�������
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
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 
				String musicId=(String)(mPanel.getTableModel().getValueAt(row,0));
				if (e.getButton()==MouseEvent.BUTTON3) {
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("ɾ���ղ�����", MenuItemCtrl.DEL_USER_MUSIC, musicId,false));
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
	/**
	 * ����������б����
	 */
	private void mTabelModelCtr(){
		mPanel.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //�����λ�� 
				int col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //�����λ�� 
				String musicId=(String)(mPanel.getTableModel().getValueAt(row,0));
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						Const.musicId=musicId;
						switch (col) {
						case 1:
							Const.type=Const.RENAME_MUSIC_NAME;
							TipDialog.getTD().showMusicInfo("�޸ĸ�����",musicId, "����");
							break;
						case 2:
							Const.type=Const.RENAME_SINGER;
							TipDialog.getTD().showMusicInfo("�޸ĸ�����", musicId,"����");
							break;
						case 4:
							Const.type=Const.RENAME_MUSIC_TIME;
							TipDialog.getTD().showMusicInfo("�޸ĸ���ʱ��", musicId,"����ʱ��");
							break;
						case 5:
							Const.type=Const.RENAME_PLAY_COUNT;
							TipDialog.getTD().showMusicInfo("�޸Ĳ��Ŵ���", musicId,"���Ŵ���");
							break;
						default:
							break;
						}
					}
				}else if (e.getButton()==MouseEvent.BUTTON3){
					MainFrame.getMF().getPopupMenu().removeAll();
					MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("ɾ������", MenuItemCtrl.DEL_MUSIC,musicId));
					if (Client.getCL().getmClassList()!=null) {
						ArrayList<MClass> mClassList=Client.getCL().getmClassList();
						for (int i = 0; i < mClassList.size(); i++) {
							MainFrame.getMF().getPopupMenu().add(new MenuItemCtrl("�������"+mClassList.get(i).getName()+"����������", 
									MenuItemCtrl.ADD_MUSIC_TO_CLASS,mClassList.get(i).getClassId(),musicId));
						}
					}
					MainFrame.getMF().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
}
