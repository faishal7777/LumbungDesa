package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.DetilTransaksiAdapter;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Detail;
import com.runupstdio.lumbungdesa.Model.DetilTransaksi;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.Model.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetilActivity extends AppCompatActivity {

    RecyclerView mRVDetilTransaksi;
    DetilTransaksiAdapter adapter;
    List<DetilTransaksi> mDetilTransaksi;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private int idTrx;

    TextView mDetailTrx, mDetailStatus, mDetailTotalPrice, mDetailName, mDetailRoad, mDetailKecKot, mDetailState, mDetailMsisdn, mDetailPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mRVDetilTransaksi = findViewById(R.id.rvDetilTransaksi);
        mDetailTrx = findViewById(R.id.detail_idtrx);
        mDetailStatus = findViewById(R.id.detail_status);
        mDetailTotalPrice = findViewById(R.id.detail_totalprice);
        mDetailName = findViewById(R.id.detail_name);
        mDetailRoad = findViewById(R.id.detail_road);
        mDetailKecKot = findViewById(R.id.detail_keckot);
        mDetailState = findViewById(R.id.detail_state);
        mDetailMsisdn = findViewById(R.id.detail_msisdn);
        mDetailPayment = findViewById(R.id.detail_payment);

        idTrx = getIntent().getIntExtra("idTrx", 1);

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
            Intent a = new Intent(DetilActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(DetilActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();
        }
    }

    private void setFeedData(){
        mDetilTransaksi = new ArrayList<>();
        Observable<Detail> london = mApiClient.get_detail("Bearer "+idToken, idTrx);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dt = sdf.parse(feedInfo.getData().getCreatedAt());
                        long epoch = dt.getTime();
                        mDetailTrx.setText("LDRN"+(int)(epoch/1000)+feedInfo.getData().getId());

                        String tempStatus = "";
                        if(feedInfo.getData().getCencelled().equals("1")) tempStatus = "Dibatalkan";
                        else if(feedInfo.getData().getCheckedOut().equals("1") && feedInfo.getData().getPaid().equals("0") && feedInfo.getData().getShipped().equals("0") && feedInfo.getData().getDelivered().equals("0")) tempStatus = "Belum Bayar";
                        else if(feedInfo.getData().getCheckedOut().equals("1") && feedInfo.getData().getPaid().equals("1") && feedInfo.getData().getShipped().equals("0") && feedInfo.getData().getDelivered().equals("0")) tempStatus = "Dibayar";
                        else if(feedInfo.getData().getCheckedOut().equals("1") && feedInfo.getData().getPaid().equals("1") && feedInfo.getData().getShipped().equals("1") && feedInfo.getData().getDelivered().equals("0")) tempStatus = "Dikirim";
                        else if(feedInfo.getData().getCheckedOut().equals("1") && feedInfo.getData().getPaid().equals("1") && feedInfo.getData().getShipped().equals("1") && feedInfo.getData().getDelivered().equals("1")) tempStatus = "Sukses";

                        mDetailStatus.setText(tempStatus);
                        mDetailTotalPrice.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().getPriceTotal()))));

                        if(feedInfo.getData().getIdPayment().equals("1")) mDetailPayment.setText("Cash On Delivery");
                        else if(feedInfo.getData().getIdPayment().equals("2")) mDetailPayment.setText("OVO");

                        for(int i=0; i<feedInfo.getData().getProducts().size(); i++){
                            mDetilTransaksi.add(new DetilTransaksi(feedInfo.getData().getProducts().get(i).getProductName(), feedInfo.getData().getProducts().get(i).getQuantity(),"Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().getProducts().get(i).getProductPrice()))),feedInfo.getData().getProducts().get(i).getAvaProduct()));
                        }
                    } else {

                    }
                    initRecycler();
                });

        Observable<Profile> paris = mApiClient.user_info("Bearer "+idToken);

        paris.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    Log.e("Current Weather", idToken);
                    if(userInfo.getStatus()){
                        mDetailName.setText(userInfo.getData().getName());
                        mDetailMsisdn.setText(userInfo.getData().getMsisdn());
                        mDetailRoad.setText(userInfo.getData().getAddress().getRoad());
                        mDetailKecKot.setText(userInfo.getData().getAddress().getKecamatan()+", "+userInfo.getData().getAddress().getCity());
                        mDetailState.setText(userInfo.getData().getAddress().getState());
                    } else {

                    }
                });
    }

    public void initRecycler() {
        adapter = new DetilTransaksiAdapter(mDetilTransaksi, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetilActivity.this);
        mRVDetilTransaksi.setLayoutManager(layoutManager);
        mRVDetilTransaksi.setAdapter(adapter);
    }
}
