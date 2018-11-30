package com.jdjr.courtcanteen.utils;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */

public class Constant {
    public static int AlarmBreakfastEnd = 0;
    public static int AlarmBreakfastStart = 0;
    public static int AlarmDinnerEnd = 0;
    public static int AlarmDinnerStart = 0;
    public static int AlarmLunchEnd = 0;
    public static int AlarmLunchStart = 0;
    public static int DeleteDatabaseOneDay = 0;
    public static final String EAT_NUMBER = "eat_number";
    public static String PublicKey;
    public static final String SHAREDPREFERENCES_NAME = "config";
    public static String regCode = "iHpw3q+MeosKUsUYUTazTbYwR9wPADSiOKQtq7HrOX9jtTup5aEh59B+meRCd+53VxZkmS46JfrVbe6IvNNNYHmHe9pc21BwDgCHittgcRG3DOUtbdc2XhMiI+s3RKVamIGVW4i+wlavS/6ASz7+J7Rc19phT9sggIbRizHa1v4=";

    static {
        PublicKey = "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZC03UVnGfbwX7ryo/+LBV3MP9\nSr9BpziVAVAyPgyCmtPjIgAt4CMxKW83+z0u8RDNA0sI8S4ACIpL7k2vXeECEcv+\nXo/MiKvWoodM8Vya8ZM6tQqwBuVbqBTavTrI2/Z1OWuRf3RPEDA+aGQECWEWpi1K\ns/h9ftKF3lgRGW8BWwIDAQAB\n-----END PUBLIC KEY-----\n";
        AlarmBreakfastStart = 0;
        AlarmLunchStart = 1;
        AlarmDinnerStart = 2;
        AlarmBreakfastEnd = 3;
        AlarmLunchEnd = 4;
        AlarmDinnerEnd = 5;
        DeleteDatabaseOneDay = 6;
    }
}
