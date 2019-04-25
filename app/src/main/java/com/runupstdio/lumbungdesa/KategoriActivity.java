package com.runupstdio.lumbungdesa;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Adapter.ProdukKategoriAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.Keranjang;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KategoriActivity extends AppCompatActivity {

    RecyclerView mRVKategori;
    ProdukKategoriAdapter adapter;
    List<BarangHariIni> mKategori;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private Boolean isLoggedIn = false;
    private int category_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mRVKategori = findViewById(R.id.rvKategori);
        category_id = getIntent().getIntExtra("catid", 1);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                isLoggedIn = true;
                                setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            isLoggedIn = false;
            setFeedData();
        }
    }

    private void setFeedData(){
        mKategori = new ArrayList<>();
        Observable<Feed> london;
        if(isLoggedIn) london = mApiClient.feed_home_cat("Bearer "+idToken, category_id);
        else london = mApiClient.feed_index_cat(category_id);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            mKategori.add(new BarangHariIni(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getProductPrice()))), feedInfo.getData().get(i).getAvaProduct()));
                        }
                        initRecyclerView();
                        Log.d("Kategori", ""+category_id);
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        adapter = new ProdukKategoriAdapter(mKategori, getApplicationContext());
        mRVKategori.setLayoutManager(new GridLayoutManager(KategoriActivity.this, 2));
        mRVKategori.setAdapter(adapter);
    }
}
