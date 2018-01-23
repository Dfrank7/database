/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dfrank.pets;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
;import com.example.dfrank.pets.data.PetContract;
import com.example.dfrank.pets.data.PetContract.PetEntry;
import com.example.dfrank.pets.data.PetProvider;
import com.example.dfrank.pets.data.PetsDbHelper;

import static com.example.dfrank.pets.data.PetsDbHelper.DbName;
import static com.example.dfrank.pets.data.PetsDbHelper.DbVersion;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<CursorLoader> {
    private static final int Pet_Loader = 0;
    Adapter mAdapter ;
    PetsAdapter petsAdapter;
    Cursor cursor;

        /** Database helper that will provide us access to the database */
        private PetsDbHelper mDbHelper;
        private ListView listView;
        private RecyclerView recyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_catalog);
            listView = findViewById(R.id.recycler);
//            recyclerView = findViewById(R.id.recycler);

//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            petsAdapter = new PetsAdapter(this, displayDatabaseInfo());
//            recyclerView.setAdapter(petsAdapter);

            View emptyView = findViewById(R.id.emptyview);

            listView.setEmptyView(emptyView);
            mAdapter = new Adapter(this, null, false);
            listView.setAdapter(mAdapter);



//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                    Intent intent = new Intent(getBaseContext(), EditorActivity.class);
//                    Uri currentPetUri = ContentUris.withAppendedId(PetProvider.Content_Provider1,id);
//                    intent.putExtra("id", id);
//                    intent.setData(currentPetUri);
//                    startActivity(intent);
//                }
//            });

            getLoaderManager().initLoader(Pet_Loader, null, this);



            // Setup FAB to open EditorActivity
            FloatingActionButton fab =findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                    startActivity(intent);

//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplication());

                }
            });
//            displayDatabaseInfo();

            // To access our database, we instantiate our subclass of SQLiteOpenHelper
            // and pass the context, which is the current activity.
            mDbHelper = new PetsDbHelper(getBaseContext(), DbName, null, DbVersion);
        }
        private void insertDummy(){
            ContentValues contentValues = new ContentValues();
            contentValues.put(PetEntry.Name,"Toto");
            contentValues.put(PetEntry.Breed,"Asian");
            contentValues.put(PetEntry.Gender,PetEntry.Female);
            contentValues.put(PetEntry.Weight, 17);
            getContentResolver().insert(PetProvider.Content_Provider,contentValues);


        }
        private void deleteData(){
            getContentResolver().delete(PetProvider.Content_Provider,null,null);
            //Toast.makeText(getApplication(),"Data sucessfully deleted",Toast.LENGTH_SHORT).show();
        }

//    @Override
//    protected void onStart() {
//            displayDatabaseInfo();
//            super.onStart();
//    }


    private Cursor displayDatabaseInfo(){
            String[] projection ={PetEntry.Id,PetEntry.Name,PetEntry.Breed,PetEntry.Weight,
            PetEntry.Gender,PetEntry.Weight};

        Cursor cursor = getContentResolver().query(PetProvider.Content_Provider,projection,
                null,null,null);

//        Adapter adapter = new Adapter(this, cursor,false);
//        listView.setAdapter(adapter);



//            try {
//                TextView displayView = findViewById(R.id.text_view_pet);
//                int idColumnIndex = cursor.getColumnIndex(PetEntry.Id);
//                int nameColumnIndex = cursor.getColumnIndex(PetEntry.Name);
//                int breedColumnIndex = cursor.getColumnIndex(PetEntry.Breed);
//                int genderColumnIndex = cursor.getColumnIndex(PetEntry.Gender);
//                int weightColumnIndex = cursor.getColumnIndex(PetEntry.Weight);
//
//                while (cursor.moveToNext()){
//                    int id = cursor.getInt(idColumnIndex);
//                    String name = cursor.getString(nameColumnIndex);
//                    String breed = cursor.getString(breedColumnIndex);
//                    int gender = cursor.getInt(genderColumnIndex);
//                    int weight = cursor.getInt(weightColumnIndex);
//                    displayView.append("\n"+id+" -"+name+" -"+breed+" -"+gender+" -"+weight);
//
//                }
//            }finally {
                cursor.close();
           // }
        return cursor;
        }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummy();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {PetEntry.Id,PetEntry.Name,PetEntry.Breed};

        return new CursorLoader(this,PetProvider.Content_Provider,projection,null,
                null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursorLoader) {
//        mAdapter.swapCursor(cursorLoader);
        petsAdapter.swapCursor(cursorLoader);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mAdapter.swapCursor(null);
        petsAdapter.swapCursor(null);

    }
}
