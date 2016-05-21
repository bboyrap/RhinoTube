package com.example.ultrabook.rhinotube.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ultrabook.rhinotube.Constant;
import com.example.ultrabook.rhinotube.Model.Video;

import java.util.ArrayList;
import java.util.List;
/*
!Classe para intermediar a comunicação da Interface gráfica com o banco de dados!
-
O VideoDbHelper helper é utilizado para pegar a instância do banco
helper.getWritableDatabase(), é usado para tal.
-
Criamos o objeto ContentValues passando o nome das colunas e os respectivos valores.
-
Sempre apos pegar a instância do baanco deve-se fechar a mesma.
*/
public class  VideoDAO {

    private Context mContext;

    public VideoDAO(Context context){
        this.mContext = context;
    }

    public String insert(Video video){

        ContentValues values = valuesVideo(video);

        //Instrução de insert na tabela, os valores desejados
        //retornando o id(primary Key) do registro inserido
        String id = String.valueOf(getWritableDatabase().insert(Constant.DB_TABLE, null, values));

        video.setId(id);
        getWritableDatabase().close();

        return id;
    }

    public List<Video> getList(){


        //Faz uma busca no banco de dados retornando um cursor da tabela desejada.
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + Constant.DB_TABLE, null);
        List<Video> list = new ArrayList<>();

        //Para saber se o cursor está vazio antes de processar os dados.
        if(cursor.getCount() > 0) {
            //Para saber o indice da coluna e ganhar perfomance no lugar de repetir
            //o ColumnIndex, pega o indice da coluna dentro do cursor.
            int idxId = cursor.getColumnIndex(Constant.DB_ID);
            int idxTitle = cursor.getColumnIndex(Constant.DB_TITLE);
            int idxThumbnail = cursor.getColumnIndex(Constant.DB_THUMBNAIL);
            int idxDescription = cursor.getColumnIndex(Constant.DB_DESCRIPTION);

            //Inicialmente o cursor não é apontado para nenhuma posição.
            //Quando usado o moveToNext() ele passa para o primeiro registro
            //Se não tiver nenhum registro, é retornado falso e pronto, rs.
            //Semântico ao cursor utilizado em COBOL para armazenar consultas.
            while (cursor.moveToNext()) {
                Video video = new Video();
                //Pega a informacao de acordo com o tipo de dado
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

        //Altera a tabela, passando os valores, e usando o 3 parametrô como o WHERE
        //Lembrando que cada "?" vai ser substituido pela posição respectiva do array
        //Retornando a quantidade de registros afetados
        int rowsAffected = getWritableDatabase().update(Constant.DB_TABLE, values,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});

        getWritableDatabase().close();

        return rowsAffected;
    }

    public int delete(Video video){
        //Retornando a quantidade de registros afetados
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
