package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
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
    int[] sampleImages = {R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img};

    RecyclerView barangHariIni;

    List<BarangHariIni> barangHariIniArrayList;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private Boolean isLoggedIn = false;

    //array-list
//    private ArrayList<String> mImgProductUrl = new ArrayList<>();
//    private ArrayList<String> mProductName = new ArrayList<>();
//    private ArrayList<String> mProductPrice = new ArrayList<>();

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

        final SwipeRefreshLayout refreshBeranda = view.findViewById(R.id.refreshBeranda);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                setFeedData();
                refreshBeranda.setRefreshing(false);
            }
        });

        carouselView = view.findViewById(R.id.carouselBeranda);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        barangHariIni = view.findViewById(R.id.BarangHariIni);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

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

//        testing = view.findViewById(R.id.testing);
//        testing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gobuy = new Intent(v.getContext(), ProductClickedActivity.class);
//                startActivity(gobuy);
//            }
//        });

        return view;
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
                            barangHariIniArrayList.add(new BarangHariIni(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+feedInfo.getData().get(i).getProductPrice(), feedInfo.getData().get(i).getAvaProduct()));
                        }

                        initRecyclerView();
                    } else {

                    }
                });
    }

//    private void getImageBitmaps(){
//
//        mImgProductUrl.add("https://pbs.twimg.com/profile_images/500471438155841537/JqaBWfYK.jpeg");
//        mProductName.add("Havasu Falls");
//        mProductPrice.add("Rp 100000");
//
//        mImgProductUrl.add("https://i.redd.it/tpsnoz5bzo501.jpg");
//        mProductName.add("Trondheim");
//        mProductPrice.add("Rp 200000");
//
//        mImgProductUrl.add("https://i.redd.it/qn7f9oqu7o501.jpg");
//        mProductName.add("Portugal");
//        mProductPrice.add("Rp 300000");
//
//        mImgProductUrl.add("https://i.redd.it/j6myfqglup501.jpg");
//        mProductName.add("Rocky Mountain National Park");
//        mProductPrice.add("Rp 400000");
//
//        mImgProductUrl.add("https://i.redd.it/0h2gm1ix6p501.jpg");
//        mProductName.add("Mahahual");
//        mProductPrice.add("Rp 500000");
//
//        mImgProductUrl.add("https://i.redd.it/k98uzl68eh501.jpg");
//        mProductName.add("Frozen Lake");
//        mProductPrice.add("Rp 600000");
//
//        mImgProductUrl.add("https://i.redd.it/glin0nwndo501.jpg");
//        mProductName.add("White Sands Desert");
//        mProductPrice.add("Rp 700000");
//
//        mImgProductUrl.add("https://i.redd.it/obx4zydshg601.jpg");
//        mProductName.add("Austrailia");
//        mProductPrice.add("Rp 800000");
//
//        mImgProductUrl.add("https://i.imgur.com/ZcLLrkY.jpg");
//        mProductName.add("Washington");
//        mProductPrice.add("Rp 900000");
//
//        initRecyclerView();
//    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        BarangHariIniAdapter adapter = new BarangHariIniAdapter(barangHariIniArrayList, getContext());
        barangHariIni.setLayoutManager(layoutManager);
        barangHariIni.setAdapter(adapter);
    }
}
