package com.example.dfrank.pets;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dfrank.pets.data.PetContract;

/**
 * Created by dfrank on 12/13/17.
 */

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.viewHolder> {
    private Context context;
    private Cursor cursor;
    public PetsAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }
    @Override
    public PetsAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowcard,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetsAdapter.viewHolder holder, int position) {
        if (!cursor.move(position)){
            return;
        }
        String mName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.Name));
        String mBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.Breed));

        holder.name.setText(mName);
        holder.breed.setText(mBreed);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name, breed;
        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            breed = itemView.findViewById(R.id.breed);
        }
    }
    public void swapCursor(Cursor newCursor){
        if (cursor!=null){
            cursor.close();
        }
        cursor=newCursor;
        if (newCursor!=null){
            notifyDataSetChanged();
        }
    }
}
