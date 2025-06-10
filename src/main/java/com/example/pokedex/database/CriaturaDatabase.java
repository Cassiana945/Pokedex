package com.example.pokedex.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pokedex.model.Criatura;

import java.util.ArrayList;
import java.util.List;

public class CriaturaDatabase extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME_BD = "Pokedex";
    private static final String C_ID = "id";
    private static final String C_NOME = "nome";
    private static final String C_TIPO = "tipo";
    private static final String C_PODER = "poder";
    private static final String C_DESCOBERTO = "descoberto";
    private static final String TB_CRIATURA = "criatura";
    public static Context contexto;

    public CriaturaDatabase(@Nullable Context context) {
        super(context,NOME_BD, null, VERSAO);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TB_CRIATURA + "( " +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_NOME + " TEXT, " +
                C_TIPO + " TEXT, " +
                C_PODER + " TEXT, " +
                C_DESCOBERTO + " BOOLEAN)";

        db.execSQL(query);
    }

    public void addCriatura(Criatura criatura) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_NOME, criatura.getNome());
        values.put(C_TIPO, criatura.getTipo());
        values.put(C_PODER, criatura.getPoder());
        values.put(C_DESCOBERTO, criatura.isDescoberto());
        db.insert(TB_CRIATURA, null, values);
        db.close();
    }

    public Criatura findOneCriatura(int cod) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    TB_CRIATURA,
                    new String[]{C_ID, C_NOME, C_TIPO, C_PODER, C_DESCOBERTO},
                    C_ID + " = ?",
                    new String[]{String.valueOf(cod)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                return new Criatura(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) > 0
                );
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void excluirCriatura(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                TB_CRIATURA,
                C_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();

    }

    public void updateDescoberto(int id, boolean descoberto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_DESCOBERTO, descoberto);
        db.update(
                TB_CRIATURA,
                values,
                C_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
    }

    public List<Criatura> findAllCriatura(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Criatura> criaturas = new ArrayList<>();
        String query = "SELECT * FROM "+TB_CRIATURA;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Criatura criatura = new Criatura();
                criatura.setId(cursor.getInt(0));
                criatura.setNome(cursor.getString(1));
                criatura.setTipo(cursor.getString(2));
                criatura.setPoder(cursor.getString(3));
                criatura.setDescoberto(cursor.getInt(4) > 0);
                criaturas.add(criatura);
            }while (cursor.moveToNext());
        }
        db.close();
        return criaturas;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
