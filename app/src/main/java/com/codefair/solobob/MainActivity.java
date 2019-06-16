package com.codefair.solobob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements RetrofitManager.SuccessGetScheduleListListener, RetrofitManager.SuccessMakeScheduleListener, RetrofitManager.SuccessApplyScheduleListener, RetrofitManager.SuccessDecideApplyListener {

    private List<ScheduleInfoTO> scheduleInfoList;
    private List<ApplyListViewInfo> applyListViewInfoList;
    private List<ScheduleInfoTO> myScheduleInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitManager.getInstance().setOnSuccessGetScheduleListListener(this);
        RetrofitManager.getInstance().setOnSuccessMakeScheduleListener(this);
        RetrofitManager.getInstance().setOnSuccessApplyScheduleListener(this);
        RetrofitManager.getInstance().setOnSuccessDecideApplyListener(this);

        RetrofitManager.getInstance().getScheduleList();

        findViewById(R.id.submitBtn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = getUserInfo();
                String restaurant = ((EditText) findViewById(R.id.restaurant_editText_main)).getText().toString();
                Integer number = Integer.parseInt(((EditText) findViewById(R.id.maxNumber_editText_main)).getText().toString());
                String explanation = ((EditText) findViewById(R.id.explanation_editText_main)).getText().toString();
                RetrofitManager.getInstance().makeSchedule(userInfo.getId(), new MakeScheduleTO(restaurant, number, explanation));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListView allListView = findViewById(R.id.all_listView_main);
        RelativeLayout makeVIew = findViewById(R.id.make_main);
        ListView applyListView = findViewById(R.id.aply_listView_main);
        ListView myScheduleListView = findViewById(R.id.mySchedule_listView_main);
        switch (item.getItemId()) {
            case R.id.action_list:
                allListView.setVisibility(View.VISIBLE);
                makeVIew.setVisibility(View.GONE);
                applyListView.setVisibility(View.GONE);
                myScheduleListView.setVisibility(View.GONE);
                return true;
            case R.id.action_make:
                allListView.setVisibility(View.GONE);
                makeVIew.setVisibility(View.VISIBLE);
                applyListView.setVisibility(View.GONE);
                myScheduleListView.setVisibility(View.GONE);
                return true;
            case R.id.action_manage:
                allListView.setVisibility(View.GONE);
                makeVIew.setVisibility(View.GONE);
                applyListView.setVisibility(View.VISIBLE);
                myScheduleListView.setVisibility(View.GONE);
                return true;
            case R.id.action_schedule:
                allListView.setVisibility(View.GONE);
                makeVIew.setVisibility(View.GONE);
                applyListView.setVisibility(View.GONE);
                myScheduleListView.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private UserInfo getUserInfo() {
        return (UserInfo) getIntent().getSerializableExtra("userInfo");
    }

    @Override
    protected void onDestroy() {
        RetrofitManager.getInstance().removeSuccessGetScheduleListListener();
        RetrofitManager.getInstance().removeSuccessMakeScheduleListener();
        RetrofitManager.getInstance().removeSuccessApplyScheduleListener();
        RetrofitManager.getInstance().removeSuccessDecideApplyListener();
        super.onDestroy();
    }

    @Override
    public void onSuccessGetScheduleList(List<ScheduleListTO> scheduleListTOList) {
        UserInfo userInfo = getUserInfo();

        ListView allListView = findViewById(R.id.all_listView_main);
        ListView applyListView = findViewById(R.id.aply_listView_main);
        ListView myScheduleListView = findViewById(R.id.mySchedule_listView_main);
        this.scheduleInfoList = new ArrayList<>();
        this.applyListViewInfoList = new ArrayList<>();
        this.myScheduleInfoList = new ArrayList<>();
        for (ScheduleListTO scheduleListTO : scheduleListTOList) {
            List<ApplyInfoTO> applyInfoTOList = scheduleListTO.getApplyInfoTOList();
            Set<Long> userIdSet = applyInfoTOList.stream().map(ApplyInfoTO::getUserId).collect(Collectors.toSet());
            ScheduleInfoTO scheduleInfoTO = scheduleListTO.getScheduleInfoTO();
            if (!scheduleInfoTO.getUserId().equals(userInfo.getId()) && !userIdSet.contains(userInfo.getId())) {
                this.scheduleInfoList.add(scheduleInfoTO);
            }
            if (scheduleInfoTO.getUserId().equals(userInfo.getId())) {
                for (ApplyInfoTO applyInfoTO : applyInfoTOList) {
                    if (applyInfoTO.getStatus().equals("Apply")) {
                        this.applyListViewInfoList.add(new ApplyListViewInfo(scheduleInfoTO.getExplanation(), applyInfoTO));
                    }
                }
            }
            if (!scheduleInfoTO.getUserId().equals(userInfo.getId())) {
                for (ApplyInfoTO applyInfoTO : applyInfoTOList) {
                    if (applyInfoTO.getUserId().equals(userInfo.getId()) && applyInfoTO.getStatus().equals("Accepted")) {
                        this.myScheduleInfoList.add(scheduleInfoTO);
                    }
                }
            } else {
                this.myScheduleInfoList.add(scheduleInfoTO);
            }
        }
        allListView.setAdapter(new AllListListViewAdapter(scheduleInfoList, userInfo));
        applyListView.setAdapter(new ApplyListVIewAdapter(applyListViewInfoList));
        myScheduleListView.setAdapter(new MyScheduleListViewAdapter(myScheduleInfoList));
    }

    @Override
    public void onSuccessMakeSchedule(ScheduleInfoTO scheduleInfoTO) {
        ListView allListView = findViewById(R.id.all_listView_main);
        RelativeLayout makeVIew = findViewById(R.id.make_main);
        allListView.setVisibility(View.VISIBLE);
        makeVIew.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessApplySchedule(int position) {
        this.scheduleInfoList.remove(position);

        ListView allListView = findViewById(R.id.all_listView_main);
        AllListListViewAdapter adapter = (AllListListViewAdapter) allListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessDecideApply(int position) {
        this.applyListViewInfoList.remove(position);

        ListView applyListView = findViewById(R.id.aply_listView_main);
        ApplyListVIewAdapter adapter = (ApplyListVIewAdapter) applyListView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}
