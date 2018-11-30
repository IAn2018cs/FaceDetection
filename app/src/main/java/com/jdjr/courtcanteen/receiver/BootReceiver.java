package com.jdjr.courtcanteen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jdjr.courtcanteen.main.MainActivity;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context paramContext, Intent paramIntent) {
        try {
            paramIntent = new Intent(paramContext, MainActivity.class);
            paramIntent.addFlags(268435456);
            paramContext.startActivity(paramIntent);
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
