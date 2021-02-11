package com.example.notatkon.database;

/*
https://developer.android.com/codelabs/android-room-with-a-view#7
*/

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class},
        version = 1,
        exportSchema = false)

public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase INSTANCE;

    static NoteRoomDatabase getNoteRoomDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}