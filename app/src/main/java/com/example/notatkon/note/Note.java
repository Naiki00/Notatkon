package com.example.notatkon.note;

public class Note {
    private String Title;
    private String Content;

    // automatyczne dane do tablicy
    private static String[] Titles = { "Title1" };

    private static String[] Contents = { "Content1" };

    //konstruktor
    public Note() {

    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }
}