package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Cart;
import com.runupstdio.lumbungdesa.Model.CartData;
import com.runupstdio.lumbungdesa.Model.Keranjang;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KeranjangActivity extends AppCompatActivity {

    RecyclerView mRVKeranjang;
    KeranjangAdapter adapter;
    List<Keranjang> mKeranjang;
    Button mBeliSekarang, mBtnHapusKeranjang;
    TextView mCartSize, mTotalPrice;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    double tempPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            Intent a = new Intent(KeranjangActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(KeranjangActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();
        }

        mRVKeranjang = findViewById(R.id.rvKeranjang);
        mCartSize = findViewById(R.id.cart_size);
        mTotalPrice = findViewById(R.id.total_price);

        mBeliSekarang = findViewById(R.id.btn_beliSekarang);
        mBeliSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjutBeli = new Intent(KeranjangActivity.this, CheckoutActivity.class);
                startActivity(lanjutBeli);
            }
        });
    }

    private void setData(){
        mKeranjang = new ArrayList<>();

        Observable<Cart> london = mApiClient.get_cart("Bearer "+idToken);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        mCartSize.setText("Total Pesanan ("+feedInfo.getData().size()+" item)");
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            mKeranjang.add(new Keranjang(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotal()))), feedInfo.getData().get(i).getQuantity()+" Pcs",feedInfo.getData().get(i).getProductAva()));
                            tempPrice += Integer.parseInt(feedInfo.getData().get(i).getPriceTotal());
                        }
                        mTotalPrice.setText("Rp "+String.format("%,.0f", tempPrice));
                        initRecyler();
                    } else {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initRecyler(){
        adapter = new KeranjangAdapter(idToken, mKeranjang, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KeranjangActivity.this);
        mRVKeranjang.setLayoutManager(layoutManager);
        mRVKeranjang.setAdapter(adapter);
    }

}
