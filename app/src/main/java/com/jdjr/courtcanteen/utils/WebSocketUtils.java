package com.jdjr.courtcanteen.utils;


import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class WebSocketUtils {
    public static Observable<WebSocketInfo> initWebSocket() {
        OkHttpClient localOkHttpClient = new OkHttpClient().newBuilder().pingInterval(5L, TimeUnit.SECONDS).retryOnConnectionFailure(true).connectTimeout(5L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).writeTimeout(5L, TimeUnit.SECONDS).build();
        RxWebSocketUtil.getInstance().setClient(localOkHttpClient);
        return RxWebSocketUtil.getInstance().getWebSocketInfo(URLs.NUMBER_CHANGE_WS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}
