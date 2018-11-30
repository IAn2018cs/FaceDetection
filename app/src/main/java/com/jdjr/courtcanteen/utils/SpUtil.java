package com.jdjr.courtcanteen.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jdjr.courtcanteen.MyApplication;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class SpUtil {
    private static SharedPreferences sSharedPreferences = MyApplication.getAppContext().getSharedPreferences("config", 0);

    public static boolean getBoolean(String paramString, boolean paramBoolean) {
        return sSharedPreferences.getBoolean(paramString, paramBoolean);
    }

    public static int getInt(String paramString, int paramInt) {
        return sSharedPreferences.getInt(paramString, paramInt);
    }

    public static String getString(String paramString1, String paramString2) {
        return sSharedPreferences.getString(paramString1, paramString2);
    }

    public static void putBoolean(String paramString, boolean paramBoolean) {
        sSharedPreferences.edit().putBoolean(paramString, paramBoolean).apply();
    }

    public static void putInt(String paramString, int paramInt) {
        sSharedPreferences.edit().putInt(paramString, paramInt).apply();
    }

    public static void putString(String paramString1, String paramString2) {
        sSharedPreferences.edit().putString(paramString1, paramString2).apply();
    }

    public static void remove(String paramString) {
        sSharedPreferences.edit().remove(paramString).apply();
    }
}
