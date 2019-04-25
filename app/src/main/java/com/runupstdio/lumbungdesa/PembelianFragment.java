package com.runupstdio.lumbungdesa;
import android.content.Intent;
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
import com.runupstdio.lumbungdesa.Adapter.PembelianAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Address;
import com.runupstdio.lumbungdesa.Model.Done;
import com.runupstdio.lumbungdesa.Model.History;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mShimmerPembelian = v.findViewById(R.id.shimmerPembelian);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        mPembelian = new ArrayList<>();
        for(int i=0; i<6; i++){
            mPembelian.add(new Tagihan(1,"Unknown", "Rp 0", null, "Unknown"));
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

        final SwipeRefreshLayout refreshBeranda = v.findViewById(R.id.refreshPembelian);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShimmerPembelian.startShimmerAnimation();
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
                            if(feedInfo.getData().get(i).getPaid().equals("1") && feedInfo.getData().get(i).getDelivered().equals("0")) tempStatus = "Dikirim";
                            else if(feedInfo.getData().get(i).getPaid().equals("1") && feedInfo.getData().get(i).getDelivered().equals("1")) tempStatus = "Selesai";
                            Log.d("Pembelian", ""+feedInfo.getData().get(i).getPaid());
                            mPembelian.add(new Tagihan(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProducts().get(0).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getPriceTotal()))), prodUrl, tempStatus));
                        }
                        initRecyclerView();
                        mShimmerPembelian.stopShimmerAnimation();
                    } else {

                    }
                });
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        PembelianAdapter adapter = new PembelianAdapter(mPembelian, getContext(), this);
        mRVListPembelian.setLayoutManager(layoutManager);
        mRVListPembelian.setAdapter(adapter);
    }

    public void complateTrx(int idtrx){
        Call<Done> addrCall = mApiClient.done("Bearer "+idToken, idtrx);
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
