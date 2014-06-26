package com.csci3308.precrastinate;

public class Task {
	private String title;
	private long date;
	private String month;
	private String day;
	private String year;
	private float priority;
	private int group;
	private boolean complete;
	
	public Task(String taskTitle, long dueDate, float priorityRating,
			int groupNumber, boolean isCompleted) {
		this.title = taskTitle;
		this.date = dueDate;
		this.month = Long.toString((dueDate % 10000) / 100);
		this.day = Long.toString(dueDate % 100);
		this.year = Long.toString(dueDate / 10000);
		this.priority = priorityRating;
		this.group = groupNumber;
		this.complete = isCompleted;
	}
	
	public Task(String taskTitle, String dueMonth, String dueDay, String dueYear,
			float priorityRating, int groupNumber, boolean isCompleted) {
		this.title = taskTitle;
		this.date = (Long.valueOf(dueYear) * 10000) + (Long.valueOf(dueMonth) * 100)
				+ Long.valueOf(dueDay);
		this.month = dueMonth;
		this.day = dueDay;
		this.year = dueYear;
		this.priority = priorityRating;
		this.group = groupNumber;
		this.complete = isCompleted;
	}
	
	public Task(String taskTitle, String dueDate, float priorityRating,
			int groupNumber, boolean isCompleted) {
		this.title = taskTitle;
		this.priority = priorityRating;
		this.group = groupNumber;
		this.complete = isCompleted;
		
		if(dueDate.length() < 10) {
			this.month = "0";
			this.day = "0";
			this.year = "0";
			this.date = 0L;
		}
		else {
			String[] parts = dueDate.split(" / ");
			this.month = parts[0];
			this.day = parts[1];
			this.year = parts[2];
			this.date = (Long.valueOf(this.year) * 10000) + (Long.valueOf(this.month) * 100)
					+ Long.valueOf(this.day);
		}
	}
	
	public Task(String taskTitle, int dueMonth, int dueDay, int dueYear,
			float priorityRating, int groupNumber, boolean isCompleted) {
		this.title = taskTitle;
		this.date = (long) ((dueYear * 10000) + (dueMonth * 100) + dueDay);
		this.month = Integer.toString(dueMonth);
		this.day = Integer.toString(dueDay);
		this.year = Integer.toString(dueYear);
		this.priority = priorityRating;
		this.group = groupNumber;
		this.complete = isCompleted;
	}
	
	public String getTaskTitle() {
		return this.title;
	}
	
	public long getDueDateAsLong() {
		return this.date;
	}
	
	public String getDueDateAsString() {
		return this.month + " / " + this.day + " / " + this.year;
	}
	
	public float getPriorityAsFloat() {
		return this.priority;
	}
	
	public String getPriorityAsString() {
		return Float.toString(this.priority);
	}
	
	public int getGroupNum() {
		return this.group;
	}
	
	public boolean getCompleted() {
		return this.complete;
	}
	
	public void setTaskTitle(String newTaskTitle) {
		this.title = newTaskTitle;
	}
	
	public void setDueDateFromLong(long newDueDate) {
		this.date = newDueDate;
		this.month = Long.toString((newDueDate % 10000) / 100);
		this.day = Long.toString(newDueDate % 100);
		this.year = Long.toString(newDueDate / 10000);
	}
	
	public void setDueDateFromString(String newDueDate) {
		// If newDueDate is in "mm / dd / yyyy" format:
		//if(newDueDate.matches("[1]?[0-9][ / ][1]?[0-9][ / ][0-9]{4}")) {
			String[] parts = newDueDate.split(" / ");
			this.month = parts[0];
			this.day = parts[1];
			this.year = parts[2];
		//} // If newDueDate is in "mm/dd/yyyy" format:
		//else if(newDueDate.matches("[1]?[0-9][/][1]?[0-9][/][0-9]{4}")) {
			//String[] parts = newDueDate.split("/");
			//this.month = parts[0];
			//this.day = parts[1];
			//this.year = parts[2];
		//}
		//else { // If newDueDate is in "yyyymmdd" format:
			//char[] charsM = {newDueDate.charAt(4), newDueDate.charAt(5)};
			//char[] charsD = {newDueDate.charAt(6), newDueDate.charAt(7)};
			//char[] charsY = {newDueDate.charAt(0), newDueDate.charAt(1),
					//newDueDate.charAt(2), newDueDate.charAt(3)};
			//String[] parts = {charsM.toString(), charsD.toString(), charsY.toString()};
			//this.month = ((Integer) Integer.valueOf(parts[0])).toString();
			//this.day = ((Integer) Integer.valueOf(parts[1])).toString();
			//this.year = parts[2];
		//}
		this.date = (Long.valueOf(this.year) * 10000) + (Long.valueOf(this.month) * 100)
				+ Long.valueOf(this.day);
	}
	
	public void setDueDateFromString(String newDueMonth, String newDueDay, String newDueYear) {
		this.date = (Long.parseLong(newDueYear) * 10000) + (Long.parseLong(newDueMonth) * 100)
				+ Long.parseLong(newDueDay);
		this.month = newDueMonth;
		this.day = newDueDay;
		this.year = newDueYear;
	}
	
	public void setDueDateFromInt(int newDueMonth, int newDueDay, int newDueYear) {
		this.date = (long) ((newDueYear * 10000) + (newDueMonth * 100) + newDueDay);
		this.month = Integer.toString(newDueMonth);
		this.day = Integer.toString(newDueDay);
		this.year = Integer.toString(newDueYear);
	}
	
	public void setPriority(float newPriorityRating) {
		this.priority = newPriorityRating;
	}
	
	public void setGroupNum(int newGroupNumber) {
		this.group = newGroupNumber;
	}
	
	public void setCompleted(boolean isCompleted) {
		this.complete = isCompleted;
	}

}