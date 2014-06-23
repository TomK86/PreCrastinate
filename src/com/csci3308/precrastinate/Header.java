package com.csci3308.precrastinate;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.csci3308.precrastinate.R;
import com.csci3308.precrastinate.TwoTextArrayAdapter.RowType;

public class Header implements Item {
    private final String         name;

    public Header(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.header, null);
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(name);

        return view;
    }

}