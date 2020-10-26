package br.senai.sc.produto_exercicio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.senai.sc.produto_exercicio.database.contract.ProductContract;

public class DatabaseDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db.produto";
    public static final int DATABASE_VERSION = 1;

    public DatabaseDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(ProductContract.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProductContract.removerTabela());
        db.execSQL(ProductContract.criarTabela());
    }
}
