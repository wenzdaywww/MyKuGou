package ctrl.playctrl;

import model.User;
import util.Const;
import model.MClass;
import java.io.File;
import model.Collect;
import model.JSONMsg;
import java.net.Socket;
import util.MyFileUtil;
import model.MusicInfo;
import java.util.Vector;
import util.PictureUtil;
import ctrl.dao.MInfoDao;
import javax.swing.JPanel;
import ctrl.dao.GMusicDao;
import model.CollectMusic;
import java.io.IOException;
import java.util.ArrayList;
import view.view.MainFrame;
import view.view.TipDialog;
import view.view.NetSongPan;
import view.view.UserDialog;
import view.view.GroupPanel;
import net.sf.json.JSONArray;
import view.view.DownloadPan;
import view.view.LocalSongPan;
import net.sf.json.JSONObject;
import java.io.DataInputStream;
import view.view.MusicClassPan;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Client extends Thread{

	private User user=null;
	private int downloadNum=0;
	private boolean login=false;
	private static Socket socket;
	private Collect collect=null;
	private static Client client;
	private DataInputStream input;
	private String nowClassId="All";
	private DataOutputStream output;
	private JPanel lastPan=new JPanel();
	private GroupPanel collectPanel=null;
	public static boolean isConnect=false;
	private ArrayList<MClass> mClassList=null;
	private ArrayList<CollectMusic> cMusicList=null;
	private MusicInfo downloadMusic=new MusicInfo();
	private ArrayList<MusicInfo> onlineMusicList=null;
	/**@see �ղظ�����Ϣ����*/
	private ArrayList<MusicInfo> collectMusicList=null;
	private GroupPanel downloadPanel=new GroupPanel("��������", 0);
	private Vector<DownloadPan> downloadList=new Vector<DownloadPan>();

	public static Client getCL(){
		if (client==null) {
			client=new Client();
			client.start();
		}
		return client;
	}
	private Client(){
	}
	@Override
	public void run() {
		try {
			socket=new Socket(Const.IP, Const.PORT);
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			if (socket.isConnected()) {
				isConnect=true;
				MainFrame.getMF().getLblUserName().setText("����롣����");
			}
			getMClass();
			while (true) {
				String msg=input.readUTF();
				JSONMsg jMsg=JSONMsg.toJSONMsg(msg);
				switch (jMsg.getType()) {
				case JSONMsg.LOGIN:
					getLoginInfo(jMsg);
					break;
				case JSONMsg.REGIST:
					getRegistInfo(jMsg);
					break;
				case JSONMsg.DEL_COLLECT:
					login(user.getUserId(), user.getPwd());
					break;
				case JSONMsg.ADD_COLLECT:
					addCollectResult(jMsg);
					break;
				case JSONMsg.MCLASS:
					getMClass(jMsg);
					break;
				case JSONMsg.CMUSIC:
					getClassMusic(jMsg);
					break;
				case JSONMsg.SERACH:
					getSearchResult(jMsg);
					break;
				case JSONMsg.DOWNLOAD:
					saveMusic(jMsg);
					break;
				case JSONMsg.LISTENING:
					saveMusic(jMsg);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			clientException();
			e.printStackTrace();
		}
	}
	/**
	 * �������ػ�������
	 * @param filePath
	 */
	public void downloadMusic(MusicInfo mInfo){
		File downMusicFile=new File(Const.downloadPath+MyFileUtil.getMusicName(mInfo.getMusicPath()));//�����ļ����и����ļ�
		String musicPath=new MInfoDao().isExist(MyFileUtil.getMusicName(mInfo.getMusicPath()));
		if (downMusicFile.exists()) {
			String musicId=new MInfoDao().addMusic(mInfo, downMusicFile.getAbsolutePath());
			if (musicId!=null) {
				new GMusicDao().addGMusic("1", musicId);
			}
			TipDialog.getTD().showSureMsg("������ʾ", "�ø��������أ������ظ����ء�");
		}else {
			System.out.println("�ļ�����"+downMusicFile.getName());
			if (musicPath==null) {	//���ݿⲻ���ڣ�����·�������ļ�������,���ʱ���ظ���
				if (downloadNum<Const.downloadNum) {
					if (downloadMusic!=mInfo) {//���ͬһ�׸��������ֻ�е�һ����Ч
						downloadMusic=mInfo;
						MainFrame.getMF().addDownloadLastPanel(lastPan);
						JSONMsg	jMsg=new JSONMsg(JSONMsg.DOWNLOAD, mInfo.getMusicId(),mInfo.getMusicPath(), true);
						sendMsg(jMsg.toMsg());
					}
				}else {
					TipDialog.getTD().showSureMsg("������ʾ", "���ض������������Եȡ�����");
				}
			}else {
				File localFile=new File(musicPath);
				if (localFile.exists()) {  //���ݿ���ڣ����ݿ�·���������ļ�����
					TipDialog.getTD().showSureMsg("������ʾ", "�ø��������أ������ظ����ء�");
				}else {	//���ݿ���ڣ����ݿ�·���������ļ������ڣ�����·�������ļ�������,���ʱ���ظ���
					if (downloadNum<Const.downloadNum) {
						if (downloadMusic!=mInfo) {//���ͬһ�׸��������ֻ�е�һ����Ч
							downloadMusic=mInfo;
							MainFrame.getMF().addDownloadLastPanel(lastPan);
							JSONMsg	jMsg=new JSONMsg(JSONMsg.DOWNLOAD, mInfo.getMusicId(),mInfo.getMusicPath(), true);
							sendMsg(jMsg.toMsg());
						}
					}else {
						TipDialog.getTD().showSureMsg("������ʾ", "���ض������������Եȡ�����");
					}
				}
			}
		}
	}
	/**
	 * ����
	 * @param mInfo
	 */
	public void listeningMusic(MusicInfo mInfo){
		File listenFile=new File(Const.listeingPath+MyFileUtil.getMusicName(mInfo.getMusicPath()));
		if (listenFile.exists()) {//����ʱ���ػ����ļ��д��ڸ������ļ�
			if (Const.isPlaying) {
				PlayMusic.getPM().closePlay();
			}
			PlayMusic.getPM().startPlay(listenFile.getAbsolutePath(),mInfo);
		}else {//�����ڸ������ļ�
			if (downloadMusic!=mInfo) {//���ͬһ�׸��������ֻ�е�һ����Ч
				downloadMusic=mInfo;
				JSONMsg jMsg=new JSONMsg(JSONMsg.LISTENING,mInfo.getMusicId(),mInfo.getMusicPath(), true);
				sendMsg(jMsg.toMsg());
			}
		}
	}
	/**
	 * ���������ļ�
	 * @param jMsg
	 */
	private void saveMusic(JSONMsg jMsg){
		byte dataFile[]=new byte[(int) jMsg.getDataLength()];
		try {
			input.read(dataFile);
			File file;
			if (jMsg.getType().equals(JSONMsg.DOWNLOAD)) {
				file=new File(Const.downloadPath+jMsg.getFileName());
				if (!file.exists()) {
					downloadNum++;
					getClassMusic(nowClassId);
					new DownloadState(JSONMsg.DOWNLOAD,downloadMusic,file.getAbsolutePath(),jMsg.getTotalLength()).start();
				}
			}else {
				file=new File(Const.listeingPath+jMsg.getFileName());
				if (!file.exists()) {
					new DownloadState(JSONMsg.LISTENING,downloadMusic,file.getAbsolutePath(),jMsg.getTotalLength()).start();
				}
			}
			FileOutputStream fileOut=new FileOutputStream(file, true);
			fileOut.write(dataFile);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����ղؽ��
	 * @param jMsg
	 */
	private void addCollectResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			login(user.getUserId(), user.getPwd());
			TipDialog.getTD().showSureMsg("�ղسɹ�", "�ɹ��ղظø�����");
		}else {
			TipDialog.getTD().showSureMsg("�ղ�ʧ��", "�ø����Ѿ��ղأ��������ղء�");
		}
	}
	/**
	 * ��������
	 * @param musicName
	 */
	public void searchMusic(String musicName){
		JSONMsg jMsg=new JSONMsg(JSONMsg.SERACH, musicName, 0);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ȡ�����������
	 * @param jMsg
	 */
	private void getSearchResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getClassMusic(jMsg);
		}else {
			showState("δ���ҵ���ƥ��ĸ���,�볢�������ؼ���");
		}
	}
	/**
	 * ��ȡ���з����б�
	 * @param jMsg
	 */
	private void getMClass(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			mClassList=new ArrayList<MClass>();
			JSONArray array=obj.getJSONArray("mClassList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MClass mClass=(MClass) JSONObject.toBean(object,MClass.class);
				mClassList.add(mClass);
			}
			getClassMusic("All");
			addNetClass();
		}
	}
	/**
	 * ��ȡ�����еĸ���
	 * @param jMsg
	 */
	private void getClassMusic(JSONMsg jMsg){
		//����JSONArray���ݣ���ȡ�����е�CMusicList���϶���
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			//����JSONArray���ݣ���ȡ�����е�MusicList���϶���
			nowClassId=jMsg.getClassId();
			onlineMusicList=new ArrayList<MusicInfo>();
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("musicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MusicInfo music=(MusicInfo) JSONObject.toBean(object,MusicInfo.class);
				onlineMusicList.add(music);
			}
			addOnlineMusic();
		}
	}
	/**
	 * �û�����
	 * @param userId
	 * @param userPwd
	 */
	public void login(String userId,String userPwd){
		JSONMsg jMsg=new JSONMsg(JSONMsg.LOGIN, userId, userPwd);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ȡ�û�������Ϣ
	 * @param jMsg
	 */
	private void getLoginInfo(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			login=true;
			user=jMsg.getUser();
			collect=jMsg.getCollect();
			cMusicList=new ArrayList<CollectMusic>();
			collectMusicList=new ArrayList<MusicInfo>();
			//����JSONArray���ݣ���ȡ�����е�CMusicList���϶���
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("cMusicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				CollectMusic cMusic=(CollectMusic) JSONObject.toBean(object,CollectMusic.class);
				cMusicList.add(cMusic);
			}
			//����JSONArray���ݣ���ȡ�����е�MusicList���϶���
			array=obj.getJSONArray("musicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MusicInfo music=(MusicInfo) JSONObject.toBean(object,MusicInfo.class);
				collectMusicList.add(music);
			}
			addLoveSongPanel();
			UserDialog.getUD().getBtnOk().setText("�˳��˺�");
			MainFrame.getMF().getLblUserName().setText(user.getName());
		}else {
			initLogin();
			MainFrame.getMF().getLblUserName().setText("����ʧ�ܡ�����");
			TipDialog.getTD().showSureMsg("����ʧ��", "�˺Ż�����������������롣");
		}
	}
	/**
	 * �û�ע�����Ϣ
	 * @param userName
	 * @param userPwd
	 */
	public void regiset(String userName,String userPwd){
		JSONMsg jMsg=new JSONMsg(JSONMsg.REGIST,userName, userPwd);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ȡ�û�ע�����Ϣ
	 * @param jMsg
	 */
	private void getRegistInfo(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			user=jMsg.getUser();
			collect=jMsg.getCollect();
			UserDialog.getUD().getTextId().setText(user.getUserId());
			UserDialog.getUD().getPwdLField().setText(user.getPwd());
			UserDialog.getUD().getBtnOk().setText("�˳��˺�");
			MainFrame.getMF().getLblUserName().setText(user.getName());
			TipDialog.getTD().showSureMsg("ע��ɹ�", "����˺�IDΪ��"+user.getUserId()+" ,�����ƹ���");
			login(user.getUserId(), user.getPwd());
		}else {
			initLogin();
			TipDialog.getTD().showSureMsg("ע��ʧ��", "������ע�ᡣ");
		}
	}
	/**
	 * ɾ���ղظ���
	 * @param collectId
	 * @param musicId
	 */
	public void delCollect(String collectId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_COLLECT, user.getUserId(), collectId, musicId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ����ղظ���
	 * @param collectId
	 * @param musicId
	 */
	public void addCollect(String collectId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_COLLECT, user.getUserId(), collectId, musicId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ���ظ�������ղظ���
	 * @param collectId
	 * @param musicId
	 */
	public void addCollect(MusicInfo mInfo){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_COLLECT,collect.getCollectId(), mInfo);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ȡ�����еĸ���
	 * @param classId
	 */
	public void getClassMusic(String classId){
		nowClassId=classId;
		addNetClass();
		JSONMsg jMsg=new JSONMsg(JSONMsg.CMUSIC,classId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ȡ���ֵ����з���
	 */
	private void getMClass(){
		JSONMsg jMsg=new JSONMsg(JSONMsg.MCLASS);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * ��ʼ���û������û��ղ��б�����ղ��б�������϶�������������϶���
	 */
	public void initLogin(){
		user=null;
		collect=null;
		collectMusicList=null;
		cMusicList=null;
		collectPanel=null;
		UserDialog.getUD().getBtnOk().setText("����");
		MainFrame.getMF().getLovePanel().removeAll();
		MainFrame.getMF().getLovePanel().repaint();
		MainFrame.getMF().getLblUserName().setText("����롣����");
	}
	/**
	 * ��������ղظ�����
	 */
	private void addLoveSongPanel(){
		MainFrame.getMF().getLovePanel().removeAll();
		collectPanel=new GroupPanel(collect);
		collectPanel.setLblNumber(collectMusicList.size());
		collectPanel.setOpen(true);
		collectPanel.getLblOpen().setIcon(PictureUtil.CLOSE_ICO);
		MainFrame.getMF().addLoveMusicPanel(collectPanel);
		for (int i = 0; i < collectMusicList.size(); i++) {
			LocalSongPan sPanel=new LocalSongPan(collectMusicList.get(i), collect);
			MainFrame.getMF().addLoveMusicPanel(sPanel);
		}
		MainFrame.getMF().addLoveLastPanel(new JPanel());
		MainFrame.getMF().validate();
	}
	/**
	 * ������߸�������
	 */
	private void addNetClass(){
		MainFrame.getMF().getClassPanel().removeAll();
		MClass allClass=new MClass();
		allClass.setClassId("All");
		allClass.setName("���Ÿ���");
		MusicClassPan allMPan=new MusicClassPan(allClass);
		MainFrame.getMF().addClassMusicPanel(allMPan);
		for (int i = 0; i < mClassList.size(); i++) {
			MusicClassPan mPan=new MusicClassPan(mClassList.get(i));
			if (nowClassId.equals(mClassList.get(i).getClassId())) {
				mPan.setBackground(Const.MENU_MOUSE_IN);
			}else if(nowClassId.equals("All")){
				allMPan.setBackground(Const.MENU_MOUSE_IN);
			}else {
				mPan.setBackground(Const.WHITE_COLOR);
			}
			MainFrame.getMF().addClassMusicPanel(mPan);
		}
		MainFrame.getMF().addClasLastPanel(new JPanel());
		MainFrame.getMF().validate();
	}
	/**
	 * ������߸�����Ϣ
	 */
	private void addOnlineMusic(){
		MainFrame.getMF().getNetMusicPanel().removeAll();
		for (int i = 0; i < onlineMusicList.size(); i++) {
			NetSongPan nPan=new NetSongPan(i+1,onlineMusicList.get(i));
			MainFrame.getMF().addOnlineMusicPanel(nPan);
		}
		MainFrame.getMF().addOnlineLastPanel(new JPanel());
		MainFrame.getMF().validate();
	}
	/**
	 * �������������ʾ״̬��Ϣ
	 * @param msg
	 */
	private void showState(String msg){
		MainFrame.getMF().getNetMusicPanel().removeAll();
		NetSongPan nPan=new NetSongPan(msg);
		MainFrame.getMF().addOnlineMusicPanel(nPan);
		MainFrame.getMF().addOnlineLastPanel(new JPanel());
		MainFrame.getMF().validate();
	}
	/**
	 * ��������
	 * @param msg
	 */
	private void sendMsg(String msg){
		if (socket.isConnected()) {
			try {
				output.writeUTF(msg);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					output.close();
				} catch (IOException e1) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ʵʱ��ʾ���ؽ��ȵ���
	 * @author wWw
	 *
	 */
	private class DownloadState extends Thread{

		private File musicFile;
		private long fileLength;
		private DownloadPan dPan;
		private MusicInfo mInfo;
		private String type;

		public DownloadState(String type,MusicInfo mInfo,String fileName,long fileLength){
			setName(fileName);
			this.mInfo=mInfo;
			this.type=type;
			this.fileLength=fileLength;
			this.musicFile=new File(fileName);
			dPan=new DownloadPan(mInfo, fileLength);
			downloadList.add(dPan);
		}
		@Override
		public void run() {
			if (type.equals(JSONMsg.DOWNLOAD)) {
				downloadMusic();
			}else {
				listeningMusic();
			}
		}
		/**
		 * ���ظ���
		 */
		private void downloadMusic(){
			openDownloadList();
			while (true) {
				if ((fileLength-musicFile.length())<100*1024) {
					dPan.getProBar().setValue((int)fileLength);
					MainFrame.getMF().getDownloadPanel().remove(dPan);
					MainFrame.getMF().repaint();
					addToSql("1", mInfo.getMusicName(), mInfo.getSinger(), 
							musicFile.getAbsolutePath(), mInfo.getMusicTime(), mInfo.getNetMusic());
					MainFrame.getMF().getMFC().showMusic();
					synchronized (DownloadState.class) {
						downloadList.remove(dPan);
						downloadNum--;
						downloadPanel.setLblNumber(downloadNum);
					}
					break;
				}
				dPan.getProBar().setValue((int)musicFile.length());
				dPan.validate();
			}
		}
		/**
		 * ��������
		 */
		private void listeningMusic(){
			MainFrame.getMF().setLblPlayName("���ڻ��桰"+mInfo.getSinger()+" - "+mInfo.getMusicName()+"��, ���Եȡ�����");
			System.out.println("�ļ��ܴ�С=="+fileLength);
			while (true) {
				if ((fileLength-musicFile.length())<100*1024) {
					System.out.println("�ļ���ǰ��С=="+musicFile.length());
					if (Const.isPlaying) {
						PlayMusic.getPM().closePlay();
					}
					MainFrame.getMF().setLblPlayName(mInfo.getSinger()+" - "+mInfo.getMusicName());
					PlayMusic.getPM().startPlay(musicFile.getAbsolutePath(),mInfo);
					downloadNum--;
					break;
				}
			}
		}
	}
	/**
	 * չ�������б�
	 */
	public void openDownloadList(){
		synchronized (Client.class){
			MainFrame.getMF().getDownloadPanel().removeAll();
			MainFrame.getMF().addDownloadMusicPanel(downloadPanel);
			downloadPanel.setLblNumber(downloadNum);
			downloadPanel.setOpen(true);
			downloadPanel.getLblOpen().setIcon(PictureUtil.CLOSE_ICO);
			for (int i = 0; i < downloadList.size(); i++) {
				MainFrame.getMF().addDownloadMusicPanel(downloadList.get(i));
			}
			MainFrame.getMF().addDownloadLastPanel(lastPan);
			MainFrame.getMF().repaint();
		}
	}
	/**
	 * �ر����ط���
	 */
	public void closeDownloadList(){
		synchronized (Client.class){
			MainFrame.getMF().getDownloadPanel().removeAll();
			MainFrame.getMF().addDownloadMusicPanel(downloadPanel);
			downloadPanel.setLblNumber(downloadNum);
			downloadPanel.setOpen(false);
			downloadPanel.getLblOpen().setIcon(PictureUtil.OPEN_ICO);
			MainFrame.getMF().addDownloadLastPanel(lastPan);
			MainFrame.getMF().repaint();
		}
	}
	/**
	 * ������ֵ���������
	 * @param groupId
	 * @param musicName
	 * @param singer
	 * @param musicPath
	 * @param musicTime
	 * @param netMusic
	 */
	private void addToSql(String groupId,String musicName,String singer,String musicPath,String musicTime,String netMusic){
		MInfoDao mDao=new MInfoDao();
		GMusicDao gmDao=new GMusicDao();
		mDao.addMusic(musicName, singer,musicPath, musicTime, netMusic);
		gmDao.addGMusic(groupId, mDao.getMaxId());
	}
	/**
	 * �ͻ����쳣����
	 */
	private void clientException(){
		try {
			if (client!=null) {
				UserDialog.getUD().getBtnOk().setText("����");
			}
			if (output!=null&&input!=null&&socket!=null) {
				output.close();
				input.close();
				socket.close();
			}
		} catch (IOException e1) {
		}
		client=null;
		isConnect=false;
		MainFrame.getMF().getLblUserName().setText("�ͻ�������ʧ�ܣ����������Ƿ����ӡ�");
		showState("��ȡ�������ʧ��,���������Ƿ����ӡ�");
	}
	public User getUser() {
		return user;
	}
	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	public Collect getCollect() {
		return collect;
	}
	public ArrayList<CollectMusic> getCMusicList() {
		return cMusicList;
	}
	public ArrayList<MusicInfo> getCollectMusicList() {
		return collectMusicList;
	}
	public Vector<DownloadPan> getDownloadList() {
		return downloadList;
	}
}

