package com.runupstdio.lumbungdesa;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.nineoldandroids.view.ViewHelper;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.Product;
import com.runupstdio.lumbungdesa.Model.Profile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductClickedActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

//    private Toolbar mToolbar;
    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private int mProductID;
    private int iProductPrice;
    private String szProductName;
    private String szProductAva;
    TextView mProductName, mProductPrice, mProductExp, mProductDesc, mSellerName, mSellerCity;
    ImageView mProductAva, mSellerAva;
    Button mbtnOpenKeranjang;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_clicked);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        mImageView = findViewById(R.id.product_ava);
        mProductAva = findViewById(R.id.product_ava);
        mProductName = findViewById(R.id.product_name);
        mProductPrice = findViewById(R.id.product_price);
        mProductExp = findViewById(R.id.product_exp);
        mProductDesc = findViewById(R.id.product_desc);
        mToolbarView = findViewById(R.id.toolbar);
        mSellerName = findViewById(R.id.prodClick_name);
        mSellerCity = findViewById(R.id.prodClick_city);
        mSellerAva = findViewById(R.id.prodClick_ava);
        setSupportActionBar((Toolbar) mToolbarView);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorBtnBlock)));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);

        mProductID = getIntent().getIntExtra("prodId", 1);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setProductData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {

        }

        mScrollView = findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mbtnOpenKeranjang = findViewById(R.id.btnOpenKeranjang);
        mbtnOpenKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("idToken", idToken);
                data.putInt("productId", mProductID);
                data.putString("productName", szProductName);
                data.putInt("productPrice", iProductPrice);
                data.putString("productAva", szProductAva);
                KeranjangBottomSheetDialog bottomSheet = new KeranjangBottomSheetDialog();
                bottomSheet.setArguments(data);
                bottomSheet.show(getSupportFragmentManager(), "Keranjang Bottom Sheet");
            }
        });



        LinearLayout mbtnHome = findViewById(R.id.product_click_home);
        mbtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setProductData(){
        Observable<Product> london = mApiClient.product_detail(mProductID, "Bearer "+idToken);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productDetails -> {
                    if(productDetails.getStatus()){
                        mProductName.setText(productDetails.getData().getProductName());
                        szProductName = productDetails.getData().getProductName();
                        mProductPrice.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(productDetails.getData().getProductPrice()))));
                        iProductPrice = Integer.parseInt(productDetails.getData().getProductPrice());

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d1 = format.parse(productDetails.getData().getCreatedAt());
                        Date d2 = format.parse(productDetails.getData().getExpiredAt());
                        long diff = d2.getTime() - d1.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000);

                        mProductExp.setText(String.valueOf(diffDays)+" Hari");
                        mProductDesc.setText(productDetails.getData().getProductDesc());
                        szProductAva = productDetails.getData().getAvaProduct();
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(productDetails.getData().getAvaProduct())
                                .into(mProductAva);
                    } else {

                    }
                });

        Observable<Profile> paris = mApiClient.user_info_prod("Bearer "+idToken, mProductID);

        paris.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    Log.e("Current Weather", idToken);
                    if(userInfo.getStatus()){
                        if(userInfo.getData().getId().equals(mAuth.getUid())) {
                            mbtnOpenKeranjang.setEnabled(false);
                            mbtnOpenKeranjang.setBackground(getDrawable(R.drawable.bg_btn_shimmer));
                        }
                        mSellerName.setText(userInfo.getData().getName());
                        mSellerCity.setText(userInfo.getData().getAddress().getCity());
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(userInfo.getData().getAvaUrl())
                                .into(mSellerAva);
                    } else {

                    }
                });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorBtnBlock);
        float alpha = Math.min(1, (float)scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        if ((alpha == 1)) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_btn);
        }
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }


//
}
