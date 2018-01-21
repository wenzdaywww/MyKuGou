package model;

public class CollectMusic {
	private String cllectId;
	private String musicId;
	public String getCllectId() {
		return cllectId;
	}
	public void setCllectId(String cllectId) {
		this.cllectId = cllectId;
	}
	public String getMusicId() {
		return musicId;
	}
	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}
	@Override
	public String toString() {
		return "CollectMusic [cllectId=" + cllectId + ", musicId=" + musicId + "]";
	}
	
}
