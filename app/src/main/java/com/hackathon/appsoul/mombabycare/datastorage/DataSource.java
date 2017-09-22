package com.hackathon.appsoul.mombabycare.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hackathon.appsoul.mombabycare.model.Event;

import java.util.ArrayList;

/**
 * Created by A. A. M. Sharif on 27-Apr-16.
 */
public class DataSource {
    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Event eventModel;

    public DataSource(Context context) {
        helper = new DataBaseHelper(context);
    }

    private void open() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean insert(Event event) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_TYPE, event.getType().name());
        cv.put(DataBaseHelper.COL_DATE, event.getDate().toString());
        cv.put(DataBaseHelper.COL_TEXT, event.getText());
        cv.put(DataBaseHelper.COL_IMAGE_PATH, event.getPhotoFile());
        cv.put(DataBaseHelper.COL_AUDIO_PATH, event.getAudioFile());

        long inserted = database.insert(DataBaseHelper.TABLE_NAME, null, cv);
        this.close();

        if (inserted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(int id, Event event) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_TYPE, event.getType().name());
        cv.put(DataBaseHelper.COL_DATE, event.getDate().toString());
        cv.put(DataBaseHelper.COL_TEXT, event.getText());
        cv.put(DataBaseHelper.COL_IMAGE_PATH, event.getPhotoFile());
        cv.put(DataBaseHelper.COL_AUDIO_PATH, event.getAudioFile());

        int updated = database.update(DataBaseHelper.TABLE_NAME, cv, DataBaseHelper.COL_ID + "= " + id, null);
        this.close();

        if (updated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean delete(int id) {
        this.open();

        int deleted=   database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper.COL_ID + "= " + id, null);

        this.close();

        if (deleted > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Event> getAllEvents(){
        this.open();
        ArrayList<Event> eventList=new ArrayList<>();

        Cursor cursor=database.query(DataBaseHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor!=null && cursor.getCount()>0){

            cursor.moveToFirst();

            for (int i=0; i<cursor.getCount(); i++){

                int id=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String type=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TYPE));
                String date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_DATE));
                String text=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TEXT));
                String imagePath=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_IMAGE_PATH));
                String audioPath=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_AUDIO_PATH));

                eventModel = new Event(String.valueOf(id) , type, date, text, imagePath, audioPath);
                eventList.add(eventModel);
                cursor.moveToNext();
            }
        }
        this.close();
        return eventList;


    }
    public Event getEvent(int id){
        this.open();

        Cursor cursor=database.query(DataBaseHelper.TABLE_NAME,new String []{DataBaseHelper.COL_ID,DataBaseHelper.COL_TYPE,DataBaseHelper.COL_DATE,DataBaseHelper.COL_TEXT,DataBaseHelper.COL_IMAGE_PATH,DataBaseHelper.COL_AUDIO_PATH},DataBaseHelper.COL_ID+"= "+id,null,null,null,null);
        cursor.moveToFirst();

        String type=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TYPE));
        String date=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_DATE));
        String text=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TEXT));
        String imagePath=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_IMAGE_PATH));
        String audioPath=cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_AUDIO_PATH));

        eventModel = new Event(String.valueOf(id) , type, date, text, imagePath, audioPath);
        this.close();
        return eventModel;
    }
}
