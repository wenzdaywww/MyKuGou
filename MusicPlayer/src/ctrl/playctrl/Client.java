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
	/**@see 收藏歌曲信息集合*/
	private ArrayList<MusicInfo> collectMusicList=null;
	private GroupPanel downloadPanel=new GroupPanel("正在下载", 0);
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
				MainFrame.getMF().getLblUserName().setText("请登入。。。");
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
	 * 音乐下载或者试听
	 * @param filePath
	 */
	public void downloadMusic(MusicInfo mInfo){
		File downMusicFile=new File(Const.downloadPath+MyFileUtil.getMusicName(mInfo.getMusicPath()));//下载文件夹中歌曲文件
		String musicPath=new MInfoDao().isExist(MyFileUtil.getMusicName(mInfo.getMusicPath()));
		if (downMusicFile.exists()) {
			String musicId=new MInfoDao().addMusic(mInfo, downMusicFile.getAbsolutePath());
			if (musicId!=null) {
				new GMusicDao().addGMusic("1", musicId);
			}
			TipDialog.getTD().showSureMsg("下载提示", "该歌曲已下载，无需重复下载。");
		}else {
			System.out.println("文件名："+downMusicFile.getName());
			if (musicPath==null) {	//数据库不存在，下载路径歌曲文件不存在,则此时下载歌曲
				if (downloadNum<Const.downloadNum) {
					if (downloadMusic!=mInfo) {//如果同一首歌点击多次则只有第一次有效
						downloadMusic=mInfo;
						MainFrame.getMF().addDownloadLastPanel(lastPan);
						JSONMsg	jMsg=new JSONMsg(JSONMsg.DOWNLOAD, mInfo.getMusicId(),mInfo.getMusicPath(), true);
						sendMsg(jMsg.toMsg());
					}
				}else {
					TipDialog.getTD().showSureMsg("下载提示", "下载队列已满，请稍等。。。");
				}
			}else {
				File localFile=new File(musicPath);
				if (localFile.exists()) {  //数据库存在，数据库路径的音乐文件存在
					TipDialog.getTD().showSureMsg("下载提示", "该歌曲已下载，无需重复下载。");
				}else {	//数据库存在，数据库路径的音乐文件不存在，下载路径歌曲文件不存在,则此时下载歌曲
					if (downloadNum<Const.downloadNum) {
						if (downloadMusic!=mInfo) {//如果同一首歌点击多次则只有第一次有效
							downloadMusic=mInfo;
							MainFrame.getMF().addDownloadLastPanel(lastPan);
							JSONMsg	jMsg=new JSONMsg(JSONMsg.DOWNLOAD, mInfo.getMusicId(),mInfo.getMusicPath(), true);
							sendMsg(jMsg.toMsg());
						}
					}else {
						TipDialog.getTD().showSureMsg("下载提示", "下载队列已满，请稍等。。。");
					}
				}
			}
		}
	}
	/**
	 * 试听
	 * @param mInfo
	 */
	public void listeningMusic(MusicInfo mInfo){
		File listenFile=new File(Const.listeingPath+MyFileUtil.getMusicName(mInfo.getMusicPath()));
		if (listenFile.exists()) {//试听时本地缓存文件夹存在该音乐文件
			if (Const.isPlaying) {
				PlayMusic.getPM().closePlay();
			}
			PlayMusic.getPM().startPlay(listenFile.getAbsolutePath(),mInfo);
		}else {//不存在该音乐文件
			if (downloadMusic!=mInfo) {//如果同一首歌点击多次则只有第一次有效
				downloadMusic=mInfo;
				JSONMsg jMsg=new JSONMsg(JSONMsg.LISTENING,mInfo.getMusicId(),mInfo.getMusicPath(), true);
				sendMsg(jMsg.toMsg());
			}
		}
	}
	/**
	 * 保存音乐文件
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
	 * 返回收藏结果
	 * @param jMsg
	 */
	private void addCollectResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			login(user.getUserId(), user.getPwd());
			TipDialog.getTD().showSureMsg("收藏成功", "成功收藏该歌曲。");
		}else {
			TipDialog.getTD().showSureMsg("收藏失败", "该歌曲已经收藏，无需再收藏。");
		}
	}
	/**
	 * 歌曲搜索
	 * @param musicName
	 */
	public void searchMusic(String musicName){
		JSONMsg jMsg=new JSONMsg(JSONMsg.SERACH, musicName, 0);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取歌曲搜索结果
	 * @param jMsg
	 */
	private void getSearchResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getClassMusic(jMsg);
		}else {
			showState("未能找到相匹配的歌曲,请尝试其他关键字");
		}
	}
	/**
	 * 获取所有分类列表
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
	 * 获取分类中的歌曲
	 * @param jMsg
	 */
	private void getClassMusic(JSONMsg jMsg){
		//遍历JSONArray数据，获取数组中的CMusicList集合对象
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			//遍历JSONArray数据，获取数组中的MusicList集合对象
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
	 * 用户登入
	 * @param userId
	 * @param userPwd
	 */
	public void login(String userId,String userPwd){
		JSONMsg jMsg=new JSONMsg(JSONMsg.LOGIN, userId, userPwd);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取用户登入信息
	 * @param jMsg
	 */
	private void getLoginInfo(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			login=true;
			user=jMsg.getUser();
			collect=jMsg.getCollect();
			cMusicList=new ArrayList<CollectMusic>();
			collectMusicList=new ArrayList<MusicInfo>();
			//遍历JSONArray数据，获取数组中的CMusicList集合对象
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("cMusicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				CollectMusic cMusic=(CollectMusic) JSONObject.toBean(object,CollectMusic.class);
				cMusicList.add(cMusic);
			}
			//遍历JSONArray数据，获取数组中的MusicList集合对象
			array=obj.getJSONArray("musicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MusicInfo music=(MusicInfo) JSONObject.toBean(object,MusicInfo.class);
				collectMusicList.add(music);
			}
			addLoveSongPanel();
			UserDialog.getUD().getBtnOk().setText("退出账号");
			MainFrame.getMF().getLblUserName().setText(user.getName());
		}else {
			initLogin();
			MainFrame.getMF().getLblUserName().setText("登入失败。。。");
			TipDialog.getTD().showSureMsg("登入失败", "账号或密码错误，请重新输入。");
		}
	}
	/**
	 * 用户注册后信息
	 * @param userName
	 * @param userPwd
	 */
	public void regiset(String userName,String userPwd){
		JSONMsg jMsg=new JSONMsg(JSONMsg.REGIST,userName, userPwd);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取用户注册后信息
	 * @param jMsg
	 */
	private void getRegistInfo(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			user=jMsg.getUser();
			collect=jMsg.getCollect();
			UserDialog.getUD().getTextId().setText(user.getUserId());
			UserDialog.getUD().getPwdLField().setText(user.getPwd());
			UserDialog.getUD().getBtnOk().setText("退出账号");
			MainFrame.getMF().getLblUserName().setText(user.getName());
			TipDialog.getTD().showSureMsg("注册成功", "你的账号ID为："+user.getUserId()+" ,请妥善管理。");
			login(user.getUserId(), user.getPwd());
		}else {
			initLogin();
			TipDialog.getTD().showSureMsg("注册失败", "请重新注册。");
		}
	}
	/**
	 * 删除收藏歌曲
	 * @param collectId
	 * @param musicId
	 */
	public void delCollect(String collectId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_COLLECT, user.getUserId(), collectId, musicId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 添加收藏歌曲
	 * @param collectId
	 * @param musicId
	 */
	public void addCollect(String collectId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_COLLECT, user.getUserId(), collectId, musicId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 本地歌曲添加收藏歌曲
	 * @param collectId
	 * @param musicId
	 */
	public void addCollect(MusicInfo mInfo){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_COLLECT,collect.getCollectId(), mInfo);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取分类中的歌曲
	 * @param classId
	 */
	public void getClassMusic(String classId){
		nowClassId=classId;
		addNetClass();
		JSONMsg jMsg=new JSONMsg(JSONMsg.CMUSIC,classId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取音乐的所有分类
	 */
	private void getMClass(){
		JSONMsg jMsg=new JSONMsg(JSONMsg.MCLASS);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 初始化用户对象、用户收藏列表对象、收藏列表歌曲集合对象、网络歌曲集合对象
	 */
	public void initLogin(){
		user=null;
		collect=null;
		collectMusicList=null;
		cMusicList=null;
		collectPanel=null;
		UserDialog.getUD().getBtnOk().setText("登入");
		MainFrame.getMF().getLovePanel().removeAll();
		MainFrame.getMF().getLovePanel().repaint();
		MainFrame.getMF().getLblUserName().setText("请登入。。。");
	}
	/**
	 * 添加网络收藏歌曲条
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
	 * 添加在线歌曲分类
	 */
	private void addNetClass(){
		MainFrame.getMF().getClassPanel().removeAll();
		MClass allClass=new MClass();
		allClass.setClassId("All");
		allClass.setName("热门歌曲");
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
	 * 添加在线歌曲信息
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
	 * 网络歌曲区域显示状态信息
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
	 * 发送数据
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
	 * 实时显示下载进度的类
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
		 * 下载歌曲
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
		 * 试听歌曲
		 */
		private void listeningMusic(){
			MainFrame.getMF().setLblPlayName("正在缓存“"+mInfo.getSinger()+" - "+mInfo.getMusicName()+"”, 请稍等。。。");
			System.out.println("文件总大小=="+fileLength);
			while (true) {
				if ((fileLength-musicFile.length())<100*1024) {
					System.out.println("文件当前大小=="+musicFile.length());
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
	 * 展开下载列表
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
	 * 关闭下载分组
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
	 * 添加音乐到本地数据
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
	 * 客户端异常处理
	 */
	private void clientException(){
		try {
			if (client!=null) {
				UserDialog.getUD().getBtnOk().setText("登入");
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
		MainFrame.getMF().getLblUserName().setText("客户端连接失败，请检查网络是否连接。");
		showState("获取网络歌曲失败,请检查网络是否连接。");
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

