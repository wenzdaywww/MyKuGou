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
	 * 下载条监听
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
	 * 名字标签监听
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
	 * 进度条监听
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
	 * 鼠标进入显示
	 */
	private void mouseIn(){
		dPan.setBackground(Const.MUSIC_MOUSE_IN);
	}
	/**
	 * 鼠标退出下载条
	 */
	private void mouseOut(){
		dPan.setBackground(Const.WHITE_COLOR);
	}
}
