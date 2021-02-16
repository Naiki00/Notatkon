package com.example.notatkon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notatkon.R;
import com.example.notatkon.entities.NoteEntity;

import java.util.ArrayList;
import java.util.List;

//https://developer.android.com/guide/topics/ui/layout/recyclerview

/* Aby klasa była adapterem musi dziedziczyć po RecyclerView.Adapter oraz wskazywać na ViewHolder */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private List<NoteEntity> listNotes;

    //konstruktor
    public NoteAdapter(ArrayList<NoteEntity> arrNotes) {
        this.listNotes = arrNotes;
    }

    //implementacja ViewHoldera
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTitle, noteSubtitle, textDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteSubtitle = (TextView) itemView.findViewById(R.id.note_subtitle);
            textDateTime = (TextView) itemView.findViewById(R.id.textDateTime);
        }

        // wpisz notatke
        void setNote(NoteEntity note) {
            noteTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()) {
                noteSubtitle.setVisibility(View.GONE);
            } else {
                noteSubtitle.setText(note.getSubtitle());
            }
            textDateTime.setText(note.getDateTime());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_note,
                parent,
                false
        );
        // utwórz nowy ViewHolder i usuń
        return new ViewHolder(view);
    }

    ///// Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setNote(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}