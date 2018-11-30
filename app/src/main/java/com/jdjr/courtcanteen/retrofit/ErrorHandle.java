package com.jdjr.courtcanteen.retrofit;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class ErrorHandle {
    public static class ServiceError extends Throwable {
        String errorCode;

        public ServiceError(String paramString1, String paramString2) {
            super();
            this.errorCode = paramString1;
        }
    }
}
