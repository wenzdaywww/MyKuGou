package ctrl.viewctrl;

import util.Const;
import model.Group;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import model.MusicInfo;
import java.awt.Toolkit;
import java.util.Random;
import javax.media.Time;
import util.PictureUtil;
import ctrl.dao.GroupDao;
import ctrl.dao.MInfoDao;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import ctrl.dao.GMusicDao;
import java.util.ArrayList;
import view.view.MainFrame;
import view.view.SetDialog;
import view.view.TipDialog;
import ctrl.playctrl.Client;
import view.view.GroupPanel;
import view.view.MenuDialog;
import view.view.UserDialog;
import view.view.PlayModeDlg;
import view.view.LocalSongPan;
import view.view.MusicInfoDlg;
import ctrl.playctrl.PlayMusic;
import java.awt.event.KeyEvent;
import ctrl.playctrl.ShowThread;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class MainFrameCtrl {

	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private int playTime;
	private MainFrame mFrame;
	private boolean fullScreen=false;
	
	public MainFrameCtrl(MainFrame mFrame) {
		this.mFrame=mFrame;
		showMusic();
		panelCtrl();
		lblSetCtrl();
		lblMinCtr();
		lblExitCtr();
		trayIcoCtrl();
		lblNextCtrl();
		lblPlayCtrl();
		SliderSongCtrl();
		lblPlayModeCtrl();
		lblPreviousCtrl();
		userPanelCtrl();
		txtSearchCtrl();
		lblSerachCtrl();
		ShowThread.getST();
		initState();
		initPlayMode();
	}
	/**
	 * ��ʼ������״̬
	 */
	private void initState(){
		this.mFrame.addDownloadMusicPanel(new GroupPanel("��������", 0));
		this.mFrame.addDownloadLastPanel(new JPanel());
		this.mFrame.addLoveMusicPanel(new GroupPanel("�������ʾ�ղ�"));
		this.mFrame.addLoveLastPanel(new JPanel());
	}
	/**
	 * �û�����ע�����ʾ����
	 */
	private void userPanelCtrl(){
		mFrame.getUserPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserDialog.getUD().setVisible(true);
			}
		});
	}
	/**
	 * ��Ӹ������б�
	 */
	public void showMusic(){
		Const.localGroupPanel.clear();
		Const.localSongPanel.clear();
		mFrame.getListPanel().removeAll();
		mFrame.repaint();
		GroupDao gDao=new GroupDao();
		GMusicDao gmDao=new GMusicDao();
		MInfoDao mDao=new MInfoDao();
		ArrayList<Group> gList=gDao.getGroup();
		//��ȡȫ���б�������
		for (int i = 0; i < gList.size(); i++) {
			GroupPanel lPanel;
			if (i==0||i==1) {	//Ĭ�������б���ɾ��
				lPanel=new GroupPanel(gList.get(i),false);
			}else {
				lPanel=new GroupPanel(gList.get(i),true);
			}
			lPanel.setLblNumber(gmDao.getMusicNum(gList.get(i).getGroupId()));
			Const.localGroupPanel.add(lPanel);
		}
		//��ȡ�������е����и���������
		for (int i = 0; i < Const.localGroupPanel.size(); i++) {
			//��ȡ���������еĸ�������
			ArrayList<MusicInfo> mList=mDao.getGroupMusic(Const.localGroupPanel.get(i).getGroup().getGroupId());
			ArrayList<LocalSongPan> sList=new ArrayList<LocalSongPan>();
			for (int j = 0; j < mList.size(); j++) {
				LocalSongPan sPanel=new LocalSongPan(mList.get(j),Const.localGroupPanel.get(i).getGroup());
				if (Const.isPlaying) {
					if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {
						sPanel.getsCtrl().isPlayShow();
					}
				}
				sList.add(sPanel);//����������
			}
			Const.localSongPanel.put(Const.localGroupPanel.get(i).getGroup().getGroupId(), sList);//������������Ӹ���������
		}
		//�б�������ʾ���и�����
		for (int i = 0; i < Const.localGroupPanel.size(); i++) {
			mFrame.addLocalMusicPanel(Const.localGroupPanel.get(i));
			//�ж��ĸ�������չ��
			if (Const.localGroupPanel.get(i).getGroup().getGroupId().equals(Const.openGroupId)) {
				int num=0;
				for (int j = 0; j < Const.localGroupPanel.size(); j++) {
					if (Const.localGroupPanel.get(j).getGroup().getGroupId().equals(Const.openGroupId)){
						num=j;
						break;
					}
				}
				ArrayList<LocalSongPan> sList=Const.localSongPanel.get(Const.openGroupId);
				if (sList.size()>0) {
					Const.localGroupPanel.get(num).getLblOpen().setIcon(PictureUtil.CLOSE_ICO);
					Const.localGroupPanel.get(num).setOpen(true);
				}
				for (int j = 0; j < sList.size(); j++) {
					mFrame.addLocalMusicPanel(sList.get(j));
				}
			}
		}
		mFrame.addLocalLastPanel(new JPanel());
	}
	/**
	 * ��С����ť����
	 */
	private void lblMinCtr(){
		mFrame.getLblMin().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblMin().setForeground(Const.BRIGHT_WHITE);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblMin().setForeground(Const.DARK_WHITE);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					mFrame.setVisible(false);
					MenuDialog.getMD().setVisible(false);
					if (Const.canHide) {
						TipDialog.getTD().setVisible(false);
					}
				}
			}
		});
	}
	/**
	 * �رհ�ť����
	 */
	private void lblExitCtr(){
		mFrame.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblExit().setForeground(Const.BRIGHT_WHITE);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblExit().setForeground(Const.DARK_WHITE);
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.tipType=Const.EXIT;
					TipDialog.getTD().showSureMsg("�˳�", "���Ҫ�뿪���𣿣���");
				}
			}
		});
	}
	/**
	 * ϵͳ���̼���
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
		mFrame.getShowItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mFrame.setVisible(true);
			}
		});
		mFrame.getExitItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mFrame.deleteTrayIco();
				System.exit(0);
			}
		});
	}
	/**
	 * ��һ�װ�ť����
	 */
	private void lblPreviousCtrl(){
		mFrame.getLblPrevious().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mFrame.getLblPrevious().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mFrame.getLblPrevious().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (!Const.isListening) {
						playPreviousSong();
					}
				}
			}
		});
	}
	/**
	 * ���Ű�ť����
	 */
	private void lblPlayCtrl(){
		mFrame.getLblPlay().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mFrame.getLblPlay().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mFrame.getLblPlay().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
						if (Const.isPlaying) {
							Const.isPlaying=false;
							mFrame.getLblPlay().setIcon(PictureUtil.PLAY_ICO);
							mFrame.getLblPlay().setToolTipText("����");
							PlayMusic.getPM().getPlayer().stop();
						}else {
							Const.isPlaying=true;
							mFrame.getLblPlay().setIcon(PictureUtil.STOP_ICO);
							mFrame.getLblPlay().setToolTipText("��ͣ");
							PlayMusic.getPM().getPlayer().start();
						}
					}
				}
			}
		});
	}
	/**
	 * ��һ�װ�ť����
	 */
	private void lblNextCtrl(){
		mFrame.getLblNext().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				mFrame.getLblNext().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				mFrame.getLblNext().setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {//���������һ��
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (!Const.isListening) {
						palyNextSong();
					}
				}
			}
		});
	}
	/**
	 * ������һ��
	 */
	private void playPreviousSong(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			switch (Const.playStyle) {
			case Const.PLAY_RANDOM:
				playRandom();
				break;
			default:
				playListPrevious();
				break;
			}
		}
	}
	/**
	 * ������һ��
	 */
	private void palyNextSong(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			switch (Const.playStyle) {
			case Const.PLAY_RANDOM:
				playRandom();
				break;
			default:
				playListNext();
				break;
			}
		}
	}
	/**
	 * �������Ž���
	 */
	public void playEnd(){
		if (!Const.isListening) {
			switch (Const.playStyle) {
			case Const.PLAY_ONE:
				playOneSong();
				break;
			case Const.LOOP_ONE:
				loopOneSong();
				break;
			case Const.PLAY_ORDER:
				playOrderNext();
				break;
			case Const.PLAY_LIST:
				playListNext();
				break;
			case Const.PLAY_RANDOM:
				playRandom();
				break;
			default:
				break;
			}
			mFrame.repaint();
		}else {//�������Ž�������Ĭ���б��һ�׸���
			System.out.println("�������Ž���...................");
			Const.isListening=true;
			PlayMusic.getPM().closePlay();
			initPlayShow();
		}
	}
	/**
	 * ��������
	 */
	private void playOneSong(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			ArrayList<LocalSongPan> sList=getNowPlay();
			sList.get(getNowPlayNo()).getsCtrl().noPlayShow();
			PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
			initPlayShow();
		}
	}
	/**
	 * ����ѭ��
	 */
	private void loopOneSong(){
		PlayMusic.getPM().getPlayer().setMediaTime(new Time(0));
		PlayMusic.getPM().getPlayer().start();
	}
	/**
	 * ˳�򲥷���һ��
	 */
	private void playOrderNext(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			int next=getNowPlayNo()+1;
			ArrayList<LocalSongPan> sList=getNowPlay();
			if ((next)<=sList.size()-1) {//�����б����һ���򲥷���һ��
				PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
				sList.get(next).getsCtrl().isPlayShow();
				PlayMusic.getPM().startPlay(sList.get(next).getmInfo());
				addToRecent(sList.get(next).getmInfo().getMusicId());
			}else {
				sList.get(getNowPlayNo()).getsCtrl().noPlayShow();
				PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
				initPlayShow();
			}
		}
	}
	/**
	 * �б�����һ��
	 */
	private void playListPrevious(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			int previous=getNowPlayNo()-1;
			ArrayList<LocalSongPan> sList=getNowPlay();
			PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
			if ((previous)>=0) {//�����б����һ���򲥷���һ��
				sList.get(previous).getsCtrl().isPlayShow();
				PlayMusic.getPM().startPlay(sList.get(previous).getmInfo());
				addToRecent(sList.get(previous).getmInfo().getMusicId());
			}else {//���б����һ���򲥷ŵ�һ��
				sList.get(sList.size()-1).getsCtrl().isPlayShow();
				PlayMusic.getPM().startPlay(sList.get(sList.size()-1).getmInfo());
				addToRecent(sList.get(sList.size()-1).getmInfo().getMusicId());
			}
		}
	}
	/**
	 * �б�����һ��
	 */
	private void playListNext(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			int next=getNowPlayNo()+1;
			ArrayList<LocalSongPan> sList=getNowPlay();
			PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
			if ((next)<=sList.size()-1) {//�����б����һ���򲥷���һ��
				sList.get(next).getsCtrl().isPlayShow();
				PlayMusic.getPM().startPlay(sList.get(next).getmInfo());
				addToRecent(sList.get(next).getmInfo().getMusicId());
			}else {//���б����һ���򲥷ŵ�һ��
				sList.get(0).getsCtrl().isPlayShow();
				PlayMusic.getPM().startPlay(sList.get(0).getmInfo());
				addToRecent(sList.get(0).getmInfo().getMusicId());
			}
		}
	}
	/**
	 * ��������
	 */
	private void lblSerachCtrl(){
		mFrame.getLblSearch().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.getLblSearch().setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.getLblSearch().setBorder(null);
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
	 * ���ŵĸ�����ӵ���������б�
	 */
	public void addToRecent(String playingMusicId){
		GMusicDao gDao=new GMusicDao();
		if (!gDao.isExist("2", playingMusicId)) {
			gDao.addGMusic("2", playingMusicId);
			showMusic();
		}
	}
	/**
	 * ���������
	 */
	private void txtSearchCtrl(){
		mFrame.getTxtSearch().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					searchMusic();
					mFrame.getTxtSearch().setFocusable(false);
				}
			}
		});
		mFrame.getTxtSearch().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mFrame.getTxtSearch().setFocusable(true);
			}
		});
		mFrame.getTxtSearch().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String temp=mFrame.getTxtSearch().getText();
				if (temp.equals("")) {
					mFrame.getTxtSearch().setText("�����������");
					mFrame.getTxtSearch().setForeground(new Color(255, 255, 255, 180));
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				String temp=mFrame.getTxtSearch().getText();
				if (temp.equals("�����������")) {
					mFrame.getTxtSearch().setText("");
					mFrame.getTxtSearch().setForeground(Color.WHITE);
				}
			}
		});
	}
	/**
	 * ��������
	 */
	private void searchMusic(){
		if (Client.isConnect) {
			if (!mFrame.getTxtSearch().getText().equals("")) {
				if (mFrame.getTxtSearch().getText().equals(" ")) {
					Client.getCL().getClassMusic("All");
				}else {
					Client.getCL().searchMusic(mFrame.getTxtSearch().getText());
				}
			}
		}
	}
	/**
	 * �������
	 */
	private void playRandom(){
		if (!PlayMusic.getPM().getMusicPath().equals("musicPath")) {
			ArrayList<LocalSongPan> sList=getNowPlay();
			PlayMusic.getPM().closePlay();//�ر����ڲ��ŵ�����
			Random random=new Random();
			int next=random.nextInt(sList.size());
			PlayMusic.getPM().startPlay(sList.get(next).getmInfo());
			addToRecent(sList.get(next).getmInfo().getMusicId());
		}
	}
	/**
	 * ��������������
	 */
	private void SliderSongCtrl(){
		mFrame.getSliderSong().addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {//��ȡ����ڻ������ϵ���ֵ
				int a=e.getX();
				int b=mFrame.getSliderSong().getWidth();
				playTime=(a*mFrame.getSliderSong().getMaximum())/b;
			}
			@Override
			public void mouseDragged(MouseEvent e) {//��ȡ����϶���������ֵ
				int a=e.getX();
				int b=mFrame.getSliderSong().getWidth();
				playTime=(a*mFrame.getSliderSong().getMaximum())/b;
				mFrame.getSliderSong().setValue(playTime);
			}
		});
		mFrame.getSliderSong().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {//�������ֹͣ����
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.isPlaying=false;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					Const.isPlaying=true;
					newPlayTime();
				}
			}
		});
	}
	/**
	 * ����ģʽ��ǩ����
	 */
	private void lblPlayModeCtrl(){
		mFrame.getLblPlayMode().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (PlayModeDlg.getPM().isVisible()) {
						PlayModeDlg.getPM().setVisible(false);
					}else {
						PlayModeDlg.getPM().setLocation((int)(mFrame.getX()+mFrame.getWidth()-140), (int)(mFrame.getY()+mFrame.getHeight()-235));
						PlayModeDlg.getPM().setVisible(true);
					}
				}
			}
		});
	}
	/**
	 * ��ʼ������������
	 */
	public void initPlayShow(){
		mFrame.setLblPlayName("��ӭʹ��3W����");
		mFrame.setLblPlayTime("00:00", "00:00");
		mFrame.getSliderSong().setValue(0);
		mFrame.repaint();
	}
	/**
	 * �����������ÿ������ʱ��
	 */
	private void newPlayTime(){
		if (Const.isPlaying) {
			Const.isPlaying=false;
			if (playTime<0) {
				playTime=0;
			}else if(playTime>mFrame.getSliderSong().getMaximum()){
				playTime=mFrame.getSliderSong().getMaximum();
			}
			mFrame.getSliderSong().setValue(playTime);
			PlayMusic.getPM().setPlayTime(playTime);
			Const.isPlaying=true;
		}
	}
	/**
	 * ��ȡ��ǰ���ڲ��ŵĸ������ڵķ��������еĸ�����
	 * @return
	 */
	private ArrayList<LocalSongPan> getNowPlay(){
		boolean out=false;
		ArrayList<LocalSongPan> sList=new ArrayList<LocalSongPan>();
		for (int i = 0; i < Const.localGroupPanel.size(); i++) {
			sList.clear();
			sList=Const.localSongPanel.get(Const.localGroupPanel.get(i).getGroup().getGroupId());
			for (int j = 0; j < sList.size(); j++) {
				if (PlayMusic.getPM().getMusicPath().equals(sList.get(j).getmInfo().getMusicPath())) {
					sList.get(j).getsCtrl().noPlayShow();
					out=true;
				}
			}
			if (out) {
				break;
			}
		}
		return sList;
	}
	/**
	 * ��ȡ��ǰ���ڲ��ŵĸ���������ķ����еڼ���λ��
	 * @return �����ڷ����м��ϵ�λ��
	 */
	private int getNowPlayNo(){
		int now=0;
		boolean out=false;
		ArrayList<LocalSongPan> sList=new ArrayList<LocalSongPan>();
		for (int i = 0; i < Const.localGroupPanel.size(); i++) {
			sList.clear();
			sList=Const.localSongPanel.get(Const.localGroupPanel.get(i).getGroup().getGroupId());
			for (int j = 0; j < sList.size(); j++) {
				if (PlayMusic.getPM().getMusicPath().equals(sList.get(j).getmInfo().getMusicPath())) {
					now=j;
					out=true;
				}
			}
			if (out) {
				break;
			}
		}
		return now;
	}
	/**
	 * ���ñ�ǩ����
	 */
	private void lblSetCtrl(){
		mFrame.getLblSet().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {//���������һ��
				if (e.getButton()==MouseEvent.BUTTON1) {
					SetDialog.getSD().getTxtDownload().setText(Const.downloadPath);
					SetDialog.getSD().getTxtListening().setText(Const.listeingPath);
					SetDialog.getSD().setVisible(true);
				}
			}
		});
	}
	/**
	 * ��ʼ������ģʽ
	 */
	private void initPlayMode(){
		switch (Const.playStyle) {
		case Const.PLAY_ONE:
			mFrame.getLblPlayMode().setIcon(PictureUtil.PLAY_ONE_ICO);
			mFrame.getLblPlayMode().setToolTipText("��������");
			break;
		case Const.LOOP_ONE:
			mFrame.getLblPlayMode().setIcon(PictureUtil.LOOP_ONE_ICO);
			mFrame.getLblPlayMode().setToolTipText("����ѭ��");
			break;
		case Const.PLAY_ORDER:
			mFrame.getLblPlayMode().setIcon(PictureUtil.PLAY_ORDER_ICO);
			mFrame.getLblPlayMode().setToolTipText("˳�򲥷�");
			break;
		case Const.PLAY_LIST:
			mFrame.getLblPlayMode().setIcon(PictureUtil.PLAY_LIST_ICO);
			mFrame.getLblPlayMode().setToolTipText("�б���");
			break;
		case Const.PLAY_RANDOM:
			mFrame.getLblPlayMode().setIcon(PictureUtil.PLAY_RANDOM_ICO);
			mFrame.getLblPlayMode().setToolTipText("�������");
			break;
		default:
			break;
		}
		mFrame.validate();
	}
	/**
	 * ��������
	 */
	private void panelCtrl(){
		//==========������������ѱ����أ�����������ƶ�����=========
		mFrame.getMainPane().addMouseMotionListener(new MouseMotionAdapter() {//�������˶��������������ƶ�����
			@Override
			public void mouseDragged(MouseEvent e) {
				mFrame.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
				PlayModeDlg.getPM().setLocation((int)(mFrame.getX()+mFrame.getWidth()-140), (int)(mFrame.getY()+mFrame.getHeight()-235));
			}
		});
		//==========��ȡ�������������==========
		mFrame.getMainPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				MusicInfoDlg.getMID().setVisible(false);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=mFrame.getX();
				jdy=mFrame.getY();
				MenuDialog.getMD().setVisible(false);
				if (Const.canHide) {
					TipDialog.getTD().setVisible(false);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayModeDlg.getPM().setVisible(false);
				MenuDialog.getMD().setVisible(false);
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (e.getClickCount()==2) {
						if (fullScreen) {
							fullScreen=false;
							mFrame.setSize(900, 600);
							mFrame.setLocationRelativeTo(null);
						}else {
							fullScreen=true;
							Dimension screenSize = Toolkit.getDefaultToolkit()
									.getScreenSize();
							Rectangle bounds = new Rectangle(screenSize);
							Insets insets = Toolkit.getDefaultToolkit()
									.getScreenInsets(mFrame.getGraphicsConfiguration());
							bounds.x += insets.left;
							bounds.y += insets.top;
							bounds.width -= insets.left + insets.right;
							bounds.height -= insets.top + insets.bottom;
							mFrame.setBounds(bounds);
						}
					}
				}
				mFrame.initLocation();
			}
		});
	}
}
