package com.jdjr.courtcanteen.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class CrashUtil
        implements Thread.UncaughtExceptionHandler {
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
    public static final boolean DEBUG = true;
    private static CrashUtil INSTANCE;
    private static final String STACK_TRACE = "STACK_TRACE";
    public static final String TAG = "CrashUtil";
    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private static Object syncRoot = new Object();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Properties mDeviceCrashInfo = new Properties();

    private String[] getCrashReportFiles(Context paramContext) {
        return paramContext.getFilesDir().list(new FilenameFilter() {
            public boolean accept(File paramFile, String paramString) {
                return paramString.endsWith(".cr");
            }
        });
    }

    public static CrashUtil getInstance() {
        if (INSTANCE == null)
            synchronized (syncRoot) {
                if (INSTANCE == null)
                    INSTANCE = new CrashUtil();
            }
        return INSTANCE;
    }

    private boolean handleException(Throwable paramThrowable) {
        if (paramThrowable == null) {
            Log.w("CrashUtil", "handleException --- ex==null");
            return true;
        }
        String str = paramThrowable.getLocalizedMessage();
        if (str == null)
            return false;
        new Thread(str) {
            public void run() {
                Looper.prepare();
                Object localObject = new StringBuilder();
                ((StringBuilder) localObject).append("异常信息->");
                ((StringBuilder) localObject).append(this.val$msg);
                Log.d("CrashUtil", ((StringBuilder) localObject).toString());
                localObject = CrashUtil.this.mContext;
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("程序出错，即将退出:\r\n");
                localStringBuilder.append(this.val$msg);
                localObject = Toast.makeText((Context) localObject, localStringBuilder.toString(), 1);
                ((Toast) localObject).setGravity(17, 0, 0);
                ((Toast) localObject).show();
                Looper.loop();
            }
        }
                .start();
        collectCrashDeviceInfo(this.mContext);
        saveCrashInfoToFile(paramThrowable);
        return true;
    }

    private void postReport(File paramFile) {
    }

    private String saveCrashInfoToFile(Throwable paramThrowable) {
        StringWriter localStringWriter = new StringWriter();
        PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
        paramThrowable.printStackTrace(localPrintWriter);
        for (Object localObject = paramThrowable.getCause(); localObject != null; localObject = ((Throwable) localObject).getCause())
            ((Throwable) localObject).printStackTrace(localPrintWriter);
        localObject = localStringWriter.toString();
        localPrintWriter.close();
        this.mDeviceCrashInfo.put("EXEPTION", paramThrowable.getLocalizedMessage());
        this.mDeviceCrashInfo.put("STACK_TRACE", localObject);
        try {
            paramThrowable = new Time("GMT+8");
            paramThrowable.setToNow();
            int i = paramThrowable.year;
            int j = paramThrowable.month;
            int k = paramThrowable.monthDay;
            int m = paramThrowable.hour;
            int n = paramThrowable.minute;
            int i1 = paramThrowable.second;
            paramThrowable = new StringBuilder();
            paramThrowable.append("crash-");
            paramThrowable.append(i * 10000 + j * 100 + k);
            paramThrowable.append("-");
            paramThrowable.append(m * 10000 + n * 100 + i1);
            paramThrowable.append(".cr");
            paramThrowable = paramThrowable.toString();
            localObject = this.mContext.openFileOutput(paramThrowable, 0);
            this.mDeviceCrashInfo.store((OutputStream) localObject, "");
            ((FileOutputStream) localObject).flush();
            ((FileOutputStream) localObject).close();
            return paramThrowable;
        } catch (Exception paramThrowable) {
            Log.e("CrashUtil", "an error occured while writing report file...", paramThrowable);
        }
        return (String) null;
    }

    private void sendCrashReportsToServer(Context paramContext) {
        Object localObject1 = getCrashReportFiles(paramContext);
        if ((localObject1 != null) && (localObject1.length > 0)) {
            Object localObject2 = new TreeSet();
            ((TreeSet) localObject2).addAll(Arrays.asList(localObject1));
            localObject1 = ((TreeSet) localObject2).iterator();
            while (((Iterator) localObject1).hasNext()) {
                localObject2 = (String) ((Iterator) localObject1).next();
                localObject2 = new File(paramContext.getFilesDir(), (String) localObject2);
                postReport((File) localObject2);
                ((File) localObject2).delete();
            }
        }
    }

    public void collectCrashDeviceInfo(Context paramContext) {
        PackageInfo localPackageInfo;
        Object localObject;
        try {
            localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
            if (localPackageInfo != null) {
                localObject = this.mDeviceCrashInfo;
                if (localPackageInfo.versionName == null)
                    paramContext = "not set";
                else
                    paramContext = localPackageInfo.versionName;
                ((Properties) localObject).put("versionName", paramContext);
                paramContext = this.mDeviceCrashInfo;
                localObject = new StringBuilder();
                ((StringBuilder) localObject).append("");
                ((StringBuilder) localObject).append(localPackageInfo.versionCode);
                paramContext.put("versionCode", ((StringBuilder) localObject).toString());
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException paramContext) {
            Log.e("CrashUtil", "Error while collect package info", paramContext);
        }
        paramContext = Build.class.getDeclaredFields();
        int j = paramContext.length;
        int i = 0;
        while (i < j) {
            localPackageInfo = paramContext[i];
            try {
                localPackageInfo.setAccessible(true);
                localObject = this.mDeviceCrashInfo;
                String str = localPackageInfo.getName();
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("");
                localStringBuilder.append(localPackageInfo.get(null));
                ((Properties) localObject).put(str, localStringBuilder.toString());
                localObject = new StringBuilder();
                ((StringBuilder) localObject).append(localPackageInfo.getName());
                ((StringBuilder) localObject).append(" : ");
                ((StringBuilder) localObject).append(localPackageInfo.get(null));
                Log.d("CrashUtil", ((StringBuilder) localObject).toString());
            } catch (Exception localException) {
                Log.e("CrashUtil", "Error while collect crash info", localException);
            }
            i += 1;
        }
    }

    public void init(Context paramContext) {
        this.mContext = paramContext;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(this.mContext);
    }

    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null)) {
            this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
            return;
        }
        try {
            Thread.sleep(5000L);
        } catch (java.lang.InterruptedException paramThread) {
            Log.e("CrashUtil", "Error : ", paramThread);
        }
        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}