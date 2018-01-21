package view.view;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import ctrl.viewctrl.MainFrameCtrl;
import util.PictureUtil;
import view.ui.BackPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private BackPanel mainPanel;
	private MenuItem showItem;
	private MenuItem exitItem;
	private SystemTray systemTray;
	private TrayIcon trayIcon=null;
	private static MainFrame mFrame;
	private JLabel lblExit;
	private JLabel lblSearchMusic;
	private JLabel lblMusicClass;
	private JLabel lblTotalMusic;
	private JLabel lblMPreviousPage;
	private JLabel lblMNextPage;
	private JLabel lblFirstPage;
	private JLabel lblLastPage;
	private JLabel lblMTotalPage;
	public Color musicColor=Color.blue;
	public Color pageColor=new Color(54, 143, 192,180);
	private JLabel lblAddMusic;
	private JPanel musicInfoPanel;
	private CardLayout cl_musicInfoPanel=new CardLayout();
	private ClassPanel classPanel;
	private JLabel lblUploadState;
	private JTextField textMusicSearch;
	private JLabel lblMin;
	private JLabel lblTip;
	private JLabel lblConnectState;
	private JPopupMenu popMenu;
	private JLabel lblMusicManage;
	private JPanel musicPanel;
	private JPanel userPanel;
	private JLabel lblAddUser;
	private JLabel lblAllUser;
	private JLabel lblUserManage;
	private UserPanel userInfoPan;
	private MusicPanel collectPanel;
	private JTextField textSearchUser;
	private JLabel lblResult;
	private JLabel lblSearchUser;
	private JLabel lblNewLabel;
	
	
	public static MainFrame getMF(){
		if (mFrame==null) {
			mFrame=new MainFrame();
		}
		return mFrame;
	}
	
	/**
	 * Create the frame.
	 */
	private MainFrame() {
		initUI();
		initTrayIcon();
		new MainFrameCtrl(this);
	}
	
	private void initUI(){
		setUndecorated(true);
		setSize(800,600);
		setLocationRelativeTo(null);
		setTitle("WWWÒôÀÖºóÌ¨¹ÜÀí");
		setFocusable(true);
		
		setIconImage(PictureUtil.ICO);
		
		mainPanel =new BackPanel();
		mainPanel.setImage(PictureUtil.MAIN_BACKGROUP);
		mainPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		setContentPane(mainPanel);
		mainPanel.setOpaque(false);
		mainPanel.setLayout(null);
		
		musicPanel = new JPanel();
		musicPanel.setOpaque(false);
		musicPanel.setLayout(null);
		musicPanel.setBounds(242, 105, 533, 485);
		
		userPanel = new JPanel();
		userPanel.setOpaque(false);
		userPanel.setLayout(null);
		userPanel.setBounds(242, 105, 533, 473);
		
		lblExit = new JLabel();
		lblExit.setForeground(pageColor);
		lblExit.setIcon(PictureUtil.MAIN_EXIT_ICO);
		lblExit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblExit.setBounds(775, 5, 20, 20);
		mainPanel.add(lblExit);
		
		lblAddMusic = new JLabel("ÉÏ´«¸èÇú");
		lblAddMusic.setVisible(false);
		lblAddMusic.setForeground(pageColor);
		lblAddMusic.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMusic.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblAddMusic.setBounds(70, 145, 60, 20);
		mainPanel.add(lblAddMusic);
		
		lblSearchMusic = new JLabel("ËÑË÷¸èÇú");
		lblSearchMusic.setForeground(pageColor);
		lblSearchMusic.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchMusic.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblSearchMusic.setBounds(229, 10, 60, 20);
		musicPanel.add(lblSearchMusic);
		
		lblMusicClass = new JLabel("¸èÇú·ÖÀà");
		lblMusicClass.setVisible(false);
		lblMusicClass.setForeground(pageColor);
		lblMusicClass.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicClass.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblMusicClass.setBounds(70, 175, 60, 20);
		mainPanel.add(lblMusicClass);
		
		lblTotalMusic = new JLabel("¸èÇúÅÅÐÐ");
		lblTotalMusic.setVisible(false);
		lblTotalMusic.setForeground(pageColor);
		lblTotalMusic.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalMusic.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblTotalMusic.setBounds(70, 205, 60, 20);
		mainPanel.add(lblTotalMusic);
		
		lblMPreviousPage = new JLabel("ÉÏÒ»Ò³");
		lblMPreviousPage.setForeground(pageColor);
		lblMPreviousPage.setVisible(false);
		lblMPreviousPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMPreviousPage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblMPreviousPage.setBounds(230, 457, 45, 18);
		musicPanel.add(lblMPreviousPage);
		
		lblMNextPage = new JLabel("ÏÂÒ»Ò³");
		lblMNextPage.setForeground(pageColor);
		lblMNextPage.setVisible(false);
		lblMNextPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMNextPage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblMNextPage.setBounds(285, 457, 45, 18);
		musicPanel.add(lblMNextPage);
		
		lblFirstPage = new JLabel("Ê×Ò³");
		lblFirstPage.setForeground(pageColor);
		lblFirstPage.setVisible(false);
		lblFirstPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstPage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblFirstPage.setBounds(190, 457, 30, 18);
		musicPanel.add(lblFirstPage);
		
		lblLastPage = new JLabel("Ä©Ò³");
		lblLastPage.setForeground(pageColor);
		lblLastPage.setVisible(false);
		lblLastPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastPage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblLastPage.setBounds(340, 457, 30, 18);
		musicPanel.add(lblLastPage);
		
		lblMTotalPage = new JLabel("0/0Ò³");
		lblMTotalPage.setForeground(pageColor);
		lblMTotalPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMTotalPage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblMTotalPage.setBounds(107, 457, 80, 18);
		musicPanel.add(lblMTotalPage);
		
		musicInfoPanel = new JPanel();
		musicInfoPanel.setOpaque(false);
		musicInfoPanel.setBounds(20, 174, 500, 255);
		musicInfoPanel.setLayout(cl_musicInfoPanel);
		musicPanel.add(musicInfoPanel);
		
		classPanel=new ClassPanel();
		classPanel.setSize(500, 112);
		classPanel.setLocation(20, 40);
		classPanel.getTable().setOpaque(false);
		classPanel.setOpaque(false);
		musicPanel.add(classPanel);
		
		lblUploadState = new JLabel();
		lblUploadState.setForeground(pageColor);
		lblUploadState.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblUploadState.setBounds(20, 432, 500, 20);
		musicPanel.add(lblUploadState);
		
		textMusicSearch = new JTextField();
		textMusicSearch.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		textMusicSearch.setBounds(20, 10, 192, 21);
		musicPanel.add(textMusicSearch);
		textMusicSearch.setColumns(10);
		
		lblMin = new JLabel();
		lblMin.setIcon(PictureUtil.MAIN_MAIN_ICO);
		lblMin.setBounds(745, 5, 20, 20);
		mainPanel.add(lblMin);
		
		lblTip = new JLabel();
		lblTip.setForeground(pageColor);
		lblTip.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblTip.setBounds(20, 155, 150, 15);
		musicPanel.add(lblTip);
		
		lblConnectState = new JLabel("ÒÑÁ¬½Ó");
		lblConnectState.setForeground(Color.RED);
		lblConnectState.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblConnectState.setBounds(45, 454, 80, 15);
		mainPanel.add(lblConnectState);
		
		lblMusicManage = new JLabel("ÒôÀÖ¿â¹ÜÀí");
		lblMusicManage.setForeground(pageColor);
		lblMusicManage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblMusicManage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicManage.setBounds(28, 105, 150, 30);
		mainPanel.add(lblMusicManage);
		
		lblUserManage = new JLabel("ÓÃ»§¹ÜÀí");
		lblUserManage.setForeground(pageColor);
		lblUserManage.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserManage.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		lblUserManage.setBounds(28, 277, 150, 30);
		mainPanel.add(lblUserManage);
		
		lblAddUser = new JLabel("Ìí¼ÓÓÃ»§");
		lblAddUser.setVisible(false);
		lblAddUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddUser.setForeground(new Color(54, 143, 192, 180));
		lblAddUser.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblAddUser.setBounds(70, 317, 60, 20);
		mainPanel.add(lblAddUser);
		
		lblAllUser = new JLabel("ËùÓÐÓÃ»§");
		lblAllUser.setVisible(false);
		lblAllUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblAllUser.setForeground(new Color(54, 143, 192, 180));
		lblAllUser.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblAllUser.setBounds(70, 347, 60, 20);
		mainPanel.add(lblAllUser);
		
		lblNewLabel = new JLabel("Ë«»÷»òÓÒ»÷¶ÔÓ¦Êý¾Ý¼´¿É²Ù×÷");
		lblNewLabel.setForeground(pageColor);
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12));
		lblNewLabel.setBounds(26, 255, 156, 15);
		mainPanel.add(lblNewLabel);
		
		popMenu=new JPopupMenu();
		
		userInfoPan =new UserPanel();
		userInfoPan.setSize(500, 160);
		userInfoPan.setLocation(20, 42);
		userPanel.add(userInfoPan);
		
		collectPanel=new MusicPanel(0);
		collectPanel.setBounds(20, 223, 500, 250);
		userPanel.add(collectPanel);
		
		textSearchUser = new JTextField();
		textSearchUser.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		textSearchUser.setBounds(20, 10, 190, 21);
		userPanel.add(textSearchUser);
		textSearchUser.setColumns(10);
		
		lblSearchUser = new JLabel("ËÑË÷ÓÃ»§");
		lblSearchUser.setForeground(pageColor);
		lblSearchUser.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
		lblSearchUser.setBounds(220, 13, 60, 15);
		userPanel.add(lblSearchUser);
		
		lblResult = new JLabel("ÓÃ»§ÊÕ²ØËÑË÷½á¹û");
		lblResult.setForeground(pageColor);
		lblResult.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblResult.setBounds(20, 205, 302, 15);
		userPanel.add(lblResult);
	}
	/**
	 * ³õÊ¼»¯ÍÐÅÌÍ¼±ê
	 */
	private void initTrayIcon(){
		if (SystemTray.isSupported()) {
			systemTray=SystemTray.getSystemTray();
			PopupMenu popupMenu=new PopupMenu();
			showItem=new MenuItem("´ò¿ªÖ÷Ãæ°å");
			exitItem=new MenuItem("ÍË³ö");
			popupMenu.add(showItem);
			popupMenu.add(exitItem);
			trayIcon=new TrayIcon(PictureUtil.TRAY_ICO, "´ò¿ªÖ÷Ãæ°å",popupMenu);
			trayIcon.setImageAutoSize(true);
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
			}
		}
	}
	/**
	 * ÉèÖÃ×ÜÒ³Êý
	 */
	public void setMTotalPage(int num,int total){
		lblMTotalPage.setText(Integer.toString(num)+"/"+Integer.toString(total)+"Ò³");
	}
	
	
	public BackPanel getMainPanel() {
		return mainPanel;
	}

	public MenuItem getShowItem() {
		return showItem;
	}

	public MenuItem getExitItem() {
		return exitItem;
	}

	public SystemTray getSystemTray() {
		return systemTray;
	}

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public JLabel getLblExit() {
		return lblExit;
	}

	public JPanel getMusicInfoPanel() {
		return musicInfoPanel;
	}

	public JLabel getLblMusicClass() {
		return lblMusicClass;
	}

	public JLabel getLblTotalMusic() {
		return lblTotalMusic;
	}

	public JLabel getLblMPreviousPage() {
		return lblMPreviousPage;
	}

	public JLabel getLblMNextPage() {
		return lblMNextPage;
	}

	public JLabel getLblMFirstPage() {
		return lblFirstPage;
	}

	public JLabel getLblMLastPage() {
		return lblLastPage;
	}

	public JLabel getLblSearchMusic() {
		return lblSearchMusic;
	}

	public JLabel getLblAddMusic() {
		return lblAddMusic;
	}

	public JLabel getLblUploadState() {
		return lblUploadState;
	}

	public ClassPanel getClassPanel() {
		return classPanel;
	}

	public JLabel getLblMin() {
		return lblMin;
	}

	public CardLayout getCly() {
		return cl_musicInfoPanel;
	}

	public JLabel getLblConnectState() {
		return lblConnectState;
	}

	public JTextField getTextMusicSearch() {
		return textMusicSearch;
	}

	public JLabel getLblTip() {
		return lblTip;
	}

	public JPopupMenu getPopupMenu() {
		return popMenu;
	}

	public JLabel getLblAddUser() {
		return lblAddUser;
	}

	public JLabel getLblAllUser() {
		return lblAllUser;
	}

	public JPanel getUserPanel() {
		return userPanel;
	}

	public JLabel getLblMusicManage() {
		return lblMusicManage;
	}

	public JLabel getLblUserManage() {
		return lblUserManage;
	}

	public JPanel getMusicPanel() {
		return musicPanel;
	}

	public UserPanel getUserInfoPan() {
		return userInfoPan;
	}

	public JTextField getTextSearchUser() {
		return textSearchUser;
	}

	public JLabel getLblSearchUser() {
		return lblSearchUser;
	}

	public MusicPanel getCollectPanel() {
		return collectPanel;
	}

	public JLabel getLblResult() {
		return lblResult;
	}
	
}
