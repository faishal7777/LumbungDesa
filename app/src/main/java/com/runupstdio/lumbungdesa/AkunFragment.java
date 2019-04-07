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

public class AkunFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, null);

        Button mLogin = v.findViewById(R.id.btn_login);
        Button mDaftar = v.findViewById(R.id.btn_daftar);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(v.getContext(), SigninActivity.class);
                startActivity(login);
            }
        });
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(v.getContext(), SignupActivity.class);
                startActivity(daftar);
            }
        });

        Button mJualProduk = v.findViewById(R.id.btnJualProduk);
        mJualProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mJual = new Intent(v.getContext(), JualActivity.class);
                startActivity(mJual);
            }
        });

        return v;
    }
}
