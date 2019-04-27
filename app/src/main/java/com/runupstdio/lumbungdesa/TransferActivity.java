package com.runupstdio.lumbungdesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity {

    TextView mTransferExp, mTransferNominal, mTransferInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
    }
}
