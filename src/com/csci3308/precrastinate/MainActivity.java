package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends ListActivity implements OnClickListener {

	static ArrayList<Group> listGroupObjs;
    static ArrayList<Task> listTaskObjs;
    static SparseIntArray colorList;
    static SharedPreferences saveData;
    static SharedPreferences.Editor editData;
    static TaskListAdapter taskListAdapter;
    static boolean firstAppOpen, sortMode;
    public static int mHour, mMinute, AmPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        
        // initialize the group color list
        colorList = new SparseIntArray();
        colorList.put(0, 0xFF00CC00); // green
        colorList.put(1, 0xFF0066FF); // blue
        colorList.put(2, 0xFF9933FF); // purple
        colorList.put(3, 0xFFFF0000); // red
        colorList.put(4, 0xFFFF9900); // orange

        // initialize the saved data
        saveData = getSharedPreferences("Saved Data", 0);
        editData = saveData.edit();
        if(!(saveData.contains("firstAppOpen")))
        	editData.clear().putBoolean("firstAppOpen", true).apply();
        firstAppOpen = saveData.getBoolean("firstAppOpen", true);
        
        sortMode = false; // this is the current sorting mode (false = date, true = priority)
        
        loadGroupData(); // load the group list data
        
        loadTaskData(); // load the task list data
        
        // turn off first-open flag
    	firstAppOpen = false;
    	editData.putBoolean("firstAppOpen", false).apply();
        
        // set the task list adapter
        taskListAdapter = new TaskListAdapter(this);
        setListAdapter(taskListAdapter);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	firstAppOpen = false;
    	editData.putBoolean("firstAppOpen", false).apply();
    	loadGroupData();
    	loadTaskData();
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
	public void onClick(View v) {
		// this is what happens when you click a task in the list
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle action bar item clicks here. The action bar will
    	// automatically handle clicks on the Home/Up button, so long
    	// as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
        	case R.id.action_add_task:
        		addTask();
        		return true;
        	case R.id.action_preferences:
        		setPrefs();
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }
    
    public void onCheckboxClicked(View view) {
    	boolean checked = ((CheckBox) view).isChecked();
    	int position = (Integer) ((CheckBox) view).getTag();
    	listTaskObjs.get(position).setCompleted(checked);
    	if(checked)
    		Toast.makeText(this, "Great job!", Toast.LENGTH_SHORT).show();
    	else
    		Toast.makeText(this, "If at first you don't succeed...", Toast.LENGTH_SHORT).show();
    	sortTasks();
    }
    
    public void onSortSwitchClicked(View view) {
    	sortMode = ((Switch) view).isChecked();
    	sortTasks();
    }
    
    // sort task list first by date/priority, then by completion status -- updates list afterwards
    public static void sortTasks() {
    	
    	ArrayList<Task> sortedTasks = new ArrayList<Task>();
    	int max = listTaskObjs.size();
    	
    	if(sortMode) { // sort by priority (highest to lowest)
    		Map<Float, List<Integer>> duplicates = new HashMap<Float, List<Integer>>();
    		for(int i = 0; i < max; i++) {
    			Float key = listTaskObjs.get(i).getPriorityAsFloat();
    			if(duplicates.containsKey(key))
    				duplicates.get(key).add(i);
    			else {
    				List<Integer> value = new ArrayList<Integer>();
    				value.add(i);
    				duplicates.put(key, value);
    			}
    		}
    		for(Float key = 5F; key >= 0F; key = key - 0.5F) {
    			if(duplicates.keySet().contains(key)) {
    				for(int i = 0; i < duplicates.get(key).size(); i++)
    					sortedTasks.add(listTaskObjs.get(duplicates.get(key).get(i)));
    			}
    		}
    		
    	}
    	else { // sort by date (lowest to highest, with zeroes on the bottom)
    		Map<Long, List<Integer>> duplicates = new HashMap<Long, List<Integer>>();
    		for(int i = 0; i < max; i++) {
    			Long key = listTaskObjs.get(i).getDueDateAsLong();
    			if(duplicates.containsKey(key))
    				duplicates.get(key).add(i);
    			else {
    				List<Integer> value = new ArrayList<Integer>();
    				value.add(i);
    				duplicates.put(key, value);
    			}
    		}
    		int zeroes = 0;
    		Long high = 0L;
    		Long oldHigh = 100000000L;
    		if(duplicates.containsKey(0L))
    			zeroes = duplicates.get(0L).size();
    		Set<Long> keys = duplicates.keySet();
    		while(sortedTasks.size() < (max - zeroes)) {
	    		for(Iterator<Long> iter = keys.iterator(); iter.hasNext();) {
	    			Long key = iter.next();
	    			if(key > high && key < oldHigh)
	    				high = key;
	    		}
	    		for(int i = 0; i < duplicates.get(high).size(); i++)
	    			sortedTasks.add(0, listTaskObjs.get(duplicates.get(high).get(i)));
	    		oldHigh = high;
	    		high = 0L;
    		}
    		if(zeroes > 0) {
				for(int i = 0; i < zeroes; i++)
					sortedTasks.add(max - zeroes, listTaskObjs.get(duplicates.get(0L).get(i)));
			}
    	}
    	
    	listTaskObjs.clear();
    	listTaskObjs.addAll(sortedTasks);
    	sortedTasks.clear();
    	boolean complete;
    	
    	// sort by completion status
    	for(int i = 0; i < max; i++) {
    		complete = listTaskObjs.get(i).getCompleted();
    		if(!complete)
    			sortedTasks.add(listTaskObjs.get(i));
    	}
    	for(int i = 0; i < max; i++) {
    		complete = listTaskObjs.get(i).getCompleted();
    		if(complete)
    			sortedTasks.add(listTaskObjs.get(i));
    	}
    	
    	listTaskObjs.clear();
    	listTaskObjs.addAll(sortedTasks);
    	updateTaskList();
    }
    
    // sets task list headers to the proper locations, then updates the list view
    public static void updateTaskList() {
    	if(taskListAdapter != null) {
    		taskListAdapter.setCompletedHeaderRow();
    		taskListAdapter.notifyDataSetChanged();
        }
    }
    
    // commit Task object to saved data, add to listTaskObjs, sort/update task list
    public static void saveTaskData(Task task) {
    	int i = 0;
		String key = "task" + i;
	      
		while(saveData.contains(key)) {
			i++;
			key = "task" + i;
		}
		
		editData.putBoolean(key, true)
		.putString(key + "nm", task.getTaskTitle())
		.putLong(key + "due", task.getDueDateAsLong())
		.putFloat(key + "pri", task.getPriorityAsFloat())
		.putInt(key + "grp", task.getGroupNum())
		.putBoolean(key + "cmp", task.getCompleted())
		.apply();
		
		listTaskObjs.add(task);
		sortTasks();
    }
    
    // commit task data to saved data, add Task object to listTaskObjs, sort/update task list
  	public static void saveTaskData(String name, long due, float priority, int group, boolean completed) {
        int i = 0;
        String key = "task" + i;
        
        while(saveData.contains(key)) {
      		i++;
      		key = "task" + i;
        }
        
        editData.putBoolean(key, true)
        .putString(key + "nm", name)
   		.putLong(key + "due", due)
   		.putFloat(key + "pri", priority)
        .putInt(key + "grp", group)
        .putBoolean(key + "cmp", completed)
        .apply();
        
        listTaskObjs.add(new Task(name, due, priority, group, completed));
        sortTasks();
    }
  	
  	// commit all groups from listGroupObjs to saved data
  	public static void saveGroupData() {
  		  String key;
  		  String name;
  		  int color;
  		  
  		  for(int i = 0; i < listGroupObjs.size(); i++) {
  			  key = "grp" + i;
  			  name = listGroupObjs.get(i).getName();
  			  color = listGroupObjs.get(i).getColor();
  			  
  			  editData.remove(key)
  			  .remove(key + "nm")
  			  .remove(key + "clr")
  			  .apply();
  			  
  			  editData.putBoolean(key, true)
  			  .putString(key + "nm", name)
  			  .putInt(key + "clr", color)
  			  .apply();
  		  }
  		  key = "grp" + listGroupObjs.size();
  		  
  		  if(saveData.contains(key)) {
  			  editData.remove(key)
  			  .remove(key + "nm")
  			  .remove(key + "clr")
  			  .apply();
  		  }
      }
  	  
  	  // commit reminder time to saved data
  	  public static void saveRemTime(int hour, int minute, int ampm) {
  		  editData.putInt("hour", hour)
  		  .putInt("minute", minute)
  		  .putInt("ampm", ampm)
  		  .apply();
  	  }
  	
  	  // delete task data from saved data, remove Task object from listTaskObjs, update task list view
  	  public static void deleteTaskData(int position) {
  		  String key = "task" + position;
    	  if(saveData.contains(key)) {
    		  editData.remove(key)
    		  .remove(key + "nm")
			  .remove(key + "due")
			  .remove(key + "pri")
			  .remove(key + "grp")
			  .remove(key + "cmp")
    		  .apply();
    		  
    		  int i = position + 1;
    		  key = "task" + i;
    		  
    		  while(saveData.contains(key)) {
    			  String name = saveData.getString(key, "");
    			  long due = saveData.getLong(key, 0);
    			  float priority = saveData.getFloat(key, 0);
    			  int group = saveData.getInt(key, 0);
    			  boolean completed = saveData.getBoolean(key, false);
    			  
    			  editData.putBoolean("task" + (i-1), true)
    			  .putString("task" + (i-1) + "nm", name)
    			  .putLong("task" + (i-1) + "due", due)
    			  .putFloat("task" + (i-1) + "pri", priority)
    			  .putInt("task" + (i-1) + "grp", group)
    			  .putBoolean("task" + (i-1) + "cmp", completed);
    			  
    			  editData.remove(key)
    			  .remove(key + "nm")
    			  .remove(key + "due")
    			  .remove(key + "pri")
    			  .remove(key + "grp")
    			  .remove(key + "cmp")
    			  .apply();
    			  
    			  i++;
    			  key = "task" + i;
    		  }
    		  listTaskObjs.remove(position);
    		  updateTaskList();
    	  }
    	  else Log.v("MainActivity", "Error: Deleted task at position "
    			  		+ position + " does not exist in saved data");
  	  }
     
      // delete specified group from saved data, decrement all following group keys by one
      public static void deleteGroupData(int position) {
    	  String key = "grp" + position;
     	  if(saveData.contains(key)) {
     		  editData.remove(key)
     		  .remove(key + "nm")
     		  .remove(key + "clr")
     		  .apply();
     		  
     		  int i = position + 1;
     		  key = "grp" + i;
     		  
     		  while(saveData.contains(key)) {
     			  String name = saveData.getString(key + "nm", "");
 				  int color = saveData.getInt(key + "clr", 0);
 				  
 				  editData.putBoolean("grp" + (i-1), true)
 				  .putString("grp" + (i-1) + "nm", name)
 				  .putInt("grp" + (i-1) + "clr", color);
 				  
 				  editData.remove(key)
 				  .remove(key + "nm")
 	     		  .remove(key + "clr")
 				  .apply();
 				  
 				  i++;
 				  key = "grp" + i;
 			  }
     	 }
     	 else Log.v("MainActivity", "Error: Deleted group at position "
			  		+ position + " does not exist in saved data");
      }
      
      // delete reminder time from saved data & reset it in preferences 
      public static void deleteRemTime() {
    	  editData.remove("hour")
    	  .remove("minute")
    	  .remove("ampm")
    	  .apply();
    	  
    	  Preferences.setRemTime(-1, -1, -1);
      }
    

      // dynamically create Task objects from saved data
      public static void loadTaskData() {
    	  listTaskObjs = new ArrayList<Task>();
    	  if(firstAppOpen) {
    		  // test tasks -- these should be deleted before the app is finalized
    		  // and replaced with an example task... maybe "Set up my preferences"
    		  // with today's date and completed?
    		  
    		  saveTaskData("Prepare bomb shelter", 20121221L, 5F, 1, true);
    		  saveTaskData("Turn in expense reports", 20140705L, 4.5F, 2, false);
    		  saveTaskData("Get mom something for Mother's Day", 20140511L, 4F, 0, true);
    		  saveTaskData("Finish TPS reports", 20091102L, 1.5F, 2, true);
    		  saveTaskData("Mow the lawn", 0L, 2F, 1, false);
    		  saveTaskData("Go fly a kite", 0L, 0F, 0, true);
    		  saveTaskData("Go grocery shopping for Thanksgiving", 20141127L, 3.5F, 0, false);
    	  }
    	  else {
    		  int i = 0;
    		  String key = "task" + i;
    		  while(saveData.contains(key)) {
    			  listTaskObjs.add(new Task(
    					  saveData.getString(key + "nm", ""),
    					  saveData.getLong(key + "due", 0),
    					  saveData.getFloat(key + "pri", 0),
    					  saveData.getInt(key + "grp", 0),
    					  saveData.getBoolean(key + "cmp", true)
    					  ));
    			  i++;
    			  key = "task" + i;
    		  }
    	  }
    	  sortTasks();
      }
    
      // dynamically create Group objects from saved data
      public static void loadGroupData() {
    	  listGroupObjs = new ArrayList<Group>();
    	  if(firstAppOpen) {
    			// create default Group objects
	        	listGroupObjs.add(0, new Group("To Do", 0));
	        	listGroupObjs.add(1, new Group("Home", 4));
	        	listGroupObjs.add(2, new Group("Work", 1));
	        	
	        	// save default Group data
	        	saveGroupData();
    	  }
    	  else {
	        	int i = 0;
	        	String key = "grp" + i;
	        	
	        	// add saved child data
	        	while(saveData.contains(key)) {
        			String name = saveData.getString(key + "nm", "");
        			int color = saveData.getInt(key + "clr", 0);
        			listGroupObjs.add(i, new Group(name, color));
        			i++;
        			key = "grp" + i;
	        	}
    	  }
      }
      // load saved reminder time, if it exists
      public static void loadRemTime() {
  		mHour = saveData.getInt("hour", -1);
  		mMinute = saveData.getInt("minute", -1);
  		AmPm = saveData.getInt("ampm", -1);
      }

      // launches the AddTask activity
      public void addTask() {
    	  Intent addtask = new Intent(this, AddTask.class);
    	  startActivity(addtask);
      }
      
      // launches the Preferences activity
      public void setPrefs() {
    	  Intent prefs = new Intent(this, Preferences.class);
    	  startActivity(prefs);
      }

}