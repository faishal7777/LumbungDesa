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

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.ViewHolder>{

    public static final int BELUM_DIBAYAR = 0;
    public static final int DIBAYAR = 1;
    public static final int DIBATALKAN = 2;

    private List<Tagihan> mPembelian;
    private Context mContext;

    public PembelianAdapter(List<Tagihan> mPembelian, Context mContext) {
        this.mPembelian = mPembelian;
        this.mContext = mContext;
    }



    @Override
    public PembelianAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BELUM_DIBAYAR) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_delivered, parent, false);
            return new PembelianAdapter.ViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_tagihan, parent, false);
            return new PembelianAdapter.ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(PembelianAdapter.ViewHolder holder, final int position) {
        final Tagihan listPembelian = mPembelian.get(position);

        String currentUrlProduct = listPembelian.getImageProductUrl();

        Glide.with(mContext)
                .asBitmap()
                .load(currentUrlProduct)
                .into(holder.ImgProduct);

        holder.ProductName.setText(listPembelian.getProductName());
        holder.ProductPrice.setText(listPembelian.getProductPrice());
        holder.Status.setText(listPembelian.getStatus());

        holder.TerimaBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPembelian.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct;
        TextView ProductName, ProductPrice, Status;
        Button TerimaBarang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct = itemView.findViewById(R.id.imgProductTagihan);
            ProductName = itemView.findViewById(R.id.namaProductTagihan);
            ProductPrice = itemView.findViewById(R.id.hargaProductTagihan);
            Status = itemView.findViewById(R.id.statusProductTagihan);
            TerimaBarang = itemView.findViewById(R.id.btn_pickUps);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPembelian.get(position).getStatus().equals("0")){
            return BELUM_DIBAYAR;
        }
        else if (mPembelian.get(position).getStatus().equals("1")){
            return DIBAYAR;
        }
        else {
            return DIBATALKAN;
        }
    }
}
