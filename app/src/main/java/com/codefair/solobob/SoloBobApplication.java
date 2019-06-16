package com.codefair.solobob;

import android.app.Application;
import android.content.Context;

public class SoloBobApplication extends Application {

    private static SoloBobApplication application;

    public static Context getGlobalContext() {
        return SoloBobApplication.application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
