package com.jdjr.courtcanteen.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jdjr.courtcanteen.MyApplication;
import com.jdjr.courtcanteen.bean.ClockInfo;
import com.jdjr.courtcanteen.utils.TimeUtil;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class FaceDatabase {
    public static final String DB_NAME = "face_info";
    public static final int VERSION = 1;
    private static FaceDatabase myDatabase;
    private final SQLiteDatabase db;

    private FaceDatabase(Context paramContext) {
        this.db = new FaceDatabaseHelper(paramContext, "face_info", null, 1).getWritableDatabase();
    }

    public static FaceDatabase getInstance() {
        monitorenter;
        try {
            if (myDatabase == null)
                myDatabase = new FaceDatabase(MyApplication.getAppContext());
            FaceDatabase localFaceDatabase = myDatabase;
            return localFaceDatabase;
        } finally {
            monitorexit;
        }
        throw localObject;
    }

    public void addFaceClockInfo(ClockInfo paramClockInfo) {
        if (paramClockInfo != null) {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("employeeId", paramClockInfo.getEmployeeId());
            localContentValues.put("name", paramClockInfo.getName());
            localContentValues.put("imageURL", paramClockInfo.getImageURL());
            localContentValues.put("position", paramClockInfo.getPosition());
            localContentValues.put("createdTime", TimeUtil.getDayTimeString());
            localContentValues.put("modifiedTime", TimeUtil.getDayTimeString());
            localContentValues.put("eatTime", Integer.valueOf(TimeUtil.getEatTime()));
            localContentValues.put("day", TimeUtil.getDayString());
            this.db.insert("FaceClock", null, localContentValues);
        }
    }

    public int checkClock(ClockInfo paramClockInfo) {
        int j = TimeUtil.getEatTime();
        Object localObject1 = new StringBuilder();
        ((StringBuilder) localObject1).append("eatTimeCode:");
        ((StringBuilder) localObject1).append(j);
        Log.d("checkClock", ((StringBuilder) localObject1).toString());
        int i = 3;
        if (j != 3) {
            localObject1 = this.db;
            String str1 = paramClockInfo.getEmployeeId();
            i = 0;
            String str2 = TimeUtil.getDayString();
            Object localObject2 = new StringBuilder();
            ((StringBuilder) localObject2).append(j);
            ((StringBuilder) localObject2).append("");
            localObject2 = ((StringBuilder) localObject2).toString();
            j = 2;
            localObject1 = ((SQLiteDatabase) localObject1).query("FaceClock", null, "employeeId=? and day=? and eatTime=?", new String[]{str1, str2, localObject2}, null, null, null);
            if (((Cursor) localObject1).moveToNext()) {
                long l = ((Cursor) localObject1).getLong(((Cursor) localObject1).getColumnIndex("id"));
                paramClockInfo = ((Cursor) localObject1).getString(((Cursor) localObject1).getColumnIndex("createdTime"));
                if (TimeUtil.getDayTimeLong() - TimeUtil.parseDayTime(paramClockInfo) <= 1800000L) {
                    updateTime(l);
                    i = 1;
                } else {
                    i = j;
                }
            } else {
                addFaceClockInfo(paramClockInfo);
            }
            ((Cursor) localObject1).close();
        }
        return i;
    }

    public void deleteDay(String paramString) {
        this.db.delete("FaceClock", "day = ?", new String[]{paramString});
    }

    public int getEatNumber() {
        int i = 0;
        Object localObject = this.db;
        String str = TimeUtil.getDayString();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(TimeUtil.getEatTime());
        localStringBuilder.append("");
        localObject = ((SQLiteDatabase) localObject).query("FaceClock", null, "day=? and eatTime=?", new String[]{str, localStringBuilder.toString()}, null, null, null);
        while (((Cursor) localObject).moveToNext())
            i += 1;
        ((Cursor) localObject).close();
        return i;
    }

    public void updateTime(long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("modifiedTime", TimeUtil.getDayTimeString());
        this.db.update("FaceClock", localContentValues, "id = ?", new String[]{String.valueOf(paramLong)});
    }
}