package ctrl.viewctrl;

import model.MClass;
import java.awt.Color;
import java.awt.Cursor;
import view.view.MainFrame;
import ctrl.playctrl.Client;
import view.view.MusicClassPan;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MClassPanelCtrl {
	private MClass mClass;
	private MusicClassPan mPanel;
	/**
	 * 音乐分类监听
	 * @param mPanel
	 * @param mClass
	 */
	public MClassPanelCtrl(MusicClassPan mPanel,MClass mClass){
		this.mClass=mClass;
		this.mPanel=mPanel;
		lblNameCtrl();
	}
	/**
	 * 分类标签监听
	 */
	private void lblNameCtrl(){
		mPanel.getLblName().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mPanel.getLblName().setForeground(Color.blue);
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mPanel.getLblName().setForeground(Color.black);
				MainFrame.getMF().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().getClassMusic(mClass.getClassId());
						MainFrame.getMF().repaint();
					}
				}
			}
		});
	}
}
