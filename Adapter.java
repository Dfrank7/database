package com.example.dfrank.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.dfrank.pets.data.PetContract;

/**
 * Created by dfrank on 11/23/17.
 */

public class Adapter extends CursorAdapter {
    public Adapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.rowcard,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.name);
        TextView breed = view.findViewById(R.id.breed);

        String mName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.Name));
        String mBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.Breed));

        name.setText(mName);
        breed.setText(mBreed);

    }
}
