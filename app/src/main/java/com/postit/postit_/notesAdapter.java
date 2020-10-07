package com.postit.postit_;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.NotesViewHolder> {

private List<note> Notes ;

    public notesAdapter(List<note> notes) {
        Notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_note_design,parent,false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
    holder.setNote(Notes.get(position));
    }

    @Override
    public int getItemCount() {
        return Notes.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitle,dateTime ;
        
        

         NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            
            noteTitle= itemView.findViewById(R.id.noteTitle);
             dateTime= itemView.findViewById(R.id.dateTime);

        }
        void setNote (note note){
             noteTitle.setText(note.getTitle());
             dateTime.setText(note.getDateTime());


        }
    }
}
