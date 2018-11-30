package com.jdjr.courtcanteen.main;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dhh.websocket.WebSocketSubscriber;
import com.jdjr.courtcanteen.MyApplication;
import com.jdjr.courtcanteen.base.BasePresenter;
import com.jdjr.courtcanteen.bean.ClockInfo;
import com.jdjr.courtcanteen.bean.ClockInfo2;
import com.jdjr.courtcanteen.bean.ClockInfo2.DataBean;
import com.jdjr.courtcanteen.bean.ClockInfo2.DataBean.PersonBean;
import com.jdjr.courtcanteen.bean.ServiceState;
import com.jdjr.courtcanteen.db.FaceDatabase;
import com.jdjr.courtcanteen.receiver.DeleteDatabaseReceiver;
import com.jdjr.courtcanteen.utils.Constant;
import com.jdjr.courtcanteen.utils.NetworkUtil;
import com.jdjr.courtcanteen.utils.SpUtil;
import com.jdjr.courtcanteen.utils.TimeUtil;
import com.jdjr.courtcanteen.utils.URLs;
import com.jdjr.courtcanteen.utils.WebSocketUtils;

import java.util.Calendar;

import okhttp3.WebSocket;
import okio.ByteString;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class MainPresenter extends BasePresenter<MainContract.IMainView>
        implements MainContract.IMainPresenter {
    public static volatile boolean checkInterface;
    public static volatile boolean checkTime;
    public static volatile long endTime;
    public static volatile boolean isRequest = false;
    private int mErrorNumber = 0;
    private MainContract.IMainModel mModel = new MainModel();
    private int unIdentify = 0;

    static {
        checkTime = true;
        checkInterface = true;
        endTime = 0L;
    }

    private void networkError() {
        this.mErrorNumber += 1;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("失败");
        localStringBuilder.append(this.mErrorNumber);
        localStringBuilder.append("次");
        Log.e("networkError", localStringBuilder.toString());
        if (getView() != null) {
            if (this.mErrorNumber <= 5) {
                ((MainContract.IMainView) getView()).clearFaceRect();
                ((MainContract.IMainView) getView()).playSound(2131492864);
                ((MainContract.IMainView) getView()).showScanSuccessPopWindow();
                ((MainContract.IMainView) getView()).showErrorPopWindow("", "再试一次", "");
                return;
            }
            ((MainContract.IMainView) getView()).showInterfaceError();
        }
    }

    private void parseJson(ClockInfo2 paramClockInfo2) {
        int i = paramClockInfo2.getCode();
        Object localObject1 = paramClockInfo2.getMsg();
        Object localObject2 = new StringBuilder();
        ((StringBuilder) localObject2).append((String) localObject1);
        ((StringBuilder) localObject2).append(",code:");
        ((StringBuilder) localObject2).append(i);
        Log.d("parseJson", ((StringBuilder) localObject2).toString());
        if (i != 0) {
            if (i != 4004) {
                if (getView() != null) {
                    ((MainContract.IMainView) getView()).showScanSuccessPopWindow();
                    ((MainContract.IMainView) getView()).playSound(2131492869);
                    ((MainContract.IMainView) getView()).showErrorPopWindow("", "抱歉，未能识别到您", "");
                }
                endTime = System.currentTimeMillis();
                ((MainContract.IMainView) getView()).restartFaceCheck();
                isRequest = false;
                return;
            }
            if (getView() != null) {
                if (this.unIdentify > 1) {
                    ((MainContract.IMainView) getView()).showScanSuccessPopWindow();
                    ((MainContract.IMainView) getView()).playSound(2131492869);
                    ((MainContract.IMainView) getView()).showErrorPopWindow("", "抱歉，未能识别到您", "");
                    this.unIdentify = 0;
                    endTime = System.currentTimeMillis();
                    ((MainContract.IMainView) getView()).restartFaceCheck();
                    isRequest = false;
                    return;
                }
                this.unIdentify += 1;
                endTime = System.currentTimeMillis() - 1700L;
                ((MainContract.IMainView) getView()).restartFaceCheck();
                isRequest = false;
                return;
            }
        } else {
            this.unIdentify = 0;
            Log.d("parseJson", "识别成功");
            localObject2 = paramClockInfo2.getData().getPerson();
            localObject1 = ((ClockInfo2.DataBean.PersonBean) localObject2).getName();
            paramClockInfo2 = (ClockInfo2) localObject1;
            if (((String) localObject1).length() > 4)
                paramClockInfo2 = ((String) localObject1).substring(0, 4);
            long l = ((ClockInfo2.DataBean.PersonBean) localObject2).getId();
            localObject1 = ((ClockInfo2.DataBean.PersonBean) localObject2).getPosition();
            localObject2 = ((ClockInfo2.DataBean.PersonBean) localObject2).getSmallImageURL().replace("icourt.gov.cn", URLs.HOST);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("员工注册图片");
            localStringBuilder.append((String) localObject2);
            Log.d("parseJson", localStringBuilder.toString());
            if (getView() != null)
                ((MainContract.IMainView) getView()).showScanSuccessPopWindow();
            localObject1 = new ClockInfo(String.valueOf(l), paramClockInfo2, (String) localObject2, (String) localObject1);
            i = FaceDatabase.getInstance().checkClock((ClockInfo) localObject1);
            if (getView() != null)
                switch (i) {
                    default:
                        break;
                    case 3:
                        playVoice();
                        ((MainContract.IMainView) getView()).showErrorPopWindow(paramClockInfo2, "还未到用餐时间哦", (String) localObject2);
                        break;
                    case 2:
                        ((MainContract.IMainView) getView()).playSound(2131492865);
                        ((MainContract.IMainView) getView()).showSuccessPopWindow((String) localObject2, paramClockInfo2, "当前时段您已用过餐");
                        break;
                    case 1:
                        ((MainContract.IMainView) getView()).playSound(2131492866);
                        ((MainContract.IMainView) getView()).showSuccessPopWindow((String) localObject2, paramClockInfo2, "祝您用餐愉快");
                        break;
                    case 0:
                        ((MainContract.IMainView) getView()).showSuccessPopWindow((String) localObject2, paramClockInfo2, "刷脸成功");
                        playVoice();
                        ((MainContract.IMainView) getView()).addEatNumber();
                }
            endTime = System.currentTimeMillis();
            ((MainContract.IMainView) getView()).restartFaceCheck();
            isRequest = false;
        }
    }

    private void playVoice() {
        switch (TimeUtil.getEatTime()) {
            default:
                return;
            case 3:
                ((MainContract.IMainView) getView()).playSound(2131492867);
                return;
            case 2:
                ((MainContract.IMainView) getView()).playSound(2131492868);
                return;
            case 1:
                ((MainContract.IMainView) getView()).playSound(2131492868);
                return;
            case 0:
        }
        ((MainContract.IMainView) getView()).playSound(2131492868);
    }

    private void requestService() {
        this.mModel.getServiceState().subscribe(new Subscriber() {
            public void onCompleted() {
            }

            public void onError(Throwable paramThrowable) {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("请求服务器失败:");
                localStringBuilder.append(paramThrowable.getLocalizedMessage());
                Log.e("ServiceState", localStringBuilder.toString());
                if (MainPresenter.this.getView() != null) {
                    if (NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) {
                        ((MainContract.IMainView) MainPresenter.this.getView()).clearFaceRect();
                        ((MainContract.IMainView) MainPresenter.this.getView()).showInterfaceError();
                        return;
                    }
                    ((MainContract.IMainView) MainPresenter.this.getView()).clearFaceRect();
                    ((MainContract.IMainView) MainPresenter.this.getView()).dismissCard();
                    ((MainContract.IMainView) MainPresenter.this.getView()).dismissScanSuccess();
                    ((MainContract.IMainView) MainPresenter.this.getView()).showNoNetwork();
                }
            }

            public void onNext(ServiceState paramServiceState) {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("服务器接口状态：");
                localStringBuilder.append(paramServiceState.getMsg());
                Log.d("ServiceState", localStringBuilder.toString());
                if (MainPresenter.this.getView() != null) {
                    if (((MainContract.IMainView) MainPresenter.this.getView()).getNoNetworkState()) {
                        ((MainContract.IMainView) MainPresenter.this.getView()).hiddenNoNetwork();
                        MainPresenter.this.checkTime();
                        return;
                    }
                    if (paramServiceState.getCode() == 0) {
                        if (((MainContract.IMainView) MainPresenter.this.getView()).getInterfaceState()) {
                            ((MainContract.IMainView) MainPresenter.this.getView()).hiddenInterfaceError();
                            MainPresenter.this.checkTime();
                            return;
                        }
                    } else {
                        ((MainContract.IMainView) MainPresenter.this.getView()).clearFaceRect();
                        ((MainContract.IMainView) MainPresenter.this.getView()).showInterfaceError();
                    }
                }
            }
        });
    }

    private void setAlarm(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3, PendingIntent paramPendingIntent) {
        paramActivity = (AlarmManager) paramActivity.getSystemService("alarm");
        long l3 = System.currentTimeMillis();
        long l4 = SystemClock.elapsedRealtime();
        Object localObject = Calendar.getInstance();
        ((Calendar) localObject).set(11, paramInt1);
        ((Calendar) localObject).set(12, paramInt2);
        ((Calendar) localObject).set(13, paramInt3);
        ((Calendar) localObject).set(14, 0);
        long l2 = ((Calendar) localObject).getTimeInMillis();
        long l1 = l2;
        if (l3 > l2) {
            ((Calendar) localObject).add(6, 1);
            l1 = ((Calendar) localObject).getTimeInMillis();
        }
        l1 = l4 + (l1 - l3);
        localObject = new StringBuilder();
        ((StringBuilder) localObject).append("启动时间：");
        ((StringBuilder) localObject).append(l1);
        Log.d("AlarmTest", ((StringBuilder) localObject).toString());
        if (Build.VERSION.SDK_INT >= 19) {
            paramActivity.setWindow(2, l1, 86400000L, paramPendingIntent);
            return;
        }
        paramActivity.setRepeating(2, l1, 86400000L, paramPendingIntent);
    }

    public void checkNetwork(Context paramContext) {
        if (!NetworkUtil.isNetworkAvalible(paramContext)) {
            ((MainContract.IMainView) getView()).showNoNetwork();
            ((MainContract.IMainView) getView()).stopFaceCheck();
            MainActivity.isFirstIn = false;
        }
    }

    public void checkServiceLive() {
        new Thread() {
            public void run() {
                while (MainPresenter.checkInterface)
                    try {
                        Thread.sleep(3000L);
                        MainPresenter.this.requestService();
                    } catch (InterruptedException localInterruptedException) {
                        localInterruptedException.printStackTrace();
                    }
            }
        }
                .start();
    }

    public void checkTime() {
        long l1 = TimeUtil.parseTime("07:30:00");
        long l2 = TimeUtil.parseTime("09:00:00");
        long l3 = TimeUtil.parseTime("12:00:00");
        long l4 = TimeUtil.parseTime("13:30:00");
        long l5 = TimeUtil.parseTime("18:00:00");
        long l6 = TimeUtil.parseTime("19:00:00");
        long l7 = TimeUtil.getTimeLong();
        int m = 0;
        int i;
        if ((l7 >= l1) && (l7 <= l2))
            i = 1;
        else
            i = 0;
        int j;
        if ((l7 >= l3) && (l7 <= l4))
            j = 1;
        else
            j = 0;
        int k = m;
        if (l7 >= l5) {
            k = m;
            if (l7 <= l6)
                k = 1;
        }
        if ((NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && ((i != 0) || (j != 0) || (k != 0))) {
            ((MainContract.IMainView) getView()).hiddenCamerapreview();
            ((MainContract.IMainView) getView()).showFaceDefault();
            ((MainContract.IMainView) getView()).restartFaceCheck();
            Log.d("launch", "在用餐时间段打开");
            return;
        }
        if (NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) {
            ((MainContract.IMainView) getView()).hiddenCamerapreview();
            ((MainContract.IMainView) getView()).showTouchDefault();
            ((MainContract.IMainView) getView()).stopFaceCheck();
        }
    }

    public void connectSocket() {
        WebSocketUtils.initWebSocket().subscribe(new WebSocketSubscriber() {
            public void onError(Throwable paramThrowable) {
                super.onError(paramThrowable);
                Log.e("WebSocket", paramThrowable.getLocalizedMessage());
            }

            public void onMessage(@NonNull String paramString) {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("webcocket mesage:");
                localStringBuilder.append(paramString);
                Log.d("WebSocket", localStringBuilder.toString());
                try {
                    int i = new JSONObject(paramString).getInt("data");
                    SpUtil.putInt("eat_number", i);
                    if (MainPresenter.this.getView() != null)
                        ((MainContract.IMainView) MainPresenter.this.getView()).setAllEatNumber(i);
                    return;
                } catch (JSONException paramString) {
                    paramString.printStackTrace();
                }
            }

            public void onMessage(@NonNull ByteString paramByteString) {
            }

            public void onOpen(@NonNull WebSocket paramWebSocket) {
                Log.d("WebSocket", "onOpen");
            }
        });
    }

    public void requestFaceClock(String paramString) {
        if (!isRequest) {
            Log.d("http", "发送请求");
            isRequest = true;
            this.mModel.getFaceInfo(paramString).subscribe(new Subscriber() {
                public void onCompleted() {
                }

                public void onError(Throwable paramThrowable) {
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("获取人脸信息error :");
                    localStringBuilder.append(paramThrowable.getLocalizedMessage());
                    Log.e("http", localStringBuilder.toString());
                    if ((NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null) && (!((MainContract.IMainView) MainPresenter.this.getView()).getInterfaceState()) && (!MainActivity.lostFace))
                        MainPresenter.this.networkError();
                    MainPresenter.endTime = System.currentTimeMillis();
                    if ((NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null))
                        ((MainContract.IMainView) MainPresenter.this.getView()).restartFaceCheck();
                    MainPresenter.isRequest = false;
                }

                public void onNext(ClockInfo2 paramClockInfo2) {
                    StringBuilder localStringBuilder = new StringBuilder();
                    localStringBuilder.append("onNext: ");
                    localStringBuilder.append(paramClockInfo2.getMsg());
                    Log.d("http", localStringBuilder.toString());
                    MainPresenter.access$102(MainPresenter.this, 0);
                    if (MainPresenter.this.getView() != null)
                        ((MainContract.IMainView) MainPresenter.this.getView()).clearFaceRect();
                    if ((NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null) && (!((MainContract.IMainView) MainPresenter.this.getView()).getInterfaceState()) && (!MainActivity.lostFace)) {
                        MainPresenter.this.parseJson(paramClockInfo2);
                    } else if (NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) {
                        MainPresenter.endTime = System.currentTimeMillis() - 1700L;
                        if (MainPresenter.this.getView() != null)
                            ((MainContract.IMainView) MainPresenter.this.getView()).restartFaceCheck();
                        MainPresenter.isRequest = false;
                    }
                    if ((NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null) && (((MainContract.IMainView) MainPresenter.this.getView()).getInterfaceState()))
                        ((MainContract.IMainView) MainPresenter.this.getView()).hiddenInterfaceError();
                }
            });
        }
    }

    public void setLaunchAlarm(Activity paramActivity) {
        Intent localIntent = new Intent(paramActivity, DeleteDatabaseReceiver.class);
        localIntent.setAction("cn.jdjr.DELETE_DATABASE");
        setAlarm(paramActivity, 23, 59, 59, PendingIntent.getBroadcast(paramActivity, Constant.DeleteDatabaseOneDay, localIntent, 134217728));
        new Thread() {
            public void run() {
                while (MainPresenter.checkTime)
                    try {
                        Object localObject = TimeUtil.getTimeString();
                        StringBuilder localStringBuilder;
                        if (((((String) localObject).equals("07:30:00")) || (((String) localObject).equals("12:00:00")) || (((String) localObject).equals("18:00:00"))) && (NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null)) {
                            localStringBuilder = new StringBuilder();
                            localStringBuilder.append("现在时间：");
                            localStringBuilder.append((String) localObject);
                            localStringBuilder.append(" 用餐开始");
                            Log.d("checkTime", localStringBuilder.toString());
                            ((MainContract.IMainView) MainPresenter.this.getView()).showCamerapreview();
                            ((MainContract.IMainView) MainPresenter.this.getView()).restartFaceCheck();
                            ((MainContract.IMainView) MainPresenter.this.getView()).updateEatNumber();
                        }
                        if (((((String) localObject).equals("09:00:00")) || (((String) localObject).equals("13:30:00")) || (((String) localObject).equals("19:00:00"))) && (NetworkUtil.isNetworkAvalible(MyApplication.getAppContext())) && (MainPresenter.this.getView() != null)) {
                            localStringBuilder = new StringBuilder();
                            localStringBuilder.append("现在时间：");
                            localStringBuilder.append((String) localObject);
                            localStringBuilder.append(" 用餐结束");
                            Log.d("checkTime", localStringBuilder.toString());
                            ((MainContract.IMainView) MainPresenter.this.getView()).stopFaceCheck();
                            ((MainContract.IMainView) MainPresenter.this.getView()).dismissCard();
                            ((MainContract.IMainView) MainPresenter.this.getView()).dismissScanSuccess();
                            ((MainContract.IMainView) MainPresenter.this.getView()).clearFaceRect();
                            ((MainContract.IMainView) MainPresenter.this.getView()).hiddenCamerapreview();
                            ((MainContract.IMainView) MainPresenter.this.getView()).showTouchDefault();
                            ((MainContract.IMainView) MainPresenter.this.getView()).updateEatNumber();
                        }
                        if (((String) localObject).equals("23:59:59")) {
                            localObject = new StringBuilder();
                            ((StringBuilder) localObject).append("现在时间：");
                            ((StringBuilder) localObject).append(TimeUtil.getDayTimeString());
                            ((StringBuilder) localObject).append(" 删除数据库");
                            Log.d("checkTime", ((StringBuilder) localObject).toString());
                            FaceDatabase.getInstance().deleteDay(TimeUtil.getDayString());
                        }
                        Thread.sleep(1000L);
                    } catch (InterruptedException localInterruptedException) {
                        localInterruptedException.printStackTrace();
                    }
            }
        }
                .start();
    }
}