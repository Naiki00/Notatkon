package com.example.notatkon.listener;

import com.example.notatkon.entities.NoteEntity;

public interface NoteListener {

    void onNoteClicked(NoteEntity noteEntity, int position);
}
