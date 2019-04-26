package com.runupstdio.lumbungdesa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Done;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.Register;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationBar extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        settings = getSharedPreferences("RUNUP", getApplicationContext().MODE_PRIVATE);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                                setUserData();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {

        }

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new BerandaFragment());
    }

    private void setUserData()
    {
        Observable<Profile> london = mApiClient.user_info("Bearer "+idToken);

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfo -> {
                    Log.e("Current Weather", idToken);
                    if(userInfo.getStatus()){
                        editor = settings.edit();
                        editor.putString("ProfileName", userInfo.getData().getName());
                        editor.putString("ProfilCity", userInfo.getData().getAddress().getCity());
                        if(userInfo.getData().getIsVerified().equals("1")) editor.putString("ProfileStatus", "Verified");
                        else editor.putString("ProfileStatus", "Unverified");
                        editor.putString("ProfileAva", userInfo.getData().getAvaUrl());
                        editor.commit();
                    } else {

                    }
                });

        Call<Done> daftarCall = mApiClient.fcm("Bearer "+idToken, settings.getString("notifToken", "null"));
        daftarCall.enqueue(new Callback<Done>() {
            @Override
            public void onResponse(Call<Done> call, Response<Done> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Log.d("updateNotifToken", ""+response.body().getMessage()+"|"+settings.getString("notifToken", "null"));
                    } else {
                        Log.d("updateNotifToken", ""+response.body().getMessage()+"|"+settings.getString("notifToken", "null"));
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Log.d("updateNotifToken", ""+response.body().getMessage()+"|"+settings.getString("notifToken", "null"));
                }
            }

            @Override
            public void onFailure(Call<Done> call, Throwable t) {
                Log.d("updateNotifToken", ""+settings.getString("notifToken", "null"));
            }
        });
    }

    private boolean loadFragment (Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_beranda:
                setTheme(R.style.AppTheme);
                fragment = new BerandaFragment();
                break;

            case R.id.nav_temukan:
                setTheme(R.style.AppTheme);
                fragment = new TemukanFragment();
                break;

            case R.id.nav_transaksi:
                setTheme(R.style.AppTheme);
                fragment = new TransaksiFragment();
                break;

            case R.id.nav_akun:
                setTheme(R.style.AppTheme);

                SharedPreferences prefs = getSharedPreferences("Login", MODE_PRIVATE);
                Boolean restoredText = prefs.getBoolean("isLogin", false);
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    fragment = new AkunLoginFragment();
                }
                else fragment = new AkunFragment();

                break;
        }
        return loadFragment(fragment);
    }
}
