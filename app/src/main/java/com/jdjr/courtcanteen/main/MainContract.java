package com.jdjr.courtcanteen.main;

import android.app.Activity;
import android.content.Context;

import com.jdjr.courtcanteen.base.IBaseView;
import com.jdjr.courtcanteen.bean.ClockInfo2;
import com.jdjr.courtcanteen.bean.ServiceState;

import rx.Observable;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class MainContract {
    public static abstract interface IMainModel {
        public abstract Observable<ClockInfo2> getFaceInfo(String paramString);

        public abstract Observable<ServiceState> getServiceState();
    }

    public static abstract interface IMainPresenter {
        public abstract void checkNetwork(Context paramContext);

        public abstract void checkServiceLive();

        public abstract void checkTime();

        public abstract void connectSocket();

        public abstract void requestFaceClock(String paramString);

        public abstract void setLaunchAlarm(Activity paramActivity);
    }

    public static abstract interface IMainView extends IBaseView {
        public abstract void addEatNumber();

        public abstract void clearFaceRect();

        public abstract void dismissCard();

        public abstract void dismissScanSuccess();

        public abstract void drawFaceRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

        public abstract boolean getInterfaceState();

        public abstract boolean getNoNetworkState();

        public abstract void hiddenCamerapreview();

        public abstract void hiddenInterfaceError();

        public abstract void hiddenNoNetwork();

        public abstract void playSound(int paramInt);

        public abstract void restartFaceCheck();

        public abstract void setAllEatNumber(int paramInt);

        public abstract void showCamerapreview();

        public abstract void showErrorPopWindow(String paramString1, String paramString2, String paramString3);

        public abstract void showFaceDefault();

        public abstract void showInterfaceError();

        public abstract void showNoNetwork();

        public abstract void showScanSuccessPopWindow();

        public abstract void showSuccessPopWindow(String paramString1, String paramString2, String paramString3);

        public abstract void showTouchDefault();

        public abstract void stopFaceCheck();

        public abstract void updateEatNumber();
    }
}
