package com.jdjr.courtcanteen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class FaceDatabaseHelper extends SQLiteOpenHelper {
    public static final String FACE_TABLE = "create table FaceClock (id integer primary key autoincrement,employeeId text,name text,imageURL text,position text,eatTime integer,day text,createdTime text,modifiedTime text)";

    public FaceDatabaseHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt) {
        super(paramContext, paramString, paramCursorFactory, paramInt);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table FaceClock (id integer primary key autoincrement,employeeId text,name text,imageURL text,position text,eatTime integer,day text,createdTime text,modifiedTime text)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    }
}