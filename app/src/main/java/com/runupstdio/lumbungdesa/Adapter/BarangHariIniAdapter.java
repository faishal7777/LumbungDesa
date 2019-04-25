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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.ListChatActivity;
import com.runupstdio.lumbungdesa.Model.BarangHariIni;
import com.runupstdio.lumbungdesa.ProductClickedActivity;
import com.runupstdio.lumbungdesa.R;

import java.util.List;

public class BarangHariIniAdapter extends RecyclerView.Adapter<BarangHariIniAdapter.ViewHolder> {

    private List<BarangHariIni> listBarangHariIni;
    private Context mContext;

    public BarangHariIniAdapter(List<BarangHariIni> listBarangHariIni, Context mContext) {
        this.listBarangHariIni = listBarangHariIni;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout_beranda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BarangHariIni list = listBarangHariIni.get(position);
        String currentUrlProduct = list.getImageProductUrl();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 0);

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(list.getProductName());
        holder.ProductName.setLayoutParams(params);
        holder.ProductName.setBackgroundResource(0);

        holder.ProductPrice.setText(list.getProductPrice());
        holder.ProductPrice.setLayoutParams(params);
        holder.ProductPrice.setBackgroundResource(0);
        holder.mItemBeranda.setOnClickListener(new View.OnClickListener() {
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
        return listBarangHariIni.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice;
        CardView mItemBeranda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProductRecycle);
            ProductName = itemView.findViewById(R.id.productNameRecycle);
            ProductPrice = itemView.findViewById(R.id.productPriceRecycle);
            mItemBeranda = itemView.findViewById(R.id.item_beranda);
        }
    }
}
