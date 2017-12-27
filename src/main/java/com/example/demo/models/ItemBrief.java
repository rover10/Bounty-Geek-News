package com.example.demo.models;

public class ItemBrief {

	private int id;
	private String by;
	private long time;
	private int score;
	private String title;
	private String url;
	private String type;
	
	//Mark it saved for all user for now
	//To make it user specific we can maintain a user set whose have saved it.
	private boolean saved = false;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setType(String type) {
		this.type = type;
	}	
	
	public String getType() {
		return this.type;
	}
	
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}


}
