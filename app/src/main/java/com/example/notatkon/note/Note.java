package com.example.notatkon.note;

public class Note {
    private String noteTitle;
    private String noteContent;

    // automatyczne dane do tablicy
    private static String[] NoteTitles = { "Title1" };

    private static String[] NoteContents = { "Content1" };

    //konstruktor
    public Note() {
        noteTitle = NoteTitles[0];
        noteContent = NoteContents[0];
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }
}