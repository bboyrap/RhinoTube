package com.example.ultrabook.rhinotube.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ultrabook.rhinotube.Constant;
/*
!Classe responsável por criar o banco de dados, ou alterar!
-
Para alterar a estrutura do banco já criada, temos que:
Alterar a versão do banco (Constant.DB_VERSION).
Fazer a alteração das estruturas, inserir campos, renomear, etc.
No onUpgrade: if(oldVersion == 1) db.execSQL("ALTER TABLE bla bla...)
*/
public class VideoDbHelper extends SQLiteOpenHelper {
    //Nome e Versão do banco
    public VideoDbHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    //Chamada apenas uma vez, para a criação da estrutura do banco de dados, tabelas, etc.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ Constant.DB_TABLE + "(" +
                Constant.DB_ID          + " TEXT PRIMARY KEY," +
                Constant.DB_TITLE       + " TEXT NOT NULL," +
                Constant.DB_THUMBNAIL   + " TEXT NOT NULL," +
                Constant.DB_DESCRIPTION + " TEXT NOT NULL)");
    }

    //Usado quando necessário fazer uma atualização da estrutura do banco
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
