package com.example.dfrank.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by dfrank on 11/19/17.
 */

public class PetsDbHelper extends SQLiteOpenHelper {
    public static final int DbVersion=1;
    public static final String DbName = "pets.db";

    public PetsDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DbName, null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PetContract.PetEntry.CreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
