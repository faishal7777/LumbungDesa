package com.runupstdio.lumbungdesa;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.runupstdio.lumbungdesa.Adapter.TagihanAdapter;
import com.runupstdio.lumbungdesa.Adapter.TemukanAdapter;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

public class TemukanFragment extends Fragment {

    RecyclerView mRVListTemukan;
    List<BarangHariIni> mTemukan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_temukan, null);

        mTemukan = new ArrayList<>();
        mTemukan.add(new BarangHariIni(12, "Lentho Bakar", "Rp 20.000", "https://picsum.photos/50/?random"));
        mTemukan.add(new BarangHariIni(13, "Ndog Jaran", "Rp 10.000", "https://picsum.photos/50/?random"));
        mTemukan.add(new BarangHariIni(14, "KONTOL", "Rp 40.000", "https://picsum.photos/50/?random"));
        mTemukan.add(new BarangHariIni(15, "Gasido Tuku lurd", "Rp 2.000", "https://picsum.photos/50/?random"));

        mRVListTemukan = v.findViewById(R.id.rvTagihan);

        initRecyclerView();

        return v;
    }

    private void initRecyclerView(){
        TemukanAdapter adapter = new TemukanAdapter(mTemukan, getContext());
        mRVListTemukan.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRVListTemukan.setAdapter(adapter);
    }

}
