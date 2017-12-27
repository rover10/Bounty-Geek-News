package com.example.demo.models;

import java.util.List;

public class Item {
	/*
	id	The item's unique id.
	deleted	true if the item is deleted.
	type	The type of item. One of "job", "story", "comment", "poll", or "pollopt".
	by	The username of the item's author.
	time	Creation date of the item, in Unix Time.
	text	The comment, story or poll text. HTML.
	dead	true if the item is dead.
	parent	The comment's parent: either another comment or the relevant story.
	poll	The pollopt's associated poll.
	kids	The ids of the item's comments, in ranked display order.
	url	The URL of the story.
	score	The story's score, or the votes for a pollopt.
	title	The title of the story, poll or job.
	parts	A list of related pollopts, in display order.
	descendants	In the case of stories or polls, the total comment count.
	 * */
	
	private int id;
	private boolean deleted;
	private String type;
	private String by;
	private long time;
	private String text;
	private boolean dead;
	private int parent;
	private int poll;
	private List<Integer> kids;
	private int score;
	private String title;
	private List<Integer> parts;
	private int descendants;
	private String url;
	
	//{"by":"perler","descendants":0,"id":5,"kids":[454414],"score":7,"time":1160419864,"title":"Google, YouTube acquisition announcement could come tonight","type":"story","url":"http://www.techcrunch.com/2006/10/09/google-youtube-sign-more-separate-deals/"}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getPoll() {
		return poll;
	}
	public void setPoll(int poll) {
		this.poll = poll;
	}
	public List<Integer> getKids() {
		return kids;
	}
	public void setKids(List<Integer> kids) {
		this.kids = kids;
	}
	public int getScore() {
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
	public List<Integer> getParts() {
		return parts;
	}
	public void setParts(List<Integer> parts) {
		this.parts = parts;
	}
	public int getDescendants() {
		return descendants;
	}
	public void setDescendants(int descendants) {
		this.descendants = descendants;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
