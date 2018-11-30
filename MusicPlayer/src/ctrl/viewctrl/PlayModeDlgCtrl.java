package ctrl.viewctrl;

import util.Const;
import util.DataUtil;
import java.awt.Color;
import util.PictureUtil;
import view.view.MainFrame;
import view.view.PlayModeDlg;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class PlayModeDlgCtrl {
	
	private PlayModeDlg playMode;

	public PlayModeDlgCtrl(PlayModeDlg playMode) {
		this.playMode = playMode;
		lblPlayOneCtrl();
		lblLoopOneCtrl();
		lblPlayOrderCtrl();
		lblPlayListCtrl();
		lblPlayRandomCtrl();
	}
	/**
	 * �������ű�ǩ����
	 */
	private void lblPlayOneCtrl(){
		playMode.getLblPlayOne().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMode.getLblPlayOne().setForeground(Color.yellow);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playMode.getLblPlayOne().setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.playStyle=Const.PLAY_ONE;
					MainFrame.getMF().getLblPlayMode().setIcon(PictureUtil.PLAY_ONE_ICO);
					MainFrame.getMF().getLblPlayMode().setToolTipText("��������");
					PlayModeDlg.getPM().setVisible(false);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * ����ѭ����ǩ����
	 */
	private void lblLoopOneCtrl(){
		playMode.getLblLoopOne().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMode.getLblLoopOne().setForeground(Color.yellow);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playMode.getLblLoopOne().setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.playStyle=Const.LOOP_ONE;
					MainFrame.getMF().getLblPlayMode().setIcon(PictureUtil.LOOP_ONE_ICO);
					MainFrame.getMF().getLblPlayMode().setToolTipText("����ѭ��");
					PlayModeDlg.getPM().setVisible(false);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * ˳�򲥷ű�ǩ����
	 */
	private void lblPlayOrderCtrl(){
		playMode.getLblPlayOrder().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMode.getLblPlayOrder().setForeground(Color.yellow);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playMode.getLblPlayOrder().setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.playStyle=Const.PLAY_ORDER;
					MainFrame.getMF().getLblPlayMode().setIcon(PictureUtil.PLAY_ORDER_ICO);
					MainFrame.getMF().getLblPlayMode().setToolTipText("˳�򲥷�");
					PlayModeDlg.getPM().setVisible(false);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * �б��ű�ǩ����
	 */
	private void lblPlayListCtrl(){
		playMode.getLblPlayList().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMode.getLblPlayList().setForeground(Color.yellow);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playMode.getLblPlayList().setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.playStyle=Const.PLAY_LIST;
					MainFrame.getMF().getLblPlayMode().setIcon(PictureUtil.PLAY_LIST_ICO);
					MainFrame.getMF().getLblPlayMode().setToolTipText("�б���");
					PlayModeDlg.getPM().setVisible(false);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * ������ű�ǩ����
	 */
	private void lblPlayRandomCtrl(){
		playMode.getLblPlayRandom().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMode.getLblPlayRandom().setForeground(Color.yellow);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playMode.getLblPlayRandom().setForeground(Color.white);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.playStyle=Const.PLAY_RANDOM;
					MainFrame.getMF().getLblPlayMode().setIcon(PictureUtil.PLAY_RANDOM_ICO);
					MainFrame.getMF().getLblPlayMode().setToolTipText("�������");
					PlayModeDlg.getPM().setVisible(false);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	
}
