package com.example.notatkon.note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.notatkon.R;
import com.example.notatkon.adapter.NoteAdapter;
import com.example.notatkon.entities.NoteEntity;
import com.example.notatkon.database.NoteRoomDatabase;
import com.example.notatkon.listener.NoteListener;
import com.example.notatkon.note.CreateNote;

import java.util.ArrayList;
import java.util.List;

/*
https://developer.android.com/guide/components/activities/activity-lifecycle
https://developer.android.com/training/basics/intents/result
*/

public class MainActivity extends AppCompatActivity implements NoteListener {

    public static final int REQUEST_CODE_NEW_NOTE = 1;
    public static final int REQUEST_EDIT_NOTE = 2;
    public static final int REQUEST_SHOW_NOTE = 3;

    private int notePosition = -1;

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

        //-- Pierwsza wersja apki --
        //metody
        //https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView#next-steps

        noteRecycler = findViewById(R.id.notes);
        //ustaw LayoutManagera wertykalnie
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ustaw LayoutManagera horyzontalnie
        noteRecycler.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        noteEntityList = new ArrayList<>();
        //połącz Adapter z RecycleView
        noteAdapter = new NoteAdapter(noteEntityList, this);
        noteRecycler.setAdapter(noteAdapter);

        // wyświetl na początku wszystkie notatki z bazy
        getAllNotes(REQUEST_SHOW_NOTE);
    }


    @Override
    public void onNoteClicked(NoteEntity note, int position) {
        notePosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNote.class);
        intent.putExtra("update", true);
        intent.putExtra("noteEntity", note);
        startActivityForResult(intent, REQUEST_EDIT_NOTE);
    }

    // pobranie notatek z bazy i wyswietlanie na ekranie
    // przekazujemy request jako parametr by przekazać potem kod
    // wyświetlajacy wszystkie notatki lub aktywność edycji notatki
    private void getAllNotes(int requestCode) {

        @SuppressLint("StaticFieldLeak")
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
                // zamiast requestów
                // bez możliwości edycji
                /*
                if (noteEntityList.size() == 0) {
                    noteEntityList.addAll(noteEntities);
                    noteAdapter.notifyDataSetChanged();
                } else {
                    noteEntityList.add(0, noteEntities.get(0));
                    noteAdapter.notifyItemInserted(0);
                }
                noteRecycler.smoothScrollToPosition(0);
                */

                if (requestCode == REQUEST_SHOW_NOTE) {
                    noteEntityList.addAll(noteEntities);
                    noteAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_NEW_NOTE) {
                    noteEntityList.add(0, noteEntities.get(0));
                    noteAdapter.notifyItemInserted(0);
                    noteRecycler.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_EDIT_NOTE) {
                    noteEntityList.remove(notePosition);
                    noteEntityList.add(notePosition, noteEntities.get(notePosition));
                    noteAdapter.notifyItemChanged(notePosition);
                }
            }
        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // sprawdzamy request i przechodzimy do odpowiedniego widoku
        if (requestCode == REQUEST_CODE_NEW_NOTE && resultCode == RESULT_OK) {
            getAllNotes(REQUEST_CODE_NEW_NOTE);
        } else if (requestCode == REQUEST_EDIT_NOTE && resultCode == RESULT_OK) {
            if(data != null) {
                getAllNotes(REQUEST_EDIT_NOTE);
            }
        }
    }
}