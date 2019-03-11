package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class SignupActivity extends AppCompatActivity {

    EditText telpon;
    TextView mLogin;
    LinearLayout mBottomTxt, mBg, mWelcomeTxt;
    CardView mCardViewToolbar, mCardNumber;

    Animation fromBottom, fromTop, bgAnim;

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Anim
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        bgAnim = AnimationUtils.loadAnimation(this, R.anim.bg_anim);

        //Animate fromBottom
        mBottomTxt = findViewById(R.id.bottomTxtSignUp);
        mBottomTxt.startAnimation(fromBottom);

        mCardNumber = findViewById(R.id.cardNumberSignUp);
        mCardNumber.startAnimation(fromBottom);

        //Animate bg
        mBg = findViewById(R.id.bgSignUp);
        mBg.startAnimation(bgAnim);

        //Animate fromTop
        mWelcomeTxt = findViewById(R.id.welcomeTxtSignUp);
        mWelcomeTxt.startAnimation(fromTop);

        mCardViewToolbar = findViewById(R.id.cardViewToolbarSignUp);
        mCardViewToolbar.startAnimation(fromTop);

        mLogin = findViewById(R.id.loginReg);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(SignupActivity.this, OTPActivity.class);
                startActivity(b);
                finish();
            }
        });

        telpon = findViewById(R.id.noTelponSignUp);

        //set listener
        telpon.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    private  void checkFieldsForEmptyValues(){
        Button btnRegLanjut = findViewById(R.id.btnRegLanjut);
        final String noTelpon = telpon.getText().toString();

        if(noTelpon.equals(""))
        {
            btnRegLanjut.setEnabled(false);
            btnRegLanjut.setBackground(this.getResources().getDrawable(R.drawable.btn_login_disabled));
        }

        else
        {
            btnRegLanjut.setEnabled(true);
            btnRegLanjut.setBackground(this.getResources().getDrawable(R.drawable.btn_login));

            btnRegLanjut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(SignupActivity.this, "62" + noTelpon, Toast.LENGTH_LONG).show();
                        Intent a = new Intent(SignupActivity.this, OTPActivity.class);
                        startActivity(a);
                }
            });

        }
    }
}
