package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Model.Keranjang;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

public class KeranjangActivity extends AppCompatActivity {

    RecyclerView mRVKeranjang;
    KeranjangAdapter adapter;
    List<Keranjang> mKeranjang;
    Button mBeliSekarang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        mKeranjang = new ArrayList<>();
        mKeranjang.add(new Keranjang("Lentho Bakar", "Rp 12.000", "7", "https://picsum.photos/50/?random"));
        mKeranjang.add(new Keranjang("Ndog Jaran", "Rp 12.000", "5", "https://picsum.photos/50/?random"));
        mKeranjang.add(new Keranjang("KONTOL", "Rp 12.000", "2", "https://picsum.photos/50/?random"));
        mKeranjang.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));

        mRVKeranjang = findViewById(R.id.rvKeranjang);

        adapter = new KeranjangAdapter(mKeranjang, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KeranjangActivity.this);

        mRVKeranjang.setLayoutManager(layoutManager);

        mRVKeranjang.setAdapter(adapter);

        mBeliSekarang = findViewById(R.id.btn_beliSekarang);
        mBeliSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjutBeli = new Intent(KeranjangActivity.this, CheckoutActivity.class);
                startActivity(lanjutBeli);
            }
        });
    }
}
