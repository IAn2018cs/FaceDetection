package com.jdjr.courtcanteen.utils;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.List;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class PhoneUtil {
    public static final String getIMEI(Context paramContext) {
        try {
            String str = ((TelephonyManager) paramContext.getSystemService("phone")).getDeviceId();
            paramContext = str;
            if (str == null)
                paramContext = "";
            return paramContext;
        } catch (Exception paramContext) {
            paramContext.printStackTrace();
        }
        return "";
    }

    public static String getTopActivity(Context paramContext) {
        paramContext = ((ActivityManager) paramContext.getSystemService("activity")).getRunningTasks(1);
        if (paramContext != null)
            return ((ActivityManager.RunningTaskInfo) paramContext.get(0)).topActivity.toString();
        return null;
    }
}