package com.example.mltrial;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name) {
        Calendar calobj = Calendar.getInstance();

        String [] he=calobj.getTime().toString().split(" ");
        String formattedTime =he[0]+" "+he[1]+" "+he[2]+" "+he[3];
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NUMBER, name);
        contentValue.put(DatabaseHelper.DATE, formattedTime);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NUMBER, DatabaseHelper.DATE };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, "_ID"+ " DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
    public void deleteAll(){
        database.delete(DatabaseHelper.TABLE_NAME, null, null);
        System.out.println("Table cleared");
    }
}