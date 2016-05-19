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
public class VideoDAO {

    private Context mContext;

    public VideoDAO(Context context){
        this.mContext = context;
    }

    public long insert(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesVideo(video);

        //Instrução de inserir na tabela, os valores desejados
        //retornando o id(primary Key) do registro inserido
        long id = db.insert(Constant.DB_TABLE, null, values);

        video.setId(id);
        db.close();

        return id;
    }

    public List<Video> read(){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        //Como só eh feito leitura, usasse o metodo getReadableDatabase.
        SQLiteDatabase db = helper.getReadableDatabase();

        //Faz uma busca no banco de dados retornando um cursor da tabela desejada.
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constant.DB_TABLE, null);
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
                video.setId(cursor.getLong(idxId));
                video.setTitle(cursor.getString(idxTitle));
                video.setThumbnail(cursor.getString(idxThumbnail));
                video.setDescription(cursor.getString(idxDescription));

                list.add(video);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    public int update(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesVideo(video);

        //Altera a tabela, passando os valores, e usando o 3 parametrô como o WHERE
        //Lembrando que cada "?" vai ser substituido pela posição respectiva do array
        //Retornando a quantidade de registros afetados
        int rowsAffected = db.update(Constant.DB_TABLE, values,
                Constant.DB_ID + " = ?",
                new String[]{String.valueOf(video.getId())});

        db.close();

        return rowsAffected;
    }

    public int delete(Video video){
        VideoDbHelper helper = new VideoDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Retornando a quantidade de registros afetados
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
