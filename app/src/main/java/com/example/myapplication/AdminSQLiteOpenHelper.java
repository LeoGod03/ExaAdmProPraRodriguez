package com.example.myapplication;//librerias de conexion a BD SQLite

import  android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteDatabase.CursorFactory;
import  android.database.sqlite.SQLiteOpenHelper;
import  android.content.Context;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea tabla en base de datos administracion
        db.execSQL("create table Alumnos (matricula integer primary key, nombre text, carrera text, cal1 decimal, cal2 decimal, promedio decimal)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Alumnos");
        db.execSQL("create table Alumnos (matricula integer primary key, nombre text, carrera text, cal1 decimal, cal2 decimal, promedio decimal)");
    }
}