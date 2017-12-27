package com.example.demo.models;

public class SearchParameter {
	//@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private String startDate;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private String endDate;
	
	private String type;
	private String user;
	private String contains; 
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
		
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContains() {
		return contains;
	}
	public void setContains(String contains) {
		this.contains = contains;
	}

	
	
}
