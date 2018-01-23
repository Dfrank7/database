package com.example.dfrank.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.dfrank.pets.PetsAdapter;

import static com.example.dfrank.pets.data.PetsDbHelper.DbName;
import static com.example.dfrank.pets.data.PetsDbHelper.DbVersion;

/**
 * Created by dfrank on 11/22/17.
 */

public class PetProvider extends ContentProvider {

    private static final int Pets = 100;
    private static final int Pets_Id = 101;

    private static final String Content = "content://";
    private static final String Authority = "com.example.dfrank.pets";
    private static final String Path_pets = "pets";
    private static final String Path_pets_Id = "pets/#";

    public static final Uri Content_Provider = Uri.parse(Content+Authority+"/"+Path_pets);
    public static final Uri Content_Provider1 = Uri.parse(Content+Authority+"/"+Path_pets_Id);

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Authority, Path_pets,Pets);
        uriMatcher.addURI(Authority, Path_pets_Id, Pets_Id);
    }
    PetsAdapter petsAdapter;

    PetsDbHelper dbHelper;
    @Override
    public boolean onCreate() {
        dbHelper = new PetsDbHelper(getContext(), DbName, null, DbVersion);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = uriMatcher.match(uri);
        switch (match){
            case Pets:
                cursor = database.query(PetContract.PetEntry.TableName, strings,
                        s,strings1,null,
                        null,s1);
                break;
            case Pets_Id:
                s = PetContract.PetEntry.Id +"=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(PetContract.PetEntry.TableName, strings,s,strings1,
                        null,null,s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI "+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case Pets:
            insertpet(uri, contentValues);
            return (uri);
            default:throw new IllegalArgumentException("insertion is not supported for "+uri);
        }
    }
    private Uri insertpet(Uri uri, ContentValues contentValues){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String name = contentValues.getAsString(PetContract.PetEntry.Name);
        if (name==null){
            throw new IllegalArgumentException("Name must not be empty");
        }
        Integer gender = contentValues.getAsInteger(PetContract.PetEntry.Gender);
        if (gender ==null){
            throw new IllegalArgumentException("Pet requires valid gender");
        }
        Integer weight = contentValues.getAsInteger(PetContract.PetEntry.Weight);
        if (weight==null&&weight<0){
            throw new IllegalArgumentException("Enter valid weight");
        }
        long id = database.insert(PetContract.PetEntry.TableName, null,contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        //petsAdapter.swapCursor(contentValues);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int id = database.delete(PetContract.PetEntry.TableName, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);

        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int id = database.update(PetContract.PetEntry.TableName,contentValues,s,strings);
        getContext().getContentResolver().notifyChange(uri,null);
        return id;
    }
}
