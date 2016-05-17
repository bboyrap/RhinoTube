package com.example.ultrabook.rhinotube.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ultrabook.rhinotube.Constant;
import com.example.ultrabook.rhinotube.Model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoDAO {

    private Context mContext;

    public VideoDAO(Context context){
        this.mContext = context;
    }

    public long insert(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesVideo(video);

        long id = db.insert(Constant.DB_TABLE, null, values);

        db.close();

        return id;
    }

    public List<Video> read(){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.DB_TABLE, null);
        List<Video> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Video video = new Video();
            video.setId(cursor.getLong(cursor.getColumnIndex(Constant.DB_ID)));
            video.setTitle(cursor.getString(cursor.getColumnIndex(Constant.DB_TITLE)));
            video.setThumbnail(cursor.getString(cursor.getColumnIndex(Constant.DB_THUMBNAIL)));
            video.setDescription(cursor.getString(cursor.getColumnIndex(Constant.DB_DESCRIPTION)));

            list.add(video);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int update(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesVideo(video);

        int rowsAffected = db.update(Constant.DB_TABLE, values,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});

        db.close();

        return rowsAffected;
    }

    public int delete(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(Constant.DB_TABLE,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});

        db.close();

        return rowsAffected;
    }

    private ContentValues valuesVideo(Video video){
        ContentValues values = new ContentValues();
        values.put(Constant.DB_TITLE, video.getTitle());
        values.put(Constant.DB_THUMBNAIL, video.getThumbnail());
        values.put(Constant.DB_DESCRIPTION, video.getDescription());

        return values;
    }

}
