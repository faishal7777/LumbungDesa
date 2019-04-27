package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.DetilActivity;
import com.runupstdio.lumbungdesa.Model.Dagangan;
import com.runupstdio.lumbungdesa.ProductClickedActivity;
import com.runupstdio.lumbungdesa.R;
import com.runupstdio.lumbungdesa.TransferActivity;

import java.util.List;

public class DaganganAdapter extends RecyclerView.Adapter<DaganganAdapter.ViewHolder> {

    private List<Dagangan> mDagangan;
    private Context mContext;

    public DaganganAdapter(List<Dagangan> mDagangan, Context mContext) {
        this.mDagangan = mDagangan;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DaganganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_dagangan, parent, false);
        return new DaganganAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dagangan listDagangan = mDagangan.get(position);
        String currentUrlProduct = listDagangan.getImgProdukDagangan();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductNama.setText(listDagangan.getNamaProdukDagangan());

        holder.ProductHarga.setText(listDagangan.getHargaProdukDagangan());

        holder.mLnDagangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(mContext, ProductClickedActivity.class);
                a.putExtra("prodId", listDagangan.getProdid());
                mContext.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDagangan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductNama, ProductHarga;
        LinearLayout mLnDagangan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProdukDagangan);
            ProductNama = itemView.findViewById(R.id.namaProdukDagangan);
            ProductHarga = itemView.findViewById(R.id.hargaProdukDagangan);
            mLnDagangan = itemView.findViewById(R.id.lnDagangan);
        }
    }
}
