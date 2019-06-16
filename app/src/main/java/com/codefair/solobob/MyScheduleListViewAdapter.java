package com.codefair.solobob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyScheduleListViewAdapter extends BaseAdapter {

    private List<ScheduleInfoTO> myScheduleInfoTOList = new ArrayList<>();

    public MyScheduleListViewAdapter(List<ScheduleInfoTO> myScheduleInfoTOList) {
        this.myScheduleInfoTOList = myScheduleInfoTOList;
    }

    @Override
    public int getCount() {
        return myScheduleInfoTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return myScheduleInfoTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.myschedule_listview_item, parent, false);
        }

        TextView explanationTextView = convertView.findViewById(R.id.explanation_text_myScheduleListView);
        explanationTextView.setText(myScheduleInfoTOList.get(position).getExplanation());

        return convertView;
    }
}
