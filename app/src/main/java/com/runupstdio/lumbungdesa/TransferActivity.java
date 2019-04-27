package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Detail;
import com.runupstdio.lumbungdesa.Model.DetilTransaksi;
import com.runupstdio.lumbungdesa.Model.Profile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TransferActivity extends AppCompatActivity {

    TextView mTransferExp, mTransferNominal, mTransferInvoice;
    private int idTrx;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mTransferExp = findViewById(R.id.transfer_exp);
        mTransferNominal = findViewById(R.id.transfer_nominal);
        mTransferInvoice = findViewById(R.id.transfer_invoice);

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
            Intent a = new Intent(TransferActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(TransferActivity.this, "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();
        }
    }

    private void setFeedData(){
        Observable<Detail> london = mApiClient.get_detail("Bearer "+idToken, idTrx);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dt = sdf.parse(feedInfo.getData().getCreatedAt());
                        long epoch = dt.getTime();
                        mTransferInvoice.setText("LDRN"+(int)(epoch/1000)+feedInfo.getData().getId());
                        mTransferNominal.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().getPriceTotal()))));
                        Calendar c = Calendar.getInstance();
                        c.setTime(dt);
                        c.add(Calendar.DATE, 1);
                        dt = c.getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm");
                        mTransferExp.setText(formatter.format(dt)+" WIB");
                    } else {

                    }
                });
    }

}
