package com.example.notatkon.note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notatkon.R;
import com.example.notatkon.entities.NoteEntity;
import com.example.notatkon.database.NoteRoomDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
https://www.studytonight.com/android/get-edittext-set-textview#
https://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
*/

public class CreateNote extends AppCompatActivity {

    private EditText noteTitle, noteSubtitle, inputNote;
    private TextView textDataTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);

        ImageView imageBack = findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        noteTitle = findViewById(R.id.noteTitle);
        noteSubtitle = findViewById(R.id.noteSubtitle);
        inputNote = findViewById(R.id.inputNote);
        textDataTime = findViewById(R.id.textDateTime);

        textDataTime.setText(
                new SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm", Locale.getDefault())
                        .format(new Date())
        );

        // nasłuchiwanie czy przycisk zapisz został klikniety
        ImageView imgSave = findViewById(R.id.save);
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {

        //pobierz wpisywany tekst z textview
        final NoteEntity note = new NoteEntity();
        note.setTitle(noteTitle.getText().toString());
        note.setSubtitle(noteSubtitle.getText().toString());
        note.setContent(inputNote.getText().toString());
        note.setDateTime(textDataTime.getText().toString());

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteRoomDatabase.getNoteRoomDatabase(getApplicationContext()).noteDao().insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

}