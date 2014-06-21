// Attempt at a home screen/to do list for our android application
// following a tutorial online, found at http://www.youtube.com/watch?v=rwEozDvCMj0

package com.example.homescreen;

import java.util.ArrayList;

//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener, OnKeyListener  {

	
	EditText addItem;
	Button addButton;
	ListView toDo;
	
	ArrayList<String> currentTasks;
	ArrayAdapter<String> adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addItem = (EditText)findViewById(R.id.addItem);
        addButton = (Button)findViewById(R.id.addButton);
        toDo = (ListView)findViewById(R.id.toDoList);
        
        addButton.setOnClickListener(this);
        addItem.setOnKeyListener(this);
        
        currentTasks = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currentTasks);
        toDo.setAdapter(adapter);
        
        
/*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
*/
    }
    
    private void addToList(String task) {
    	if (task.length() > 0) {
    		this.currentTasks.add(task);
    		this.adapter.notifyDataSetChanged();
    		this.addItem.setText("");
    	}
    }

	@Override
	public void onClick(View v) {
		if (v == this.addButton) {
			this.addToList(this.addItem.getText().toString());
		}
		
	}
	

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			this.addToList(this.addItem.getText().toString());
		}
		return false;
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
 /*   public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }*/


}
