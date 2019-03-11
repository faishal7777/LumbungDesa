package com.runupstdio.lumbungdesa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class BarangHariIniAdapter extends RecyclerView.Adapter<BarangHariIniAdapter.ViewHolder> {

    private static final String TAG = "BarangHariIniAdapter";

    //array-list
    private ArrayList<String> mImgProductUrl = new ArrayList<>();
    private ArrayList<String> mProductName = new ArrayList<>();
    private ArrayList<String> mProductPrice = new ArrayList<>();
    private Context mContext;

    public BarangHariIniAdapter(Context mContext, ArrayList<String> mImgProductUrl, ArrayList<String> mProductName, ArrayList<String> mProductPrice) {
        this.mImgProductUrl = mImgProductUrl;
        this.mProductName = mProductName;
        this.mProductPrice = mProductPrice;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_beranda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext)
                .asBitmap()
                .load(mImgProductUrl.get(position))
                .into(holder.ImgProduct);
        holder.ProductName.setText(mProductName.get(position));
        holder.ProductPrice.setText(mProductPrice.get(position));
        holder.ImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on image" + mProductName.get(position));

                Toast.makeText(mContext, mProductName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImgProductUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProductRecycle);
            ProductName = itemView.findViewById(R.id.productNameRecycle);
            ProductPrice = itemView.findViewById(R.id.productPriceRecycle);
        }
    }
}
