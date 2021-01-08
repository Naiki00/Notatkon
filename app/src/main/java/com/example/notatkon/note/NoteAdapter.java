package com.example.notatkon.note;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notatkon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter{

    private ArrayList<Note> arrNotes = new ArrayList<>();

    //obiekt listy notatek
    private RecyclerView noteRecyclerView;

    //implementacja ViewHoldera
    //https://developer.android.com/guide/topics/ui/layout/recyclerview
    private class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle;
        public TextView noteContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteContent = (TextView) itemView.findViewById(R.id.note_content);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}