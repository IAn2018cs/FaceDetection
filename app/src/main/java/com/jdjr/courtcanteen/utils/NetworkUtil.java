package com.jdjr.courtcanteen.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class NetworkUtil {
    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_WIFI = 1;

    public static void checkNetwork(Activity paramActivity) {
        if (!isNetworkAvalible(paramActivity))
            new AlertDialog.Builder(paramActivity).setIcon(2131427328).setTitle("网络状态提示").setMessage("当前没有可以使用的网络，请设置网络！").setPositiveButton("确定", new DialogInterface.OnClickListener(paramActivity) {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    this.val$activity.startActivityForResult(new Intent("android.settings.WIRELESS_SETTINGS"), 0);
                }
            }).create().show();
    }

    public static final int getNetWorkStates(Context paramContext) {
        paramContext = ((ConnectivityManager) paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (paramContext != null) {
            if (!paramContext.isConnected())
                return -1;
            switch (paramContext.getType()) {
                default:
                    return -1;
                case 1:
                    return 1;
                case 0:
            }
            return 0;
        }
        return -1;
    }

    public static boolean isNetworkAvalible(Context paramContext) {
        paramContext = (ConnectivityManager) paramContext.getSystemService("connectivity");
        if (paramContext == null)
            return false;
        paramContext = paramContext.getAllNetworkInfo();
        if (paramContext != null) {
            int i = 0;
            while (i < paramContext.length) {
                if (paramContext[i].getState() == NetworkInfo.State.CONNECTED)
                    return true;
                i += 1;
            }
        }
        return false;
    }
}
