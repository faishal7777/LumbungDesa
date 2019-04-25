package com.runupstdio.lumbungdesa;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.TagihanAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TagihanFragment extends Fragment {

    RecyclerView mRVListTagihan;
    List<Tagihan> mTagihan;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    ShimmerFrameLayout mShimmerTagihan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tagihan, null);

        mShimmerTagihan = v.findViewById(R.id.shimmerTagihan);
        mRVListTagihan = v.findViewById(R.id.rvTagihan);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mTagihan = new ArrayList<>();
        for(int i=0; i<6; i++){
            mTagihan.add(new Tagihan(1,"Unknown", "Rp 0", null, "Unknown"));
        }
        initRecyclerView();
        mShimmerTagihan.startShimmerAnimation();

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            Intent a = new Intent(getActivity(), NavigationBar.class);
            startActivity(a);
            Toast.makeText(getActivity(), "Silahkan masuk terlebih dahulu", Toast.LENGTH_LONG).show();

        }

        final SwipeRefreshLayout refreshBeranda = v.findViewById(R.id.refreshTagihan);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerTagihan.startShimmerAnimation();
                setFeedData();
                refreshBeranda.setRefreshing(false);
//
//                barangHariIni.setVisibility(View.GONE);
//                mShimmerPembelian.setVisibility(View.VISIBLE);
//                mShimmerPembelian.startShimmerAnimation();
            }
        });

        return v;
    }

    private void setFeedData(){
        mTagihan = new ArrayList<>();
        Observable<History> london = mApiClient.get_history("Bearer "+idToken, "TAGIHAN");

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            List<String> prodUrl = new ArrayList<>();
                            for(int j=0; j<feedInfo.getData().get(i).getProducts().size(); j++){
                                if(j>2) break;
                                prodUrl.add(feedInfo.getData().get(i).getProducts().get(j).getAvaProduct());
                            }
                            mTagihan.add(new Tagihan(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProducts().get(0).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotal()))), prodUrl, "Belum Bayar"));
                        }
                        initRecyclerView();
                        mShimmerTagihan.stopShimmerAnimation();
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        TagihanAdapter adapter = new TagihanAdapter(mTagihan, getContext());
        mRVListTagihan.setLayoutManager(layoutManager);
        mRVListTagihan.setAdapter(adapter);
    }
}
