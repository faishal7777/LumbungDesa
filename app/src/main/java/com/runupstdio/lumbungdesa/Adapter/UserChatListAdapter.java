package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Model.User;
import com.runupstdio.lumbungdesa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserChatListAdapter extends RecyclerView.Adapter<UserChatListAdapter.UserViewHolder> {

    private Context mContext;

    //array-list
    private List<User> mData;

    public UserChatListAdapter (List<User> mData, Context mContext){
        //this.mNamaUser = mNamaUser;
        //this.mImgUrlUser = mImgUrlUser;
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user_chat, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        User list = mData.get(position);

        String currentUrl = list.getImageUrl();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrl)
                .into(holder.imgUrlUser);

        //holder.namaUser.setText(mNamaUser.get(position));
            holder.namaUser.setText(list.getUsername());

        //String currentUrl = mImgUrlUser.get(position);


    }

    @Override
    public int getItemCount() {
            return mData.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView namaUser;
        public RoundedImageView imgUrlUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            namaUser = itemView.findViewById(R.id.username_List_Chat);
            imgUrlUser = itemView.findViewById(R.id.img_Profile_List_Chat);
        }
    }
}
