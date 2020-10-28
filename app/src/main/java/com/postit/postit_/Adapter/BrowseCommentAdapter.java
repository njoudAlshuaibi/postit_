package com.postit.postit_.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.postit.postit_.Objects.comment;
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.Objects.note;
import com.postit.postit_.R;
import com.postit.postit_.helper.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.postit.postit_.Objects.favoriteList;
import com.postit.postit_.R;
import com.postit.postit_.Student_Visitor.ExplorerNote;
import com.postit.postit_.Student_Visitor.StudentActivity;
import com.postit.postit_.Student_Visitor.favoritelist;
import com.postit.postit_.helper.CustomItemClickListener;
import com.postit.postit_.Objects.note;
import java.util.List;

public class BrowseCommentAdapter extends ArrayAdapter<comment> {
    Context mcontext;
    int mResource;
    public BrowseCommentAdapter(Context context, int resource, ArrayList<comment> obj) {
        super(context, resource, obj);
        mcontext=context;
        mResource=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       String comm = getItem(position).getComm();
        String commID = getItem(position).getCommID();
        String notID = getItem(position).getNoteID();

        comment comment = new comment(commID,notID,comm);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvcomm = (TextView) convertView.findViewById(R.id.commentET);
        tvcomm.setText(comm);
        return convertView;
    }


}

