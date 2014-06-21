package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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

public class Preferences extends Activity {
 
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
        setContentView(R.layout.preferences_main);
 
        // get the parent view
        expListView = (ExpandableListView) findViewById(R.id.groupExpList);
        
        // get the child views
        remTime = (EditText) findViewById(R.id.remTimeEdit);
        
        grpNames = (EditText) findViewById(R.id.grpName);
        
        grpColors = (RadioGroup) findViewById(R.id.grpColor);
        
        // get expandable list adapter
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // set expandable list adapter
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
            	mTimePicker = new TimePickerDialog(Preferences.this, new TimePickerDialog.OnTimeSetListener() {
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
    
}