package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Adapter.ProdukKategoriAdapter;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Keranjang;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity {

    RecyclerView mRVKategori;
    ProdukKategoriAdapter adapter;
    List<BarangHariIni> mKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        mKategori = new ArrayList<>();
        mKategori.add(new BarangHariIni(12, "Lentho Bakar", "Rp 20.000", "https://picsum.photos/50/?random"));
        mKategori.add(new BarangHariIni(13, "Ndog Jaran", "Rp 10.000", "https://picsum.photos/50/?random"));
        mKategori.add(new BarangHariIni(14, "KONTOL", "Rp 40.000", "https://picsum.photos/50/?random"));
        mKategori.add(new BarangHariIni(15, "Gasido Tuku lurd", "Rp 2.000", "https://picsum.photos/50/?random"));

        mRVKategori = findViewById(R.id.rvKategori);

        adapter = new ProdukKategoriAdapter(mKategori, getApplicationContext());

        mRVKategori.setLayoutManager(new GridLayoutManager(KategoriActivity.this, 2));

        mRVKategori.setAdapter(adapter);
    }
}
