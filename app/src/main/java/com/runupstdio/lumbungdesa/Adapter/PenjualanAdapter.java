package com.runupstdio.lumbungdesa.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        String currentUrlProduct1 = listPenjualan.getImageProductUrl1();
        String currentUrlProduct2 = listPenjualan.getImageProductUrl2();
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

        holder.ProductPrice.setText(listPenjualan.getTotalPrice());
        holder.ProductPrice.setLayoutParams(params);
        holder.ProductPrice.setBackgroundResource(0);

        holder.Status.setText(listPenjualan.getStatus());
        holder.Status.setLayoutParams(params);
        holder.Status.setBackgroundResource(0);

        holder.Terima.setBackgroundResource(R.drawable.btn_login);
        holder.Terima.setText("Terima Order");
        holder.Terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.Batal.setBackgroundResource(R.drawable.btn_akun_login );
        holder.Batal.setText("Tolak Order");
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
        ImageView ImgProduct1, ImgProduct2;
        TextView ProductPrice, Status;
        Button Terima, Batal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            ImgProduct1 = itemView.findViewById(R.id.imgProduct1);
            ImgProduct2 = itemView.findViewById(R.id.imgProduct2);
            ProductPrice = itemView.findViewById(R.id.totalHargaProduct);
            Status = itemView.findViewById(R.id.status);
            Terima = itemView.findViewById(R.id.btn_terima);
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
