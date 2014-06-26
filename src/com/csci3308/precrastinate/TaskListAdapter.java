package com.csci3308.precrastinate;

import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
	                holder.colorField = (TextView) convertView.findViewById(R.id.color_field);
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
        if(headerRows.contains(position)) {
        	if(position == 0)
            	holder.headerField.setText(headerStrings[0]);
            else
            	holder.headerField.setText(headerStrings[1]);
        }
        else {
        	if(position < headerRows.last())
        		position -= 1;
			else
				position -= 2;
        	holder.taskField.setText(MainActivity.listTaskObjs
    				.get(position).getTaskTitle());
    		holder.colorField.setBackgroundColor(MainActivity.colorList
    				.get(MainActivity.listGroupObjs.get(MainActivity.listTaskObjs
    						.get(position).getGroupNum()).getColor()));
    		holder.priorityField.setText("Priority: " + MainActivity.listTaskObjs
    				.get(position).getPriorityAsString());
    		holder.completeField.setChecked(MainActivity.listTaskObjs
    				.get(position).getCompleted());
    		holder.completeField.setTag(position);
    		if(MainActivity.listTaskObjs.get(position).getDueDateAsLong() == 0L)
    			holder.dueDateField.setText("");
    		else
    			holder.dueDateField.setText("by " + MainActivity.listTaskObjs
        				.get(position).getDueDateAsString());
        }
 
        return convertView;
	}
	
	public static class ViewHolder {
		public TextView headerField;
		public TextView taskField;
		public TextView colorField;
		public TextView dueDateField;
		public TextView priorityField;
		public CheckBox completeField;
	}
	
	
	
}