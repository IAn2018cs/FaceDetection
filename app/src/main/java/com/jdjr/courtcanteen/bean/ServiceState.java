package com.jdjr.courtcanteen.bean;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class ServiceState {
    private int code;
    private int elapsed;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public int getElapsed() {
        return this.elapsed;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(int paramInt) {
        this.code = paramInt;
    }

    public void setElapsed(int paramInt) {
        this.elapsed = paramInt;
    }

    public void setMsg(String paramString) {
        this.msg = paramString;
    }
}