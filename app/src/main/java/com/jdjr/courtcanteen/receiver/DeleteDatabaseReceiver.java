package com.jdjr.courtcanteen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jdjr.courtcanteen.db.FaceDatabase;
import com.jdjr.courtcanteen.utils.BitmapUtil;
import com.jdjr.courtcanteen.utils.TimeUtil;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class DeleteDatabaseReceiver extends BroadcastReceiver {
    public void onReceive(Context paramContext, Intent paramIntent) {
        FaceDatabase.getInstance().deleteDay(TimeUtil.getDayString());
        Log.d("onReceive", "删除当天记录");
        BitmapUtil.deleteFaceImage();
    }
}
