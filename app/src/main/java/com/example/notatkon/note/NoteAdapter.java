package com.example.notatkon.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.notatkon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

//https://developer.android.com/guide/topics/ui/layout/recyclerview

/* Aby klasa była adapterem musi dziedziczyć po RecyclerView.Adapter oraz wskazywać na ViewHolder */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private ArrayList<Note> arrNotes = new ArrayList<>();

    //obiekt listy notatek
    private RecyclerView noteRecyclerView;

    //konstruktor
    public NoteAdapter(ArrayList<Note> Notes, RecyclerView notesRecyclerView) {
        arrNotes = Notes;
        noteRecyclerView = notesRecyclerView;
    }

    //implementacja ViewHoldera
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle;
        public TextView noteContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteContent = (TextView) itemView.findViewById(R.id.note_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_note, parent, false);
        // utwórz nowy ViewHolder i usuń
        return new ViewHolder(view);
    }

    ///// Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = arrNotes.get(position);
        ((ViewHolder) holder).noteTitle.setText(note.getNoteTitle());
        ((ViewHolder) holder).noteContent.setText(note.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }
}