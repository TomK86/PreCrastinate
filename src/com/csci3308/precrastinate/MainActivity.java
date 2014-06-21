package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {
	
    List<String> listGroupHeaders;
    HashMap<String, Integer> listGroupSettings;
    SharedPreferences saveGroup;
    SharedPreferences.Editor editGroup;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences_main);
        
        // initialize group list data
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
    }
    
    // Save group data to a SharedPreferences object
    public void saveGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, String name, Integer color) {
        Integer i = 0;
        while(i <= Integer.MAX_VALUE) {
        	if(saveGroup.contains("grp" + i)) {
        		i++;
        		continue;
        	}
        	else {
        		editGroup.putString("grp" + i, name);
        		editGroup.putInt("grp" + i, color);
                break;
        	}
        }
        editGroup.commit();
    }
    
    // Delete group data from a SharedPreferences object
    public void deleteGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, Integer position) {
    	String key = "grp" + position;
    	if(saveGroup.contains(key)) {
    		editGroup.remove(key);
    		editGroup.commit();
    		Integer i = position + 1;
    		while(i <= Integer.MAX_VALUE) {
    			key = "grp" + i;
    			if(saveGroup.contains(key)) {
    				String name = saveGroup.getString(key, "");
    				Integer color = saveGroup.getInt(key, 0);
    				editGroup.putString("grp" + (i-1), name);
    				editGroup.putInt("grp" + (i-1), color);
    				editGroup.remove(key);
    				editGroup.commit();
    				i++;
    				continue;
    			}
    			else
    				break;
    		}
    		//listAdapter.notifyDataSetChanged();
    	}
    }
    
    // prepare group list from saved SharedPreferences group data
    private void prepareGroupData(SharedPreferences saveGroup, SharedPreferences.Editor editGroup, boolean firstAppOpen) {
    	if(firstAppOpen) {
	        listGroupHeaders = new ArrayList<String>();
	        listGroupSettings = new HashMap<String, Integer>();
	 
	        // add default child data
	        listGroupHeaders.add("To Do");
	        listGroupHeaders.add("Work");
	        listGroupHeaders.add("Home");
	        listGroupHeaders.add(saveGroup.getString("newGrp", ""));
	 
	        listGroupSettings.put(listGroupHeaders.get(0), 0);
	        listGroupSettings.put(listGroupHeaders.get(1), 4);
	        listGroupSettings.put(listGroupHeaders.get(2), 1);
	        listGroupSettings.put(listGroupHeaders.get(3), saveGroup.getInt("newGrp", 5));
	        
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
}