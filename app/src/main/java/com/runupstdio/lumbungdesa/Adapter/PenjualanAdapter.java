package com.runupstdio.lumbungdesa.Adapter;
import android.content.Context;
import android.content.Intent;
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
import com.runupstdio.lumbungdesa.DetilActivity;
import com.runupstdio.lumbungdesa.Model.Tagihan;
import com.runupstdio.lumbungdesa.PenjualanFragment;
import com.runupstdio.lumbungdesa.R;
import java.util.List;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.ViewHolder>{

    private List<Tagihan> mPembelian;
    private Context mContext;

    PenjualanFragment mFragment;

    public PenjualanAdapter(List<Tagihan> mPembelian, Context mContext, PenjualanFragment fragment) {
        this.mPembelian = mPembelian;
        this.mContext = mContext;
        this.mFragment = fragment;
    }

    @Override
    public PenjualanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tagihan, parent, false);
        return new PenjualanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PenjualanAdapter.ViewHolder holder, final int position) {
        final Tagihan listTagihan = mPembelian.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 0);

        if(listTagihan.getProductUrl() != null) {
            List<String> imgProductUrl = listTagihan.getProductUrl();
            Glide.with(mContext)
                    .asBitmap()
                    .load(imgProductUrl.get(0))
                    .into(holder.ImgProduct1);

            if (imgProductUrl.size() > 1) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(imgProductUrl.get(1))
                        .into(holder.ImgProduct2);
                holder.ImgProduct2.setVisibility(View.VISIBLE);
                holder.ProductName.setVisibility(View.GONE);
            } else {
                holder.ProductName.setVisibility(View.VISIBLE);
                holder.ProductName.setText(listTagihan.getProductName());
            }
            holder.ProductPrice.setText(listTagihan.getTotalPrice());
            holder.mTagihanStatus.setText(listTagihan.getProductStatus());
            if(listTagihan.getProductStatus().equals("Dibatalkan"))
                holder.mTagihanStatus.setTextColor(mContext.getColor(R.color.colorCencelled));
            if(listTagihan.getProductStatus().equals("Dibayar")){
                holder.mLnBtnAction.setVisibility(View.VISIBLE);
            }
            holder.mLnPenjualan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(mContext, DetilActivity.class);
                    a.putExtra("idTrx", listTagihan.getIdTrx());
                    mContext.startActivity(a);
                }
            });
            holder.mBtnTerima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.complateTrx(listTagihan.getIdTrx());
                }
            });
            holder.mBtnBatal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.cencel(listTagihan.getIdTrx());
                }
            });
        } else {
            holder.ProductName.setText("");
            holder.ProductName.setBackgroundColor(mContext.getResources().getColor(R.color.shimmer));
            holder.ProductPrice.setText("      ");
            holder.ProductPrice.setBackgroundColor(mContext.getResources().getColor(R.color.shimmer));
            holder.mTagihanStatus.setText("      ");
            holder.mTagihanStatus.setBackgroundColor(mContext.getResources().getColor(R.color.shimmer));
        }
    }

    @Override
    public int getItemCount() {
        return mPembelian.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgProduct1, ImgProduct2;
        TextView ProductName, ProductPrice, mTagihanStatus;
        LinearLayout mLnBtnAction;
        LinearLayout mLnPenjualan;
        Button mBtnBatal, mBtnTerima;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgProduct1 = itemView.findViewById(R.id.imgProduct1);
            ImgProduct2 = itemView.findViewById(R.id.imgProduct2);
            ProductName = itemView.findViewById(R.id.tagihan_productName);
            ProductPrice = itemView.findViewById(R.id.tagihan_ProductPrice);
            mTagihanStatus = itemView.findViewById(R.id.tagihan_status);
            mLnBtnAction = itemView.findViewById(R.id.LnBtnAction);
            mLnPenjualan = itemView.findViewById(R.id.lnTagihan);
            mBtnBatal = itemView.findViewById(R.id.btn_batal);
            mBtnTerima = itemView.findViewById(R.id.btn_terima);
        }
    }
}
