package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.R;
import java.util.List;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.ViewHolder>{

    private List<Tagihan> mTagihan;
    private Context mContext;

    public TagihanAdapter(List<Tagihan> mTagihan, Context mContext) {
        this.mTagihan = mTagihan;
        this.mContext = mContext;
    }



    @Override
    public TagihanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tagihan, parent, false);
        return new TagihanAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(TagihanAdapter.ViewHolder holder, final int position) {
        final Tagihan listTagihan = mTagihan.get(position);

        String currentUrlProduct = listTagihan.getImageProductUrl();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(listTagihan.getProductName());
        holder.ProductPrice.setText(listTagihan.getProductPrice());
        holder.Status.setText(listTagihan.getStatus());
    }

    @Override
    public int getItemCount() {
        return mTagihan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice, Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProductTagihan);
            ProductName = itemView.findViewById(R.id.namaProductTagihan);
            ProductPrice = itemView.findViewById(R.id.hargaProductTagihan);
            Status = itemView.findViewById(R.id.statusProductTagihan);
        }
    }
}
