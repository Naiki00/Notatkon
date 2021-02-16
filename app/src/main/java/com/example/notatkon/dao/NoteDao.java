package com.example.notatkon.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notatkon.entities.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_table")
    LiveData<List<NoteEntity>> getNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteEntity noteEntity);

    @Delete()
    void delete(NoteEntity noteEntity);
}
