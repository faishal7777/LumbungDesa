package com.runupstdio.lumbungdesa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KeranjangActivity extends AppCompatActivity {

    Button mBeliSekarang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        mBeliSekarang = findViewById(R.id.btn_beliSekarang);
        mBeliSekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjutBeli = new Intent(KeranjangActivity.this, CheckoutActivity.class);
                startActivity(lanjutBeli);
            }
        });
    }
}
