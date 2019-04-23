package com.runupstdio.lumbungdesa;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.runupstdio.lumbungdesa.Adapter.PembelianAdapter;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

public class PembelianFragment extends Fragment {

    RecyclerView mRVListPembelian;
    List<Tagihan> mPembelian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pembelian, null);

        ShimmerFrameLayout mShimmerPembelian = v.findViewById(R.id.shimmerPembelian);
        mShimmerPembelian.startShimmerAnimation();

        mPembelian = new ArrayList<>();
        mPembelian.add(new Tagihan("Lentho Bakar", "Rp 12.000", "https://picsum.photos/50/?random", "0"));
        mPembelian.add(new Tagihan("Ayam Goreng", "Rp 36.000", "https://picsum.photos/50/?random", "1"));
        mPembelian.add(new Tagihan("Buah Khuldi", "Rp 6.000", "https://picsum.photos/50/?random", "1"));

        mRVListPembelian = v.findViewById(R.id.rvPembelian);

        initRecyclerView();

        return v;
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        PembelianAdapter adapter = new PembelianAdapter(mPembelian, getContext());
        mRVListPembelian.setLayoutManager(layoutManager);
        mRVListPembelian.setAdapter(adapter);
    }
}
