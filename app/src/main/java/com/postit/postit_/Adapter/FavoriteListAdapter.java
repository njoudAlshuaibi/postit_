package com.postit.postit_.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.MainActivity;
import com.postit.postit_.R;
import com.postit.postit_.Student_Visitor.ExplorerNote;
import com.postit.postit_.Student_Visitor.StudentActivity;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import java.util.List;


public class FavoriteListAdapter  extends RecyclerView.Adapter <FavoriteListAdapter.ViewHolder> {
    Context context;
    private List<note> noteList;
    CustomItemClickListener listener;
    private DatabaseReference favouriteRef;


    public FavoriteListAdapter(Context context, List<note> noteList , CustomItemClickListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FavoriteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notedesign, viewGroup, false);

        final FavoriteListAdapter.ViewHolder mv = new FavoriteListAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(view , mv.getAdapterPosition());
            }
        });
        return  mv;
    }



    @Override
    public int getItemCount() {
        return noteList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.ViewHolder holder, int position) {
        favouriteRef = FirebaseDatabase.getInstance().getReference().child("FavoriteList");
        holder.noteTitleD.setText(noteList.get(position).getTitle());
        holder.notedate.setText(noteList.get(position).getDate());

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitleD ;
        TextView notedate;
        ImageButton deleten2;
        ImageView imageView3;
        ImageButton addFavourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleD = itemView.findViewById(R.id.noteTitle);
            notedate = itemView.findViewById(R.id.notedate);
            deleten2 = itemView.findViewById(R.id.deleten2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            addFavourite = itemView.findViewById(R.id.fvrt_f2_item);

        }
    }

    }
