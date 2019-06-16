package com.codefair.solobob;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public interface SuccessRegisterListener {
        void onSuccessRegister(UserInfo userInfo);
    }

    public interface SuccessLoginListener {
        void onSuccessLogin(UserInfo userInfo);
    }

    public interface SuccessGetScheduleListListener {
        void onSuccessGetScheduleList(List<ScheduleListTO> scheduleListTOList);
    }

    public interface SuccessMakeScheduleListener {
        void onSuccessMakeSchedule(ScheduleInfoTO scheduleInfoTO);
    }

    public interface SuccessApplyScheduleListener {
        void onSuccessApplySchedule(int position);
    }

    public interface SuccessDecideApplyListener {
        void onSuccessDecideApply(int position);
    }

    private static String TAG = "Retrofit";
    final private String requestURL = "http://10.0.2.2:8080";
    private static RetrofitManager retrofitManager;
    private Retrofit retrofit;
    private RetrofitService service;
    private SuccessRegisterListener mSuccessRegisterListener;
    private SuccessLoginListener mSuccessLoginListener;
    private SuccessGetScheduleListListener mSuccessGetScheduleListListener;
    private SuccessMakeScheduleListener mSuccessMakeScheduleListener;
    private SuccessApplyScheduleListener mSuccessApplyScheduleListener;
    private SuccessDecideApplyListener mSuccessDecideApplyListener;

    private RetrofitManager() {
        retrofit = new Retrofit.Builder().baseUrl(requestURL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        service = retrofit.create(RetrofitService.class);
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    public void setOnSuccessRegisterListener(SuccessRegisterListener mSuccessRegisterListener) {
        this.mSuccessRegisterListener = mSuccessRegisterListener;
    }

    public void setOnSuccessLoginListener(SuccessLoginListener mSuccessLoginListener) {
        this.mSuccessLoginListener = mSuccessLoginListener;
    }

    public void setOnSuccessGetScheduleListListener(SuccessGetScheduleListListener mSuccessGetScheduleListListener) {
        this.mSuccessGetScheduleListListener = mSuccessGetScheduleListListener;
    }

    public void setOnSuccessMakeScheduleListener(SuccessMakeScheduleListener mSuccessMakeScheduleListener) {
        this.mSuccessMakeScheduleListener = mSuccessMakeScheduleListener;
    }

    public void setOnSuccessApplyScheduleListener(SuccessApplyScheduleListener mSuccessApplyScheduleListener) {
        this.mSuccessApplyScheduleListener = mSuccessApplyScheduleListener;
    }

    public void setOnSuccessDecideApplyListener(SuccessDecideApplyListener mSuccessDecideApplyListener) {
        this.mSuccessDecideApplyListener = mSuccessDecideApplyListener;
    }

    public void removeSuccessRegisterListener() {
        this.mSuccessRegisterListener = null;
    }

    public void removeSuccessLoginListener() {
        this.mSuccessLoginListener = null;
    }

    public void removeSuccessGetScheduleListListener() {
        this.mSuccessGetScheduleListListener = null;
    }

    public void removeSuccessMakeScheduleListener() {
        this.mSuccessMakeScheduleListener = null;
    }

    public void removeSuccessApplyScheduleListener() {
        this.mSuccessApplyScheduleListener = null;
    }

    public void removeSuccessDecideApplyListener() {
        this.mSuccessDecideApplyListener = null;
    }

    private void showToast(int message) {
        Toast.makeText(SoloBobApplication.getGlobalContext(), message, Toast.LENGTH_LONG).show();
    }

    private void logBadResponse(int errorCode, String errorMessage, String methodName) {
        Log.e(TAG, methodName + " Error Code: " + errorCode);
        Log.e(TAG, methodName + ": " + errorMessage);
    }

    private void logConnectionFailure(String errorMessage, String methodName) {
        showToast(R.string.connection_failure_message);
        Log.e(TAG, methodName + ": " + errorMessage);
    }

    public void registerUser(RegisterUserTO registerUserTO) {
        final String methodName = "registerUser";
        Call<UserInfo> req = service.registerUser(registerUserTO);
        req.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    showToast(R.string.success_register_message);
                    if (mSuccessRegisterListener != null) {
                        mSuccessRegisterListener.onSuccessRegister(response.body());
                    }
                } else {
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }

    public void login(LoginTO loginTO) {
        final String methodName = "login";
        Call<UserInfo> req = service.login(loginTO);
        req.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    showToast(R.string.success_login_message);
                    if (mSuccessLoginListener != null) {
                        mSuccessLoginListener.onSuccessLogin(response.body());
                    }
                } else {
                    showToast(R.string.fail_login_message);
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }

    public void getScheduleList() {
        final String methodName = "getScheduleList";
        Call<List<ScheduleListTO>> req = service.getScheduleList();
        req.enqueue(new Callback<List<ScheduleListTO>>() {
            @Override
            public void onResponse(Call<List<ScheduleListTO>> call, Response<List<ScheduleListTO>> response) {
                if (response.isSuccessful()) {
                    if (mSuccessGetScheduleListListener != null) {
                        mSuccessGetScheduleListListener.onSuccessGetScheduleList(response.body());
                    }
                } else {
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<List<ScheduleListTO>> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }

    public void makeSchedule(Long userId, MakeScheduleTO makeScheduleTO) {
        final String methodName = "makeSchedule";
        Call<ScheduleInfoTO> req = service.makeSchedule(userId, makeScheduleTO);
        req.enqueue(new Callback<ScheduleInfoTO>() {
            @Override
            public void onResponse(Call<ScheduleInfoTO> call, Response<ScheduleInfoTO> response) {
                if (response.isSuccessful()) {
                    if (mSuccessMakeScheduleListener != null) {
                        mSuccessMakeScheduleListener.onSuccessMakeSchedule(response.body());
                    }
                } else {
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<ScheduleInfoTO> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }

    public void applySchedule(Long userId, Long scheduleId, int position) {
        final String methodName = "applySchedule";
        Call<JsonObject> req = service.applySchedule(userId, scheduleId);
        req.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (mSuccessApplyScheduleListener != null) {
                        mSuccessApplyScheduleListener.onSuccessApplySchedule(position);
                    }
                } else {
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }

    public void decideApply(Long scheduleId, Long applyId, int position, String status) {
        final String methodName = "decideApply";
        Call<JsonObject> req = service.decideApply(scheduleId, applyId, new DecideApplyTO(status));
        req.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (mSuccessDecideApplyListener != null) {
                        mSuccessDecideApplyListener.onSuccessDecideApply(position);
                    }
                } else {
                    logBadResponse(response.code(), response.errorBody().toString(), methodName);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                logConnectionFailure(t.getMessage(), methodName);
            }
        });
    }
}
