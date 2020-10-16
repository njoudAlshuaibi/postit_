package com.postit.postit_.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import java.util.List;

public class BrowseNoteAdapter  extends RecyclerView.Adapter <BrowseNoteAdapter.ViewHolder> {

    Context context;
    private List<note> noteList;
    CustomItemClickListener listener;


    public BrowseNoteAdapter(Context context, List<note> noteList , CustomItemClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BrowseNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notedesign, viewGroup, false);

        final ViewHolder mv = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(view , mv.getAdapterPosition());
            }
        });
        return  mv;
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseNoteAdapter.ViewHolder holder, int position) {

        //   holder.notedate.setText("\n\n\n\n\n                   "+"                 "+date);
        holder.noteTitleD.setText(noteList.get(position).getTitle());
        Log.d("===" , noteList.get(position).getTitle()+" 0");
        holder.notedate.setText("\n\n\n\n\n                   "+"                 "+noteList.get(position).getDate());


    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleD ;
        TextView notedate;
        String date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleD = itemView.findViewById(R.id.noteTitle);
            notedate = itemView.findViewById(R.id.notedate);


        }
    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }
}