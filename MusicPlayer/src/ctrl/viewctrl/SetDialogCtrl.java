package ctrl.viewctrl;

import util.Const;
import java.io.File;
import util.DataUtil;
import java.awt.Color;
import java.awt.Cursor;
import view.view.SetDialog;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class SetDialogCtrl {
	
	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private SetDialog sDialog;
	
	public SetDialogCtrl(SetDialog sDialog) {
		this.sDialog = sDialog;
		panelCtrl();
		btnOkCtrl();
		txtNumCtrl();
		btnCancelCtrl();
		lblExitCtrl();
		lblDownloadCtrl();
		lblListeningCtrl();
		lblDefaultCtrl();
	}
	/**
	 * 默认路径设置
	 */
	private void lblDefaultCtrl(){
		sDialog.getLblDefault().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sDialog.getLblDefault().setForeground(Const.MENU_MOUSE_IN);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sDialog.getLblDefault().setForeground(Color.gray);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					String tempPath="Temp/";
					String downloadPath="Music/";
					File temp=new File(tempPath);
					File download=new File(downloadPath);
					if (!temp.exists()) {
						temp.mkdir();
					}
					if (!download.exists()) {
						download.mkdir();
					}
					Const.listeingPath=tempPath;
					Const.downloadPath=downloadPath;
					sDialog.getTxtListening().setText(tempPath);
					sDialog.getTxtDownload().setText(downloadPath);
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * 应用按钮监听
	 */
	private void  btnOkCtrl(){
		sDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					sDialog.setVisible(false);
					Const.listeingPath=sDialog.getTxtListening().getText();
					Const.downloadPath=sDialog.getTxtDownload().getText();
					if (sDialog.getTxtNumber().getText().equals("")) {
						Const.downloadNum=1;
					}else {
						Const.downloadNum=Integer.parseInt(sDialog.getTxtNumber().getText());
					}
					DataUtil.saveData(Const.listeingPath, Const.downloadPath, Const.playStyle, Const.downloadNum);
				}
			}
		});
	}
	/**
	 * 取消按钮监听
	 */
	private void btnCancelCtrl(){
		sDialog.getBtnCancel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					sDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * 关闭标签监听
	 */
	private void lblExitCtrl(){
		sDialog.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sDialog.getLblExit().setForeground(Const.BRIGHT_WHITE);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sDialog.getLblExit().setForeground(Const.DARK_WHITE);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					sDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * 下载路径标签监听
	 */
	private void lblDownloadCtrl(){
		sDialog.getLblDownloadPath().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sDialog.getLblDownloadPath().setForeground(Const.MENU_MOUSE_IN);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sDialog.getLblDownloadPath().setForeground(Color.gray);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					String dirPath = null;
					JFileChooser fileChooser = new JFileChooser(Const.histryPath);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					sDialog.setVisible(false);
					int returnVal = fileChooser.showOpenDialog(fileChooser);
					if(returnVal == JFileChooser.APPROVE_OPTION){       
						dirPath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径
						String musicPath=dirPath.replaceAll("\\\\", "/");
						Const.histryPath=dirPath;
						sDialog.getTxtDownload().setText(musicPath+"/");
						sDialog.setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * 缓存路径监听
	 */
	private void lblListeningCtrl(){
		sDialog.getLblListeningPath().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sDialog.getLblListeningPath().setForeground(Const.MENU_MOUSE_IN);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sDialog.getLblListeningPath().setForeground(Color.gray);
				sDialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					String dirPath = null;
					JFileChooser fileChooser = new JFileChooser(Const.histryPath);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					sDialog.setVisible(false);
					int returnVal = fileChooser.showOpenDialog(fileChooser);
					if(returnVal == JFileChooser.APPROVE_OPTION){       
						dirPath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的路径
						String musicPath=dirPath.replaceAll("\\\\", "/");
						Const.histryPath=dirPath;
						sDialog.getTxtListening().setText(musicPath+"/");
						sDialog.setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * 下载数量限制
	 */
	private void txtNumCtrl(){
		sDialog.getTxtNumber().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int keyChar=e.getKeyChar();
				if (keyChar>=KeyEvent.VK_1&&keyChar<=KeyEvent.VK_3&&sDialog.getTxtNumber().getText().length()<1) {
				}else {
					e.consume();
				}
			}
		});
	}
	/**
	 * 面板移动
	 */
	private void panelCtrl(){
		sDialog.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				sDialog.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		sDialog.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=sDialog.getX();
				jdy=sDialog.getY();
			}
		});
	}
}
