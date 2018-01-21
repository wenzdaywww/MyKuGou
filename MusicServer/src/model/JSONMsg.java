package model;

import java.util.ArrayList;

import net.sf.json.JSONObject;

public class JSONMsg {
	public static final String LOGIN="Login";
	public static final String REGIST="Regist";
	public static final String FAIL="Fail";
	public static final String SUCCESS="Success";
	public static final String DEL_COLLECT="DelCollect";
	public static final String ADD_COLLECT="AddCollect";
	public static final String MCLASS="MClass";
	public static final String CMUSIC="CMusic";
	public static final String SERACH="Search";
	public static final String DOWNLOAD="Download";
	public static final String UPLOAD="Upload";
	public static final String CAN_UPLOAD="CanUpload";
	public static final String LISTENING="Listening";
	public static final String REVISE_MUSIC="ReviseMusic";
	public static final String DEL_MUSIC="DelMusic";
	public static final String DEL_CLASS="DelClass";
	public static final String ADD_CLASS="AddClass";
	public static final String ADD_CLASS_MUSIC="AddClassMusic";
	public static final String REVISE_CLASS="ReviseClass";
	public static final String RENAME_CLASS="RenameClass";
	
	public static final String GET_USER="GetUser";
	public static final String RENAME_USER="RenameUser";
	public static final String SEARCH_USER="SearchUser";
	public static final String ADD_USER="AddUser";
	public static final String DEL_USER="DelUser";
	public static final String USER_MUSIC="UserMusic";
	
	private User user;
	private String type;
	private String result;
	private String userPwd;
	private String classId;
	private String idOrName;
	private Collect collect;
	private String musicId;
	private String musicName;
	private String singer;
	private String musicTime;
	private String playCount;
	private String rename;
	private String collectId;
	private String fileName;
	private long totalLength;
	private String filePath;
	private long dataLength;
	private String renameType;
	private ArrayList<MClass> mClassList;
	private ArrayList<MusicInfo> musicList;
	private ArrayList<CollectMusic> cMusicList;
	private ArrayList<UserCollect> uCollectList;

	public JSONMsg() {
	}
	/**
	 * 音乐下载构造
	 * @param type
	 * @param fileName
	 * @param totalLenth 文件长度
	 * @param dataLenth 每次发送的数据长度
	 */
	public JSONMsg(String type, String fileName, long totalLenth, long dataLenth) {
		super();
		this.type = type;
		this.fileName = fileName;
		this.totalLength = totalLenth;
		this.dataLength = dataLenth;
	}
	/**
	 * 用户收藏信息
	 * @param type
	 * @param result
	 * @param uCollectList
	 * @param i
	 */
	public JSONMsg(String type, String result, ArrayList<UserCollect> uCollectList,int i) {
		super();
		this.type = type;
		this.result = result;
		this.uCollectList = uCollectList;
	}
	/**
	 * 分类歌曲构造
	 * @param type
	 * @param result
	 * @param nullstr
	 * @param musicList
	 */
	public JSONMsg(String type, String result,String classId,ArrayList<MusicInfo> musicList) {
		super();
		this.type = type;
		this.result = result;
		this.classId=classId;
		this.musicList = musicList;
	}
	/**
	 * 歌曲搜索构造
	 * @param type
	 * @param result
	 * @param nullInt
	 * @param musicList
	 */
	public JSONMsg(String type, String result,int nullInt,ArrayList<MusicInfo> musicList) {
		super();
		this.type = type;
		this.result = result;
		this.musicList = musicList;
	}
	/**
	 * 获取所有歌曲分类
	 * @param type
	 * @param result
	 * @param mClassList
	 */
	public JSONMsg(String type, String result, ArrayList<MClass> mClassList) {
		super();
		this.type = type;
		this.result = result;
		this.mClassList = mClassList;
	}
	/**
	 * 用户注册成功构造
	 * @param type
	 * @param result
	 * @param user
	 * @param collect
	 */
	public JSONMsg(String type, String result, User user,Collect collect) {
		super();
		this.type = type;
		this.result = result;
		this.user = user;
		this.collect=collect;
	}
	/**
	 * 用户登入获取网络收藏歌曲信息构造
	 * @param type
	 * @param result
	 * @param user
	 * @param collect
	 * @param cMusicList
	 * @param musicList
	 */
	public JSONMsg(String type, String result, User user, Collect collect, ArrayList<CollectMusic> cMusicList,
			ArrayList<MusicInfo> musicList) {
		super();
		this.type = type;
		this.result = result;
		this.user = user;
		this.collect = collect;
		this.cMusicList = cMusicList;
		this.musicList = musicList;
	}
	/**
	 * 删除收藏歌曲成功
	 * @param type
	 * @param result
	 */
	public JSONMsg(String type, String result) {
		super();
		this.type = type;
		this.result = result;
	}
	/**
	 * JSON对象转换成字符串输出
	 * @return 转换后的字符串
	 */
	public String toMsg(){
		JSONObject obj=JSONObject.fromObject(this);
		return obj.toString();
	}
	/**
	 * 将字符串转换成JSONMsg对象
	 * @param jsonStr 要转换的字符串
	 * @return JSONMsg对象
	 */
	public static JSONMsg toJSONMsg(String jsonStr){
		JSONObject obj=JSONObject.fromObject(jsonStr);
		JSONMsg jMsg=(JSONMsg) JSONObject.toBean(obj, JSONMsg.class);
		return jMsg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getIdOrName() {
		return idOrName;
	}
	public void setIdOrName(String userId) {
		this.idOrName = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collect getCollect() {
		return collect;
	}
	public void setCollect(Collect collect) {
		this.collect = collect;
	}

	public ArrayList<MusicInfo> getMusicList() {
		return musicList;
	}


	public void setMusicList(ArrayList<MusicInfo> musicList) {
		this.musicList = musicList;
	}
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public ArrayList<CollectMusic> getcMusicList() {
		return cMusicList;
	}
	public void setcMusicList(ArrayList<CollectMusic> cMusicList) {
		this.cMusicList = cMusicList;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public ArrayList<MClass> getmClassList() {
		return mClassList;
	}
	public void setmClassList(ArrayList<MClass> mClassList) {
		this.mClassList = mClassList;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(long totalLenth) {
		this.totalLength = totalLenth;
	}
	public long getDataLength() {
		return dataLength;
	}
	public void setDataLength(long dataLenth) {
		this.dataLength = dataLenth;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMusicTime() {
		return musicTime;
	}
	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getRenameType() {
		return renameType;
	}
	public void setRenameType(String renameType) {
		this.renameType = renameType;
	}
	public String getRename() {
		return rename;
	}
	public void setRename(String rename) {
		this.rename = rename;
	}
	public ArrayList<UserCollect> getuCollectList() {
		return uCollectList;
	}
	public void setuCollectList(ArrayList<UserCollect> uCollectList) {
		this.uCollectList = uCollectList;
	}
	
}
