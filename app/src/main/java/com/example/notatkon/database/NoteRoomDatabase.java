package com.example.notatkon.database;

/*
https://developer.android.com/codelabs/android-room-with-a-view#7
*/

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notatkon.dao.NoteDao;
import com.example.notatkon.entities.NoteEntity;

@Database(entities = {NoteEntity.class},
        version = 1,
        exportSchema = false)

public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static NoteRoomDatabase noteRoomDatabase;

    public static synchronized NoteRoomDatabase getNoteRoomDatabase(final Context context) {
        if (noteRoomDatabase == null) {
            noteRoomDatabase = Room.databaseBuilder(
                    context,
                    NoteRoomDatabase.class,
                    "note_database"
            ).build();
        }
        return noteRoomDatabase;
    }
}
