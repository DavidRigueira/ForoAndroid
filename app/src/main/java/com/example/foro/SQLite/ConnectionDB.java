package com.example.foro.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foro.constans.Constants;

public class ConnectionDB extends SQLiteOpenHelper {


    public ConnectionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_BOARD);
        db.execSQL(Constants.CREATE_TABLE_CATEGORY);
        db.execSQL(Constants.CREATE_TABLE_USER);
        db.execSQL(Constants.CREATE_TABLE_TOPIC);
        db.execSQL(Constants.CREATE_TABLE_POST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_Name_Board);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_Name_Category);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_Name_User);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_Name_Topic);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_Name_Post);
        onCreate(db);
    }
}
