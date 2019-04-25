package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.ProductClickedActivity;
import com.runupstdio.lumbungdesa.R;

import java.util.List;

public class TemukanAdapter extends RecyclerView.Adapter<TemukanAdapter.ViewHolder> {

    private List<BarangHariIni> mTemukan;
    private Context mContext;

    public TemukanAdapter(List<BarangHariIni> mTemukan, Context mContext) {
        this.mTemukan = mTemukan;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TemukanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_produk_kategori, parent, false);
        return new TemukanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BarangHariIni list = mTemukan.get(position);
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
                Intent productDetails = new Intent(mContext, ProductClickedActivity.class);
                productDetails.putExtra("prodId", list.getProductId());
                mContext.startActivity(productDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTemukan.size();
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
