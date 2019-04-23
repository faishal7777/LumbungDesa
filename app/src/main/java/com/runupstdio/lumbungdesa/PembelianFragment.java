package com.runupstdio.lumbungdesa;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.runupstdio.lumbungdesa.Adapter.PembelianAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PembelianFragment extends Fragment {

    RecyclerView mRVListPembelian;
    List<Tagihan> mPembelian;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    ShimmerFrameLayout mShimmerPembelian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pembelian, null);

        mRVListPembelian = v.findViewById(R.id.rvPembelian);
        mShimmerPembelian = v.findViewById(R.id.shimmerTagihan);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mPembelian = new ArrayList<>();
        for(int i=0; i<6; i++){
            mPembelian.add(new Tagihan("Unknown", "Rp 0", null, "Unknown"));
        }
        initRecyclerView();
        mShimmerPembelian.startShimmerAnimation();

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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerPembelian.startShimmerAnimation();
        setFeedData();
    }

    @Override
    public void onPause() {
        mShimmerPembelian.stopShimmerAnimation();
        super.onPause();
    }

    private void setFeedData(){
        mPembelian = new ArrayList<>();
        Observable<History> london = mApiClient.get_history("Bearer "+idToken, "PAID");

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            List<String> prodUrl = new ArrayList<>();
                            String tempStatus = null;
                            for(int j=0; j<feedInfo.getData().get(i).getProducts().size(); j++){
                                if(j>2) break;
                                prodUrl.add(feedInfo.getData().get(i).getProducts().get(j).getAvaProduct());
                            }
                            if(Boolean.parseBoolean(feedInfo.getData().get(i).getPaid())) tempStatus = "Dibayar";
                            else if(Boolean.parseBoolean(feedInfo.getData().get(i).getDelivered())) tempStatus = "Selesai";
                            mPembelian.add(new Tagihan(feedInfo.getData().get(i).getProducts().get(0).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotal()))), prodUrl, tempStatus));
                        }
                        initRecyclerView();
                        Log.d("Tagihan", ""+mPembelian.get(0).getProductStatus());
                        mShimmerPembelian.stopShimmerAnimation();
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        PembelianAdapter adapter = new PembelianAdapter(mPembelian, getContext());
        mRVListPembelian.setLayoutManager(layoutManager);
        mRVListPembelian.setAdapter(adapter);
    }
}
