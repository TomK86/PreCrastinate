package com.csci3308.precrastinate;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RadioButton;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        
        // get the parent view
        expListView = (ExpandableListView) findViewById(R.id.groupExpList);
        
        // get the child views
        remTime = (EditText) findViewById(R.id.remTimeEdit);
        
        grpNames = (EditText) findViewById(R.id.grpName);
        
        grpColors = (RadioGroup) findViewById(R.id.grpColor);
        
        // get expandable list adapter
        listAdapter = new ExpandableListAdapter(this);
 
        // set expandable list adapter
        expListView.setAdapter(listAdapter);
        
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
        	int previousGroup = -1;
 
            @Override
            public void onGroupExpand(int groupPosition) {
            	if((groupPosition != previousGroup) && (previousGroup != -1))
                    expListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
 
        // Listview Group collapsed listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
            	//Toast.makeText(getApplicationContext(),
                		//"'" + MainActivity.listGroupObjs.get(groupPosition).getName() + "' group saved",
                        //Toast.LENGTH_SHORT).show();
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                //if(v.getTag() == "group save button") {
                	//String name = listAdapter.getChildName(groupPosition, null);
                	//MainActivity.listGroupObjs.get(groupPosition).setName(name);
                	//listAdapter.notifyDataSetChanged();
                	//return true;
                //}
                //else
                	return false;
            }
        });
    }
    
    public void onReminderTimeClicked(View v) {
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
    
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    int position = (Integer) ((RadioGroup) view.getParent()).getTag();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.color0:
	            if (checked)
	                MainActivity.listGroupObjs.get(position).setColor(0);
	            break;
	        case R.id.color1:
	            if (checked)
	            	MainActivity.listGroupObjs.get(position).setColor(1);
	            break;
	        case R.id.color2:
	            if (checked)
	            	MainActivity.listGroupObjs.get(position).setColor(2);
	            break;
	        case R.id.color3:
	            if (checked)
	            	MainActivity.listGroupObjs.get(position).setColor(3);
	            break;
	        case R.id.color4:
	            if (checked)
	            	MainActivity.listGroupObjs.get(position).setColor(4);
	            break;
	    }
	    listAdapter.notifyDataSetChanged();
	    Toast.makeText(getApplicationContext(),
        		"'" + MainActivity.listGroupObjs.get(position).getName() + "' group color saved",
                Toast.LENGTH_SHORT).show();
	}
	
	public void onGrpSaveBtnClicked(View view) {
		int position = (Integer) view.getTag();
		EditText grpName = (EditText) view.getRootView().findViewById(R.id.grpName);
		//RadioGroup grpColor = (RadioGroup) view.getRootView().findViewById(R.id.grpColor);
    	String name = grpName.getText().toString();
    	//int color = grpColor.indexOfChild(grpColor.findViewById(grpColor.getCheckedRadioButtonId()));
    	MainActivity.listGroupObjs.get(position).setName(name);
    	//MainActivity.listGroupObjs.get(position).setColor(color);
    	listAdapter.notifyDataSetChanged();
    	Toast.makeText(getApplicationContext(),
        		"'" + MainActivity.listGroupObjs.get(position).getName() + "' group name saved",
                Toast.LENGTH_SHORT).show();
	}
	
	public void onAddGrpBtnClicked(View view) {
		int key = MainActivity.listGroupObjs.size();
		MainActivity.listGroupObjs.put(key, new Group("New Group", 0));
		listAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(),
        		"New group added to list",
                Toast.LENGTH_SHORT).show();
	}
	
	public void onPrefSaveBtnClicked(View view) {
		Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
	}
    
}