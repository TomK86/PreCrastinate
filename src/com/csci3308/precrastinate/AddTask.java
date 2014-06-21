package com.csci3308.precrastinate;



import java.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View.OnClickListener;
import com.csci3308.precrastinate.R;


public class AddTask extends ActionBarActivity {
	
	EditText duedate;
	Spinner groupSpinner;
	RatingBar priority;
	EditText taskTitleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		// Assign Due Date
	    duedate = (EditText) findViewById(R.id.due_date);
		
		 // EditText click listener
        duedate.setOnClickListener(new OnClickListener() {
       	
       	@Override
            public void onClick(View v) {
       		Toast.makeText(getApplicationContext(),
           		"Please select the deadline or due date for this task",
           		Toast.LENGTH_LONG).show();
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

		// Create Spinner
		groupSpinner = (Spinner) findViewById(R.id.groups);
		ArrayAdapter<String> groupsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MainActivity.listGroupHeaders);
		groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupsAdapter);
        
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
        	public void onItemSelected(AdapterView<?> adapterView, 
            View view, int i, long l) { 
            }
              // If no option selected
        	public void onNothingSelected(AdapterView<?> arg0) {
        		// TODO Auto-generated method stub
        
        	} 
        	

        });
        
		// Assign Priority
		priority = (RatingBar) findViewById(R.id.priority);
		priority.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
			}
		});
		
	
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/**
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	} */

	/** Called when the user clicks the Save button */
	
	public void saveTaskButton(View view) {
		
		taskTitleName = (EditText) findViewById(R.id.new_task);
		String taskTitle = taskTitleName.getText().toString();
		String str = duedate.getText().toString();
		String dueDateName = str.replace(" / ", "");
		long chosenDate = Long.valueOf(dueDateName);
		String chosenGroup = groupSpinner.getSelectedItem().toString();
		int index = MainActivity.listGroupHeaders.indexOf(chosenGroup);
		float chosenRating = priority.getRating();
		
		
		MainActivity.saveTaskData(taskTitle, chosenDate, chosenRating, index, false);
		
		Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
		
		finish();
	}


	
}
	

	

		


