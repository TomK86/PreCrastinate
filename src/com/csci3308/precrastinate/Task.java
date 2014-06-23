package com.csci3308.precrastinate;

public class Task {
	String title;
	long due;
	float priority;
	int group;
	boolean complete;
	
	
	public Task(String taskTitle, long dueDate, float rating, int grouping, boolean start) {
		title = taskTitle;
		due = dueDate;
		priority = rating;
		group = grouping;
		complete = false;
	}
	
	public void EditTask(String newTitle, long newDueDate, float newRating, int newGrouping, boolean newComplete) {
		title = newTitle;
		due = newDueDate;
		priority = newRating;
		group = newGrouping;
		complete = newComplete;
	}
}


	/** public static void main(String []args) {
		Task newTask = new Task("Hello", "23", 15, "Work", false);
		System.out.println(newTask.group);
		ArrayList<String> groups = new ArrayList<String>();
		
		
		newTask.EditTask("Hello", "21", 14, "Home", true);
		System.out.println(newTask.group);
		
		Task newTask1 = new Task("Hell", "223", 15, "Work", false);
		System.out.println(newTask1.group);
		Task newTask3 = new Task("111", "111", 15, "Home", false);
		Task newTask4 = new Task("1121", "1211", 125, "Work", false);
		
	    
		Map<String, Task> newTask2 = new HashMap<String, Task>();
		newTask2.put(title, newTask);
		newTask2.put("Home", newTask1);
		newTask2.put("Stuff", newTask3);
		newTask2.put("LOL", newTask4);
		System.out.println(newTask2.get("Home").due);
		
		Iterator<Entry<String, Task>> entries = newTask2.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry<?, ?> thisEntry = (Entry<?, ?>) entries.next();
		  Task value = (Task) thisEntry.getValue();
		  System.out.println(value.group);
		  System.out.println(groups.contains(value.group));
		  
		  if (groups.contains(value.group) == false) {
				  groups.add(value.group);
		  }
		  
		  
		  /* ITERATE THROUGH ARRAY AND CHECK IF ANYTHING MATCHES 
		}
		
		  System.out.println(groups);
	} 
	
}
 */
