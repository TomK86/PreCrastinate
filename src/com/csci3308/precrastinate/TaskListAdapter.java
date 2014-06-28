package com.csci3308.precrastinate;

import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {
	
	private static final int TYPE_TASK = 0;
	private static final int TYPE_HEADER = 1;
	
	private TreeSet<Integer> headerRows = new TreeSet<Integer>();
	private String[] headerStrings = {"Active Tasks", "Completed Tasks"};
	
	private LayoutInflater mInflater;
	
	public TaskListAdapter(Context context) {
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setCompletedHeaderRow();
	}
	
	public void setCompletedHeaderRow() {
		boolean complete;
		int activeCounter = 1;
		for(int i = 0; i < MainActivity.listTaskObjs.size(); i++) {
			complete = MainActivity.listTaskObjs.get(i).getCompleted();
			if(!complete)
				activeCounter++;
		}
		this.headerRows.clear();
		this.headerRows.add(0);
		this.headerRows.add(activeCounter);
	}
	
	@Override
	public int getItemViewType(int position) {
        return headerRows.contains(position) ? TYPE_HEADER : TYPE_TASK;
    }
	
	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public int getCount() {
		return MainActivity.listTaskObjs.size() + 2;
	}

	@Override
	public Object getItem(int position) {
		if(this.headerRows.contains(position)) {
			if(position == 0)
				return headerStrings[0];
			else
				return headerStrings[1];
		}
		else {
			if(position < headerRows.last())
				return MainActivity.listTaskObjs.get(position - 1);
			else
				return MainActivity.listTaskObjs.get(position - 2);
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
        int rowType = getItemViewType(position);
        
        if(convertView != null)
        	holder = (ViewHolder) convertView.getTag();
        if ((convertView == null) ||
        		(headerRows.contains(position) && holder.headerField == null) ||
        		(!(headerRows.contains(position)) && holder.taskField == null)) {
            holder = new ViewHolder();
            switch (rowType) {
	            case TYPE_TASK:
	                convertView = mInflater.inflate(R.layout.task_list_item, null);
	                holder.taskField = (TextView) convertView.findViewById(R.id.task_field);
	                holder.colorField = (ImageView) convertView.findViewById(R.id.color_field);
	                holder.dueDateField = (TextView) convertView.findViewById(R.id.duedate_field);
	                holder.priorityField = (TextView) convertView.findViewById(R.id.priority_field);
	                holder.completeField = (CheckBox) convertView.findViewById(R.id.checkbox);
	                break;
	            case TYPE_HEADER:
	                convertView = mInflater.inflate(R.layout.task_list_header, null);
	                holder.headerField = (TextView) convertView.findViewById(R.id.separator);
	                break;
            }
            convertView.setTag(holder);
        }
        switch(rowType) {
	        case TYPE_TASK:
	        	// correct position to reference listGroupObjs
	        	if(position < headerRows.last())
	        		position -= 1;
				else
					position -= 2;
	        	
	        	// display the group color box
	        	int color = MainActivity.listGroupObjs.get(MainActivity.listTaskObjs
						.get(position).getGroupNum()).getColor();
	        	if(color == 0)
		        	holder.colorField.setImageResource(R.drawable.green_box);
	        	if(color == 1)
	        		holder.colorField.setImageResource(R.drawable.blue_box);
	        	if(color == 2)
	        		holder.colorField.setImageResource(R.drawable.purple_box);
	        	if(color == 3)
	        		holder.colorField.setImageResource(R.drawable.red_box);
	        	if(color == 4)
	        		holder.colorField.setImageResource(R.drawable.orange_box);
	        	if(color == 5)
	        		holder.colorField.setImageResource(R.drawable.white_box);
	        	//holder.colorField.setMinimumHeight(minHeight);
	        	
	        	// display the task title
	        	holder.taskField.setText(MainActivity.listTaskObjs
	    				.get(position).getTaskTitle());
	        	
	        	// display the due date, if it exists
	        	if(MainActivity.listTaskObjs.get(position).getDueDateAsLong() == 0L)
	    			holder.dueDateField.setText("");
	    		else
	    			holder.dueDateField.setText("by " + MainActivity.listTaskObjs
	        				.get(position).getDueDateAsString());
	        	
	        	// display the priority, and color it according to its value
	        	float priority = MainActivity.listTaskObjs
	    				.get(position).getPriorityAsFloat();
	        	if(priority < 2)
	        		holder.priorityField.setTextColor(0xff33cc33);
	        	else if(priority < 4)
	        		holder.priorityField.setTextColor(0xffff9900);
	        	else
	        		holder.priorityField.setTextColor(0xffff3300);
	    		holder.priorityField.setText("Priority: " + priority);
	    		
	    		// display the check box according to completion status
	    		holder.completeField.setChecked(MainActivity.listTaskObjs
	    				.get(position).getCompleted());
	    		holder.completeField.setTag(position);
	    		break;
	        case TYPE_HEADER:
	        	if(position == 0)
	            	holder.headerField.setText(headerStrings[0]);
	            else
	            	holder.headerField.setText(headerStrings[1]);
	        	break;
        }
 
        return convertView;
	}
	
	public static class ViewHolder {
		public TextView headerField;
		public TextView taskField;
		public ImageView colorField;
		public TextView dueDateField;
		public TextView priorityField;
		public CheckBox completeField;
	}
	
	
	
}