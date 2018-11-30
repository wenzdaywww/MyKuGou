package ctrl.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import ctrl.viewctrl.MainFrameCtrl;
import model.JSONMsg;
import model.MClass;
import model.MusicInfo;
import model.User;
import model.UserCollect;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Const;
import view.view.LoginFrame;
import view.view.MainFrame;
import view.view.MusicPanel;
import view.view.TipDialog;

public class Client extends Thread{

	private User user=null;
	private static Client client;
	private static Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	private String nowClassId="All";
	private ArrayList<MusicInfo> musicList=null;
	private String uploadFilePath=null;
	public static boolean isConnect=false;
	private ArrayList<MClass> mClassList=null;
	private ArrayList<UserCollect> userList=null;
	private ArrayList<MusicInfo> userMusicList=null;
	private String nowClassName="所有";
	private int totalPage=0;

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
			}
			while (true) {
				String msg=input.readUTF();
				System.out.println("后台客户端收到："+msg);
				JSONMsg jMsg=JSONMsg.toJSONMsg(msg);
				switch (jMsg.getType()) {
				case JSONMsg.LOGIN:
					getLoginInfo(jMsg);
					break;
				case JSONMsg.CAN_UPLOAD:
					startUpload(jMsg);
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
				case JSONMsg.REVISE_MUSIC:
					getReviseResult(jMsg);
					break;
				case JSONMsg.DEL_CLASS:
					getDelClassResult(jMsg);
					break;
				case JSONMsg.ADD_CLASS:
					getAddClassResult(jMsg);
					break;
				case JSONMsg.RENAME_CLASS:
					getRenameClassResult(jMsg);
					break;
				case JSONMsg.DEL_MUSIC:
					getDelMusicResult(jMsg);
					break;
				case JSONMsg.ADD_CLASS_MUSIC:
					getAddClassMusicResult(jMsg);
					break;
				case JSONMsg.GET_USER:
					getUserResult(jMsg);
					break;
				case JSONMsg.SEARCH_USER:
					getUserResult(jMsg);
					break;
				case JSONMsg.ADD_USER:
					getAddUserResult(jMsg);
					break;
				case JSONMsg.RENAME_USER:
					getAddUserResult(jMsg);
					break;
				case JSONMsg.DEL_USER:
					getAddUserResult(jMsg);
					break;
				case JSONMsg.USER_MUSIC:
					getUserMusicResult(jMsg);
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
	 * 删除收藏歌曲
	 * @param collectId
	 * @param musicId
	 */
	public void delCollectMusic(String collectId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_COLLECT,"", collectId, musicId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取用户的收藏音乐
	 * @param collectId
	 */
	public void getUserMusic(String collectId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.USER_MUSIC, collectId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取用户的歌曲信息
	 */
	private void getUserMusicResult(JSONMsg jMsg){
		if (userMusicList!=null) {
			userMusicList.clear();
		}
		MainFrame.getMF().getCollectPanel().getTableModel().setRowCount(0);
		//遍历JSONArray数据，获取数组中的CMusicList集合对象
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			//遍历JSONArray数据，获取数组中的MusicList集合对象
			userMusicList=new ArrayList<MusicInfo>();
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("musicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MusicInfo music=(MusicInfo) JSONObject.toBean(object,MusicInfo.class);
				userMusicList.add(music);
			}
			MainFrame.getMF().getCollectPanel().insertMusic(userMusicList);
			MainFrame.getMF().getLblResult().setText("用户ID："+Const.userId+" 歌曲收藏的搜索结果");
		}else {
			MainFrame.getMF().getLblResult().setText("用户ID："+Const.userId+" 没有歌曲收藏");
		}
	}
	/**
	 * 删除用户
	 */
	public void delUser(String collectId,String userId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_USER, collectId, userId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 修改用户信息
	 * @param userId
	 * @param renameType
	 * @param rename
	 */
	public void renameUser(String userId,String renameType,String rename){
		JSONMsg jMsg=new JSONMsg(JSONMsg.RENAME_USER, userId,renameType, rename, false);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 用户注册后信息
	 * @param userName
	 * @param userPwd
	 */
	public void addUser(String userName,String userPwd){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_USER,userName, userPwd);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取注册后的结果
	 * @param jMsg
	 */
	private void getAddUserResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getAllUser("All");
		}else {
			TipDialog.getTD().showSureMsg("注册失败", "注册用户失败");
		}
	}
	/**
	 * 搜索用户
	 * @param keyWord
	 */
	public void searchUser(String keyWord){
		JSONMsg jMsg=new JSONMsg(JSONMsg.SEARCH_USER, keyWord);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取用户结果
	 * @param jMsg
	 */
	private void getUserResult(JSONMsg jMsg){
		if (userList!=null) {
			userList.clear();
		}
		MainFrame.getMF().getUserInfoPan().getTableModel().setRowCount(0);
		//遍历JSONArray数据，获取数组中的CMusicList集合对象
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			//遍历JSONArray数据，获取数组中的MusicList集合对象
			userList=new ArrayList<UserCollect>();
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("uCollectList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				UserCollect user=(UserCollect) JSONObject.toBean(object,UserCollect.class);
				userList.add(user);
			}
			MainFrame.getMF().getUserInfoPan().insertMClass(userList);
		}else {
			TipDialog.getTD().showSureMsg("搜索失败", "查无此人");
		}
	}
	/**
	 * 获取所有用户
	 */
	public void getAllUser(String userId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.GET_USER, userId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 添加分类中的音乐
	 * @param classId
	 * @param musicId
	 */
	public void musicAddToClass(String classId,String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_CLASS_MUSIC, classId, musicId, false);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 添加音乐分类歌曲
	 * @param jMsg
	 */
	private void getAddClassMusicResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
//			getClassMusic(nowClassId, nowClassName);
		}else {
			TipDialog.getTD().showSureMsg("操作失败", "添加分类歌曲失败,该歌曲已存在该分类中");
		}
	}
	/**
	 * 获取歌曲信息修改结果
	 * @param jMsg
	 */
	private void getReviseResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getClassMusic(nowClassId, nowClassName);
		}else {
			TipDialog.getTD().showSureMsg("操作失败", "修改歌曲信息失败");
		}
	}
	/**
	 * 修改歌曲信息
	 * @param musicId
	 * @param renameType
	 * @param rename
	 */
	public void reviseMusic(String musicId,String renameType,String rename){
		JSONMsg jMsg=new JSONMsg(JSONMsg.REVISE_MUSIC, musicId,renameType, rename, false);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取分类中的歌曲
	 * @param classId
	 */
	public void getClassMusic(String classId,String className){
		nowClassId=classId;
		nowClassName=className;
		JSONMsg jMsg=new JSONMsg(JSONMsg.CMUSIC,classId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 删除音乐分类
	 * @param classId
	 */
	public void delClass(String classId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_CLASS,classId);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 删除音乐
	 * @param classId
	 */
	public void delMusic(String musicId){
		JSONMsg jMsg=new JSONMsg(JSONMsg.DEL_MUSIC,nowClassId,musicId,0);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取删除音乐的结果
	 * @param jMsg
	 */
	private void getDelMusicResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getClassMusic(nowClassId, nowClassName);
		}else {
			TipDialog.getTD().showSureMsg("删除失败", "删除歌曲失败");
		}
	}
	/**
	 * 修改分类名
	 * @param classId
	 * @param className
	 */
	public void renameClass(String classId,String className){
		JSONMsg jMsg=new JSONMsg(JSONMsg.RENAME_CLASS, classId, className);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 修改歌曲分类名称
	 * @param jMsg
	 */
	private void getRenameClassResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getAllMClass();
		}else {
			TipDialog.getTD().showSureMsg("修改失败", "修改歌曲分类名称失败");
		}
	}
	/**
	 * 获取删除分类和添加的结果
	 * @param jMsg
	 */
	private void getDelClassResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getAllMClass();
		}else {
			TipDialog.getTD().showSureMsg("删除失败", "删除歌曲分类失败");
		}
	}
	/**
	 * 获取分类添加的结果
	 * @param jMsg
	 */
	private void getAddClassResult(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getAllMClass();
		}else {
			TipDialog.getTD().showSureMsg("添加失败", "添加歌曲分类失败");
		}
	}
	/**
	 * 添加分类
	 * @param className
	 */
	public void addClass(String className){
		JSONMsg jMsg=new JSONMsg(JSONMsg.ADD_CLASS,className);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取所有的歌曲
	 * @param classId
	 */
	public void getAllMusic(){
		getClassMusic("All", "歌曲排行");
	}
	/**
	 * 获取分类中的歌曲
	 * @param jMsg
	 */
	private void getClassMusic(JSONMsg jMsg){
		if (musicList!=null) {
			musicList.clear();
		}
		//遍历JSONArray数据，获取数组中的CMusicList集合对象
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			//遍历JSONArray数据，获取数组中的MusicList集合对象
			nowClassId=jMsg.getClassId();
			musicList=new ArrayList<MusicInfo>();
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			JSONArray array=obj.getJSONArray("musicList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MusicInfo music=(MusicInfo) JSONObject.toBean(object,MusicInfo.class);
				musicList.add(music);
			}
		}else {
			MainFrame.getMF().getMusicInfoPanel().removeAll();
			MainFrame.getMF().getMusicInfoPanel().repaint();
		}
		if (musicList!=null&&musicList.size()>0) {
			musicPage();
		}
		MainFrame.getMF().getLblTip().setText(nowClassName+"歌曲的搜索结果");
		MainFrame.getMF().validate();
	}
	/**
	 * 音乐信息分页存取
	 */
	private void musicPage(){
		totalPage=0;
		MainFrameCtrl.setNumPage(0);
		MainFrame.getMF().getMusicInfoPanel().removeAll();
		ArrayList<MusicInfo> mList=new ArrayList<MusicInfo>();
		for (int i = 0; i < musicList.size(); i++) {
			if ((i+1)%10!=0) {//10条记录为一页
				mList.add(musicList.get(i));
				if (i==musicList.size()-1) {
					totalPage++;
					MusicPanel mPanel=new MusicPanel();
					mPanel.insertMusic(mList);
					MainFrame.getMF().getMusicInfoPanel().add(mPanel);
				}
			}else {//超过10条新建分页
				mList.add(musicList.get(i));
				MusicPanel mPanel=new MusicPanel();
				mPanel.insertMusic(mList);
				MainFrame.getMF().getMusicInfoPanel().add(mPanel);
				totalPage++;
				mList.clear();
			}
		}
		if (musicList.size()!=0) {
			MainFrame.getMF().setMTotalPage(1,totalPage);
			MainFrame.getMF().getLblMPreviousPage().setVisible(false);
			MainFrameCtrl.setNumPage(1);
			if (totalPage>1) {
				MainFrame.getMF().getLblMNextPage().setVisible(true);
				MainFrame.getMF().getLblMFirstPage().setVisible(true);
				MainFrame.getMF().getLblMLastPage().setVisible(true);
			}else {
				MainFrame.getMF().getLblMNextPage().setVisible(false);
				MainFrame.getMF().getLblMFirstPage().setVisible(false);
				MainFrame.getMF().getLblMLastPage().setVisible(false);
			}
		}else {
			MainFrame.getMF().getLblMNextPage().setVisible(false);
			MainFrame.getMF().getLblMPreviousPage().setVisible(false);
			MainFrame.getMF().getLblMFirstPage().setVisible(false);
			MainFrame.getMF().getLblMLastPage().setVisible(false);
			MainFrame.getMF().setMTotalPage(0,totalPage);
		}
	}
	/**
	 * 获取音乐的所有分类
	 */
	public void getAllMClass(){
		JSONMsg jMsg=new JSONMsg(JSONMsg.MCLASS);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 获取所有分类列表
	 * @param jMsg
	 */
	private void getMClass(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			MainFrame.getMF().getClassPanel().getTableModel().setRowCount(0);
			JSONObject obj=JSONObject.fromObject(jMsg.toMsg());
			mClassList=new ArrayList<MClass>();
			JSONArray array=obj.getJSONArray("mClassList");
			for (int i = 0; i < array.size(); i++) {
				JSONObject object=array.getJSONObject(i);
				MClass mClass=(MClass) JSONObject.toBean(object,MClass.class);
				mClassList.add(mClass);
			}
			MainFrame.getMF().getClassPanel().insertMClass(mClassList);
		}else {
			TipDialog.getTD().showSureMsg("操作失败", "获取歌曲分类失败");
		}
	}
	/**
	 * 发送是否可以上传请求
	 * @param fileName
	 * @param singer
	 * @param musicName
	 * @param musicTime
	 */
	public void isCanUpload(String singer,String musicName,String musicTime,String filePath){
		File file=new File(filePath);
		uploadFilePath=filePath;
		JSONMsg jMsg=new JSONMsg(JSONMsg.CAN_UPLOAD, file.getName(), musicName, singer, musicTime);
		sendMsg(jMsg.toMsg());
	}
	/**
	 * 可以上传文件
	 * @param jMsg
	 */
	private void startUpload(JSONMsg jMsg){
		if (jMsg.getResult().equals(JSONMsg.SUCCESS)) {
			getAllMusic();
			new Upload(uploadFilePath).start();
		}else {
			TipDialog.getTD().showSureMsg("上传失败", "服务器路径存在同名文件，无法上传");
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
			MainFrame.getMF().getMusicInfoPanel().removeAll();
			MainFrame.getMF().getMusicInfoPanel().repaint();
			TipDialog.getTD().showSureMsg("搜索失败", "未能找到相匹配的歌曲,请尝试其他关键字");
		}
		MainFrame.getMF().getLblTip().setText("搜索结果");
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
			user=jMsg.getUser();
			if (user.getUserId().equals(Const.MANAGE_ID)) {
				LoginFrame.getLF().setVisible(false);
				MainFrame.getMF().setVisible(true);
			}else {
				TipDialog.getTD().showSureMsg("登入失败", "后台账号错误");
			}
		}else {
			TipDialog.getTD().showSureMsg("登入失败", "后台账号或密码错误");
		}
	}
	/**
	 * 线程调用下载音乐
	 */
	public void uploadMusicFile(String fileMsg,byte fileDate[],int start,int lenth){
		synchronized(Client.class){
			try {
				output.writeUTF(fileMsg);
				output.write(fileDate, start, lenth);
				output.flush();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					output.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	/**
	 * 客户端异常处理
	 */
	private void clientException(){
		Const.canUpload=true;
		try {
			if (output!=null&&input!=null&&socket!=null) {
				output.close();
				input.close();
				socket.close();
			}
		} catch (IOException e1) {
		}
		client=null;
		isConnect=false;
		LoginFrame.getLF().getLblTip().setText("服务器连接失败，请确认网络是否已连接");
		MainFrame.getMF().getLblConnectState().setText("已断开连接");
	}
	/**
	 * 发送数据
	 * @param msg
	 */
	private void sendMsg(String msg){
		synchronized (Client.class) {
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
	}
	public User getUser() {
		return user;
	}
	public String getNowClassId() {
		return nowClassId;
	}
	public ArrayList<MClass> getmClassList() {
		return mClassList;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public String getClassName() {
		return nowClassName;
	}

}

