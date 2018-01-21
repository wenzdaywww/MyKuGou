package view.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import ctrl.viewctrl.MainFrameCtrl;
import util.Const;
import util.PictureUtil;
import view.ui.BackPanel;
import view.ui.MyScrollBarUI;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private BackPanel mainPanel;
	private JPanel musicPanel;
	private JPanel searchPanel;
	private MenuItem showItem;
	private MenuItem exitItem;
	private SystemTray systemTray;
	private TrayIcon trayIcon=null;
	private JPanel playPanel;
	private JLabel lblMin;
	private JLabel lblExit;
	private WebTabbedPane infoTabPane;
	private JScrollPane localScrPanel;
	private JScrollPane loveScrPanel;
	private JScrollPane downScrPanel;
	private JPanel localPanel;
	private JPanel lovePanel;
	private JLabel lblSet;
	private static MainFrame mFrame;
	private JLabel lblPrevious;
	private JLabel lblPlay;
	private JLabel lblNext;
	private JSlider sliderSong;
	private JLabel lblPlayName;
	private JLabel lblPlayTime;
	private JLabel lblPlayMode;
	private MainFrameCtrl mFrameCtrl;
	private BackPanel userPanel;
	private JLabel lblUserName;
	private JLabel lblRank;
	private JPanel classPanel;
	private JPanel onlineMusicPanel;
	private JPanel downloadPanel;
	private JPanel textPanel;
	private JTextField txtSearch;
	private JLabel lblSearch;
	private GridBagLayout localGridbag = new GridBagLayout();
	private GridBagLayout loveGridbag = new GridBagLayout();
	private GridBagLayout classGridbag = new GridBagLayout();
	private GridBagLayout onlineGridbag = new GridBagLayout();
	private GridBagLayout downloadGridbag = new GridBagLayout();
	private GridBagConstraints localC = new GridBagConstraints();
	private GridBagConstraints loveC = new GridBagConstraints();
	private GridBagConstraints classC = new GridBagConstraints();
	private GridBagConstraints onlineC = new GridBagConstraints();
	private GridBagConstraints downloadC = new GridBagConstraints();
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
		mFrameCtrl=new MainFrameCtrl(this);
	}
	private void initUI(){
		setUndecorated(true);
		setSize(970,600);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(970,600));
		setTitle("WWW音乐");
		setFocusable(true);
		setIconImage(PictureUtil.FRAME_ICO);

		localC.fill = GridBagConstraints.HORIZONTAL;
		localC.weightx = 1.0;
		localC.gridwidth = GridBagConstraints.REMAINDER; //end row
		loveC.fill = GridBagConstraints.HORIZONTAL;
		loveC.weightx = 1.0;
		loveC.gridwidth = GridBagConstraints.REMAINDER; //end row
		classC.fill = GridBagConstraints.HORIZONTAL;
		classC.weightx = 1.0;
		classC.gridwidth = GridBagConstraints.REMAINDER; //end row
		onlineC.fill = GridBagConstraints.HORIZONTAL;
		onlineC.weightx = 1.0;
		onlineC.gridwidth = GridBagConstraints.REMAINDER; //end row
		downloadC.fill = GridBagConstraints.HORIZONTAL;
		downloadC.weightx = 1.0;
		downloadC.gridwidth = GridBagConstraints.REMAINDER; //end row
		
		mainPanel = new BackPanel();
		mainPanel.setBorder(new EmptyBorder(1, 1, 1, 1));
		mainPanel.setImage(PictureUtil.SKIN);
		setContentPane(mainPanel);

		musicPanel = new JPanel();
		musicPanel.setOpaque(false);
		musicPanel.setBounds(10, 50, (int)(0.382*(getWidth()-20)), (int)(getHeight()-110));
		musicPanel.setMinimumSize(new Dimension(10, 60));
		musicPanel.setBackground(Const.BACK_COLOR);

		searchPanel = new JPanel();
		searchPanel.setBounds((int)(0.382*(getWidth()-20)+9), 50, (int)(0.618*(getWidth()-20)), (int)(getHeight()-110));
		searchPanel.setBackground(Const.BACK_COLOR);
		searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.setLayout(null);
		mainPanel.add(musicPanel);
		mainPanel.add(searchPanel);
		
		JPanel optionPanel = new JPanel();
		optionPanel.setOpaque(false);
		
		classPanel = new JPanel();
		classPanel.setLayout(classGridbag);
		classPanel.setBackground(Color.WHITE);
		JScrollPane cScrollPane = new JScrollPane();
		cScrollPane.setBorder(null);
		cScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		cScrollPane.setViewportView(classPanel);
		
		onlineMusicPanel = new JPanel();
		onlineMusicPanel.setLayout(onlineGridbag);
		onlineMusicPanel.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.setViewportView(onlineMusicPanel);
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(Color.WHITE);
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel.setHorizontalGroup(
			gl_searchPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchPanel.createSequentialGroup()
							.addComponent(cScrollPane, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(optionPanel, GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE))
					.addGap(1))
		);
		gl_searchPanel.setVerticalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGap(1)
					.addComponent(optionPanel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
						.addComponent(cScrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
					.addContainerGap())
		);
		searchPanel.setLayout(gl_searchPanel);
		lblRank = new JLabel("排行榜");
		lblRank.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		GroupLayout gl_optionPanel = new GroupLayout(optionPanel);
		gl_optionPanel.setHorizontalGroup(
			gl_optionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_optionPanel.createSequentialGroup()
					.addGap(350)
					.addComponent(lblRank)
					.addContainerGap(140, Short.MAX_VALUE))
		);
		gl_optionPanel.setVerticalGroup(
			gl_optionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblRank)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		optionPanel.setLayout(gl_optionPanel);
		
		playPanel = new JPanel();
		playPanel.setOpaque(false);
		playPanel.setBounds(10, 545, 949, 50);
		mainPanel.add(playPanel);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setOpaque(false);
		
		JPanel panel3 = new JPanel();
		panel3.setOpaque(false);
		
		JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		
		JPanel panel1 = new JPanel();
		panel1.setOpaque(false);
		GroupLayout gl_playPanel = new GroupLayout(playPanel);
		gl_playPanel.setHorizontalGroup(
			gl_playPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_playPanel.createSequentialGroup()
					.addGap(35)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel2, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel3, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
		);
		gl_playPanel.setVerticalGroup(
			gl_playPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
				.addComponent(panel1, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
				.addComponent(panel3, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
				.addComponent(panel2, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
		);
		
		lblPlayMode = new JLabel();
		lblPlayMode.setForeground(Color.WHITE);
		lblPlayMode.setIcon(PictureUtil.PLAY_LIST_ICO);
		lblPlayMode.setFont(new Font("微软雅黑", Font.BOLD, 18));
		GroupLayout gl_panel3 = new GroupLayout(panel3);
		gl_panel3.setHorizontalGroup(
			gl_panel3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel3.createSequentialGroup()
					.addContainerGap(149, Short.MAX_VALUE)
					.addComponent(lblPlayMode, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(80))
		);
		gl_panel3.setVerticalGroup(
			gl_panel3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel3.createSequentialGroup()
					.addGap(12)
					.addComponent(lblPlayMode, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		panel3.setLayout(gl_panel3);
		
		sliderSong = new JSlider();
		sliderSong.setFocusable(false);
		sliderSong.setValue(0);
		sliderSong.setOpaque(false);
		sliderSong.setToolTipText("调整歌曲播放进度");
		
		lblPlayName = new JLabel(" 我的音乐");
		lblPlayName.setForeground(Color.WHITE);
		lblPlayName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		
		lblPlayTime = new JLabel("00:00/00:00 ");
		lblPlayTime.setForeground(Color.WHITE);
		lblPlayTime.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel2.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel2.createSequentialGroup()
							.addComponent(lblPlayName, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
							.addComponent(lblPlayTime))
						.addComponent(sliderSong, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel2.createSequentialGroup()
					.addGroup(gl_panel2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlayName, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(lblPlayTime, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(1)
					.addComponent(sliderSong, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
		);
		panel2.setLayout(gl_panel2);
		panel.setLayout(null);
		
		lblPrevious = new JLabel();
		lblPrevious.setToolTipText("上一首");
		lblPrevious.setIcon(PictureUtil.PREVIOUS_ICO);
		lblPrevious.setBounds(0, 6, 38, 38);
		panel.add(lblPrevious);
		
		lblPlay = new JLabel();
		lblPlay.setToolTipText("播放");
		lblPlay.setIcon(PictureUtil.PLAY_ICO);
		lblPlay.setBounds(48, 1, 48, 48);
		panel.add(lblPlay);
		
		lblNext = new JLabel();
		lblNext.setToolTipText("下一首");
		lblNext.setIcon(PictureUtil.NEXT_ICO);
		lblNext.setBounds(108, 6, 38, 38);
		panel.add(lblNext);
		playPanel.setLayout(gl_playPanel);

		musicPanel.setLayout(new BorderLayout(0, 0));

		infoTabPane = new WebTabbedPane();
		infoTabPane.setTabbedPaneStyle(TabbedPaneStyle.attached);//不高亮边框
		infoTabPane.setTabPlacement(WebTabbedPane.LEFT);
		infoTabPane.setBottomBg(new Color(0, 0, 0, 0));
		infoTabPane.setBackground(new Color(0, 0, 0, 0));
		musicPanel.add(infoTabPane);

		localPanel=new JPanel();
		localPanel.setBackground(Color.white);
		localPanel.setLayout(localGridbag);
		localScrPanel=new JScrollPane(localPanel);
		localScrPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
		localScrPanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
		infoTabPane.addTab(null, PictureUtil.MUSIC_ICO, localScrPanel,"本地列表");

		lovePanel=new JPanel();
		lovePanel.setBackground(Color.white);
		lovePanel.setLayout(loveGridbag);
		loveScrPanel=new JScrollPane(lovePanel);
		loveScrPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
		loveScrPanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
		infoTabPane.addTab(null, PictureUtil.COLLECT_ICO, loveScrPanel,"网络收藏");
		
		downloadPanel=new JPanel();
		downloadPanel.setBackground(Color.white);
		downloadPanel.setLayout(downloadGridbag);
		downScrPanel=new JScrollPane(downloadPanel);
		downScrPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)));
		downScrPanel.getVerticalScrollBar().setUI(new MyScrollBarUI());
		infoTabPane.addTab(null, PictureUtil.DOWNLOAD, downScrPanel,"下载");

		userPanel = new BackPanel();
		userPanel.setBorder(new LineBorder(new Color(255, 255, 255, 180)));
		userPanel.setOpaque(false);
		userPanel.setToolTipText("点击登入或注册");
		userPanel.setBounds(10, 5, 40, 40);
		userPanel.setImage(PictureUtil.USER_ICO);
		mainPanel.add(userPanel);
		
		lblSet = new JLabel();
		lblSet.setBounds(890, 12, 20, 20);
		lblSet.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblSet.setIcon(PictureUtil.SET_ICO);

		lblMin = new JLabel("-");
		lblMin.setBounds(915, 10, 20, 20);
		lblMin.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblMin.setToolTipText("最小化");
		lblMin.setForeground(new Color(255, 255, 255, 180));
		lblMin.setFont(new Font("微软雅黑", Font.PLAIN, 30));

		lblExit = new JLabel("×");
		lblExit.setBounds(940, 10, 20, 20);
		lblExit.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblExit.setToolTipText("关闭");
		lblExit.setForeground(new Color(255, 255, 255, 180));
		lblExit.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		mainPanel.add(lblSet);
		mainPanel.add(lblMin);
		mainPanel.add(lblExit);
		
		lblUserName = new JLabel("请登入。。。");
		lblUserName.setForeground(Color.WHITE);
		lblUserName.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblUserName.setBounds(60, 15, 250, 20);
		mainPanel.add(lblUserName);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setBounds(444, 91, 78, -12);
		mainPanel.add(horizontalGlue);
		
		textPanel = new JPanel();
		textPanel.setOpaque(false);
		textPanel.setBounds(searchPanel.getX(), 10, 250, 25);
		mainPanel.add(textPanel);
		
		txtSearch = new JTextField();
		txtSearch .setText("搜索网络歌曲");
		txtSearch .setForeground(new Color(255, 255, 255, 180));
		txtSearch.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		txtSearch.setOpaque(false);
		txtSearch.setColumns(10);
		
		lblSearch = new JLabel();
		lblSearch.setToolTipText("搜索");
		lblSearch.setIcon(PictureUtil.SEARCH_ICO);
		GroupLayout gl_textPanel = new GroupLayout(textPanel);
		gl_textPanel.setHorizontalGroup(
			gl_textPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_textPanel.createSequentialGroup()
					.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSearch, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_textPanel.setVerticalGroup(
			gl_textPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(txtSearch, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
				.addGroup(gl_textPanel.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(lblSearch, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(2))
		);
		textPanel.setLayout(gl_textPanel);
	}
	/**
	 * 初始化托盘图标
	 */
	private void initTrayIcon(){
		if (SystemTray.isSupported()) {
			systemTray=SystemTray.getSystemTray();
			PopupMenu popupMenu=new PopupMenu();
			showItem=new MenuItem("打开音乐面板");
			exitItem=new MenuItem("退出");
			popupMenu.add(showItem);
			popupMenu.add(exitItem);
			trayIcon=new TrayIcon(PictureUtil.TRAY_ICO, "打开音乐面板",popupMenu);
			trayIcon.setImageAutoSize(true);
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
			}
		}
	}
	/**
	 * 每次改变窗体后重新设置组件大小
	 */
	public void initLocation(){
		musicPanel.setBounds(10, 50, (int)(0.382*(getWidth()-20)), (int)(getHeight()-110));
		searchPanel.setBounds((int)(0.382*(getWidth()-20)+9), 50, (int)(0.618*(getWidth()-20)), (int)(getHeight()-110));
		playPanel.setBounds(10, (int)(getHeight()-55), (int)(getWidth()-20), 50);
		textPanel.setLocation(searchPanel.getX(), 10);
		lblSet.setLocation(getWidth()-80, 12);
		lblMin.setLocation(getWidth()-55, 10);
		lblExit.setLocation(getWidth()-30, 10);
	}
	/**
	 * 本地音乐面板添加音乐
	 * @param jPanel
	 */
	public void addLocalMusicPanel(LocalSongPan localSongPan) {
		localC.weighty = 0.0;
		localGridbag.setConstraints(localSongPan, localC);
		localPanel.add(localSongPan);
		repaint();
	}
	public void addLocalMusicPanel(GroupPanel groupPanel) {
		localC.weighty = 0.0;
		localGridbag.setConstraints(groupPanel, localC);
		localPanel.add(groupPanel);
		repaint();
	}
	/**
	 * 本地音乐最后一个jpanel
	 * @param jPanel
	 */
	public void addLocalLastPanel(JPanel jPanel) {
		localC.weighty = 1.0;
		jPanel.setOpaque(false);
		localGridbag.setConstraints(jPanel, localC);
		localPanel.add(jPanel);
		repaint();
	}
	/**
	 * 收藏音乐面板添加音乐
	 * @param jPanel
	 */
	public void addLoveMusicPanel(LocalSongPan localSongPan) {
		loveC.weighty = 0.0;
		loveGridbag.setConstraints(localSongPan, loveC);
		lovePanel.add(localSongPan);
		repaint();
	}
	/**
	 * 收藏列表中的分组
	 * @param groupPanel
	 */
	public void addLoveMusicPanel(GroupPanel groupPanel) {
		loveC.weighty = 0.0;
		loveGridbag.setConstraints(groupPanel, loveC);
		lovePanel.add(groupPanel);
		repaint();
	}
	/**
	 * 网络收藏列表歌曲最后一个jpanel
	 * @param jPanel
	 */
	public void addLoveLastPanel(JPanel jPanel) {
		loveC.weighty = 1.0;
		jPanel.setOpaque(false);
		loveGridbag.setConstraints(jPanel, loveC);
		lovePanel.add(jPanel);
		repaint();
	}
	/**
	 * 音乐分类添加分类
	 * @param jPanel
	 */
	public void addClassMusicPanel(MusicClassPan musicClassPan) {
		classC.weighty = 0.0;
		classGridbag.setConstraints(musicClassPan, classC);
		classPanel.add(musicClassPan);
		repaint();
	}
	/**
	 *音乐分类最后一个jpanel
	 * @param jPanel
	 */
	public void addClasLastPanel(JPanel jPanel) {
		classC.weighty = 1.0;
		jPanel.setOpaque(false);
		classGridbag.setConstraints(jPanel, classC);
		classPanel.add(jPanel);
		repaint();
	}
	/**
	 * 网络在线音乐歌曲条
	 * @param jPanel
	 */
	public void addOnlineMusicPanel(NetSongPan netSongPan) {
		onlineC.weighty = 0.0;
		onlineGridbag.setConstraints(netSongPan, onlineC);
		onlineMusicPanel.add(netSongPan);
		onlineMusicPanel.repaint();
	}
	/**
	 *网络在线音乐歌曲条最后一个jpanel
	 * @param jPanel
	 */
	public void addOnlineLastPanel(JPanel jPanel) {
		onlineC.weighty = 1.0;
		jPanel.setOpaque(false);
		onlineGridbag.setConstraints(jPanel, onlineC);
		onlineMusicPanel.add(jPanel);
		onlineMusicPanel.repaint();
	}
	/**
	 * 网络在线音乐歌曲条
	 * @param jPanel
	 */
	public void addDownloadMusicPanel(DownloadPan dMusicP) {
		downloadC.weighty = 0.0;
		downloadGridbag.setConstraints(dMusicP, downloadC);
		downloadPanel.add(dMusicP);
		downloadPanel.repaint();
	}
	/**
	 * 下载列表中的分组
	 * @param groupPanel
	 */
	public void addDownloadMusicPanel(GroupPanel groupPanel) {
		downloadC.weighty = 0.0;
		downloadGridbag.setConstraints(groupPanel, downloadC);
		downloadPanel.add(groupPanel);
		downloadPanel.repaint();
	}
	/**
	 *网络在线音乐歌曲条最后一个jpanel
	 * @param jPanel
	 */
	public void addDownloadLastPanel(JPanel jPanel) {
		downloadC.weighty = 1.0;
		jPanel.setOpaque(false);
		downloadGridbag.setConstraints(jPanel, downloadC);
		downloadPanel.add(jPanel);
		downloadPanel.repaint();
	}
	
	public void setLblPlayName(String songName) {
		lblPlayName.setText(" "+songName);
	}

	public void setLblPlayTime(String playTime,String songTime) {
		lblPlayTime.setText(playTime+"/"+songTime+" ");
	}

	public void deleteTrayIco(){
		systemTray.remove(trayIcon);
	}
	public JPanel getMainPane() {
		return mainPanel;
	}
	public JPanel getSearchPanel() {
		return searchPanel;
	}
	public JLabel getLblMin() {
		return lblMin;
	}
	public JLabel getLblExit() {
		return lblExit;
	}
	public MenuItem getShowItem() {
		return showItem;
	}
	public MenuItem getExitItem() {
		return exitItem;
	}
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}
	public JPanel getListPanel() {
		return localPanel;
	}
	public JLabel getLblPrevious() {
		return lblPrevious;
	}
	public JLabel getLblPlay() {
		return lblPlay;
	}
	public JLabel getLblNext() {
		return lblNext;
	}
	public JSlider getSliderSong() {
		return sliderSong;
	}

	public JLabel getLblPlayMode() {
		return lblPlayMode;
	}

	public MainFrameCtrl getMFC() {
		return mFrameCtrl;
	}

	public JPanel getUserPanel() {
		return userPanel;
	}

	public JLabel getLblUserName() {
		return lblUserName;
	}

	public JPanel getLovePanel() {
		return lovePanel;
	}

	public JPanel getClassPanel() {
		return classPanel;
	}

	public JPanel getNetMusicPanel() {
		return onlineMusicPanel;
	}

	public JLabel getLblSearch() {
		return lblSearch;
	}

	public JTextField getTxtSearch() {
		return txtSearch;
	}

	public JPanel getDownloadPanel() {
		return downloadPanel;
	}

	public JLabel getLblSet() {
		return lblSet;
	}
	
}
