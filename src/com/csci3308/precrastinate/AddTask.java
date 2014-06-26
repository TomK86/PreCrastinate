package com.csci3308.precrastinate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View.OnClickListener;


public class AddTask extends Activity {
	
	EditText duedate;
	Spinner groupSpinner;
	List<String> listGroupNames;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Populate a list of the group names
		listGroupNames = new ArrayList<String>();
		for(int i = 0; i < MainActivity.listGroupObjs.size(); i++)
			listGroupNames.add(MainActivity.listGroupObjs.get(i).getName());
		
		// Create Spinner
		groupSpinner = (Spinner) findViewById(R.id.addTaskGroup);
		ArrayAdapter<String> groupsAdapter = new ArrayAdapter<String>(this, android.R.layout
				.simple_spinner_item, listGroupNames);
		groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupsAdapter);
		
		// Assign Due Date
	    duedate = (EditText) findViewById(R.id.addTaskDate);
		
		 // EditText click listener
        duedate.setOnClickListener(new OnClickListener() {
       	
        	@Override
            public void onClick(View v) {
	           	Calendar mCurrentDate = Calendar.getInstance();
	           	int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
	           	int month = mCurrentDate.get(Calendar.MONTH);
	           	int year = mCurrentDate.get(Calendar.YEAR);
	           	DatePickerDialog mDatePicker;
	           	mDatePicker = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
	           		@Override
	                public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
	                duedate.setText((selectedMonth + 1) + " / " + selectedDay + " / " + selectedYear);
	                
	                }
	           	}, year, month, day);
	               mDatePicker.setTitle("Select Due Date");
	               mDatePicker.show();
        	}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_task, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
    	// automatically handle clicks on the Home/Up button, so long
    	// as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
        	case R.id.action_preferences:
        		setPrefs();
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }

	// Called when the user clicks the Save button
	public void onSaveTaskButtonClicked(View view) {
		EditText taskTitleName = (EditText) findViewById(R.id.addTaskName);
		RatingBar priority = (RatingBar) findViewById(R.id.addTaskPriority);
		
		String chosenDate = duedate.getText().toString();
		String chosenTitle = taskTitleName.getText().toString();
		int chosenGroup = groupSpinner.getSelectedItemPosition();
		float chosenRating = priority.getRating();
		
		Task newTask = new Task(chosenTitle, chosenDate, chosenRating, chosenGroup, false); 
		MainActivity.saveTaskData(newTask);
		MainActivity.sortTasks();
		
		Toast.makeText(this, "Task saved!", Toast.LENGTH_SHORT).show();
		
		finish();
	}
	
	// launches the Preferences activity
	public void setPrefs() {
		Intent prefs = new Intent(this, Preferences.class);
		startActivity(prefs);
	}
	
}