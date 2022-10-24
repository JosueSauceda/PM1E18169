package com.example.pm1e18169.configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pm1e18169.tablas.Transacciones;


public class SQLiteConexion extends SQLiteOpenHelper{

    public SQLiteConexion(Context context,
                      String dbname,
                      SQLiteDatabase.CursorFactory factory,
                      int version){
        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Transacciones.CTBcontactos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL(Transacciones.DPcontactos);
       onCreate(db);
    }
}
