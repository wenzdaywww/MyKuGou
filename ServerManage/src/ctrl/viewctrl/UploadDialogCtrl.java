package ctrl.viewctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import ctrl.client.Client;
import util.Const;
import view.view.TipDialog;
import view.view.UploadDialog;

public class UploadDialogCtrl {
	
	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private UploadDialog uDialog;

	public UploadDialogCtrl(UploadDialog uDialog) {
		this.uDialog = uDialog;
		lblExitCtrl();
		btnOkCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
	}
	/**
	 * 确定按钮监听
	 */
	private void btnOkCtrl(){
		uDialog.getBtnUpload().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
					if (!uDialog.getTextPath().getText().equals("")) {
						File file=new File(uDialog.getTextPath().getText());
						if (file.exists()) {
							if (Client.isConnect) {
								if (Const.canUpload) {
									Client.getCL().isCanUpload(uDialog.getTextSinger().getText(), 
											uDialog.getTextName().getText(), uDialog.getTextTime().getText(), uDialog.getTextPath().getText());
									uDialog.showMusicInfo("", "", "", "");
								}else {
									TipDialog.getTD().showSureMsg("上传失败", "有文件在上传，请稍等");
								}
							}else {
								TipDialog.getTD().showSureMsg("上传错误", "请确认网络是否已连接");
							}
						}
					}else {
						TipDialog.getTD().showSureMsg("文件地址错误", "要上传的文件地址不能为空");
					}
				}
			}
		});
	}
	/**
	 * 取消按钮监听
	 */
	private void btnCancelCtrl(){
		uDialog.getBtnCancel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * 关闭标签监听
	 */
	private void lblExitCtrl(){
		uDialog.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				uDialog.getLblExit().setForeground(Const.BRIGHT_WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				uDialog.getLblExit().setForeground(Const.DARK_WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					uDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * 提示窗体主面板监听
	 */
	private void mainPanelCtrl(){
		uDialog.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				uDialog.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		uDialog.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=uDialog.getX();
				jdy=uDialog.getY();
			}
		});
	}
}
