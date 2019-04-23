package com.runupstdio.lumbungdesa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runupstdio.lumbungdesa.Adapter.PenjualanAdapter;
import com.runupstdio.lumbungdesa.Model.Tagihan;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PenjualanFragment extends Fragment {

    RecyclerView mRVListPenjualan;
    List<Tagihan> mPenjualan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_penjualan, null);

        mPenjualan = new ArrayList<>();
        mPenjualan.add(new Tagihan("Lentho Bakar", "Rp 12.000", "https://picsum.photos/50/?random", "0"));
        mPenjualan.add(new Tagihan("Ayam Goreng", "Rp 36.000", "https://picsum.photos/50/?random", "1"));
        mPenjualan.add(new Tagihan("Buah Khuldi", "Rp 6.000", "https://picsum.photos/50/?random", "2"));

        mRVListPenjualan = v.findViewById(R.id.rvPenjualan);

        initRecyclerView();

        return v;
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        PenjualanAdapter adapter = new PenjualanAdapter(mPenjualan, getContext());
        mRVListPenjualan.setLayoutManager(layoutManager);
        mRVListPenjualan.setAdapter(adapter);
    }

}
