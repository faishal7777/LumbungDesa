package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.DaganganAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Barangku;
import com.runupstdio.lumbungdesa.Model.Dagangan;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.Model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DaganganActivity extends AppCompatActivity {

    RecyclerView mRVDagangan;
    DaganganAdapter adapter;
    List<Dagangan> mDagangan;
    ViewLoad viewLoad;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagangan);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mRVDagangan = findViewById(R.id.rvDagangan);

        viewLoad = new ViewLoad(DaganganActivity.this);
        viewLoad.showDialog();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            Intent a = new Intent(DaganganActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(DaganganActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();

        }
    }

    private void setFeedData(){
        mDagangan = new ArrayList<>();
        Observable<Barangku> london = mApiClient.get_barangku("Bearer "+idToken, "MIND");

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            mDagangan.add(new Dagangan(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getAvaProduct(), feedInfo.getData().get(i).getProductName(),"Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getProductPrice())))));
                        }
                        initRecyclerView();
                        viewLoad.hideDialog();
                    } else {
                        viewLoad.hideDialog();
                    }
                });
    }

    private void initRecyclerView(){
        adapter = new DaganganAdapter(mDagangan, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DaganganActivity.this);
        mRVDagangan.setLayoutManager(layoutManager);
        mRVDagangan.setAdapter(adapter);
    }
}
