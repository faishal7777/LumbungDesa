package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Api.ApiClient;
import com.runupstdio.lumbungdesa.Api.IApiClient;
import com.runupstdio.lumbungdesa.KeranjangActivity;
import com.runupstdio.lumbungdesa.Model.AddCart;
import com.runupstdio.lumbungdesa.Model.Keranjang;
import com.runupstdio.lumbungdesa.Model.RemoveCart;
import com.runupstdio.lumbungdesa.NavigationBar;
import com.runupstdio.lumbungdesa.R;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private List<Keranjang> mKeranjang;
    private Context mContext;

    IApiClient mApiClient;
    private String idToken = null;

    public KeranjangAdapter(String token, List<Keranjang> mKeranjang, Context mContext) {
        this.idToken = token;
        this.mKeranjang = mKeranjang;
        this.mContext = mContext;
    }

    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_keranjang, parent, false);

            mApiClient = ApiClient.getClient().create(IApiClient.class);

            return new KeranjangAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(KeranjangAdapter.ViewHolder holder, final int position) {
        final Keranjang listPenjualan = mKeranjang.get(position);

        String currentUrlProduct = listPenjualan.getImg_Url_Keranjang();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(listPenjualan.getNama_Produk_Keranjang());
        holder.ProductPrice.setText(listPenjualan.getHarga_Produk_Keranjang());
        holder.ProductQuantity.setText(listPenjualan.getKuantitas_produk_Keranjang());
        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_from_cart(listPenjualan.getiProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mKeranjang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice, ProductQuantity;
        ImageButton hapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProdukKeranjang);
            ProductName = itemView.findViewById(R.id.namaProdukKeranjang);
            ProductPrice = itemView.findViewById(R.id.hargaProdukKeranjang);
            ProductQuantity = itemView.findViewById(R.id.kuantitasProdukKeranjang);
            hapus =  itemView.findViewById(R.id.btnHapusKeranjang);
        }
    }

    public void remove_from_cart(int idcart) {
        Call<RemoveCart> addCartCall = mApiClient.remove_from_cart("Bearer " + idToken, idcart);
        addCartCall.enqueue(new Callback<RemoveCart>() {
            @Override
            public void onResponse(Call<RemoveCart> call, Response<RemoveCart> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Intent a = new Intent(mContext, KeranjangActivity.class);
                        mContext.startActivity(a);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.errorBody() != null) {
                    // Get response errorBody
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(mContext, errorBody, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveCart> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
