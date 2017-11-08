package com.yxkj.basemoudle;

import android.app.Application;

import com.yxkj.basemoudle.tools.CrashHandler;

/**
 *
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;



    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        if (!BuildConfig.DEBUG) {
            CrashHandler.getInstance().init(this);
        }
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

}
