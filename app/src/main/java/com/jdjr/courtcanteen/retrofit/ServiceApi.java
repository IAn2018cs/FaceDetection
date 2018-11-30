package com.jdjr.courtcanteen.retrofit;


import com.jdjr.courtcanteen.bean.ClockInfo2;
import com.jdjr.courtcanteen.bean.ServiceState;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public abstract interface ServiceApi {
    @FormUrlEncoded
    @POST("api/dinner")
    public abstract Observable<ClockInfo2> getFaceInfo(@Field("deviceId") String paramString1, @Field("imageBase64") String paramString2);

    @FormUrlEncoded
    @POST("api/device/heartbeat")
    public abstract Observable<ServiceState> getServiceState(@Field("deviceId") String paramString);
}