package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

public class BerandaFragment extends Fragment {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};

    RecyclerView barangHariIni;


    //array-list
    private ArrayList<String> mImgProductUrl = new ArrayList<>();
    private ArrayList<String> mProductName = new ArrayList<>();
    private ArrayList<String> mProductPrice = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_beranda, null);

        final SwipeRefreshLayout refreshBeranda = view.findViewById(R.id.refreshBeranda);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                refreshBeranda.setRefreshing(false);
            }
        });

        carouselView = view.findViewById(R.id.carouselBeranda);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        barangHariIni = view.findViewById(R.id.BarangHariIni);
//        testing = view.findViewById(R.id.testing);
//        testing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gobuy = new Intent(v.getContext(), ProductClickedActivity.class);
//                startActivity(gobuy);
//            }
//        });
        getImageBitmaps();
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void getImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing image");

        mImgProductUrl.add("https://pbs.twimg.com/profile_images/500471438155841537/JqaBWfYK.jpeg");
        mProductName.add("Havasu Falls");
        mProductPrice.add("Rp 100000");

        mImgProductUrl.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mProductName.add("Trondheim");
        mProductPrice.add("Rp 200000");

        mImgProductUrl.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mProductName.add("Portugal");
        mProductPrice.add("Rp 300000");

        mImgProductUrl.add("https://i.redd.it/j6myfqglup501.jpg");
        mProductName.add("Rocky Mountain National Park");
        mProductPrice.add("Rp 400000");

        mImgProductUrl.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mProductName.add("Mahahual");
        mProductPrice.add("Rp 500000");

        mImgProductUrl.add("https://i.redd.it/k98uzl68eh501.jpg");
        mProductName.add("Frozen Lake");
        mProductPrice.add("Rp 600000");

        mImgProductUrl.add("https://i.redd.it/glin0nwndo501.jpg");
        mProductName.add("White Sands Desert");
        mProductPrice.add("Rp 700000");

        mImgProductUrl.add("https://i.redd.it/obx4zydshg601.jpg");
        mProductName.add("Austrailia");
        mProductPrice.add("Rp 800000");

        mImgProductUrl.add("https://i.imgur.com/ZcLLrkY.jpg");
        mProductName.add("Washington");
        mProductPrice.add("Rp 900000");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init the recycler view");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        barangHariIni.setLayoutManager(layoutManager);
        BarangHariIniAdapter adapter = new BarangHariIniAdapter(getContext(), mProductName, mImgProductUrl, mProductPrice);
        barangHariIni.setAdapter(adapter);
    }
}
