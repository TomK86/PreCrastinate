package com.csci3308.precrastinate;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.csci3308.precrastinate.R;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
 
    public ExpandableListAdapter(Context context) {
        this._context = context;
    }
 
    @Override
    public Group getChild(int groupPosition, int childPosition) {
    	return MainActivity.listGroupObjs.get(groupPosition);
    }
    
    @Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
    	String name = (String) getChild(groupPosition, 0).getName();
    	int color = (Integer) getChild(groupPosition, 0).getColor();
        EditText grpName = (EditText) convertView.findViewById(R.id.grpName);
        RadioGroup grpColor = (RadioGroup) convertView.findViewById(R.id.grpColor);
        Button grpSave = (Button) convertView.findViewById(R.id.grpSaveBtn);
        grpName.setText(name);
    	grpColor.check(grpColor.getChildAt(color).getId());
    	grpName.setTag(groupPosition);
        grpColor.setTag(groupPosition);
    	grpSave.setTag(groupPosition);
        
        return convertView;
    }
    
    public String getChildName(int groupPosition, View convertView) {
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
    	EditText grpName = (EditText) convertView.findViewById(R.id.grpName);
    	return grpName.getText().toString();
    }
    
    public int getChildColor(int groupPosition, View convertView) {
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
    	RadioGroup grpColor = (RadioGroup) convertView.findViewById(R.id.grpColor);
    	return grpColor.indexOfChild(grpColor.findViewById(grpColor.getCheckedRadioButtonId()));
    }
 
    @Override
    public Group getGroup(int groupPosition) {
        return MainActivity.listGroupObjs.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return MainActivity.listGroupObjs.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String name = (String) getGroup(groupPosition).getName();
        int color = (Integer) getGroup(groupPosition).getColor();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView grpHeaders = (TextView) convertView.findViewById(R.id.grpHeaders);
        TextView colorBlock = (TextView) convertView.findViewById(R.id.colorBlock);
        grpHeaders.setTypeface(null, Typeface.BOLD);
        grpHeaders.setText(name);
        colorBlock.setBackgroundColor(MainActivity.colorList.get(color));
        return convertView;
    }
    
    public void setGroup(int groupPosition, String newName, int newColor, View convertView) {
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView grpHeaders = (TextView) convertView.findViewById(R.id.grpHeaders);
        TextView colorBlock = (TextView) convertView.findViewById(R.id.colorBlock);
        grpHeaders.setText(newName);
        colorBlock.setBackgroundColor(newColor);
    }
 
    @Override
    public boolean hasStableIds() {
        return true;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}