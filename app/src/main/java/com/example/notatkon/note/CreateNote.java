package com.example.notatkon.note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notatkon.R;
import com.example.notatkon.entities.NoteEntity;
import com.example.notatkon.database.NoteRoomDatabase;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

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

    private NoteEntity selectedNote;

    private AlertDialog deleteNote;

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


        if (getIntent().getBooleanExtra("update", false)) {
            selectedNote = (NoteEntity) getIntent().getSerializableExtra("noteEntity");
            fillSelectedNote();
        }

        setBottomToolbar();



    }

    //przypisanie obecnej zawartosci notatki nowemu widokowi po kliknieciu
    private void fillSelectedNote() {
        noteTitle.setText(selectedNote.getTitle());
        noteSubtitle.setText(selectedNote.getSubtitle());
        inputNote.setText(selectedNote.getContent());
        textDataTime.setText(selectedNote.getDateTime());
    }

    private void saveNote() {

        if (noteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Tytuł nie może być pusty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (noteSubtitle.getText().toString().trim().isEmpty()
                && inputNote.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Podtytuł/notatka nie mogą być puste!", Toast.LENGTH_SHORT).show();
            return;
        }

        //pobierz wpisywany tekst z textview
        final NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteTitle.getText().toString());
        noteEntity.setSubtitle(noteSubtitle.getText().toString());
        noteEntity.setContent(inputNote.getText().toString());
        noteEntity.setDateTime(textDataTime.getText().toString());


        if (selectedNote != null) {
            noteEntity.setId(selectedNote.getId());
        }


        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteRoomDatabase.getNoteRoomDatabase(getApplicationContext()).noteDao().insert(noteEntity);
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

    private void setBottomToolbar() {

        final LinearLayout bottomToolbar = findViewById(R.id.bottomToolbar);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomToolbar);

        //wysuwanie dolnego paska narzędzi
        if (selectedNote != null) {
            bottomToolbar.findViewById(R.id.textBottomToolbar).setVisibility(View.VISIBLE);
            bottomToolbar.findViewById(R.id.deleteNote).setVisibility(View.VISIBLE);
            bottomToolbar.findViewById(R.id.textBottomToolbar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            });
            bottomToolbar.findViewById(R.id.deleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showDialog();
                }
            });
        }
    }

    /*
    https://developer.android.com/guide/topics/ui/dialogs#CustomLayout
    */

    private void showDialog() {

        if (deleteNote == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateNote.this);

            View v = LayoutInflater.from(this).inflate(
                    R.layout.delete_note,
                    (ViewGroup) findViewById(R.id.dialogDelete)
            );
            builder.setView(v);
            deleteNote = builder.create();
            if (deleteNote.getWindow() != null) {
                deleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            // task dla opcji usuń notatkę w oknie dialogowym
            v.findViewById(R.id.textDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    class DeleteNoteTask extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            NoteRoomDatabase.getNoteRoomDatabase(getApplicationContext()).noteDao()
                                    .delete(selectedNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent intent = new Intent();
                            intent.putExtra("isDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    new  DeleteNoteTask().execute();
                }
            });

            // opcja anuluj w oknie dialogowym
            v.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteNote.dismiss();
                }
            });
        }
        deleteNote.show();
    }


}