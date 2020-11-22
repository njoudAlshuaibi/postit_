package com.postit.postit_.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.firebase.auth.FirebaseUser;
import com.postit.postit_.Objects.chat;
import com.postit.postit_.R;
import java.text.SimpleDateFormat;


public class BrowseChatAdapter extends RecyclerView.Adapter<BrowseChatAdapter.ChatViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private List<chat> chatList;
    private Context context;

    FirebaseUser fuser;

    public BrowseChatAdapter(Context context, List<chat> chatList) {
        this.chatList = chatList;
        this.context = context;
    }//end BrowseChatAdapter

    @NonNull
    @Override
    public BrowseChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
            return new BrowseChatAdapter.ChatViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_message_reseived, parent, false);
            return new BrowseChatAdapter.ChatViewHolder(view);
        }


    }//end onCreateViewHolder

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        chat cht = chatList.get(position);
        holder.txtMsg.setText(cht.getMsg());
        holder.txttime.setText(cht.getMsgTime());
        // holder.txttime.setText(cht.getMsgTime()+"");


    }//onBindViewHolder

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView txttime, txtMsg;
        LinearLayout llMsg, stupid;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMsg = itemView.findViewById(R.id.text_message_body);
            //  llMsg = itemView.findViewById(R.id.llMsg);
             txttime=itemView.findViewById(R.id.text_message_time);
            //  stupid = itemView.findViewById(R.id.stupidmsg);


        }


    }//end ChatHolder

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSenderId().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}//end class