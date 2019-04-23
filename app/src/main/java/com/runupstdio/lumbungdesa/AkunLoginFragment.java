package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Adapter.UserChatListAdapter;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Profile;
import com.runupstdio.lumbungdesa.Model.UserExist;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

public class AkunLoginFragment extends Fragment {

    TextView mProfileName, mProfileStatus;
    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String idToken = null;
    RoundedImageView mProfileAva;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, null);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);
        settings = this.getActivity().getSharedPreferences("RUNUP", getContext().MODE_PRIVATE);

        mProfileName = v.findViewById(R.id.profile_name);
        mProfileStatus = v.findViewById(R.id.profile_status);
        mProfileAva = v.findViewById(R.id.profile_ava);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken = task.getResult().getToken();
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        } else {
            Intent intent = new Intent(getActivity(), NavigationBar.class);
            startActivity(intent);
        }

        setUserData();

        Button mJualProduk = v.findViewById(R.id.btnJualProduk);
        mJualProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mJual = new Intent(v.getContext(), JualActivity.class);
                startActivity(mJual);
            }
        });

        LinearLayout mChat = v.findViewById(R.id.listChat);
        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(v.getContext(), ListChatActivity.class);
                startActivity(chat);
            }
        });

        LinearLayout mKeluar = v.findViewById(R.id.keluar);
        mKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mJual = new Intent(v.getContext(), NavigationBar.class);
                startActivity(mJual);
            }
        });

        return v;
    }

    private void setUserData()
    {
        int id = settings.getInt("ID", 0);
        mProfileName.setText(settings.getString("ProfileName", "User"));
        mProfileStatus.setText(settings.getString("ProfileStatus", "Unverified"));
        Glide.with(getContext())
                .asBitmap()
                .load(settings.getString("ProfileAva", "https://cdn.pixabay.com/photo/2017/02/23/13/05/profile-2092113_960_720.png"))
                .into(mProfileAva);
    }
}
