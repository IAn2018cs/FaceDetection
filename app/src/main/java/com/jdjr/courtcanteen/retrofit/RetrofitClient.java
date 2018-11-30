package com.jdjr.courtcanteen.retrofit;

import android.util.Log;

import com.jdjr.courtcanteen.utils.URLs;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Emitter;
import rx.Emitter.BackpressureMode;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class RetrofitClient {
    private static final ServiceApi mServiceApi;

    static {
        OkHttpClient localOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            public void log(String paramString) {
                Log.e("TAG", paramString);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        mServiceApi = (ServiceApi) new Retrofit.Builder().baseUrl(URLs.HOST_NAME).addConverterFactory(GsonConverterFactory.create()).client(localOkHttpClient).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build().create(ServiceApi.class);
    }

    private static <T> Observable<T> createObservable(T paramT) {
        return Observable.create(new Action1(paramT) {
                                     public void call(Emitter<T> paramEmitter) {
                                         paramEmitter.onNext(this.val$data);
                                         paramEmitter.onCompleted();
                                     }
                                 }
                , Emitter.BackpressureMode.NONE);
    }

    public static ServiceApi getServiceApi() {
        return mServiceApi;
    }

    public static <T> Observable.Transformer<Result<T>, T> transformer() {
        return new Observable.Transformer() {
            public Observable<T> call(Observable<Result<T>> paramObservable) {
                return paramObservable.flatMap(new Func1() {
                    public Observable<T> call(Result<T> paramResult) {
                        if (paramResult.isOk())
                            return RetrofitClient.access$000(paramResult.data);
                        return Observable.error(new ErrorHandle.ServiceError("", paramResult.getMsg()));
                    }
                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
