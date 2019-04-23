package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.R;

import java.util.List;

public class ProdukKategoriAdapter extends RecyclerView.Adapter<ProdukKategoriAdapter.ViewHolder> {

    private List<BarangHariIni> mProdukKategori;
    private Context mContext;

    public ProdukKategoriAdapter(List<BarangHariIni> mProdukKategori, Context mContext) {
        this.mProdukKategori = mProdukKategori;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProdukKategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tagihan, parent, false);
        return new ProdukKategoriAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukKategoriAdapter.ViewHolder holder, int position) {
        final BarangHariIni list = mProdukKategori.get(position);
        String currentUrlProduct = list.getImageProductUrl();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(list.getProductName());

        holder.ProductPrice.setText(list.getProductPrice());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProdukKategori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductPrice, ProductName;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.productImageKategori);
            ProductName = itemView.findViewById(R.id.productNameKategori);
            ProductPrice = itemView.findViewById(R.id.productPriceKategori);
            mCardView = itemView.findViewById(R.id.item_kategori);
        }
    }
}
