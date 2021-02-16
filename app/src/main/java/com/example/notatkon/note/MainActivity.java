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
import com.example.notatkon.note.CreateNote;

import java.util.ArrayList;
import java.util.List;

/*
https://developer.android.com/guide/components/activities/activity-lifecycle
https://developer.android.com/training/basics/intents/result
*/

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
        noteAdapter = new NoteAdapter(noteEntityList);
        noteRecycler.setAdapter(noteAdapter);

        getAllNotes();
    }

    //pobranie notatek z bazy i wyswietlanie na ekranie
    private void getAllNotes() {

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
                if (noteEntityList.size() == 0) {
                    noteEntityList.addAll(noteEntities);
                    noteAdapter.notifyDataSetChanged();
                } else {
                    noteEntityList.add(0, noteEntities.get(0));
                    noteAdapter.notifyItemInserted(0);
                }
                noteRecycler.smoothScrollToPosition(0);
            }
        }
        new GetNotesTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NEW_NOTE && resultCode == RESULT_OK) {
            getAllNotes();
        }
    }
}