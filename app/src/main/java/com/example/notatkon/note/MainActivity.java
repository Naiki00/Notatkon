package com.example.notatkon.note;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notatkon.R;
import com.example.notatkon.entities.NoteEntity;
import com.example.notatkon.database.NoteRoomDatabase;
import com.example.notatkon.note.CreateNote;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_NOTE = 1;

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
    }

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

        /* -- Pierwsza wersja apki --
        //metody
        //https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView#next-steps

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notes);
        //ustaw LayoutManagera wertykalnie
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ustaw LayoutManagera horyzontalnie
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //wczytaj listę z klasy Note oraz dodaj obiekt jej klasy
        ArrayList<Note> notes = new ArrayList<Note>();
        for (int i = 0; i < 20; i++) {
            notes.add(new Note());
        }

        //połącz Adapter z RecycleView
        recyclerView.setAdapter(new NoteAdapter(notes, recyclerView));




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
    private void getNotes() {

        class GetNotesTask extends AsyncTask<Void, Void, List<NoteEntity>> {

            @Override
            protected List<NoteEntity> doInBackground(Void... voids) {
                //return null;
                return (List<NoteEntity>) NoteRoomDatabase
                        .getNoteRoomDatabase(getApplicationContext())
                        .noteDao().getNotes();
            }

            @Override
            protected void onPostExecute(List<NoteEntity> noteEntities) {
                super.onPostExecute(noteEntities);
                Log.d("NOTES", noteEntities.toString());
            }
        }
        new GetNotesTask().execute();
    }
}