package com.csci3308.precrastinate;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.csci3308.precrastinate.TwoTextArrayAdapter.RowType;

public class ListItem implements Item {
    private final String         tsk;
    private final String         due;
    private final int			 clr;
    private final boolean        cmp;
    private final int[] colors = new int[] { 0x9900CC00, 0x990066FF, 0x999933FF, 0x99FF0000, 0x99FF9900 };

    public ListItem(int colorIdx, String task, String dueDate, boolean completed) {
        this.clr = colorIdx;
        this.tsk = task;
        this.due = dueDate;
        this.cmp = completed;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.my_list_item, null);
        } else {
            view = convertView;
        }

        TextView colorField = (TextView) view.findViewById(R.id.color_field);
        TextView taskField = (TextView) view.findViewById(R.id.task_field);
        TextView dueDateField = (TextView) view.findViewById(R.id.duedate_field);
        CheckBox completedField = (CheckBox) view.findViewById(R.id.checkbox);
        colorField.setBackgroundColor(colors[clr]);
        taskField.setText(tsk);
        dueDateField.setText("Due: " + due);
        completedField.setChecked(cmp);

        return view;
    }

}