package com.recoder.medibox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class B_DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(B_DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + B_DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public B_DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public B_DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String pack_img, String name, String effect, String check, String drug_img, String code, String date, String memo, String d_effect) {

        ContentValues values = new ContentValues();

        values.put(B_DataBases.CreateDB.PACK_IMG, pack_img);
        values.put(B_DataBases.CreateDB.NAME, name);
        values.put(B_DataBases.CreateDB.EFFECT, effect);
        values.put(B_DataBases.CreateDB.CHECK, check);
        values.put(B_DataBases.CreateDB.DRUG_IMG, drug_img);
        values.put(B_DataBases.CreateDB.CODE, code);
        values.put(B_DataBases.CreateDB.DATE, date);
        values.put(B_DataBases.CreateDB.MEMO, memo);
        values.put(B_DataBases.CreateDB.D_EFFECT, d_effect);

        return mDB.insert(B_DataBases.CreateDB._TABLENAME0, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String pack_img, String name, String effect, String check, String drug_img, String code, String date, String memo, String d_effect) {
        ContentValues values = new ContentValues();

        values.put(B_DataBases.CreateDB.PACK_IMG, pack_img);
        values.put(B_DataBases.CreateDB.NAME, name);
        values.put(B_DataBases.CreateDB.EFFECT, effect);
        values.put(B_DataBases.CreateDB.CHECK, check);
        values.put(B_DataBases.CreateDB.DRUG_IMG, drug_img);
        values.put(B_DataBases.CreateDB.CODE, code);
        values.put(B_DataBases.CreateDB.DATE, date);
        values.put(B_DataBases.CreateDB.MEMO, memo);
        values.put(B_DataBases.CreateDB.D_EFFECT, d_effect);

        return mDB.update(B_DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(B_DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id) {
        return mDB.delete(B_DataBases.CreateDB._TABLENAME0, "_id=" + id, null) > 0;
    }

    // Select DB
    public Cursor selectColumns() {
        return mDB.query(B_DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(String sort) {
        Cursor c = mDB.rawQuery("SELECT * FROM usertable ORDER BY " + sort + ";", null);
        return c;
    }
}
