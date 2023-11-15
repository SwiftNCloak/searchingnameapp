package com.mj_bonifacio.searchingnameapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;

public class SQLiteDatabase extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "records.db";
    static final String PROFILE = "profile", PROFID = "prof_id", PROFFNAME = "prof_fname", PROFMNAME = "prof_mname", PROFLNAME = "prof_lname";
    static ArrayList<String> Items;
    static ArrayList<Integer> ItemsId;
    ContentValues VALUES;
    Cursor rs;

    public SQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase conn) {
        conn.execSQL("CREATE TABLE " + PROFILE + "(" + PROFID + " Integer PRIMARY KEY AUTOINCREMENT, " + PROFFNAME+" TEXT, " + PROFMNAME+" TEXT, " + PROFLNAME+" TEXT) ");
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase conn, int OldVersion, int NewVersion) {
        conn.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(conn);
    }

    public Boolean AddRecord(String Fname, String Mname, String Lname){
        android.database.sqlite.SQLiteDatabase conn = this.getWritableDatabase();
        VALUES = new ContentValues();
        VALUES.put(PROFFNAME,Fname);
        VALUES.put(PROFMNAME,Mname);
        VALUES.put(PROFLNAME,Lname);
        conn.insert(PROFILE, null, VALUES);
        return true;
    }

    @SuppressLint("Range")
    public ArrayList<String> GetAllRecords(){
        Items = new ArrayList<String>();
        ItemsId = new ArrayList<Integer>();
        android.database.sqlite.SQLiteDatabase conn = this.getReadableDatabase();
        rs = conn.rawQuery("SELECT * FROM " + PROFILE, null);
        rs.moveToFirst();
        while(rs.isAfterLast()==false){
            ItemsId.add(rs.getInt(rs.getColumnIndex(PROFID)));
            Items.add(rs.getString(rs.getColumnIndex(PROFID)) + " " + rs.getString(rs.getColumnIndex(PROFFNAME)) + " " + rs.getString(rs.getColumnIndex(PROFLNAME)));
            rs.moveToNext();
        }
        return Items;
    }

    public boolean recordExists(String Fname, String Mname, String Lname){
        android.database.sqlite.SQLiteDatabase conn = this.getReadableDatabase();
        String[] columns = {PROFFNAME, PROFMNAME, PROFLNAME};
        String selection = PROFFNAME + " = ? AND " + PROFMNAME + " = ? AND " + PROFLNAME + " = ?";
        String[] selectionArgs = {Fname, Mname, Lname};

        rs = conn.query(PROFILE, columns, selection, selectionArgs, null, null, null);
        boolean exists = rs.moveToFirst();
        return exists;
    }

    public boolean DeleteRecords(){
        android.database.sqlite.SQLiteDatabase conn = this.getWritableDatabase();
        int rowsDeleted = conn.delete(PROFILE, null, null);
        conn.close();
        return rowsDeleted > 0;
    }

    @SuppressLint("Range")
    public String[] getRecordDataByIndex(int dataIndex) {
        android.database.sqlite.SQLiteDatabase conn = this.getReadableDatabase();
        String[] recordData = null;

        String[] columns = {PROFFNAME, PROFMNAME, PROFLNAME};
        String selection = PROFID + " = ?";
        String[] selectionArgs = {String.valueOf(dataIndex)};

        rs = conn.query(PROFILE, columns, selection, selectionArgs, null, null, null);
        if (rs.moveToFirst()) {
            recordData = new String[]{
                    rs.getString(rs.getColumnIndex(PROFFNAME)),
                    rs.getString(rs.getColumnIndex(PROFMNAME)),
                    rs.getString(rs.getColumnIndex(PROFLNAME))
            };
        }

        rs.close();
        conn.close();

        return recordData;
    }

    public boolean updateRecord(int index, String Fname, String Mname, String Lname) {
        android.database.sqlite.SQLiteDatabase conn = this.getWritableDatabase();
        VALUES = new ContentValues();
        VALUES.put(PROFFNAME, Fname);
        VALUES.put(PROFMNAME, Mname);
        VALUES.put(PROFLNAME, Lname);

        String selection = PROFID + " = ?";
        String[] selectionArgs = {String.valueOf(index)};

        int rowsAffected = conn.update(PROFILE, VALUES, selection, selectionArgs);

        conn.close();
        return rowsAffected > 0;
    }

    public boolean deleteRecordByIndex(int index) {
        android.database.sqlite.SQLiteDatabase conn = this.getWritableDatabase();
        String selection = PROFID + " = ?";
        String[] selectionArgs = {String.valueOf(index)};

        int rowsDeleted = conn.delete(PROFILE, selection, selectionArgs);
        conn.close();

        return rowsDeleted > 0;
    }
}
