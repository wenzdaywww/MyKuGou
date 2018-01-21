package ctrl.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import ctrl.dao.CMusicDao;
import ctrl.dao.ClassMusicDao;
import ctrl.dao.CollectDao;
import ctrl.dao.MClassDao;
import ctrl.dao.MInfoDao;
import ctrl.dao.UserDao;
import model.Collect;
import model.CollectMusic;
import model.JSONMsg;
import model.MClass;
import model.MusicInfo;
import model.User;
import model.UserCollect;

public class ChatSocket extends Thread{

	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;

	public ChatSocket(Socket socket) {
		super();
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			output=new DataOutputStream(socket.getOutputStream());
			input=new DataInputStream(socket.getInputStream());
			while (true) {
				String msg=input.readUTF();
				System.out.println("服务器收到："+msg);
				JSONMsg jMsg=JSONMsg.toJSONMsg(msg);
				switch (jMsg.getType()) {
				case JSONMsg.LOGIN:
					login(jMsg);
					break;
				case JSONMsg.REGIST:
					regist(jMsg);
					break;
				case JSONMsg.DEL_COLLECT:
					delCollect(jMsg);
					break;
				case JSONMsg.ADD_COLLECT:
					addCollect(jMsg);
					break;
				case JSONMsg.MCLASS:
					getMClass(jMsg);
					break;
				case JSONMsg.CMUSIC:
					getCMusic(jMsg);
					break;
				case JSONMsg.SERACH:
					searchMusic(jMsg);
					break;
				case JSONMsg.DOWNLOAD:
					downloadMusic(jMsg);
					break;
				case JSONMsg.LISTENING:
					new Download(jMsg.getType(),jMsg.getFilePath(), this).start();
					break;
				case JSONMsg.UPLOAD:
					saveMusic(jMsg);
					break;
				case JSONMsg.CAN_UPLOAD:
					canUpload(jMsg);
					break;
				case JSONMsg.REVISE_MUSIC:
					reviseMusic(jMsg);
					break;
				case JSONMsg.DEL_CLASS:
					delClass(jMsg);
					break;
				case JSONMsg.ADD_CLASS:
					addClass(jMsg);
					break;
				case JSONMsg.RENAME_CLASS:
					renameClass(jMsg);
					break;
				case JSONMsg.DEL_MUSIC:
					delMusic(jMsg);
					break;
				case JSONMsg.ADD_CLASS_MUSIC:
					addClassMusic(jMsg);
					break;
				case JSONMsg.GET_USER:
					getUserInfo(jMsg);
					break;
				case JSONMsg.SEARCH_USER:
					getUserInfo(jMsg);
					break;
				case JSONMsg.ADD_USER:
					addUser(jMsg);
					break;
				case JSONMsg.RENAME_USER:
					renameUser(jMsg);
					break;
				case JSONMsg.DEL_USER:
					delUser(jMsg);
					break;
				case JSONMsg.USER_MUSIC:
					getUserMusic(jMsg);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Server.getS().deleteSocket(this);
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户的信息
	 * @param jMsg
	 */
	private void getUserMusic(JSONMsg jMsg){
		JSONMsg msg;
		ArrayList<MusicInfo> mList=new CMusicDao().getCollectMusic(jMsg.getClassId());
		if (mList.size()>0) {
			msg=new JSONMsg(JSONMsg.USER_MUSIC, JSONMsg.SUCCESS, 0, mList);
		}else {
			msg=new JSONMsg(JSONMsg.USER_MUSIC, JSONMsg.FAIL, 0, mList);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 删除用户
	 * @param jMsg
	 */
	private void delUser(JSONMsg jMsg){
		JSONMsg msg;
		if (new UserDao().deleteUser(jMsg.getIdOrName(), jMsg.getUserPwd())) {
			msg=new JSONMsg(JSONMsg.DEL_USER, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.DEL_USER, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 后台添加用户
	 * @param jMsg
	 */
	private void addUser(JSONMsg jMsg){
		JSONMsg obj;
		User user=new User();
		user.setUserId(null);
		user.setName(jMsg.getIdOrName());
		user.setPwd(jMsg.getUserPwd());
		String userId=new UserDao().addUser(user);
		if (userId!=null) {
			user.setUserId(userId);
			CollectDao cDao=new CollectDao(); 
			if (cDao.addCollect(userId)!=null) {
				obj=new JSONMsg(JSONMsg.ADD_USER, JSONMsg.SUCCESS);
			}else {
				obj=new JSONMsg(JSONMsg.ADD_USER, JSONMsg.FAIL);
			}
		}else {
			obj=new JSONMsg(JSONMsg.ADD_USER, JSONMsg.FAIL);
		}
		sendMsg(obj.toMsg());
	}
	/**
	 * 获取用户信息
	 * @param jMsg
	 */
	private void getUserInfo(JSONMsg jMsg){
		ArrayList<UserCollect> uList=new ArrayList<UserCollect>();
		uList=new UserDao().getUser(jMsg.getClassId());
		JSONMsg msg;
		if (uList.size()>0) {
			msg=new JSONMsg(JSONMsg.GET_USER, JSONMsg.SUCCESS, uList, 0);
		}else {
			msg=new JSONMsg(JSONMsg.GET_USER, JSONMsg.FAIL, uList, 0);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 添加分类音乐
	 * @param jMsg
	 */
	private void addClassMusic(JSONMsg jMsg){
		JSONMsg msg;
		if (new ClassMusicDao().addMusicClass(jMsg.getClassId(), jMsg.getMusicId())) {
			msg=new JSONMsg(JSONMsg.ADD_CLASS_MUSIC, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.ADD_CLASS_MUSIC, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 删除音乐
	 * @param jMsg
	 */
	private void delMusic(JSONMsg jMsg){
		JSONMsg msg;
		if (jMsg.getClassId().equals("")||jMsg.getClassId().equals("All")) {
			if (new MInfoDao().deleteMusic(jMsg.getMusicId())) {
				msg=new JSONMsg(JSONMsg.DEL_MUSIC, JSONMsg.SUCCESS);
			}else {
				msg=new JSONMsg(JSONMsg.DEL_MUSIC, JSONMsg.FAIL);
			}
		}else {
			if (new ClassMusicDao().delMusicClass(jMsg.getClassId(), jMsg.getMusicId())) {
				msg=new JSONMsg(JSONMsg.DEL_MUSIC, JSONMsg.SUCCESS);
			}else {
				msg=new JSONMsg(JSONMsg.DEL_MUSIC, JSONMsg.FAIL);
			}
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 删除音乐分类
	 * @param jMsg
	 */
	private void delClass(JSONMsg jMsg){
		JSONMsg msg;
		if (new MClassDao().delMusicClass(jMsg.getClassId())) {
			msg=new JSONMsg(JSONMsg.DEL_CLASS, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.DEL_CLASS, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 修改分类名称
	 * @param jMsg
	 */
	private void renameClass(JSONMsg jMsg){
		JSONMsg msg;
		if (new MClassDao().renameClass(jMsg.getIdOrName(), jMsg.getUserPwd())) {
			msg=new JSONMsg(JSONMsg.RENAME_CLASS, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.RENAME_CLASS, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 添加音乐分类
	 * @param jMsg
	 */
	private void addClass(JSONMsg jMsg){
		JSONMsg msg;
		if (new MClassDao().addClassMusic(jMsg.getClassId())) {
			msg=new JSONMsg(JSONMsg.ADD_CLASS, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.ADD_CLASS, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 重命名用户信息
	 * @param jMsg
	 */
	private void renameUser(JSONMsg jMsg){
		JSONMsg msg;
		if (new UserDao().updateUser(jMsg.getMusicId(), jMsg.getRenameType(), jMsg.getRename())) {
			msg=new JSONMsg(JSONMsg.RENAME_USER, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.RENAME_USER, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 修改歌曲信息
	 * @param jMsg
	 */
	private void reviseMusic(JSONMsg jMsg){
		JSONMsg msg;
		if (new MInfoDao().updateMusic(jMsg.getMusicId(), jMsg.getRenameType(), jMsg.getRename())) {
			msg=new JSONMsg(JSONMsg.REVISE_MUSIC, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.REVISE_MUSIC, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 音乐下载
	 * @param jMsg
	 */
	private void downloadMusic(JSONMsg jMsg){
		MInfoDao mDao=new MInfoDao();
		int playCount=Integer.parseInt(mDao.getPlayCount(jMsg.getMusicId()));
		mDao.setPlayCount(jMsg.getMusicId(), Integer.toString(playCount+1));
		new Download(jMsg.getType(),jMsg.getFilePath(), this).start();
	}
	/**
	 * 保存音乐文件
	 * @param jMsg
	 */
	private void saveMusic(JSONMsg jMsg){
		byte dataFile[]=new byte[(int) jMsg.getDataLength()];
		try {
			input.read(dataFile);
			File file=new File("Music/"+jMsg.getFileName());//上传文件存放的路径
			FileOutputStream fileOut=new FileOutputStream(file, true);
			fileOut.write(dataFile);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断是否可以上传文件
	 * @param jMsg
	 */
	private void canUpload(JSONMsg jMsg){
		JSONMsg msg;
		File dirFile=new File("Music");
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File file=new File("Music/"+jMsg.getFileName());//上传文件存放的路径
		if (!file.exists()) {
			new MInfoDao().addMusic(jMsg.getMusicName(), jMsg.getSinger(),
					file.getAbsolutePath(), jMsg.getMusicTime());
			msg=new JSONMsg(JSONMsg.CAN_UPLOAD, JSONMsg.SUCCESS);
		}else {
			msg=new JSONMsg(JSONMsg.CAN_UPLOAD, JSONMsg.FAIL);
		}
		sendMsg(msg.toMsg());
	}
	/**
	 * 歌曲搜索
	 * @param jMsg
	 */
	private void searchMusic(JSONMsg jMsg){
		JSONMsg jsg;
		ArrayList<MusicInfo> musicList=new MInfoDao().searchMusic(jMsg.getMusicName());
		if (musicList.size()>0) {
			jsg=new JSONMsg(JSONMsg.SERACH, JSONMsg.SUCCESS, 0, musicList);
		}else {
			jsg=new JSONMsg(JSONMsg.SERACH, JSONMsg.FAIL, 0, musicList);
		}
		sendMsg(jsg.toMsg());
	}
	/**
	 * 获取歌曲分类
	 * @param jMsg
	 */
	private void getMClass(JSONMsg jMsg){
		JSONMsg jsg;
		ArrayList<MClass> mClassList=new MClassDao().getAllClass();
		if (mClassList.size()>0) {
			jsg=new JSONMsg(JSONMsg.MCLASS, JSONMsg.SUCCESS,mClassList);
		}else {
			jsg=new JSONMsg(JSONMsg.MCLASS, JSONMsg.FAIL,mClassList);
		}
		sendMsg(jsg.toMsg());
	}
	/**
	 * 获取分类中的歌曲或者全部歌曲
	 * @param jMsg
	 */
	private void getCMusic(JSONMsg jMsg){
		JSONMsg jsg;
		if (jMsg.getClassId().equals("All")) {//获取所有歌曲
			ArrayList<MusicInfo> musicList=new MInfoDao().getAllMusic();
			if (musicList.size()>0) {
				jsg=new JSONMsg(JSONMsg.CMUSIC, JSONMsg.SUCCESS, jMsg.getClassId(), musicList);
			}else {
				jsg=new JSONMsg(JSONMsg.CMUSIC, JSONMsg.FAIL, jMsg.getClassId(), musicList);
			}
		}else {//获取分类中的歌曲
			ArrayList<MusicInfo> musicList=new ClassMusicDao().getClassMusic(jMsg.getClassId());
			if (musicList.size()>0) {
				jsg=new JSONMsg(JSONMsg.CMUSIC, JSONMsg.SUCCESS, jMsg.getClassId(), musicList);
			}else {
				jsg=new JSONMsg(JSONMsg.CMUSIC, JSONMsg.FAIL, jMsg.getClassId(), musicList);
			}
		}
		sendMsg(jsg.toMsg());
	}
	/**
	 * 用户登入
	 * @param jMsg
	 */
	private void login(JSONMsg jMsg){
		JSONMsg obj;
		Collect collect=null;
		ArrayList<CollectMusic> cMusicList=null;
		ArrayList<MusicInfo> musicList=null;
		User user=new UserDao().login(jMsg.getIdOrName(), jMsg.getUserPwd());
		if (user!=null) {
			collect=new CollectDao().getCollect(user.getUserId());
			if (collect!=null) {
				cMusicList=new CMusicDao().getCMusic(collect.getCollectId());
				musicList=new MInfoDao().getCollectMusic(collect.getCollectId());
			}
			obj=new JSONMsg(JSONMsg.LOGIN, JSONMsg.SUCCESS, user,collect,cMusicList,musicList);
		}else {
			obj=new JSONMsg(JSONMsg.LOGIN, JSONMsg.FAIL, user,collect,cMusicList,musicList);
		}
		sendMsg(obj.toMsg());
	}
	/**
	 * 用户注册
	 * @param jMsg
	 */
	private void regist(JSONMsg jMsg){
		JSONMsg obj;
		User user=new User();
		Collect collect=null;
		user.setUserId(null);
		user.setName(jMsg.getIdOrName());
		user.setPwd(jMsg.getUserPwd());
		String userId=new UserDao().addUser(user);
		if (userId!=null) {
			user.setUserId(userId);
			CollectDao cDao=new CollectDao(); 
			cDao.addCollect(userId);
			collect=cDao.getCollect(userId);
			if (collect!=null) {
				obj=new JSONMsg(JSONMsg.REGIST, JSONMsg.SUCCESS, user,collect);
			}else {
				obj=new JSONMsg(JSONMsg.REGIST, JSONMsg.SUCCESS, user,collect);
			}
		}else {
			obj=new JSONMsg(JSONMsg.REGIST, JSONMsg.FAIL, user, collect);
		}
		sendMsg(obj.toMsg());
	}
	/**
	 * 删除用户收藏歌曲
	 * @param jMsg
	 */
	private void delCollect(JSONMsg jMsg){
		JSONMsg obj;
		if (new CMusicDao().delCMusic(jMsg.getCollectId(), jMsg.getMusicId())) {
			obj=new JSONMsg(JSONMsg.DEL_COLLECT, JSONMsg.SUCCESS);
		}else {
			obj=new JSONMsg(JSONMsg.DEL_COLLECT, JSONMsg.FAIL);
		}
		sendMsg(obj.toMsg());
	}
	/**
	 * 删除用户收藏歌曲
	 * @param jMsg
	 */
	private void addCollect(JSONMsg jMsg){
		JSONMsg obj;
		if (!jMsg.getIdOrName().equals("")) {//网络歌曲的收藏
			CMusicDao cMDao=new CMusicDao();
			if (!cMDao.isExist(jMsg.getCollectId(), jMsg.getMusicId())) {
				cMDao.addCMusic(jMsg.getCollectId(), jMsg.getMusicId());
				obj=new JSONMsg(JSONMsg.ADD_COLLECT, JSONMsg.SUCCESS);
			}else {
				obj=new JSONMsg(JSONMsg.ADD_COLLECT, JSONMsg.FAIL);
			}
		}else {//本地歌曲的收藏
			String musicId=new MInfoDao().isExist(jMsg.getMusicName(), jMsg.getSinger(), jMsg.getMusicTime());
			if (musicId!=null) {
				CMusicDao cMDao=new CMusicDao();
				if (!cMDao.isExist(jMsg.getCollectId(), musicId)) {
					cMDao.addCMusic(jMsg.getCollectId(), musicId);
					obj=new JSONMsg(JSONMsg.ADD_COLLECT, JSONMsg.SUCCESS);
				}else {
					obj=new JSONMsg(JSONMsg.ADD_COLLECT, JSONMsg.FAIL);
				}
			}else {
				obj=new JSONMsg(JSONMsg.ADD_COLLECT, JSONMsg.FAIL);
			}
		}
		sendMsg(obj.toMsg());
	}
	/**
	 * 线程调用下载音乐
	 */
	public void downloadMusic(String fileMsg,byte fileDate[],int start,int lenth){
		synchronized(ChatSocket.class){
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
	 * 发送数据
	 * @param msg
	 */
	public void sendMsg(String msg){
		synchronized(ChatSocket.class){
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
