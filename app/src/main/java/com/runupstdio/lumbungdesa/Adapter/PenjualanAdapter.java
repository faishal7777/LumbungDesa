package com.runupstdio.lumbungdesa.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.R;
import java.util.List;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.ViewHolder>{

    public static final int BELUM_DIBAYAR = 0;
    public static final int DIBAYAR = 1;
    public static final int DIBATALKAN = 2;

    private List<Tagihan> mPenjualan;
    private Context mContext;

    public PenjualanAdapter(List<Tagihan> mPenjualan, Context mContext) {
        this.mPenjualan = mPenjualan;
        this.mContext = mContext;
    }



    @Override
    public PenjualanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BELUM_DIBAYAR) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_penjualan, parent, false);
            return new PenjualanAdapter.ViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_tagihan, parent, false);
            return new PenjualanAdapter.ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(PenjualanAdapter.ViewHolder holder, final int position) {
        final Tagihan listPenjualan = mPenjualan.get(position);

        String currentUrlProduct = listPenjualan.getImageProductUrl();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(listPenjualan.getProductName());
        holder.ProductPrice.setText(listPenjualan.getProductPrice());
        holder.Status.setText(listPenjualan.getStatus());

        holder.Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPenjualan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice, Status;
        Button Bayar, Batal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProductTagihan);
            ProductName = itemView.findViewById(R.id.namaProductTagihan);
            ProductPrice = itemView.findViewById(R.id.hargaProductTagihan);
            Status = itemView.findViewById(R.id.statusProductTagihan);
            Bayar = itemView.findViewById(R.id.btn_terima);
            Batal = itemView.findViewById(R.id.btn_batal);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPenjualan.get(position).getStatus().equals("0")){
            return BELUM_DIBAYAR;
        }
        else if (mPenjualan.get(position).getStatus().equals("1")){
            return DIBAYAR;
        }
        else {
            return DIBATALKAN;
        }
    }
}
