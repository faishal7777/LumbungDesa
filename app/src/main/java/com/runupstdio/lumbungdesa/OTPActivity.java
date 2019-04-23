package com.runupstdio.lumbungdesa;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.Register;
import com.runupstdio.lumbungdesa.Model.UserExist;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    LinearLayout mBg, mWelcomeTxt;
    CardView mCardOtp, mCardViewToolbar;
    Pinview mOtpPinview;

    Animation fromBottom, fromTop, bgAnim;

    private FirebaseAuth mAuth;
    IApiClient mApiClient;
    private String mVerificationId;
    private String mTipeMasuk = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        mApiClient = ApiClient.getClient().create(IApiClient.class);

        //Anim
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        bgAnim = AnimationUtils.loadAnimation(this, R.anim.bg_anim);

        mCardOtp = findViewById(R.id.cardOtp);
        mCardOtp.startAnimation(fromBottom);

        //Animate bg
        mBg = findViewById(R.id.bgOtp);
        mBg.startAnimation(bgAnim);

        //Animate fromTop
        mWelcomeTxt = findViewById(R.id.txtOtp);
        mWelcomeTxt.startAnimation(fromTop);

        mCardViewToolbar = findViewById(R.id.cardViewToolbarOtp);
        mCardViewToolbar.startAnimation(fromTop);

        mOtpPinview = findViewById(R.id.otpPinview);
        mOtpPinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                Toast.makeText(OTPActivity.this, ""+mOtpPinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        String msisdn = getIntent().getStringExtra("msisdn");
        checkUserExist(msisdn);
        sendVerificationCode(msisdn);

        Button mBtnVerifOtp = findViewById(R.id.btnVerifOtp);
        mBtnVerifOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mOtpPinview.getValue().trim();
                Toast.makeText(OTPActivity.this, ""+code, Toast.LENGTH_SHORT).show();
                verifyVerificationCode(code);
                //Intent intent = new Intent(OTPActivity.this, NavigationBar.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
            }
        });
    }

    private void sendVerificationCode(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+62" + no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void checkUserExist(String no) {
        Call<UserExist> checkCall = mApiClient.isUserExist("+62"+no);
        checkCall.enqueue(new Callback<UserExist>() {
            @Override
            public void onResponse(Call<UserExist> call, Response<UserExist> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        mTipeMasuk = "masuk";
                    } else {
                        mTipeMasuk = "daftar";
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(OTPActivity.this, errorBody, Toast.LENGTH_LONG).show();
                    Intent daftar = new Intent(OTPActivity.this, NavigationBar.class);
                    startActivity(daftar);
                }
            }

            @Override
            public void onFailure(Call<UserExist> call, Throwable t) {
                Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Intent daftar = new Intent(OTPActivity.this, NavigationBar.class);
                startActivity(daftar);
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            final String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                mOtpPinview.setValue(code);
                //verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent;
                            if(mTipeMasuk.equals("masuk")) intent = new Intent(OTPActivity.this, NavigationBar.class);
                            else intent = new Intent(OTPActivity.this, Signup2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(OTPActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
