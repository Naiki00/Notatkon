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

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    List<NoteEntity> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity noteEntity);

    @Delete()
    void delete(NoteEntity noteEntity);

    @Query("DELETE FROM note_Table")
    void deleteAll();
}
