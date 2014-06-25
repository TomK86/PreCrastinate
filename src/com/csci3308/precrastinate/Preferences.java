package com.csci3308.precrastinate;

import java.util.Calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 
	GroupListAdapter grpListAdapter;
    ExpandableListView grpListView;
    EditText remTimeField;
    Button prefSaveBtn, addGrpBtn;
    private static int mHour = -1;
    private static int mMinute = -1;
    private static int AmPm = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // get the group list view
        grpListView = (ExpandableListView) findViewById(R.id.groupExpList);
        
        // get the other child views
        remTimeField = (EditText) findViewById(R.id.remTimeEdit);
        prefSaveBtn = (Button) findViewById(R.id.prefSaveBtn);
        addGrpBtn = (Button) findViewById(R.id.addGrpBtn);
        
        // set the current saved reminder time, if it exists
        MainActivity.loadRemTime();
        mHour = MainActivity.mHour;
        mMinute = MainActivity.mMinute;
        AmPm = MainActivity.AmPm;
    	if(AmPm == 0) {
        	if(mMinute < 10)
    			remTimeField.setText(mHour + ":0" + mMinute + " AM");
    		else
    			remTimeField.setText(mHour + ":" + mMinute + " AM");
    	}
    	else if(AmPm == 1) {
    		if(mMinute < 10)
    			remTimeField.setText(mHour + ":0" + mMinute + " PM");
    		else
    			remTimeField.setText(mHour + ":" + mMinute + " PM");
    	}
        
        // get the group list adapter
        grpListAdapter = new GroupListAdapter(this);
 
        // set the group list adapter
        grpListView.setAdapter(grpListAdapter);
        
        // this is called when a group in the list is clicked
        grpListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                return false;
            }
        });
 
        // this is called when a group in the list is expanded
        grpListView.setOnGroupExpandListener(new OnGroupExpandListener() {
        	int previousGroup = -1;
 
            @Override
            public void onGroupExpand(int groupPosition) {
            	prefSaveBtn.setVisibility(View.GONE);
            	addGrpBtn.setVisibility(View.GONE);
            	if((groupPosition != previousGroup) && (previousGroup != -1))
                    grpListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
 
        // this is called when a group in the list is collapsed
        grpListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
            	boolean expCheck = false;
            	for(int i = 0; i < MainActivity.listGroupObjs.size(); i++)
            		expCheck = expCheck | grpListView.isGroupExpanded(i);
            	if(!expCheck)
            		prefSaveBtn.setVisibility(View.VISIBLE);
            		addGrpBtn.setVisibility(View.VISIBLE);
            	MainActivity.loadGroupData();
            }
        });
 
        // this is called when one of the settings in an expanded group is clicked
        grpListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }
    
    public int getHour() {
    	return mHour;
    }
    
    public int getMinute() {
    	return mMinute;
    }
    
    public int getAmPm() {
    	return AmPm;
    }
    
    public static void setRemTime(int hour, int minute, int ampm) {
    	mHour = hour;
    	mMinute = minute;
    	AmPm = ampm;
    }
    
    public void updateRemTime() {
    	if(AmPm == 0) {
        	if(mMinute < 10)
    			remTimeField.setText(mHour + ":0" + mMinute + " AM");
    		else
    			remTimeField.setText(mHour + ":" + mMinute + " AM");
    	}
    	else if(AmPm == 1) {
    		if(mMinute < 10)
    			remTimeField.setText(mHour + ":0" + mMinute + " PM");
    		else
    			remTimeField.setText(mHour + ":" + mMinute + " PM");
    	}
    }
    
    // this is called when the reminder time text field is clicked
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
            		AmPm = 0;
            		if(selectedHour == 0)
            			selectedHour = 12;
            		if(selectedMinute < 10)
            			remTimeField.setText(selectedHour + ":0" + selectedMinute + " AM");
            		else
            			remTimeField.setText(selectedHour + ":" + selectedMinute + " AM");
            	}
            	else {
            		AmPm = 1;
            		if(selectedHour > 12) 
            			selectedHour -= 12;
            		if(selectedMinute < 10)
            			remTimeField.setText(selectedHour + ":0" + selectedMinute + " PM");
            		else
            			remTimeField.setText(selectedHour + ":" + selectedMinute + " PM");
            	}
            	mHour = selectedHour;
            	mMinute = selectedMinute;
            }
    	}, hour, minute, false);
        mTimePicker.setTitle("Select Reminder Time");
        mTimePicker.show();
    }
    
    // this is called when one of the group color radio buttons is clicked
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    RadioGroup grpColor = (RadioGroup) view.getParent();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.color0:
	            if (checked)
	                grpColor.setTag(0);
	            break;
	        case R.id.color1:
	            if (checked)
	            	grpColor.setTag(1);
	            break;
	        case R.id.color2:
	            if (checked)
	            	grpColor.setTag(2);
	            break;
	        case R.id.color3:
	            if (checked)
	            	grpColor.setTag(3);
	            break;
	        case R.id.color4:
	            if (checked)
	            	grpColor.setTag(4);
	            break;
	    }
	}
	
	// this is called when the group update button is clicked
	public void onGrpUpdateBtnClicked(View view) {
		int position = (Integer) view.getTag();
		
		EditText grpName = (EditText) view.getRootView().findViewById(R.id.grpName);
		RadioGroup grpColor = (RadioGroup) view.getRootView().findViewById(R.id.grpColor);
		
    	String newName = grpName.getText().toString();
    	int newColor = (Integer) grpColor.getTag();
    	
    	MainActivity.listGroupObjs.get(position).setName(newName);
    	MainActivity.listGroupObjs.get(position).setColor(newColor); 
    	
    	grpListAdapter.notifyDataSetChanged();
    	grpListView.collapseGroup(position);
    	
    	Toast.makeText(getApplicationContext(),
        		"'" + MainActivity.listGroupObjs.get(position).getName()
        		+ "' group updated", Toast.LENGTH_SHORT).show();
	}
	
	// this is called when the group delete button is clicked
	public void onGrpDeleteBtnClicked(View view) {
		int position = (Integer) view.getTag();
		String deletedGrp = MainActivity.listGroupObjs.get(position).getName();
		MainActivity.listGroupObjs.remove(position);
		MainActivity.deleteGroupData(position);
		grpListView.collapseGroup(position);
		grpListAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(),
        		"'" + deletedGrp + "' group deleted",
                Toast.LENGTH_SHORT).show();
	}
	
	// this is called when the add group button is clicked
	public void onAddGrpBtnClicked(View view) {
		int key = MainActivity.listGroupObjs.size();
		MainActivity.listGroupObjs.add(key, new Group("New Group", 0));
		grpListAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(),
        		"New group added to list",
                Toast.LENGTH_SHORT).show();
	}
	
	// this is called when the save preferences button is clicked
	public void onPrefSaveBtnClicked(View view) {
		MainActivity.saveGroupData();
		MainActivity.saveRemTime(mHour, mMinute, AmPm);
		Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show();
		finish();
	}
    
}