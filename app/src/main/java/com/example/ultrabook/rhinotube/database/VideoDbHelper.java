package com.example.ultrabook.rhinotube.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultrabook.rhinotube.Constant;


public class VideoDbHelper extends SQLiteOpenHelper {
    public VideoDbHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ Constant.DB_TABLE + "(" +
                Constant.DB_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constant.DB_TITLE       + " TEXT NOT NULL," +
                Constant.DB_THUMBNAIL   + " TEXT NOT NULL," +
                Constant.DB_DESCRIPTION + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
