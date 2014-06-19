package com.csci3308.precrastination_pkg;

import java.util.HashMap;
import java.util.List;

import com.csci3308.precrastination_pkg.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // list of all group names
    private HashMap<String, Integer> _listDataChild; // map of group names to group colors
 
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, Integer> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }
 
    @Override
    public Integer getChild(int groupPosition, int childPosition) {
    	return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }
    
    @Override
	public int getChildrenCount(int groupPosition) {
		return 2;
	}
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	String name = (String) getGroup(groupPosition);
    	Integer color = (Integer) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        if(childPosition == 0) {
	        EditText grpName = (EditText) convertView.findViewById(R.id.grpName);
	        grpName.setText(name);
        }
        if(childPosition == 1) {
        	RadioGroup grpColor = (RadioGroup) convertView.findViewById(R.id.grpColor);
        	if(color < 5)
        		grpColor.check(color);
        	else
        		grpColor.clearCheck();
        }
        return convertView;
    }
 
    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView grpHeaders = (TextView) convertView.findViewById(R.id.grpHeaders);
        grpHeaders.setTypeface(null, Typeface.BOLD);
        grpHeaders.setText(headerTitle);
 
        return convertView;
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