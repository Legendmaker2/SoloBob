package com.codefair.solobob;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {
    @Headers({"Content-Type:application/json"})

    @POST("/users")
    Call<UserInfo> registerUser(@Body RegisterUserTO registerUserTO);

    @POST("/users/login")
    Call<UserInfo> login(@Body LoginTO loginTO);

    @GET("/schedules")
    Call<List<ScheduleListTO>> getScheduleList();

    @POST("/users/{id}/schedules")
    Call<ScheduleInfoTO> makeSchedule(@Path("id") Long userId, @Body MakeScheduleTO makeScheduleTO);

    @POST("/users/{id}/schedules/{scheduleId}")
    Call<JsonObject> applySchedule(@Path("id") Long userId, @Path("scheduleId") Long scheduleId);

    @PUT("/schedules/{id}/applies/{applyId}")
    Call<JsonObject> decideApply(@Path("id") Long scheduleId, @Path("applyId") Long applyId, @Body DecideApplyTO decideApplyTO);
}
