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
import com.runupstdio.lumbungdesa.Adapter.TagihanAdapter;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

public class TagihanFragment extends Fragment {

    RecyclerView mRVListTagihan;
    List<Tagihan> mTagihan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tagihan, null);

        mTagihan = new ArrayList<>();
        mTagihan.add(new Tagihan("Telur Ayam Broiler", "Rp 12.000", "https://picsum.photos/50/?random", "0"));
        mTagihan.add(new Tagihan("Jeruk Manis", "Rp 6.000", "https://picsum.photos/50/?random", "1"));
        mTagihan.add(new Tagihan("Sayur Kubis", "Rp 4.000", "https://picsum.photos/50/?random", "1"));

        mRVListTagihan = v.findViewById(R.id.rvTagihan);

        initRecyclerView();

        return v;
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        TagihanAdapter adapter = new TagihanAdapter(mTagihan, getContext());
        mRVListTagihan.setLayoutManager(layoutManager);
        mRVListTagihan.setAdapter(adapter);
    }
}
