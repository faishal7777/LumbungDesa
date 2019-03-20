package com.runupstdio.lumbungdesa;

import android.app.Activity;
import android.content.Intent;
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

public class OTPActivity extends AppCompatActivity {

    LinearLayout mBg, mWelcomeTxt;
    CardView mCardOtp, mCardViewToolbar;
    Pinview mOtpPinview;

    Animation fromBottom, fromTop, bgAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

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

        Bundle b = getIntent().getExtras();
        final String mTipeMasuk = b.getString("tipe");

        Button mBtnVerifOtp = findViewById(R.id.btnVerifOtp);
        mBtnVerifOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar, masuk;
                masuk = new Intent(OTPActivity.this, NavigationBar.class);
                daftar = new Intent(OTPActivity.this, Signup2Activity.class);

                if(mTipeMasuk.equals("masuk"))
                    startActivity(masuk);
                else
                    startActivity(daftar);
            }
        });
    }
}
