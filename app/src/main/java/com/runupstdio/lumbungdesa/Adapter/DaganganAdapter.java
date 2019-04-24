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
import com.runupstdio.lumbungdesa.Model.Dagangan;
import com.runupstdio.lumbungdesa.R;
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

        holder.ProductNama.setText(listDagangan.getHargaProdukDagangan());

        holder.ProductHarga.setText(listDagangan.getHargaProdukDagangan());
    }

    @Override
    public int getItemCount() {
        return mDagangan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductNama, ProductHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProdukDagangan);
            ProductNama = itemView.findViewById(R.id.namaProdukDagangan);
            ProductHarga = itemView.findViewById(R.id.hargaProdukDagangan);
        }
    }
}
