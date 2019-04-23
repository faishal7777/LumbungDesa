package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Model.Keranjang;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    RecyclerView mRVCheckout;
    KeranjangAdapter adapter;
    List<Keranjang> mCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        mCheckout = new ArrayList<>();
        mCheckout.add(new Keranjang("Lentho Bakar", "Rp 12.000", "7", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Ndog Jaran", "Rp 12.000", "5", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("KONTOL", "Rp 12.000", "2", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));
        mCheckout.add(new Keranjang("Gasido Tuku lurd", "Rp 12.000", "3", "https://picsum.photos/50/?random"));

        mRVCheckout = findViewById(R.id.rvCheckout);

        adapter = new KeranjangAdapter(mCheckout, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CheckoutActivity.this);

        mRVCheckout.setLayoutManager(layoutManager);

        mRVCheckout.setAdapter(adapter);

    }
}
