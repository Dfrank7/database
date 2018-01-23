package com.example.dfrank.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dfrank on 11/19/17.
 */

public final class PetContract {
    public static abstract class PetEntry implements BaseColumns {
        public static final String TableName = "pets";
        public static final String Id = BaseColumns._ID;
        public static final String Name = "name";
        public static final String Breed= "breed";
        public static final String Gender = "genger";
        public static final String Weight = "weight";

//        public static final String CreateTable = "CREATE TABLE "+TableName+"("
//                +Id+" PRIMARY KEY AUTOINCREMENT INTEGER,"+
//                Name+" TEXT NOT NULL,"
//                +Breed+" TEXT,"
//                +Gender+" INTEGER NOT NULL,"
//                +Weight+" INTEGER NOT NULL DEFAULT 0)";
        public static final String CreateTable =  "CREATE TABLE " + TableName + " ("
                + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name + " TEXT NOT NULL, "
                + Breed+ " TEXT, "
                + Gender + " INTEGER NOT NULL, "
                + Weight + " INTEGER NOT NULL DEFAULT 0);";



        public static final int Male = 1;
        public static final int Female = 2;
        public static final int NoAnswer = 0;
    }
}
