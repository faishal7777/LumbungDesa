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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.runupstdio.lumbungdesa.Adapter.BarangHariIniAdapter;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment {

    ConstraintLayout mbuahBiji;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img, R.drawable.banner_img};

    RecyclerView barangHariIni;

    List<BarangHariIni> barangHariIniArrayList;

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

        barangHariIniArrayList = new ArrayList<>();
        barangHariIniArrayList.add(new BarangHariIni("Telur Ayam Broiler", "Rp 12.000","https://i.redd.it/tpsnoz5bzo501.jpg"));
        barangHariIniArrayList.add(new BarangHariIni("Jeruk Manis", "Rp 10.000","https://i.redd.it/qn7f9oqu7o501.jpg"));
        barangHariIniArrayList.add(new BarangHariIni("Sayur Kubis", "Rp 4.000","https://i.redd.it/obx4zydshg601.jpg"));

        initRecyclerView();
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        BarangHariIniAdapter adapter = new BarangHariIniAdapter(barangHariIniArrayList, getContext());
        barangHariIni.setLayoutManager(layoutManager);
        barangHariIni.setAdapter(adapter);
    }
}
