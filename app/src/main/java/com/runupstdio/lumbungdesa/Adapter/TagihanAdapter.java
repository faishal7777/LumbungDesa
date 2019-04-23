package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        String currentUrlProduct1 = listTagihan.getImageProductUrl1();
        String currentUrlProduct2 = listTagihan.getImageProductUrl2();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 0);

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct1)
                .into(holder.ImgProduct1);

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct2)
                .into(holder.ImgProduct2);

        holder.ProductPrice.setText(listTagihan.getTotalPrice());
        holder.ProductPrice.setLayoutParams(params);
        holder.ProductPrice.setBackgroundResource(0);

        holder.Status.setText(listTagihan.getStatus());
        holder.Status.setLayoutParams(params);
        holder.Status.setBackgroundResource(0);
    }

    @Override
    public int getItemCount() {
        return mTagihan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct1, ImgProduct2;
        TextView ProductPrice, Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct1 = itemView.findViewById(R.id.imgProduct1);
            ImgProduct2 = itemView.findViewById(R.id.imgProduct2);
            ProductPrice = itemView.findViewById(R.id.totalHargaProduct);
            Status = itemView.findViewById(R.id.status);
        }
    }
}
