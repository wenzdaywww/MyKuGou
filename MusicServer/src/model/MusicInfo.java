package model;

public class MusicInfo {
	
	private String musicId;
	private String musicName;
	private String singer;
	private String musicPath;
	private String musicTime;
	private String playCount;
	private String netMusic;
	
	public MusicInfo() {
	}
	
	public MusicInfo(String musicId, String musicName, String singer, String musicPath,
			String musicTime, String playCount,String netMusic) {
		super();
		this.musicId = musicId;
		this.musicName = musicName;
		this.singer = singer;
		this.musicPath = musicPath;
		this.musicTime = musicTime;
		this.playCount = playCount;
		this.netMusic=netMusic;
	}

	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
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
	public String getNetMusic() {
		return netMusic;
	}
	public void setNetMusic(String netMusic) {
		this.netMusic = netMusic;
	}
	
}
