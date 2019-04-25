package com.runupstdio.lumbungdesa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.runupstdio.lumbungdesa.Adapter.PenjualanAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Done;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Penjualan;
import com.runupstdio.lumbungdesa.Model.Tagihan;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PenjualanFragment extends Fragment {

    RecyclerView mRVListPenjualan;
    List<Tagihan> mPenjualan;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    ShimmerFrameLayout mShimmerPenjualan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_penjualan, null);

        mRVListPenjualan = v.findViewById(R.id.rvPenjualan);
        mShimmerPenjualan = v.findViewById(R.id.shimmerPenjualan);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mPenjualan = new ArrayList<>();
        for(int i=0; i<6; i++){
            mPenjualan.add(new Tagihan(1, "Unknown", "Rp 0", null, "Unknown"));
        }
        initRecyclerView();
        mShimmerPenjualan.startShimmerAnimation();

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

        final SwipeRefreshLayout refreshBeranda = v.findViewById(R.id.refreshPenjualan);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerPenjualan.startShimmerAnimation();
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
        mPenjualan = new ArrayList<>();
        Observable<Penjualan> london = mApiClient.get_history_penjualan("Bearer "+idToken, "SALE");

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            List<String> prodUrl = new ArrayList<>();
                            String tempStatus = null;
                            prodUrl.add(feedInfo.getData().get(i).getAvaProduct());
                            if(feedInfo.getData().get(i).getCheckedOut().equals("1") && feedInfo.getData().get(i).getPaid().equals("0") && feedInfo.getData().get(i).getShipped().equals("0") && feedInfo.getData().get(i).getDelivered().equals("0")) tempStatus = "Belum Bayar";
                            else if(feedInfo.getData().get(i).getCheckedOut().equals("1") && feedInfo.getData().get(i).getPaid().equals("1") && feedInfo.getData().get(i).getShipped().equals("0") && feedInfo.getData().get(i).getDelivered().equals("0")) tempStatus = "Dibayar";
                            else if(feedInfo.getData().get(i).getCheckedOut().equals("1") && feedInfo.getData().get(i).getPaid().equals("1") && feedInfo.getData().get(i).getShipped().equals("1") && feedInfo.getData().get(i).getDelivered().equals("0")) tempStatus = "Dikirim";
                            else if(feedInfo.getData().get(i).getCheckedOut().equals("1") && feedInfo.getData().get(i).getPaid().equals("1") && feedInfo.getData().get(i).getShipped().equals("1") && feedInfo.getData().get(i).getDelivered().equals("1")) tempStatus = "Sukses";
                            Log.d("Pembelian", ""+tempStatus);
                            mPenjualan.add(new Tagihan(Integer.parseInt(feedInfo.getData().get(i).getIdTransaction()), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotals()))), prodUrl, tempStatus));
                        }
                        initRecyclerView();
                        mShimmerPenjualan.stopShimmerAnimation();
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        PenjualanAdapter adapter = new PenjualanAdapter(mPenjualan, getContext(), this);
        mRVListPenjualan.setLayoutManager(layoutManager);
        mRVListPenjualan.setAdapter(adapter);
    }

    public void complateTrx(int idtrx){
        Call<Done> addrCall = mApiClient.accept("Bearer "+idToken, idtrx);
        addrCall.enqueue(new Callback<Done>() {
            @Override
            public void onResponse(Call<Done> call, Response<Done> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Intent a = new Intent(getContext(), DetilActivity.class);
                        a.putExtra("idTrx", idtrx);
                        startActivity(a);
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(getContext(), errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Done> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
