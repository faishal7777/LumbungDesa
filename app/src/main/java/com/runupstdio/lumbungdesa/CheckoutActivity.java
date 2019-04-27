package com.runupstdio.lumbungdesa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.KeranjangAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Address;
import com.runupstdio.lumbungdesa.Model.Cart;
import com.runupstdio.lumbungdesa.Model.Checkout;
import com.runupstdio.lumbungdesa.Model.Keranjang;
import com.runupstdio.lumbungdesa.Model.Product;
import com.runupstdio.lumbungdesa.Model.Register;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSpinner;
    Dialog epicDialog;
    RecyclerView mRVCheckout;
    KeranjangAdapter adapter;
    List<Keranjang> mCheckout;
    TextView mCheckoutAddress, mCheckoutTotalPrice;
    Button mBtnPay;
    ViewLoad viewLoad;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    double tempPrice = 0.0;
    private int idPayment;
    private int idTrx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        viewLoad = new ViewLoad(this);
        viewLoad.showDialog();
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
            Intent a = new Intent(CheckoutActivity.this, NavigationBar.class);
            startActivity(a);
            Toast.makeText(CheckoutActivity.this, "Sesi anda telah berakhir", Toast.LENGTH_LONG).show();
        }

        mSpinner = findViewById(R.id.spinner_Payment);
        mSpinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> kategori = new ArrayList<>();
        kategori.add("OVO");
        kategori.add("Cash On Delivery");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategori);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

        mRVCheckout = findViewById(R.id.rvCheckout);
        mCheckoutAddress = findViewById(R.id.checkout_address);
        mCheckoutTotalPrice = findViewById(R.id.checkout_totalPrice);
        mBtnPay = findViewById(R.id.btn_checkout);
        epicDialog = new Dialog(this);

        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Checkout> addrCall = mApiClient.checkout("Bearer "+idToken, idPayment);
                addrCall.enqueue(new Callback<Checkout>() {
                    @Override
                    public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                ShowDialog();
                            } else {
                                Toast.makeText(CheckoutActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else if (response.errorBody() != null) {
                            // Get response errorBody
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(CheckoutActivity.this, errorBody, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Checkout> call, Throwable t) {
                        Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        idPayment = position+1;
        Log.d("PAYMENT", ""+idPayment);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void setData(){
        Call<Address> addrCall = mApiClient.user_address("Bearer "+idToken);
        addrCall.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        mCheckoutAddress.setText(""+response.body().getData().getRoad()+" - "+response.body().getData().getKecamatan()+", "+response.body().getData().getCity());
                    } else {
                        Toast.makeText(CheckoutActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(CheckoutActivity.this, errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mCheckout = new ArrayList<>();
        Observable<Cart> london = mApiClient.get_cart("Bearer "+idToken);
        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            idTrx = Integer.parseInt(feedInfo.getData().get(0).getIdTransaction());
                            mCheckout.add(new Keranjang(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotal()))), feedInfo.getData().get(i).getQuantity()+" Pcs",feedInfo.getData().get(i).getProductAva()));
                            tempPrice += Integer.parseInt(feedInfo.getData().get(i).getPriceTotal());
                        }
                        mCheckoutTotalPrice.setText("Rp "+String.format("%,.0f", tempPrice));
                        initRecycle();
                        viewLoad.hideDialog();
                    } else {
                        viewLoad.hideDialog();
                    }
                });
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    public void initRecycle(){
        adapter = new KeranjangAdapter(idToken, mCheckout, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CheckoutActivity.this);
        mRVCheckout.setLayoutManager(layoutManager);
        mRVCheckout.setAdapter(adapter);
    }

    public void ShowDialog(){
        PopupActivity popupDialog = new PopupActivity();
        if(idPayment==1)
            popupDialog.setType("OVO", idTrx);
        else if(idPayment==2)
            popupDialog.setType("COD", 0);
        popupDialog.show(getSupportFragmentManager(),"reserve dialog");
    }
}
