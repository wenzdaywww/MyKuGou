package ctrl.viewctrl;

import util.Const;
import model.Group;
import model.MusicInfo;
import util.MyFileUtil;
import model.GroupMusic;
import ctrl.dao.MInfoDao;
import ctrl.dao.GroupDao;
import ctrl.dao.GMusicDao;
import java.util.ArrayList;
import view.view.MainFrame;
import view.view.TipDialog;
import view.view.LocalSongPan;
import java.awt.event.KeyEvent;
import ctrl.playctrl.PlayMusic;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class TipDialogCtrl {

	private int mx=0;
	private int my=0;				
	private int jdx=0;				
	private int jdy=0;	
	private Group group;
	private TipDialog tDialog;
	private LocalSongPan sPanel;

	public TipDialogCtrl(TipDialog tDialog) {
		this.tDialog = tDialog;
		btnOkCtrl();
		txtNameCtrl();
		lblExitCtrl();
		mainPanelCtrl();
		btnCancelCtrl();
	}
	/**
	 * ȷ����ť����
	 */
	private void btnOkCtrl(){
		tDialog.getBtnOk().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					switch (Const.tipType) {
					case Const.GROUP_BUILT:
						groupBuilt();
						break;
					case Const.GROUP_CLEAR:
						clearGroup();
						break;
					case Const.GROUP_DEL:
						delGroup();
						break;
					case Const.GROUP_RENAME:
						renameGroup();
						break;
					case Const.MUSIC_RENAME:
						renameMusic();
						break;
					case Const.MUSIC_DEL:
						deleMusic();
						break;
					case Const.MUSIC_LOVE:

						break;
					case Const.MUSIC_MOVE:

						break;
					case Const.SUCCEED:
						tDialog.setVisible(false);
						break;
					case Const.FAIL:
						tDialog.setVisible(false);
						break;
					case Const.EXIT:
						MainFrame.getMF().deleteTrayIco();
						System.exit(0);
						break;
					default:
						break;
					}
					MainFrame.getMF().getMFC().showMusic();
				}
			}
		});
	}
	/**
	 * ȡ����ť����
	 */
	private void btnCancelCtrl(){
		tDialog.getBtnCancel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					tDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * �����ı������
	 */
	private void txtNameCtrl(){
		tDialog.getTxtName().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((tDialog.getTxtName().getText().length()<16)) {
				} else {
					e.consume(); 
				}
			}
		});
	}
	/**
	 * �رձ�ǩ����
	 */
	private void lblExitCtrl(){
		tDialog.getLblExit().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tDialog.getLblExit().setForeground(Const.BRIGHT_WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tDialog.getLblExit().setForeground(Const.DARK_WHITE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON1) {
					tDialog.setVisible(false);
				}
			}
		});
	}
	/**
	 * ��ʾ������������
	 */
	private void mainPanelCtrl(){
		tDialog.getMainPanel().addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				tDialog.setLocation(jdx+e.getXOnScreen()-mx, jdy+e.getYOnScreen()-my);
			}
		});
		tDialog.getMainPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getXOnScreen();
				my=e.getYOnScreen();
				jdx=tDialog.getX();
				jdy=tDialog.getY();
			}
		});
	}
	/**
	 * ����б�
	 */
	private void groupBuilt(){
		GroupDao gDao=new GroupDao();
		if (gDao.addGroup(tDialog.getTxtName().getText())) {
			Const.tipType=Const.SUCCEED;
			tDialog.showSureMsg("�б����ɹ�", "�б���"+tDialog.getTxtName().getText()+"���½��ɹ���");
		}else {
			Const.tipType=Const.FAIL;
			tDialog.showSureMsg("�б���ʧ��", "�б���"+tDialog.getTxtName().getText()+"���½�ʧ�ܣ�");
		}
		tDialog.getTxtName().setText("");
	}
	/**
	 * ɾ���б�
	 */
	private void delGroup(){
		tDialog.showSureMsg("ɾ���б�", "����ɾ���б����Եȡ�����");
		new DelMusic(group, "ɾ��").start();
	}
	/**
	 * ����б����
	 */
	private void clearGroup(){
		tDialog.showSureMsg("����б�", "��������б����Եȡ�����");
		new DelMusic(group, "���").start();
	}
	/**
	 * �������б�
	 */
	private void renameGroup(){
		GroupDao gDao=new GroupDao();
		if (!tDialog.getTxtName().getText().equals("")) {
			if (gDao.renameGroupName(group.getGroupId(), tDialog.getTxtName().getText())) {
				Const.tipType=Const.SUCCEED;
				tDialog.showSureMsg("�������б�ɹ�", "�б�������Ϊ��"+tDialog.getTxtName().getText()+"��");
			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg("�������б�ɹ�", "�б���"+group.getGroupName()+"��������ʧ�ܣ�");
			}
			tDialog.getTxtName().setText("");
		}
	}
	/**
	 * ɾ������
	 */
	private void deleMusic(){
		if (Const.isPlaying&&!group.getGroupId().equals("2")) {
			if (PlayMusic.getPM().getMusicPath().equals(sPanel.getmInfo().getMusicPath())) {
				PlayMusic.getPM().closePlay();
				MainFrame.getMF().getMFC().initPlayShow();
			}
		}
		GMusicDao gmDao=new GMusicDao();
		MInfoDao mDao=new MInfoDao();
		if ((gmDao.deleteGMusic(group.getGroupId(), sPanel.getmInfo().getMusicId()))) {//ɾ���������ڷ���
			if (!group.getGroupId().equals("2")) {//������������б������ɾ��������Ϣ
				mDao.deleteMusic(sPanel.getmInfo().getMusicId());
				if (gmDao.isExist("2", sPanel.getmInfo().getMusicId())) {//���ɾ���ĸ�������������б�����Ҳ����������б���ɾ��
					gmDao.deleteGMusic("2", sPanel.getmInfo().getMusicId());
				}
			}
			Const.tipType=Const.SUCCEED;
			String strMsg="������"+sPanel.getLblName().getText()+"���Ѵ��б���ɾ����";
			if (tDialog.getRdbtnDelFile().isSelected()) {//ɾ�������ļ�
				if (MyFileUtil.delFile(sPanel.getmInfo().getMusicPath())) {
					strMsg="������"+sPanel.getLblName().getText()+"���ļ���ɾ����";
				}else {
					strMsg="������"+sPanel.getLblName().getText()+"���ļ�ɾ��ʧ�ܣ�";
				}
			}
			tDialog.showSureMsg("ɾ�������ɹ�", strMsg);
		}else {
			Const.tipType=Const.FAIL;
			tDialog.showSureMsg("ɾ�������ɹ�", "������"+sPanel.getLblName().getText()+"�����б���ɾ��ʧ�ܣ�");
		}
	}
	/**
	 * ����������
	 */
	private void renameMusic(){
		if (!tDialog.getTxtName().getText().equals("")) {
			MInfoDao mDao=new MInfoDao();
			if (mDao.renameMusic(sPanel.getmInfo().getMusicId(), tDialog.getTxtName().getText())) {
				Const.tipType=Const.SUCCEED;
				tDialog.showSureMsg("�����������ɹ�", "����������Ϊ��"+tDialog.getTxtName().getText()+"��");
			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg("�����������ɹ�", "��������"+sPanel.getLblName().getText()+"��������ʧ�ܣ�");
			}
			tDialog.getTxtName().setText("");
		}
	}
	/**
	 * ɾ���б��������ļ�
	 */
	private boolean delGroupMusicFile(){
		int num=0;
		boolean isOk=false;
		ArrayList<LocalSongPan> sList=Const.localSongPanel.get(group.getGroupId());
		for (int i = 0; i < sList.size(); i++) {
			if (MyFileUtil.delFile(sList.get(i).getmInfo().getMusicPath())) {
				num++;
			}else {
				tDialog.showSureMsg("ɾ�������ɹ�", "������"+sList.get(i).getLblName().getText()+"���ļ�ɾ��ʧ�ܣ�");
			}
		}
		if (num==sList.size()) {
			isOk=true;
		}
		return isOk;
	}
	/**
	 * ����ɾ���߳�
	 * @author wWw
	 *
	 */
	private class DelMusic extends Thread{
		private Group group;
		private String type;
		public DelMusic(Group group,String type) {
			this.group=group;
			this.type=type;
		}
		@Override
		public synchronized void run() {
			super.run();
			int no=0,num=0;
			MInfoDao mDao=new MInfoDao();
			GroupDao gDao=new GroupDao();
			GMusicDao gmDao=new GMusicDao();
			String strMsg="�б���"+group.getGroupName()+"��"+type+"�ɹ��������������ļ���";
			if (gmDao.getMusicNum(group.getGroupId())>0) {
				Const.tipType=Const.SUCCEED;
				ArrayList<GroupMusic> gList=gmDao.getGMusic(group.getGroupId());
				num=gList.size();
				ArrayList<MusicInfo> mList=mDao.getGroupMusic(group.getGroupId());
				//�ر����ڲ��ŵ�����
				for (int j = 0; j < mList.size(); j++) {
					if (PlayMusic.getPM().getMusicPath().equals(mList.get(j).getMusicPath())) {
						PlayMusic.getPM().closePlay();
						MainFrame.getMF().getMFC().initPlayShow();
					}
				}
				//ɾ������
				for (int i = 0; i < gList.size(); i++) {
					gmDao.deleteGMusic(group.getGroupId(), gList.get(i).getMusicId());
					if (!group.getGroupId().equals("2")) {//������������б������ɾ��������Ϣ
						mDao.deleteMusic(gList.get(i).getMusicId());
						if (gmDao.isExist("2", gList.get(i).getMusicId())) {//���ɾ���ĸ�������������б�����Ҳ����������б���ɾ��
							gmDao.deleteGMusic("2", gList.get(i).getMusicId());
						}
					}
					if (tDialog.getRdbtnDelFile().isSelected()) {//ɾ�������ļ�
						if (delGroupMusicFile()) {
							strMsg="�б���"+group.getGroupName()+"��"+type+"�ɹ�����ɾ���б������������ļ���";
						}else {
							strMsg="�б���"+group.getGroupName()+"��"+type+"�ɹ����б��в��������ļ��޷�ɾ����";
						}
					}
					no++;
					TipDialog.getTD().showRunState(type+"�б�", "�б�"+group.getGroupName()+"���еĸ����Ѿ�ɾ����"+no+"/"+num+"�����Եȡ�����");
					TipDialog.getTD().getBtnOk().setVisible(false);
					TipDialog.getTD().getBtnCancel().setVisible(false);
				}

			}else {
				Const.tipType=Const.FAIL;
				tDialog.showSureMsg(type+"�б�ʧ��", "�б���"+group.getGroupName()+"��"+type+"ʧ�ܣ�");
			}
			if (type.equals("ɾ��")) {
				gDao.deleteGroup(group.getGroupId());
			}
			tDialog.showSureMsg(type+"�б�ɹ�", strMsg);
		}
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public void setsPanel(LocalSongPan sPanel) {
		this.sPanel = sPanel;
	}

}
