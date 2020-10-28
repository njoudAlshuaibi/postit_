package com.postit.postit_.Adapter;



import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;
import com.postit.postit_.Objects.chat;
import com.postit.postit_.R;



    public class BrowseChatAdapter extends RecyclerView.Adapter<BrowseChatAdapter.ChatViewHolder> {

        private List<chat> chatList;
        private Context context;



        public BrowseChatAdapter(Context context, List<chat> chatList) {
            this.chatList = chatList;
            this.context = context;
        }//end BrowseChatAdapter

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup student, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.message_item_layout, student, false);

            return new ChatViewHolder(v);

        }//end onCreateViewHolder

        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

            holder.txttime.setText("");
            holder.txtMsg.setText("");


            if(chatList.get(position).getSenderId().equals(FirebaseAuth.getInstance().getUid()))
            {
               // holder.llMsg.setBackgroundResource(R.drawable.chat_msg_sender_shape);
                holder.txtMsg.setGravity(Gravity.END);;
                holder.llMsg.setGravity(Gravity.END);
                holder.stupid.setVisibility(View.VISIBLE);

            }
            else {
              //  holder.llMsg.setBackgroundResource(R.drawable.chat_msg_shape);
                holder.txtMsg.setGravity(Gravity.START);
                holder.llMsg.setGravity(Gravity.START);
                holder.stupid.setVisibility(View.GONE);

            }//end else


            if(chatList.get(position).getMsg()!=null) {
                holder.txtMsg.setText(chatList.get(position).getMsg());
                Date d = new Date(chatList.get(position).getMsgTime());
                holder.txttime.setText(d.getHours() + ":" + d.getMinutes() + "");
            }





        }//onBindViewHolder

        @Override
        public int getItemCount() {
            return chatList.size();
        }


        class ChatViewHolder extends RecyclerView.ViewHolder {

            TextView txttime,txtMsg;
            LinearLayout llMsg, stupid;

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);

                txtMsg=itemView.findViewById(R.id.txtBody);
                llMsg=itemView.findViewById(R.id.llMsg);
                txttime=itemView.findViewById(R.id.txttime);
                stupid=itemView.findViewById(R.id.stupidmsg);



            }


        }//end ChatHolder



    }//end class

