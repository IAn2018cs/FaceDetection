package com.jdjr.courtcanteen;

import android.app.Application;
import android.content.Context;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class MyApplication extends Application {
    private static Context mContext;

    public static Context getAppContext()
    {
        return mContext;
    }

    public void onCreate()
    {
        super.onCreate();
        mContext = this;
    }
}
