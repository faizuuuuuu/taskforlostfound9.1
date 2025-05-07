package com.example.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LostFoundDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "adverts";

    // Columns
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_DATE = "date";
    private static final String COL_LOCATION = "location";
    private static final String COL_TYPE = "type"; // Lost or Found

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_PHONE + " TEXT," +
                COL_DESCRIPTION + " TEXT," +
                COL_DATE + " TEXT," +
                COL_LOCATION + " TEXT," +
                COL_TYPE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrade if version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert advert
    public boolean insertAdvert(String name, String phone, String description, String date, String location, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_DATE, date);
        values.put(COL_LOCATION, location);
        values.put(COL_TYPE, type);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Get all adverts
    public List<HashMap<String, String>> getAllAdverts() {
        List<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(cursor.getColumnIndexOrThrow(COL_ID)));
                map.put("name", cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                map.put("phone", cursor.getString(cursor.getColumnIndexOrThrow(COL_PHONE)));
                map.put("description", cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
                map.put("date", cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                map.put("location", cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)));
                map.put("type", cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE)));
                list.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // Delete advert by ID
    public boolean deleteAdvert(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{id});
        db.close();
        return rowsDeleted > 0;
    }
}
