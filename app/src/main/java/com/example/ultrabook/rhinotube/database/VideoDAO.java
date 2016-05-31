package com.example.ultrabook.rhinotube.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ultrabook.rhinotube.Constant;
import com.example.ultrabook.rhinotube.model.Video;

import java.util.ArrayList;
import java.util.List;
/*
    Classe que realiza as operações no banco de dados da aplicação.
-
O VideoDbHelper helper é utilizado para pegar a instância do banco.
-
Devemos usar o ContentValues pois ele é um objeto serializado para ser passado como paramêtro.
-
O cursor que é usado para trazer o resultado da query feita no banco, sempre começa uma linha antes.
-
A instância do get(Writable/Readable)Database através do SQLiteOpenHelper abre a conexão com o banco.
*/
public class  VideoDAO {
    private Context mContext;

    public VideoDAO(Context context){
        this.mContext = context;
    }

    public String insert(Video video){
        ContentValues values = valuesVideo(video);
        String id = String.valueOf(getWritableDatabase().insert(Constant.DB_TABLE, null, values));
        getWritableDatabase().close();
        return id;
    }

    public List<Video> getList(){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + Constant.DB_TABLE, null);
        List<Video> list = new ArrayList<>();
        if(cursor.getCount() > 0) {
            int idxId = cursor.getColumnIndex(Constant.DB_ID);
            int idxTitle = cursor.getColumnIndex(Constant.DB_TITLE);
            int idxThumbnail = cursor.getColumnIndex(Constant.DB_THUMBNAIL);
            int idxDescription = cursor.getColumnIndex(Constant.DB_DESCRIPTION);
            while (cursor.moveToNext()) {
                Video video = new Video();
                video.setId(cursor.getString(idxId));
                video.setTitle(cursor.getString(idxTitle));
                video.setThumbnail(cursor.getString(idxThumbnail));
                video.setDescription(cursor.getString(idxDescription));
                list.add(video);
            }
        }
        cursor.close();
        getReadableDatabase().close();
        return list;
    }

    public int update(Video video){
        ContentValues values = valuesVideo(video);
        int rowsAffected = getWritableDatabase().update(Constant.DB_TABLE, values,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});
        getWritableDatabase().close();
        return rowsAffected;
    }

    public int delete(Video video){
        int rowsAffected = getWritableDatabase().delete(Constant.DB_TABLE,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});
        getWritableDatabase().close();
        return rowsAffected;
    }

    private ContentValues valuesVideo(Video video){
        ContentValues values = new ContentValues();
        values.put(Constant.DB_ID, video.getId());
        values.put(Constant.DB_TITLE, video.getTitle());
        values.put(Constant.DB_THUMBNAIL, video.getThumbnail());
        values.put(Constant.DB_DESCRIPTION, video.getDescription());
        return values;
    }

    public boolean isFavorite(Video video){
        Cursor cursor = getReadableDatabase().rawQuery(
            "SELECT COUNT(*) FROM " + Constant.DB_TABLE +
                " WHERE " + Constant.DB_ID + " = ?",
                new String[]{video.getId()}
        );
        boolean result = false;
        if(cursor != null){
            cursor.moveToNext();
            result = cursor.getInt(0) > 0;
            cursor.close();
        }
        getReadableDatabase().close();
        return result;
    }

    private SQLiteDatabase getReadableDatabase(){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        return helper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableDatabase(){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        return helper.getWritableDatabase();
    }
}
