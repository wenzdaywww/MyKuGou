package ctrl.viewctrl;

import util.Const;
import view.view.DownloadPan;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class DownloadPanCtrl {
	
	private DownloadPan dPan;

	public DownloadPanCtrl(DownloadPan dPan) {
		this.dPan = dPan;
		dPanCtrl();
		lblNameCtrl();
		proBarCtrl();
	}
	/**
	 * ����������
	 */
	private void dPanCtrl(){
		dPan.addMouseListener(new MouseAdapter() {
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
	 * ���ֱ�ǩ����
	 */
	private void lblNameCtrl(){
		dPan.getLblName().addMouseListener(new MouseAdapter() {
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
	 * ����������
	 */
	private void proBarCtrl(){
		dPan.getProBar().addMouseListener(new MouseAdapter() {
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
	 * ��������ʾ
	 */
	private void mouseIn(){
		dPan.setBackground(Const.MUSIC_MOUSE_IN);
	}
	/**
	 * ����˳�������
	 */
	private void mouseOut(){
		dPan.setBackground(Const.WHITE_COLOR);
	}
}
