package com.jdjr.courtcanteen.retrofit;

import rx.Subscriber;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    public void onCompleted() {
    }

    protected abstract void onError(String paramString1, String paramString2);

    public void onError(Throwable paramThrowable) {
        paramThrowable.printStackTrace();
        if ((paramThrowable instanceof ErrorHandle.ServiceError)) {
            onError("", ((ErrorHandle.ServiceError) paramThrowable).getMessage());
            return;
        }
        onError("", "未知异常");
    }
}
