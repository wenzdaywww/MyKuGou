package model;

public class Collect {
	
	private String collectId;
	private String name;
	private String userId;
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Collect [collectId=" + collectId + ", name=" + name + ", userId=" + userId + "]";
	}
	
}
