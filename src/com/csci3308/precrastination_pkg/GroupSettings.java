package com.csci3308.precrastination_pkg;

import java.util.ArrayList;

import com.csci3308.precrastination_pkg.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GroupSettings extends ListActivity {
	
	private GroupSetAdapter grpAdapter;
	private Context _context;
	private String _grpName;
	private int _grpColor;
	
	public GroupSettings(Context context, String grpName, int grpColor) {
		this._context = context;
		this._grpName = grpName;
		this._grpColor = grpColor;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        grpAdapter = new GroupSetAdapter();
        setListAdapter(grpAdapter);
    }
	
	static class ViewHolder {
		EditText name;
		RadioGroup color;
	}
	
	private class GroupSetAdapter extends BaseAdapter {
		
		private static final int TYPE_NAME = 0;
		private static final int TYPE_COLOR = 1;
		private static final int TYPE_MAX_COUNT = TYPE_COLOR + 1;
		
		private ArrayList<String> grpList = new ArrayList<String>();
		private LayoutInflater mInflater;
		
		public GroupSetAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return grpList.size();
		}

		@Override
		public String getItem(int position) {
			return grpList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			int type = getItemViewType(position);
			
			if(convertView == null) {
				holder = new ViewHolder();
				switch (type) {
					case TYPE_NAME:
						convertView = mInflater.inflate(R.layout.list_item, parent, false);
						holder.name = (EditText) convertView.findViewById(R.id.grpName);
						break;
					case TYPE_COLOR:
						convertView = mInflater.inflate(R.layout.list_item, parent, false);
						holder.color = (RadioGroup) convertView.findViewById(R.id.grpColor);
						break;
				}
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.name.setText(grpList.get(position));
			holder.color.check(position);
			
			return convertView;
		}
		
		@Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }
		
		public void onRadioButtonClicked(View view) {
		    boolean checked = ((RadioButton) view).isChecked();
		    switch(view.getId()) {
		        case R.id.color0: // green (#00CC00)
		            if (checked)
		                // TODO
		            break;
		        case R.id.color1: // blue (#0066FF)
		            if (checked)
		                // TODO
		            break;
		        case R.id.color2: // purple (#9933FF)
		            if (checked)
		                // TODO
		            break;
		        case R.id.color3: // red (#FF0000)
		            if (checked)
		                // TODO
		            break;
		        case R.id.color4: // orange (#FF9900)
		            if (checked)
		                // TODO
		            break;
		    }
		}
		
	}

}
