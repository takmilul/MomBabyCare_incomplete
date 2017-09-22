package com.hackathon.appsoul.mombabycare.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by A. A. M. Sharif on 27-Apr-16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME="DOCTOR_HELP";
    static  final int DATABASE_VERSION=1;

    static final String TABLE_NAME="EVENT_TABLE";


    static final String COL_ID="COL_ID";
    static final String COL_TYPE="TYPE";
    static final String COL_DATE="DATE";
    static final String COL_TEXT="TEXT";
    static final String COL_IMAGE_PATH="IMAGE_PATH";
    static final String COL_AUDIO_PATH="AUDIO_PATH";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME +"(" +COL_ID +" INTEGER PRIMARY KEY, "+
            COL_TYPE +" TEXT," + COL_DATE +" TEXT," + COL_TEXT +" TEXT," + COL_IMAGE_PATH +" TEXT," + COL_AUDIO_PATH+" TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME);
    }
}
