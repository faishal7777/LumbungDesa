package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.ChatActivity;
import com.runupstdio.lumbungdesa.Model.Chat;
import com.runupstdio.lumbungdesa.Model.User;
import com.runupstdio.lumbungdesa.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context chatContext;
    private List<Chat> mChat;
    private String imgUrl;

    public ChatAdapter(Context chatContext, List<Chat> mChat, String imgUrl) {
        this.chatContext = chatContext;
        this.mChat = mChat;
        this.imgUrl = imgUrl;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_right, parent, false);
            return new ChatAdapter.ViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_left, parent, false);
            return new ChatAdapter.ViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.showMessage.setText(chat.getMessage());

        if (imgUrl.equals("default")){
            holder.imgProfile.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(chatContext)
                    .load(imgUrl)
                    .into(holder.imgProfile);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView showMessage;
        public RoundedImageView imgProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            showMessage = itemView.findViewById(R.id.message_Chat);
            imgProfile = itemView.findViewById(R.id.profile_Chat);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //nentukno value sender/receiver gae bubbles message
        if (mChat.get(position).getSender().equals(mChat.get(position).getMyID())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}
