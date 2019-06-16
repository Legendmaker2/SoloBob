package com.codefair.solobob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllListListViewAdapter extends BaseAdapter {

    private List<ScheduleInfoTO> scheduleInfoTOList = new ArrayList<>();
    private UserInfo userInfo;

    public AllListListViewAdapter(List<ScheduleInfoTO> scheduleInfoTOList, UserInfo userInfo) {
        this.scheduleInfoTOList = scheduleInfoTOList;
        this.userInfo = userInfo;
    }

    @Override
    public int getCount() {
        return scheduleInfoTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleInfoTOList.get(position);
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
            convertView = inflater.inflate(R.layout.schedule_listview_item, parent, false);
        }

        TextView explanationTextView = convertView.findViewById(R.id.explanation_text_listItem);
        Button registerBtn = convertView.findViewById(R.id.registerBtn_listItem);
        explanationTextView.setText(scheduleInfoTOList.get(position).getExplanation());
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.getInstance().applySchedule(userInfo.getId(), scheduleInfoTOList.get(pos).getId(), pos);
            }
        });
        return convertView;
    }
}
