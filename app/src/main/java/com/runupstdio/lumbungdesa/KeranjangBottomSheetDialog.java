package com.runupstdio.lumbungdesa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.makeramen.roundedimageview.RoundedImageView;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.Model.AddCart;
import com.runupstdio.lumbungdesa.Model.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangBottomSheetDialog extends BottomSheetDialogFragment {
    RoundedImageView mProductAva;
    int mProductId = 0;
    int iProductPrice = 0;
    TextView mProductName, mProductPrice, mProductQty, mProductSubtotal;
    Button mBtnCartDecrease, mBtnCartIncrease;

    IApiClient mApiClient;
    private String idToken = null;
    private int prodprice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.keranjang_sheet_layout, container, false);

        mProductAva = v.findViewById(R.id.sheetproduct_ava);
        mProductName = v.findViewById(R.id.sheetproduct_name);
        mProductPrice = v.findViewById(R.id.sheetproduct_price);
        mProductQty = v.findViewById(R.id.sheetproduct_qty);
        mProductSubtotal = v.findViewById(R.id.sheetproduct_subtotal);
        mBtnCartIncrease = v.findViewById(R.id.cart_increase);
        mBtnCartDecrease = v.findViewById(R.id.cart_decrease);

        mBtnCartIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldValue = Integer.parseInt(mProductQty.getText().toString());
                int newValue = oldValue+1;
                mProductQty.setText(String.valueOf(newValue));
                int subtotal = prodprice*newValue;
                mProductSubtotal.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(subtotal))));
            }
        });
        mBtnCartDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldValue = Integer.parseInt(mProductQty.getText().toString());
                if(oldValue != 1 || oldValue < 1) {
                    int newValue = oldValue - 1;
                    mProductQty.setText(String.valueOf(newValue));
                    int subtotal = prodprice*newValue;
                    mProductSubtotal.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(subtotal))));
                }
            }
        });

        Bundle data = getArguments();
        idToken = data.getString("idToken");
        mProductId = data.getInt("productId");
        mProductName.setText(data.getString("productName"));
        mProductPrice.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(data.getInt("productPrice")))));
        iProductPrice = data.getInt("productPrice");
        Glide.with(getContext())
                .asBitmap()
                .load(data.getString("productAva"))
                .into(mProductAva);

        prodprice = data.getInt("productPrice");
        int subtotal = data.getInt("productPrice")*Integer.parseInt(mProductQty.getText().toString());
        mProductSubtotal.setText("Rp "+String.format("%,.0f", Double.parseDouble(String.valueOf(subtotal))));

        mApiClient = ApiClient.getClient().create(IApiClient.class);

        Button mLanjutKeranjang = v.findViewById(R.id.popup_lanjutKeranjang);
        mLanjutKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_to_cart(Integer.parseInt(mProductQty.getText().toString()));
            }
        });
        return v;
    }

    public void add_to_cart(int qty){
        Call<AddCart> addCartCall = mApiClient.add_to_cart("Bearer "+idToken, mProductId, qty);
        addCartCall.enqueue(new Callback<AddCart>() {
            @Override
            public void onResponse(Call<AddCart> call, Response<AddCart> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Intent a = new Intent(getContext(), KeranjangActivity.class);
                        startActivity(a);
                    } else {
                        Toast.makeText(getContext(), "Anda belum login", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(getContext(), NavigationBar.class);
                        a.putExtra("direction", 4);
                        startActivity(a);
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(getContext(), errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddCart> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
