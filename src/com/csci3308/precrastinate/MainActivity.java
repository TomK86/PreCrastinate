package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.csci3308.precrastinate.R;

public class MainActivity extends Activity {
	
	
	static List<String> listGroupHeaders;
	List<String> listTaskKeys;
    HashMap<String, Integer> listGroupSettings;
	SharedPreferences saveGroup;
	static SharedPreferences saveTask;
	SharedPreferences.Editor editGroup;
	static SharedPreferences.Editor editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // initialize saved task list data
        saveTask = getSharedPreferences("Saved Task Data", 0);
        editTask = saveTask.edit();
        
        // initialize saved group list data
        saveGroup = getSharedPreferences("Saved Group Data", 0);
        editGroup = saveGroup.edit();
        if(!(saveGroup.contains("newGrp"))) {
        	editGroup.putString("newGrp", "Add a new group...");
        	editGroup.putInt("newGrp", 5);
        }
        if(!(saveGroup.contains("firstAppOpen"))) {
        	editGroup.putBoolean("firstAppOpen", true);
        }
        boolean firstAppOpen = saveGroup.getBoolean("firstAppOpen", true);
        editGroup.commit();
 
        // prepare group list data
        prepareGroupData(saveGroup, editGroup, firstAppOpen);
    
        // prepare task list data
        prepareTaskData(saveTask, editTask);
    }
    
    // Save task data to a SharedPreferences object
 	public static void saveTaskData(String name, long due, float priority, int group, boolean completed) {
         Integer i = 0;
         String key = "task" + i;
         while(saveTask.contains(key)) {
     		i++;
     		key = "task" + i;
         }
         editTask.putString(key, name);
  		 editTask.putLong(key, due);
  		 editTask.putFloat(key, priority);
         editTask.putInt(key, group);
         editTask.putBoolean(key, completed);
         editTask.commit();
     }
 	
 	// Delete task data from a SharedPreferences object
     public void deleteTaskData(SharedPreferences saveTask, SharedPreferences.Editor editTask,
     		Integer position) {
     	String key = "task" + position;
     	if(saveTask.contains(key)) {
     		editTask.remove(key);
     		editTask.commit();
     		Integer i = position + 1;
     		key = "task" + i;
     		while(saveTask.contains(key)) {
 				String name = saveTask.getString(key, "");
 				long due = saveTask.getLong(key, 0);
 				float priority = saveTask.getFloat(key, 0);
 				int group = saveTask.getInt(key, 0);
 				boolean completed = saveTask.getBoolean(key, false);
 				editTask.putString("task" + (i-1), name);
 				editTask.putLong("task" + (i-1), due);
 				editTask.putFloat("task" + (i-1), priority);
 				editTask.putInt("task" + (i-1), group);
 				editTask.putBoolean("task" + (i-1), completed);
 				editTask.remove(key);
 				editTask.commit();
 				i++;
 				key = "task" + i;
     		}
     	}
     }
    
    // Save group data to a SharedPreferences object
    public void saveGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, String name, Integer color) {
        Integer i = 0;
        String key = "grp" + i;
        while(saveGroup.contains(key)) {
        		i++;
        		key = "grp" + i;
        }
        editGroup.putString(key, name);
		editGroup.putInt(key, color);
        editGroup.commit();
    }
    
    // Delete group data from a SharedPreferences object
    public void deleteGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, Integer position) {
    	String key = "grp" + position;
    	if(saveGroup.contains(key)) {
    		editGroup.remove(key);
    		editGroup.commit();
    		Integer i = position + 1;
    		key = "grp" + i;
    		while(saveGroup.contains(key)) {
				String name = saveGroup.getString(key, "");
				Integer color = saveGroup.getInt(key, 0);
				editGroup.putString("grp" + (i-1), name);
				editGroup.putInt("grp" + (i-1), color);
				editGroup.remove(key);
				editGroup.commit();
				i++;
				key = "grp" + i;
    		}
    	}
    }
    
 // Dynamically create Task objects from saved SharedPreferences data
 	private void prepareTaskData(SharedPreferences saveTask, SharedPreferences.Editor editTask) {
 		int i = 0;
 		String key = "task" + i;
 		Map<String, Task> listTaskObjs = new HashMap<String, Task>();
 		while(saveTask.contains(key)) {
 			listTaskObjs.put(key, new Task(saveTask.getString(key, ""), saveTask.getLong(key, 0), 
 					saveTask.getFloat(key, 0), saveTask.getInt(key, 0), saveTask.getBoolean(key, false)));
 			i++;
 			key = "task" + i;
 		}
 	}
    
    // Dynamically populate group list from saved SharedPreferences group data
    private void prepareGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, boolean firstAppOpen) {
    	if(firstAppOpen) {
	        listGroupHeaders = new ArrayList<String>();
	        listGroupSettings = new HashMap<String, Integer>();
	        
	        
	        // add default child data
	        listGroupHeaders.add("To Do");
	        listGroupHeaders.add("Work");
	        listGroupHeaders.add("Home");
	        ///listGroupHeaders.add(saveGroup.getString("newGrp", ""));
	        
	        
	        listGroupSettings.put(listGroupHeaders.get(0), 0);
	        listGroupSettings.put(listGroupHeaders.get(1), 4);
	        listGroupSettings.put(listGroupHeaders.get(2), 1);
	        ///listGroupSettings.put(listGroupHeaders.get(3), saveGroup.getInt("newGrp", 5));
	        
	        // save default child data
	        saveGroupData(saveGroup, editGroup, listGroupHeaders.get(0), listGroupSettings.get(listGroupHeaders.get(0)));
	        saveGroupData(saveGroup, editGroup, listGroupHeaders.get(1), listGroupSettings.get(listGroupHeaders.get(1)));
	        saveGroupData(saveGroup, editGroup, listGroupHeaders.get(2), listGroupSettings.get(listGroupHeaders.get(2)));
	        
	        
	        // turn off first-open flag
	        firstAppOpen = false;
	        editGroup.putBoolean("firstAppOpen", false);
	        editGroup.commit();
	        return;
	        
    	}
    	else {
    		listGroupHeaders = new ArrayList<String>();
    		listGroupSettings = new HashMap<String, Integer>();
	        Integer i = 0;
	        String key = "grp" + i;
	        
	        // add saved child data
	        while(saveGroup.contains(key)) {
        		String name = saveGroup.getString(key, "");
        		Integer color = saveGroup.getInt(key, 0);
        		listGroupHeaders.add(name);
        		listGroupSettings.put(name, color);
        		i++;
        		key = "grp" + i;
	        }
	        return;
    	}
    } 
     
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void addTask(View view) {
		Intent addtask = new Intent(this, AddTask.class);
		startActivity(addtask);
		
	}

}
