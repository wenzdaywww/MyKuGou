package ctrl.viewctrl;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import ctrl.client.Client;
import util.Const;
import util.MusicInfoUtil;
import util.MyFileUtil;
import view.view.MainFrame;
import view.view.TipDialog;
import view.view.UploadDialog;
import view.view.UserDialog;

public class MainFrameCtrl {

	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private MainFrame mFrame;
	private static int numPage=0;

	public MainFrameCtrl(MainFrame mFrame) {
		this.mFrame=mFrame;
		mainPanelCtrl();
		showItemCtrl();
		exitItemCtrl();	
		lblExitCtrl();
		lblAddMusicCtrl();
		lblSearchMusicCtrl();
		lblMusicClassCtrl();
		lblTotalMusicCtrl();
		lblMFirstPageCtrl();
		lblMPreviousPageCtrl();
		lblMNextPageCtrl();
		lblMLastPageCtrl();
		lblMinCtrl();
		trayIcoCtrl();
		textSearchMusicCtrl();
		lblUserManageCtrl();
		lblMusicManageCtrl();
		lblAddUserCtrl();
		lblAllUserCtrl();
		lblSearchUserCtrl();
		textSearchUserCtrl();
	}
	/**
	 * �رձ�ǩ����
	 */
	private void lblExitCtrl(){
		mFrame.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblExit().setBorder(new LineBorder(mFrame.musicColor));
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblExit().setBorder(null);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.type=Const.MAIN_EXIT;
					TipDialog.getTD().showSureMsg("�˳�", "���Ҫ�뿪����");
				}
			}
		});
	}
	/**
	 * ��С����ǩ����
	 */
	private void lblMinCtrl(){
		mFrame.getLblMin().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMin().setBorder(new LineBorder(mFrame.musicColor));
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMin().setBorder(null);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mFrame.setVisible(false);
				}
			}
		});
	}
	/**
	 * ��Ӹ�����ǩ����
	 */
	private void lblAddMusicCtrl(){
		mFrame.getLblAddMusic().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblAddMusic().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblAddMusic().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						String filePath = null;
						String type;
						String musicPath;
						String musictypePath = null;
						JFileChooser fileChooser = new JFileChooser("F:/");
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						FileFilter  filter = new FileNameExtensionFilter("��Ƶ�ļ���*mp3&*wav��", "mp3","wav");
						fileChooser.setFileFilter(filter);
						fileChooser.setAcceptAllFileFilterUsed(false);
						int returnVal = fileChooser.showOpenDialog(fileChooser);
						if(returnVal == JFileChooser.APPROVE_OPTION){       
							filePath= fileChooser.getSelectedFile().getAbsolutePath();//���������ѡ����ļ��е�·��
							musictypePath=MyFileUtil.addOneMusic(filePath);
							type=musictypePath.substring(0, 3);
							musicPath=musictypePath.substring(3, musictypePath.length());
							if (type.equals("mp3")) {//���MP3�ļ�
								MusicInfoUtil.mp3Info(musicPath);
								UploadDialog.getUD().showMusicInfo(MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
										musicPath, MusicInfoUtil.getMp3Time(musicPath));
							}else if (type.equals("wav")){//���WAV�ļ�
								MusicInfoUtil.wavInfo(musicPath);
								UploadDialog.getUD().showMusicInfo(MusicInfoUtil.getSongName(), MusicInfoUtil.getArtist(), 
										musicPath, MusicInfoUtil.getWavTime(musicPath));
							}
							UploadDialog.getUD().setVisible(true);
						}
					}else {
						TipDialog.getTD().showSureMsg("�������", "��ȷ�������Ƿ�������");
					}
				}
			}
		});
	}
	/**
	 * ����������ǩ����
	 */
	private void lblSearchMusicCtrl(){
		mFrame.getLblSearchMusic().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblSearchMusic().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblSearchMusic().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					searchMusic();
				}
			}
		});
	}
	/**
	 * ������Ϣ���������
	 */
	private void textSearchMusicCtrl(){
		mFrame.getTextMusicSearch().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					searchMusic();
				}
			}
		});
	}
	/**
	 * ��������
	 */
	private void searchMusic(){
		if (Client.isConnect) {
			if (!mFrame.getTextMusicSearch().getText().equals("")) {
				if (mFrame.getTextMusicSearch().getText().equals(" ")) {
					Client.getCL().getAllMusic();
				}else {
					Client.getCL().searchMusic(mFrame.getTextMusicSearch().getText());
				}
			}
		}else {
			TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * ���������ǩ����
	 */
	private void lblMusicClassCtrl(){
		mFrame.getLblMusicClass().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMusicClass().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMusicClass().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().getAllMClass();
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
					}
				}
			}
		});
	}
	/**
	 * ���и�����ǩ����
	 */
	private void lblTotalMusicCtrl(){
		mFrame.getLblTotalMusic().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblTotalMusic().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblTotalMusic().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (Client.isConnect) {
						Client.getCL().getAllMusic();
					}else {
						TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
					}
				}
			}
		});
	}
	/**
	 * ������Ϣ��ҳ��ǩ����
	 */
	private void lblMFirstPageCtrl(){
		mFrame.getLblMFirstPage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMFirstPage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMFirstPage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					numPage=1;
					mFrame.getCly().first(mFrame.getMusicInfoPanel());
					mFrame.getLblMPreviousPage().setVisible(false);
					mFrame.getLblMNextPage().setVisible(true);
					mFrame.setMTotalPage(numPage, Client.getCL().getTotalPage());
				}
			}
		});
	}
	/**
	 * ������Ϣ��һҳ��ǩ����
	 */
	private void lblMPreviousPageCtrl(){
		mFrame.getLblMPreviousPage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMPreviousPage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMPreviousPage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mFrame.getLblMNextPage().setVisible(true);
					if (numPage>1) {
						mFrame.getLblMNextPage().setVisible(true);
						numPage--;
					}
					if(numPage==1) {
						mFrame.getLblMPreviousPage().setVisible(false);
					}
					mFrame.getCly().previous(mFrame.getMusicInfoPanel());
					mFrame.setMTotalPage(numPage, Client.getCL().getTotalPage());
				}
			}
		});
	}
	/**
	 * ������Ϣ��һҳ��ǩ����
	 */
	private void lblMNextPageCtrl(){
		mFrame.getLblMNextPage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMNextPage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMNextPage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mFrame.getLblMPreviousPage().setVisible(true);
					if (numPage<Client.getCL().getTotalPage()) {
						mFrame.getCly().next(mFrame.getMusicInfoPanel());
						numPage++;
					}
					if (numPage==Client.getCL().getTotalPage()){
						mFrame.getLblMNextPage().setVisible(false);
					}
					mFrame.setMTotalPage(numPage, Client.getCL().getTotalPage());
				}
			}
		});
	}
	/**
	 * ������Ϣĩҳ��ǩ����
	 */
	private void lblMLastPageCtrl(){
		mFrame.getLblMLastPage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMLastPage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMLastPage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mFrame.getLblMPreviousPage().setVisible(true);
					mFrame.getLblMNextPage().setVisible(false);
					numPage=Client.getCL().getTotalPage();
					mFrame.getCly().last(mFrame.getMusicInfoPanel());
					mFrame.setMTotalPage(numPage, numPage);
				}
			}
		});
	}
	/**
	 * ϵͳ����ͼ�����
	 */
	private void trayIcoCtrl(){
		mFrame.getTrayIcon().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						mFrame.setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * ϵͳ������ʾ�˵�����
	 */
	private void showItemCtrl(){
		mFrame.getShowItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mFrame.setVisible(true);
			}
		});
	}
	/**
	 * ϵͳ�����˳��˵�����
	 */
	private void exitItemCtrl(){
		mFrame.getExitItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	/**
	 * ��ʾ������������
	 */
	private void mainPanelCtrl(){
		mFrame.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mFrame.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		mFrame.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=mFrame.getX();
				jdy=mFrame.getY();
			}
		});
	}
	/**
	 * ����û���ǩ
	 */
	private void lblAddUserCtrl(){
		mFrame.getLblAddUser().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblAddUser().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblAddUser().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					UserDialog.getUD().setVisible(true);
				}
			}
		});
	}
	/**
	 * �����û���ǩ����
	 */
	private void lblAllUserCtrl(){
		mFrame.getLblAllUser().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblAllUser().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblAllUser().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Client.isConnect) {
					Client.getCL().getAllUser("All");
				}else {
					TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
				}
			}
		});
	}
	/**
	 * �����û���ǩ����
	 */
	private void lblSearchUserCtrl(){
		mFrame.getLblSearchUser().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblSearchUser().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblSearchUser().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					searchUser();
				}
			}
		});
	}
	/**
	 * ������Ϣ���������
	 */
	private void textSearchUserCtrl(){
		mFrame.getTextSearchUser().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					searchUser();
				}
			}
		});
	}
	/**
	 * �����û�
	 */
	private void searchUser(){
		if (Client.isConnect) {
			if (!mFrame.getTextSearchUser().getText().equals("")) {
				if (mFrame.getTextSearchUser().getText().equals(" ")) {
					Client.getCL().getAllUser("All");
				}else {
					Client.getCL().getAllUser(mFrame.getTextSearchUser().getText());
				}
			}
		}else {
			TipDialog.getTD().showSureMsg("����ʧ��", "��ȷ�������Ƿ�������");
		}
	}
	/**
	 * �û���Ϣ�����ǩ����
	 */
	private void lblUserManageCtrl(){
		mFrame.getLblUserManage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblUserManage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblUserManage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				showUserInfo();
			}
		});
	}
	/**
	 * ������Ϣ�����ǩ����
	 */
	private void lblMusicManageCtrl(){
		mFrame.getLblMusicManage().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMusicManage().setForeground(mFrame.musicColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMusicManage().setForeground(mFrame.pageColor);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				showMusicInfo();
			}
		});
	}
	/**
	 * ��ʾ������Ϣ�������
	 */
	private void showMusicInfo(){
		mFrame.getMainPanel().add(mFrame.getUserPanel());
		mFrame.getMainPanel().remove(mFrame.getUserPanel());
		mFrame.getMainPanel().add(mFrame.getMusicPanel());
		mFrame.getLblAddMusic().setVisible(true);
		mFrame.getLblMusicClass().setVisible(true);
		mFrame.getLblTotalMusic().setVisible(true);
		mFrame.getLblAddUser().setVisible(false);
		mFrame.getLblAllUser().setVisible(false);
		mFrame.repaint();
	}
	/**
	 * ��ʾ�û���Ϣ�������
	 */
	private void showUserInfo(){
		mFrame.getMainPanel().add(mFrame.getMusicPanel());
		mFrame.getMainPanel().remove(mFrame.getMusicPanel());
		mFrame.getMainPanel().add(mFrame.getUserPanel());
		mFrame.getLblAddMusic().setVisible(false);
		mFrame.getLblMusicClass().setVisible(false);
		mFrame.getLblTotalMusic().setVisible(false);
		mFrame.getLblAddUser().setVisible(true);
		mFrame.getLblAllUser().setVisible(true);
		mFrame.repaint();
	}
	public static int getNumPage() {
		return numPage;
	}
	public static void setNumPage(int num) {
		numPage = num;
	}

}
