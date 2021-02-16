package com.example.notatkon.note;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notatkon.R;
import com.example.notatkon.adapter.NoteAdapter;
import com.example.notatkon.entities.NoteEntity;
import com.example.notatkon.database.NoteRoomDatabase;
import com.example.notatkon.note.CreateNote;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_NOTE = 1;

    private List<NoteEntity> noteEntityList;
    private RecyclerView noteRecycler;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNote = findViewById(R.id.AddNote);
        imageAddNote.setOnClickListener((v) -> {
            startActivityForResult(
                    new Intent(getApplicationContext(), CreateNote.class),
                    REQUEST_CODE_NEW_NOTE
            );
        });


        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*
        https://developer.android.com/guide/components/activities/activity-lifecycle
        https://developer.android.com/training/basics/intents/result
        */


        /* wersja z fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, REQUEST_CODE_NEW_NOTE);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

         */

        //-- Pierwsza wersja apki --
        //metody
        //https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView#next-steps

        noteRecycler = (RecyclerView) findViewById(R.id.notes);
        //ustaw LayoutManagera wertykalnie
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ustaw LayoutManagera horyzontalnie
        noteRecycler.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        //wczytaj listę z klasy Note oraz dodaj obiekt jej klasy

        noteEntityList = new ArrayList<>();

        //połącz Adapter z RecycleView
        noteAdapter = new NoteAdapter(noteEntityList);
        noteRecycler.setAdapter(noteAdapter);

        getAllNotes();
    }







    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     */

    //pobranie notatek z bazy i wyswietlanie na ekranie
    private void getAllNotes() {

        class GetNotesTask extends AsyncTask<Void, Void, List<NoteEntity>> {

            @Override
            protected List<NoteEntity> doInBackground(Void... voids) {
                //return null;
                return NoteRoomDatabase
                        .getNoteRoomDatabase(getApplicationContext())
                        .noteDao().getNotes();
            }

            @Override
            protected void onPostExecute(List<NoteEntity> noteEntities) {
                super.onPostExecute(noteEntities);
                //Log.d("NOTES", noteEntities.toString());
            }
        }
        new GetNotesTask().execute();
    }
}