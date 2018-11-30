package com.jdjr.courtcanteen.main;

import com.jdjr.courtcanteen.bean.ClockInfo2;
import com.jdjr.courtcanteen.bean.ServiceState;
import com.jdjr.courtcanteen.retrofit.RetrofitClient;
import com.jdjr.courtcanteen.retrofit.ServiceApi;
import com.jdjr.courtcanteen.utils.URLs;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class MainModel
        implements MainContract.IMainModel {
    public Observable<ClockInfo2> getFaceInfo(String paramString) {
        return RetrofitClient.getServiceApi().getFaceInfo(URLs.DEVICE_ID, paramString).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ServiceState> getServiceState() {
        return RetrofitClient.getServiceApi().getServiceState(URLs.DEVICE_ID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
