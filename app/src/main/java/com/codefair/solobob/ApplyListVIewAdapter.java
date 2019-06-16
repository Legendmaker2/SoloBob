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

public class ApplyListVIewAdapter extends BaseAdapter {

    private List<ApplyListViewInfo> applyListViewInfoList = new ArrayList<>();

    public ApplyListVIewAdapter(List<ApplyListViewInfo> applyListViewInfoList) {
        this.applyListViewInfoList = applyListViewInfoList;
    }

    @Override
    public int getCount() {
        return applyListViewInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return applyListViewInfoList.get(position);
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
            convertView = inflater.inflate(R.layout.apply_listview_item, parent, false);
        }

        TextView userNameTextView = convertView.findViewById(R.id.userName_text_applyListItem);
        Button acceptBtn = convertView.findViewById(R.id.acceptBtn_applyListItem);
        Button rejectBtn = convertView.findViewById(R.id.rejectBtn_applyListItem);

        ApplyListViewInfo applyListViewInfo = applyListViewInfoList.get(position);
        userNameTextView.setText(applyListViewInfo.getExplanation() + "\n" + applyListViewInfo.getUserName());
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.getInstance().decideApply(applyListViewInfo.getScheduleId(), applyListViewInfo.getId(), pos, "Accepted");
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.getInstance().decideApply(applyListViewInfo.getScheduleId(), applyListViewInfo.getId(), pos, "Reject");
            }
        });

        return convertView;
    }
}
