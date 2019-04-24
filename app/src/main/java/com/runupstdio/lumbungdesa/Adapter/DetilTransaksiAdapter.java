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
import com.runupstdio.lumbungdesa.Model.DetilTransaksi;
import com.runupstdio.lumbungdesa.R;

import java.util.List;

public class DetilTransaksiAdapter extends RecyclerView.Adapter<DetilTransaksiAdapter.ViewHolder> {
    private List<DetilTransaksi> mDetilTransaksi;
    private Context mContext;

    public DetilTransaksiAdapter(List<DetilTransaksi> mDetilTransaksi, Context mContext) {
        this.mDetilTransaksi = mDetilTransaksi;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DetilTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produk_detil_transaksi, parent, false);
        return new DetilTransaksiAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilTransaksiAdapter.ViewHolder holder, int position) {
        final DetilTransaksi listDetilTransaksi = mDetilTransaksi.get(position);
        String currentUrlProduct = listDetilTransaksi.getImgProduk();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductNama.setText(listDetilTransaksi.getNamaProduk());

        holder.ProductHarga.setText(listDetilTransaksi.getHargaProduk());

        holder.ProductQuantity.setText(listDetilTransaksi.getJumlahProduk());
    }

    @Override
    public int getItemCount() {
        return mDetilTransaksi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductNama, ProductHarga, ProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgDetilProduct);
            ProductNama = itemView.findViewById(R.id.namaProductDetil);
            ProductHarga = itemView.findViewById(R.id.hargaProductDetil);
            ProductQuantity = itemView.findViewById(R.id.jumlahProductDetil);
        }
    }
}
