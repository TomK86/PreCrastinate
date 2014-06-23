package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.content.Intent;

import com.csci3308.precrastinate.R;


public class MainActivity extends ListActivity implements OnClickListener {

	List<Item> items;
	static SparseArray<Group> listGroupObjs;
    static SparseArray<Task> listTaskObjs;
    static SparseIntArray colorList;
    static SharedPreferences saveGroup, saveTask;
    static SharedPreferences.Editor editGroup, editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // initialize the group color list
        colorList = new SparseIntArray();
        colorList.put(0, 0xFF00CC00); // green
        colorList.put(1, 0xFF0066FF); // blue
        colorList.put(2, 0xFF9933FF); // purple
        colorList.put(3, 0xFFFF0000); // red
        colorList.put(4, 0xFFFF9900); // orange

        // initialize the saved task list data
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
        //boolean firstAppOpen = saveGroup.getBoolean("firstAppOpen", true);
        editGroup.commit();
 
        // prepare group list data
        prepareGroupData(true);
        
        // prepare task list data
        prepareTaskData(true);
        
        // set task list adapter
        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(this, items);
        setListAdapter(adapter);
    }
    
    @Override
	public void onClick(View v) {
		//TODO this is what happens when you click a task in the list
		
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle action bar item clicks here. The action bar will
    	// automatically handle clicks on the Home/Up button, so long
    	// as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
        //case R.id.action_add:
        	//TODO addToList();
        	//return true;
        //case R.id.action_settings:
        	//TODO call preferences screen
        	//return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
    }
    
    public void addToList() {
    	//TODO call addtolist class constructor
    }
    
    public void onCheckboxClicked(View view) {
    	boolean checked = ((CheckBox) view).isChecked();
    	view.findViewById(R.id.checkbox);
    	if(checked) {
    		//TODO move task to completed section
    	}
    	else {
    		//TODO move task to active section
    	}
    }
    
 // Save task data to a SharedPreferences object
  	public static void saveTaskData(String name, long due, float priority, int group, boolean completed) {
          int i = 0;
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
      public static void deleteTaskData(int position) {
      	String key = "task" + position;
      	if(saveTask.contains(key)) {
      		editTask.remove(key);
      		editTask.commit();
      		int i = position + 1;
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
     public static void saveGroupData(String name, int color) {
         int i = 0;
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
     public static void deleteGroupData(int position) {
     	String key = "grp" + position;
     	if(saveGroup.contains(key)) {
     		editGroup.remove(key);
     		editGroup.commit();
     		int i = position + 1;
     		key = "grp" + i;
     		while(saveGroup.contains(key)) {
 				String name = saveGroup.getString(key, "");
 				int color = saveGroup.getInt(key, 0);
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
 	private void prepareTaskData(boolean firstAppOpen) {
 		if(firstAppOpen) {
 			items = new ArrayList<Item>();
 	        items.add(new Header("Active Tasks"));
 	        items.add(new ListItem(0, "Active Task 1", "4/15/2015", false));
 	        items.add(new ListItem(1, "Active Task 2", "5/29/1984", false));
 	        items.add(new ListItem(2, "Active Task 3", "1/2/1956", false));
 	        items.add(new ListItem(3, "Active Task 4", "8/7/2043", false));
 	        items.add(new ListItem(4, "Active Task 5", "7/10/1977", false));
 	        items.add(new ListItem(4, "Active Task 6", "1/1/1776", false));
 	        items.add(new ListItem(4, "Active Task 7", "12/31/9999", false));
 	        items.add(new Header("Completed Tasks"));
 	        items.add(new ListItem(3, "Completed Task 8", "3/31/2014", true));
 	        items.add(new ListItem(2, "Completed Task 9", "1/1/0001", true));
 	        items.add(new ListItem(1, "Completed Task 10", "2/2/2016", true));
 	        items.add(new ListItem(0, "Completed Task 11", "4/1/2015", true));
 		}
 		else {
 			listTaskObjs = new SparseArray<Task>();
 	 		int i = 0;
 	 		String key = "task" + i;
 	 		while(saveTask.contains(key)) {
 	 			listTaskObjs.put(i, new Task(saveTask.getString(key, ""), saveTask.getLong(key, 0), 
 	 					saveTask.getFloat(key, 0), saveTask.getInt(key, 0), saveTask.getBoolean(key, false)));
 	 			i++;
 	 			key = "task" + i;
 	 		}
 		}
 	}
    
    // Dynamically populate group list from saved SharedPreferences group data
 	private void prepareGroupData(boolean firstAppOpen) {
    	listGroupObjs = new SparseArray<Group>();
    	if(firstAppOpen) {
    		// create Group objects from defaults
	        listGroupObjs.put(0, new Group("To Do", 0));
	        listGroupObjs.put(1, new Group("Home", 4));
	        listGroupObjs.put(2, new Group("Work", 1));
	        
	        // save default Group data
	        //saveGroupData(listGroupObjs.get(0).name, listGroupObjs.get(0).getColor());
	        //saveGroupData(listGroupObjs.get(1).name, listGroupObjs.get(1).getColor());
	        //saveGroupData(listGroupObjs.get(2).name, listGroupObjs.get(2).getColor());
	        
	        // turn off first-open flag
	        //firstAppOpen = false;
	        //editGroup.putBoolean("firstAppOpen", false);
	        //editGroup.commit();
	        return;
    	}
    	else {
	        int i = 0;
	        String key = "grp" + i;
	        
	        // add saved child data
	        while(saveGroup.contains(key)) {
        		String name = saveGroup.getString(key, "");
        		int color = saveGroup.getInt(key, 0);
        		listGroupObjs.put(i, new Group(name, color));
        		i++;
        		key = "grp" + i;
	        }
	        return;
    	}
    }

	
	public void addTask(View view) {
		Intent addtask = new Intent(this, AddTask.class);
		startActivity(addtask);
	}

}

