package com.jdjr.courtcanteen.utils;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class URLs {
    public static String DEVICE_ID;
    public static String HOST = "192.168.2.244:8080";
    public static String HOST_NAME;
    public static String NUMBER_CHANGE_WS;

    static {
        DEVICE_ID = "1270004117635073";
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("http://");
        localStringBuilder.append(HOST);
        localStringBuilder.append("/");
        HOST_NAME = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("ws://");
        localStringBuilder.append(HOST);
        localStringBuilder.append("/api/websocket/employee");
        NUMBER_CHANGE_WS = localStringBuilder.toString();
    }
}
