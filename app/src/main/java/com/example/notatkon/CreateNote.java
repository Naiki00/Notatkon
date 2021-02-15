package com.example.notatkon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notatkon.database.NoteEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
                new SimpleDateFormat("EEEE, dd MM yyyy HH:mm", Locale.getDefault())
                        .format(new Date())
        );
    }

    private void saveNote() {

        final NoteEntity note = new NoteEntity();
        note.setTitle(noteTitle.getText().toString());
        note.setSubtitle(noteSubtitle.getText().toString());
        note.setContent(inputNote.getText().toString());
        note.setDateTime(textDataTime.getText().toString());
    }



}