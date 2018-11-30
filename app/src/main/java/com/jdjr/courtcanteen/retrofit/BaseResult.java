package com.jdjr.courtcanteen.retrofit;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class BaseResult {
    String bol;
    String msg;

    public String getCode() {
        return this.bol;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isOk() {
        return "true".equals(this.bol);
    }
}