package com.csci3308.precrastinate;

public class Group {
	
	private String name;
	private int color;
	
	public Group(String groupName, int groupColor) {
		this.name = groupName;
		this.color = groupColor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setColor(int newColor) {
		this.color = newColor;
	}
	
}