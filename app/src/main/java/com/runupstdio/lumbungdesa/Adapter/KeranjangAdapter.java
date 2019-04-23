package com.runupstdio.lumbungdesa.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Model.Keranjang;
import com.runupstdio.lumbungdesa.R;
import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private List<Keranjang> mKeranjang;
    private Context mContext;

    public KeranjangAdapter(List<Keranjang> mKeranjang, Context mContext) {
        this.mKeranjang = mKeranjang;
        this.mContext = mContext;
    }

    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_keranjang, parent, false);
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
}
