package com.runupstdio.lumbungdesa;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Adapter.TagihanAdapter;
import com.runupstdio.lumbungdesa.Adapter.TemukanAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Feed;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TemukanFragment extends Fragment {

    RecyclerView mRVListTemukan;
    List<BarangHariIni> mTemukan;
    SharedPreferences settings;
    TextView mGreeting, mCity;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    private Boolean isLoggedIn = false;
    SwipeRefreshLayout refreshBeranda;
    ViewLoad viewLoad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_temukan, null);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);
        settings = this.getActivity().getSharedPreferences("RUNUP", getContext().MODE_PRIVATE);
        mRVListTemukan = v.findViewById(R.id.rvTemukan);
        mGreeting = v.findViewById(R.id.temukan_greeting);
        mCity = v.findViewById(R.id.temukan_city);

        viewLoad = new ViewLoad(getActivity());
        viewLoad.showDialog();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                isLoggedIn = true;
                                setFeedData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            isLoggedIn = false;
            setFeedData();
        }

        refreshBeranda = v.findViewById(R.id.refreshTemukan);
        refreshBeranda.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                setFeedData();
//
//                barangHariIni.setVisibility(View.GONE);
//                mShimmerPembelian.setVisibility(View.VISIBLE);
//                mShimmerPembelian.startShimmerAnimation();
            }
        });

        return v;
    }

    private void setFeedData(){
        mTemukan = new ArrayList<>();
        Observable<Feed> london;
        if(isLoggedIn) london = mApiClient.feed_home("Bearer "+idToken);
        else london = mApiClient.feed_index();

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feedInfo -> {
                    if(feedInfo.getStatus()){
                        for(int i=0; i<feedInfo.getData().size(); i++){
                            mTemukan.add(new BarangHariIni(feedInfo.getData().get(i).getId(), feedInfo.getData().get(i).getProductName(), "Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(feedInfo.getData().get(i).getProductPrice()))), feedInfo.getData().get(i).getAvaProduct()));
                        }
                        initRecyclerView();
                        viewLoad.hideDialog();
                    } else {

                    }
                });

        mGreeting.setText("Selamat datang, "+settings.getString("ProfileName", "User"));
        mCity.setText(settings.getString("ProfilCity", "Kota"));
        refreshBeranda.setRefreshing(false);
    }

    public void showCustomLoadingDialog() {
        //..show gif
        viewLoad.showDialog();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //...here i'm waiting 5 seconds before hiding the custom dialog
                //...you can do whenever you want or whenever your work is done
                viewLoad.hideDialog();
                if(mTemukan != null){
                    viewLoad.hideDialog();
                }
            }
        }, 5000);
    }

    private void initRecyclerView(){
        TemukanAdapter adapter = new TemukanAdapter(mTemukan, getContext());
        mRVListTemukan.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRVListTemukan.setAdapter(adapter);
    }

}
