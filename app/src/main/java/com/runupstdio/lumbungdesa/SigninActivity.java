package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

public class SigninActivity extends AppCompatActivity {

    TextView mReg;
    EditText telpon;
    LinearLayout mBottomTxt, mBg, mWelcomeTxt;
    CardView mCardNumber, mCardViewToolbar;

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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Anim
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);
        bgAnim = AnimationUtils.loadAnimation(this, R.anim.bg_anim);

        mBottomTxt = findViewById(R.id.bottomTxtSignIn);
        mBottomTxt.startAnimation(fromBottom);

        mCardNumber = findViewById(R.id.cardNumberSignIn);
        mCardNumber.startAnimation(fromBottom);

        //Animate bg
        mBg = findViewById(R.id.bgSignIn);
        mBg.startAnimation(bgAnim);

        //Animate fromTop
        mWelcomeTxt = findViewById(R.id.welcomeTxtSignIn);
        mWelcomeTxt.startAnimation(fromTop);

        mCardViewToolbar = findViewById(R.id.cardViewToolbarSignIn);
        mCardViewToolbar.startAnimation(fromTop);

        mReg = findViewById(R.id.btnLoginReg);
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(a);
                finish();
            }
        });

        telpon = findViewById(R.id.noTelpon2);

        //set listener
        telpon.addTextChangedListener(textWatcher);

        // run once to disable if empty
        checkFieldsForEmptyValues();
    }

    private  void checkFieldsForEmptyValues(){
        Button mLogin = findViewById(R.id.btnLogin);
        final String noTelpon = telpon.getText().toString();

        if(noTelpon.equals("") && noTelpon.length() < 10)
        {
            mLogin.setEnabled(false);
            mLogin.setBackground(this.getResources().getDrawable(R.drawable.btn_login_disabled));
        }

        else
        {
            mLogin.setEnabled(true);
            mLogin.setBackground(this.getResources().getDrawable(R.drawable.btn_login));

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tipeMasuk = new Intent(SigninActivity.this, OTPActivity.class);
                    tipeMasuk.putExtra("msisdn", noTelpon);
                    startActivity(tipeMasuk);
                }
            });
        }
    }
}
