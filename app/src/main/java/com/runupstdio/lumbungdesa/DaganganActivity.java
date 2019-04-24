package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.runupstdio.lumbungdesa.Adapter.DaganganAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Model.Dagangan;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DaganganActivity extends AppCompatActivity {

    RecyclerView mRVDagangan;
    DaganganAdapter adapter;
    List<Dagangan> mDagangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagangan);

        mDagangan = new ArrayList<>();
        mDagangan.add(new Dagangan("https://picsum.photos/50/?random", "Dimas Maulana", "Rp 12.000"));
        mDagangan.add(new Dagangan("https://picsum.photos/50/?random", "Fadly Yonk","Rp 12.000"));
        mDagangan.add(new Dagangan("https://picsum.photos/50/?random", "Ariyandi Nugraha","Rp 12.000"));
        mDagangan.add(new Dagangan("https://picsum.photos/50/?random", "Aham Siswana","Rp 12.000"));

        mRVDagangan = findViewById(R.id.rvDagangan);
        adapter = new DaganganAdapter(mDagangan, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DaganganActivity.this);
        mRVDagangan.setLayoutManager(layoutManager);
        mRVDagangan.setAdapter(adapter);
    }
}
