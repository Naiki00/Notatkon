package com.example.notatkon.note;

import java.util.Random;

public class Note {
    private String noteTitle;
    private String noteContent;

    // automatyczne dane do tablicy
    private static String[] NoteTitles = { "Title1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit" };

    private static String[] NoteContents = { "Content1", "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"" };

    //konstruktor
    public Note() {
        Random random = new Random();

        noteTitle = NoteTitles[random.nextInt(NoteTitles.length)];
        noteContent = NoteContents[random.nextInt(NoteContents.length)];
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }
}