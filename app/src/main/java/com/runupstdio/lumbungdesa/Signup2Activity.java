package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup2Activity extends AppCompatActivity {

    Button mbtnBuatAkun;
    EditText mName, mState, mCity, mKecamatan, mRoad;
    IApiClient mApiClient;

    private String idToken = null;
    private String msisdn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        mbtnBuatAkun = findViewById(R.id.btnBuatAkun);
        mName = findViewById(R.id.signup_name);
        mState = findViewById(R.id.signup_state);
        mCity = findViewById(R.id.signup_city);
        mKecamatan = findViewById(R.id.signup_kecamatan);
        mRoad = findViewById(R.id.signup_road);

        mApiClient = ApiClient.getClient().create(IApiClient.class);

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
            msisdn = mUser.getPhoneNumber();
        } else {
            Intent intent = new Intent(Signup2Activity.this, NavigationBar.class);
            startActivity(intent);
        }

        mbtnBuatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String szMsisdn = msisdn;
                final String szName = mName.getText().toString();
                String szCountry = "Indonesia";
                String szState = mState.getText().toString();
                String szCity = mCity.getText().toString();
                String szKecamatan = mKecamatan.getText().toString();
                String szDesa = "Village";
                String szRoad = mRoad.getText().toString();

                Call<Register> daftarCall = mApiClient.doRegister("Bearer "+idToken, szMsisdn, szName, szCountry, szState, szCity, szKecamatan, szDesa, szRoad);
                daftarCall.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                Intent daftar = new Intent(Signup2Activity.this, NavigationBar.class);
                                startActivity(daftar);
                            } else {
                                mState.setText(idToken);
                                Toast.makeText(Signup2Activity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else if (response.errorBody() != null) {
                            // Get response errorBody
                            String errorBody = response.errorBody().toString();
                            Toast.makeText(Signup2Activity.this, errorBody, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(Signup2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
