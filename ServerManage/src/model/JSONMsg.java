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
	private String idOrName;
	private Collect collect;
	private String classId;
	private String musicId;
	private String musicName;
	private String singer;
	private String musicTime;
	private String playCount;
	private String collectId;
	private String fileName;
	private String filePath;
	private String rename;
	private long totalLength;
	private String renameType;
	private long dataLength;
	private ArrayList<MClass> mClassList;
	private ArrayList<MusicInfo> musicList;
	private ArrayList<CollectMusic> cMusicList;
	private ArrayList<UserCollect> uCollectList;
	
	public JSONMsg() {
	}
	/**
	 * ��ȡ���ַ���
	 * @param type
	 */
	public JSONMsg(String type) {
		super();
		this.type = type;
	}
	/**
	 * ��ӷ����е�����
	 * @param type
	 * @param classId
	 * @param musicId
	 * @param nullB
	 */
	public JSONMsg(String type,String classId ,String musicId,boolean nullB){
		this.type = type;
		this.musicId=musicId;
		this.classId = classId;
	}
	/**
	 * �޸ĸ�����Ϣ
	 * @param type
	 * @param renameType
	 * @param rename
	 * @param nullB
	 */
	public JSONMsg(String type,String musicId ,String renameType,String rename,boolean nullB) {
		this.type = type;
		this.musicId=musicId;
		this.renameType = renameType;
		this.rename=rename;
	}
	/**
	 * �����ϴ�����
	 * @param type
	 * @param fileName
	 * @param totalLenth �ļ�����
	 * @param dataLenth ÿ�η��͵����ݳ���
	 */
	public JSONMsg(String type, String fileName,long totalLenth, long dataLenth) {
		super();
		this.type = type;
		this.fileName = fileName;
		this.totalLength = totalLenth;
		this.dataLength = dataLenth;
	}
	/**
	 * �����ϴ�����
	 * @param type
	 * @param fileName
	 * @param totalLenth �ļ�����
	 * @param dataLenth ÿ�η��͵����ݳ���
	 */
	public JSONMsg(String type, String fileName, String musicName,String singer,String musicTime) {
		super();
		this.type = type;
		this.fileName = fileName;
		this.musicName=musicName;
		this.singer=singer;
		this.musicTime=musicTime;
	}
	/**
	 * ��������
	 * @param type
	 * @param musicName
	 * @param nullInt
	 */
	public JSONMsg(String type,String musicName,int nullInt) {
		super();
		this.type = type;
		this.musicName=musicName;
	}
	/**
	 * ���������ȡ�����
	 * @param type
	 * @param classId �ɴ�����ID���½��ķ�����,�Ѿ��û�ID
	 */
	public JSONMsg(String type,String classId){
		super();
		this.type=type;
		this.classId=classId;
	}
	/**
	 * �û�ע�����빹�췽��
	 * @param type
	 * @param userId
	 * @param userPwd
	 */
	public JSONMsg(String type, String idOrName, String userPwd) {
		super();
		this.type = type;
		this.idOrName = idOrName;
		this.userPwd = userPwd;
	}
	/**
	 * ɾ������
	 * @param type
	 * @param classId
	 * @param musicId
	 * @param i
	 */
	public JSONMsg(String type, String classId, String musicId,int i) {
		super();
		this.type = type;
		this.classId = classId;
		this.musicId = musicId;
	}
	/**
	 * ɾ���ղظ���
	 * @param type
	 * @param idOrName
	 * @param collectId
	 * @param musicId
	 */
	public JSONMsg(String type, String idOrName, String collectId, String musicId) {
		super();
		this.type = type;
		this.idOrName = idOrName;
		this.collectId = collectId;
		this.musicId = musicId;
	}
	/**
	 * ���ظ���������ղ�
	 * @param type
	 * @param idOrName
	 * @param collectId
	 * @param mInfo
	 */
	public JSONMsg(String type,String collectId, MusicInfo mInfo){
		this.type = type;
		this.collectId = collectId;
		this.musicName=mInfo.getMusicName();
		this.singer=mInfo.getSinger();
		this.musicTime=mInfo.getMusicTime();
	}
	/**
	 * JSON����ת�����ַ������
	 * @return ת������ַ���
	 */
	public String toMsg(){
		JSONObject obj=JSONObject.fromObject(this);
		return obj.toString();
	}
	/**
	 * ���ַ���ת����JSONMsg����
	 * @param jsonStr Ҫת�����ַ���
	 * @return JSONMsg����
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
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public ArrayList<CollectMusic> getcMusicList() {
		return cMusicList;
	}
	public void setcMusicList(ArrayList<CollectMusic> cMusicList) {
		this.cMusicList = cMusicList;
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
