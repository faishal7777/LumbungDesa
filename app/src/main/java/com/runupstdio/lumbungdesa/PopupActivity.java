package com.runupstdio.lumbungdesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class PopupActivity extends DialogFragment {

    Button mOke;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_popup, null);
        builder.setView(view);

        mOke = view.findViewById(R.id.btn_Oke_Checkout);
        mOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), NavigationBar.class);
                startActivity(a);
            }
        });

        return builder.create();
    }
}
