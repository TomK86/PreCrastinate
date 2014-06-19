package com.csci3308.precrastination_pkg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.csci3308.precrastination_pkg.R;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TimePicker;
import android.widget.EditText;
 
public class MainActivity extends Activity {
 
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    EditText remTime;
    EditText grpNames;
    RadioGroup grpColors;
    List<String> listDataHeader;
    HashMap<String, Integer> listDataChild;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // set up save data
        SharedPreferences saveData = getSharedPreferences("Saved Data", 0);
        SharedPreferences.Editor saveEdit = saveData.edit();
        if(!(saveData.contains("newGrp"))) {
        	saveEdit.putString("newGrp", "Add a new group...");
        	saveEdit.putInt("newGrp", 5);
        }
        if(!(saveData.contains("firstAppOpen"))) {
        	saveEdit.putBoolean("firstAppOpen", true);
        }
        boolean firstAppOpen = saveData.getBoolean("firstAppOpen", true);
        saveEdit.commit();
 
        // get the parent view
        expListView = (ExpandableListView) findViewById(R.id.groupExpList);
        
        // get the child views
        remTime = (EditText) findViewById(R.id.remTimeEdit);
        
        grpNames = (EditText) findViewById(R.id.grpName);
        
        grpColors = (RadioGroup) findViewById(R.id.grpColor);
 
        // prepare list data
        prepareListData(saveData, saveEdit, firstAppOpen);
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // set list adapter
        expListView.setAdapter(listAdapter);
        
        // EditText click listener
        remTime.setOnClickListener(new OnClickListener() {
        	
        	@Override
            public void onClick(View v) {
        		Toast.makeText(getApplicationContext(),
            			"Please select the time of day you wish to receive reminders",
            			Toast.LENGTH_LONG).show();
            	Calendar mCurrentTime = Calendar.getInstance();
            	int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            	int minute = 0;
        		if(hour == 23)
            		hour = 0;
        		else
        			hour++;
            	TimePickerDialog mTimePicker;
            	mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    	if(selectedHour < 12) {
                    		if(selectedHour == 0)
                    			selectedHour = 12;
                    		if(selectedMinute < 10)
                    			remTime.setText(selectedHour + ":0" + selectedMinute + " AM");
                    		else
                    			remTime.setText(selectedHour + ":" + selectedMinute + " AM");
                    	}
                    	else {
                    		if(selectedHour > 12)
                    			selectedHour -= 12;
                    		if(selectedMinute < 10)
                    			remTime.setText(selectedHour + ":0" + selectedMinute + " PM");
                    		else
                    			remTime.setText(selectedHour + ":" + selectedMinute + " PM");
                    	}
                    }
            	}, hour, minute, false);
                mTimePicker.setTitle("Select Reminder Time");
                mTimePicker.show();
            }
        });
        
     // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        // Listview Group collapsed listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                //Toast.makeText(
                        //getApplicationContext(),
                        //listDataHeader.get(groupPosition)
                                //+ " : "
                                //+ listDataChild.get(
                                        //listDataHeader.get(groupPosition)).get(
                                        //childPosition), Toast.LENGTH_SHORT)
                        //.show();
                return false;
            }
        });
    }
    
    // Save group data to a SharedPreferences object
    public void saveGroupData(SharedPreferences saveData, SharedPreferences.Editor saveEdit, String name, Integer color) {
        Integer i = 0;
        while(i <= Integer.MAX_VALUE) {
        	if(saveData.contains("grp" + i)) {
        		i++;
        		continue;
        	}
        	else {
        		saveEdit.putString("grp" + i, name);
                saveEdit.putInt("grp" + i, color);
                break;
        	}
        }
        saveEdit.commit();
    }
    
    // Delete group data from a SharedPreferences object
    public void deleteGroupData(SharedPreferences saveData, SharedPreferences.Editor saveEdit, Integer position) {
    	String key = "grp" + position;
    	if(saveData.contains(key)) {
    		saveEdit.remove(key);
    		saveEdit.commit();
    		Integer i = position + 1;
    		while(i <= Integer.MAX_VALUE) {
    			key = "grp" + i;
    			if(saveData.contains(key)) {
    				String name = saveData.getString(key, "");
    				Integer color = saveData.getInt(key, 0);
    				saveEdit.putString("grp" + (i-1), name);
    				saveEdit.putInt("grp" + (i-1), color);
    				saveEdit.remove(key);
    				saveEdit.commit();
    				i++;
    				continue;
    			}
    			else
    				break;
    		}
    		listAdapter.notifyDataSetChanged();
    	}
    }
    
    /*
     * Preparing the list data
     */
    private void prepareListData(SharedPreferences saveData, SharedPreferences.Editor saveEdit, boolean firstAppOpen) {
    	if(firstAppOpen) {
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, Integer>();
	 
	        // add default child data
	        listDataHeader.add("To Do");
	        listDataHeader.add("Work");
	        listDataHeader.add("Home");
	        listDataHeader.add(saveData.getString("newGrp", ""));
	 
	        listDataChild.put(listDataHeader.get(0), 0);
	        listDataChild.put(listDataHeader.get(1), 4);
	        listDataChild.put(listDataHeader.get(2), 1);
	        listDataChild.put(listDataHeader.get(3), saveData.getInt("newGrp", 5));
	        
	        // save default child data
	        saveGroupData(saveData, saveEdit, listDataHeader.get(0), listDataChild.get(listDataHeader.get(0)));
	        saveGroupData(saveData, saveEdit, listDataHeader.get(1), listDataChild.get(listDataHeader.get(1)));
	        saveGroupData(saveData, saveEdit, listDataHeader.get(2), listDataChild.get(listDataHeader.get(2)));
	        
	        // turn off first-open flag
	        firstAppOpen = false;
	        saveEdit.putBoolean("firstAppOpen", false);
	        saveEdit.commit();
	        return;
    	}
    	else {
    		listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, Integer>();
	        Integer i = 0;
	        
	        // add saved child data
	        while(i < Integer.MAX_VALUE) {
	        	String key = "grp" + i;
	        	if(saveData.contains(key)) {
	        		String name = saveData.getString(key, "");
	        		Integer color = saveData.getInt(key, 0);
	        		listDataHeader.add(name);
	        		listDataChild.put(name, color);
	        		i++;
	        		continue;
	        	}
	        	else
	        		break;
	        }
	        return;
    	}
    }
}