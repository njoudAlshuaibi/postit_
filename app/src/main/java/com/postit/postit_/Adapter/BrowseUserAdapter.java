package com.postit.postit_.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postit.postit_.Objects.user;
import com.postit.postit_.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseUserAdapter extends RecyclerView.Adapter<BrowseUserAdapter.ViewHolder> {
    private Context context;
    private List<user> userList;


    public BrowseUserAdapter(Context context, List<user> userList){
        this.userList=userList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new BrowseUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user user = userList.get(position);
        holder.userName.setText(user.getUsername());
        holder.profile.setImageResource(R.drawable.ic_baseline_person_pin_24);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public ImageView profile;

        public ViewHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            profile = itemView.findViewById(R.id.profilICON);
        }


    }

}
