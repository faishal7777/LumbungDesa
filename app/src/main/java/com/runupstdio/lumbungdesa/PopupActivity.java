package com.runupstdio.lumbungdesa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class PopupActivity extends DialogFragment {

    Button mOke;
    String szType = "COD";
    int idTrx = 0;
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
                if(szType.equals("COD")) {
                    Intent a = new Intent(getActivity(), NavigationBar.class);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                } else {
                    Intent a = new Intent(getActivity(), TransferActivity.class);
                    a.putExtra("idTrx", idTrx);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                }
            }
        });

        return builder.create();
    }

    public void setType(String type, int idTrx){
        this.szType = type;
        this.idTrx = idTrx;
    }
}
