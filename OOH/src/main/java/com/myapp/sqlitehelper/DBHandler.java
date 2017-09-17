package com.myapp.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.drm.DrmInfoStatus;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 17-09-2017 19:18.
 */

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    private static final int DB_VER = 1;
    private static final String DB_NAME = "yohoads";
    private static final String TABLE_CAMPAIGN = "campaign";
    private static final String KEY_UID = "uid";
    private static final String KEY_URL = "url";
    private static final String KEY_START_TIME_STAMP = "start_time";
    private static final String KEY_END_TIME_STAMP = "end_time";
    private static final String KEY_DELAY = "delay";
    private static final String LOG_TAG = "DBHandler";
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CAMPAIGN_TABLE = "CREATE TABLE " + TABLE_CAMPAIGN + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_UID + " TEXT NOT NULL, " +
//        String CREATE_CAMPAIGN_TABLE = "CREATE TABLE  " + TABLE_CAMPAIGN + "(" +
//                KEY_UID + " TEXT PRIMARY KEY NOT NULL, " +
                KEY_URL + " TEXT, " +
                KEY_START_TIME_STAMP + " TEXT, " +
                KEY_END_TIME_STAMP + " TEXT, " +
                KEY_DELAY + " INT " + ")";
        Log.e(LOG_TAG, CREATE_CAMPAIGN_TABLE);
        db.execSQL(CREATE_CAMPAIGN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(LOG_TAG, "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPAIGN);
        onCreate(db);
    }

    public void addRecord(DSInterface dsi) {
        Log.e(LOG_TAG, "onUpgrade");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_UID, dsi.getUID());
        values.put(KEY_URL, dsi.getURL());
        values.put(KEY_START_TIME_STAMP, dsi.getStartTime());
        values.put(KEY_END_TIME_STAMP, dsi.getEndTime());
        values.put(KEY_DELAY, dsi.getDelay());
        db.insert(TABLE_CAMPAIGN, null, values);
    }

    public DSInterface getRecord(String uid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_CAMPAIGN, new String[]{KEY_UID,
                        KEY_URL, KEY_START_TIME_STAMP, KEY_END_TIME_STAMP, KEY_DELAY}, KEY_UID + "=?",
                new String[]{String.valueOf(uid)}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Log.e(LOG_TAG, cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4)+" "+ cursor.getString(5));
        DSInterface dis = new DSInterface(cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        db.close();
        return dis;
    }

    public List<DSInterface> getAllRecord() {
        List<DSInterface> campaignList = new ArrayList<DSInterface>();
        String selectQuery = "SELECT DISTINCT "+ KEY_UID+", "+KEY_URL+", "+KEY_START_TIME_STAMP+", "+KEY_END_TIME_STAMP+", "+KEY_DELAY+" FROM " + TABLE_CAMPAIGN ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DSInterface dsi = new DSInterface();
                dsi.setUID(cursor.getString(0));
                dsi.setURL(cursor.getString(1));
                dsi.setStartTime(cursor.getString(2));
                dsi.setEndTime(cursor.getString(3));
                dsi.setDelay(cursor.getString(4));

                campaignList.add(dsi);
            } while (cursor.moveToNext());
        }
        return campaignList;
    }

    public void delRecord() {

    }

    public void delAllRecord() {

    }

    public void updateRecord() {

    }
}
