package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;

public class AkunLoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, null);

        Button mJualProduk = v.findViewById(R.id.btnJualProduk);
        mJualProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mJual = new Intent(v.getContext(), JualActivity.class);
                startActivity(mJual);
            }
        });

        LinearLayout mChat = v.findViewById(R.id.listChat);
        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(v.getContext(), ListChatActivity.class);
                startActivity(chat);
            }
        });

        return v;
    }
}
