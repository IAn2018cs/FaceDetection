package com.jdjr.courtcanteen.utils;

import android.widget.Toast;

import com.jdjr.courtcanteen.MyApplication;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(int paramInt) {
        if (mToast == null)
            mToast = Toast.makeText(MyApplication.getAppContext(), paramInt, 0);
        else
            mToast.setText(paramInt);
        mToast.show();
    }

    public static void showToast(String paramString) {
        if (mToast == null)
            mToast = Toast.makeText(MyApplication.getAppContext(), paramString, 0);
        else
            mToast.setText(paramString);
        mToast.show();
    }

    public static void showToastLong(int paramInt) {
        if (mToast == null)
            mToast = Toast.makeText(MyApplication.getAppContext(), paramInt, 1);
        else
            mToast.setText(paramInt);
        mToast.show();
    }

    public static void showToastLong(String paramString) {
        if (mToast == null)
            mToast = Toast.makeText(MyApplication.getAppContext(), paramString, 1);
        else
            mToast.setText(paramString);
        mToast.show();
    }
}
