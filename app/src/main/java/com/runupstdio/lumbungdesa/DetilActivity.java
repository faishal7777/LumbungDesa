package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.runupstdio.lumbungdesa.Adapter.DetilTransaksiAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Model.DetilTransaksi;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DetilActivity extends AppCompatActivity {

    RecyclerView mRVDetilTransaksi;
    DetilTransaksiAdapter adapter;
    List<DetilTransaksi> mDetilTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);

        mDetilTransaksi = new ArrayList<>();
        mDetilTransaksi.add(new DetilTransaksi("Endog gan", "7","Rp 123.430","https://picsum.photos/50/?random"));
        mDetilTransaksi.add(new DetilTransaksi("Pentol gan", "4","Rp 12.080","https://picsum.photos/50/?random"));
        mDetilTransaksi.add(new DetilTransaksi("Popo Choi", "2","Rp 19.430","https://picsum.photos/50/?random"));
        mDetilTransaksi.add(new DetilTransaksi("Pakcoi", "1","Rp 13.430","https://picsum.photos/50/?random"));

        mRVDetilTransaksi = findViewById(R.id.rvDetilTransaksi);
        adapter = new DetilTransaksiAdapter(mDetilTransaksi, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetilActivity.this);
        mRVDetilTransaksi.setLayoutManager(layoutManager);
        mRVDetilTransaksi.setAdapter(adapter);
    }
}
