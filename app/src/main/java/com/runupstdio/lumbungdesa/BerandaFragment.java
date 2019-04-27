package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Adapter.BarangHariIniAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BerandaFragment extends Fragment {

    ConstraintLayout mbuahBiji;
    CarouselView carouselView;
    ViewLoad viewLoad;
    int[] sampleImages = {R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img};

    RecyclerView barangHariIni;
//    ShimmerFrameLayout mShimmerPembelian;
    List<BarangHariIni> barangHariIniArrayList;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private Boolean isLoggedIn = false;

    TextView selamat;
    RoundedImageView mBerasNBiji, mBuah, mDaging, mOlahan, mSayur, mTelur;
    LinearLayout mLnCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_beranda, null);

        mbuahBiji = view.findViewById(R.id.buahBiji);
        mbuahBiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productClicked = new Intent(view.getContext(), ProductClickedActivity.class);
                startActivity(productClicked);
            }
        });

//        mShimmerPembelian = view.findViewById(R.id.shimmerPembelian);
        final SwipeRefreshLayout refreshBeranda = view.findViewById(R.id.refreshBeranda);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                setFeedData();
                refreshBeranda.setRefreshing(false);
//
//                barangHariIni.setVisibility(View.GONE);
//                mShimmerPembelian.setVisibility(View.VISIBLE);
//                mShimmerPembelian.startShimmerAnimation();
            }
        });

        carouselView = view.findViewById(R.id.carouselBeranda);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        barangHariIni = view.findViewById(R.id.BarangHariIni);
        mBerasNBiji = view.findViewById(R.id.berasnbiji);
        mBuah = view.findViewById(R.id.buah);
        mDaging = view.findViewById(R.id.daging);
        mOlahan = view.findViewById(R.id.olahan);
        mSayur = view.findViewById(R.id.sayur);
        mTelur = view.findViewById(R.id.telur);
        mLnCart = view.findViewById(R.id.cart);

        mLnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KeranjangActivity.class);
                startActivity(productDetails);
            }
        });

        mBerasNBiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 1);
                startActivity(productDetails);
            }
        });
        mBuah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 2);
                startActivity(productDetails);
            }
        });
        mDaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 3);
                startActivity(productDetails);
            }
        });
        mOlahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 4);
                startActivity(productDetails);
            }
        });
        mSayur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 5);
                startActivity(productDetails);
            }
        });
        mTelur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(getContext(), KategoriActivity.class);
                productDetails.putExtra("catid", 6);
                startActivity(productDetails);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        viewLoad = new ViewLoad(getActivity());
        viewLoad.showDialog();
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
        return view;
    }

    public void showCustomLoadingDialog() {
        //..show gif
        viewLoad.showDialog();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //...here i'm waiting 5 seconds before hiding the custom dialog
                //...you can do whenever you want or whenever your work is done
                viewLoad.hideDialog();
                if(barangHariIniArrayList != null){
                    viewLoad.hideDialog();
                }
            }
        }, 5000);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void setFeedData(){
        barangHariIniArrayList = new ArrayList<>();
        Observable<Feed> london;
        if(isLoggedIn) london = mApiClient.feed_home("Bearer "+idToken);
        else london = mApiClient.feed_index();

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            barangHariIniArrayList.add(new BarangHariIni(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getProductPrice()))), feedInfo.getData().get(i).getAvaProduct()));
                        }
                        initRecyclerView();
                        viewLoad.hideDialog();
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        BarangHariIniAdapter adapter = new BarangHariIniAdapter(barangHariIniArrayList, getContext());
        barangHariIni.setLayoutManager(layoutManager);
        barangHariIni.setAdapter(adapter);
    }
}
