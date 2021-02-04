package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdabterJurnal extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> recyclerViewItems;
    private final Context mContext;
    JurnalFragment jurnalFragment;

    public AdabterJurnal(Context context, List<Object> recyclerViewItems,JurnalFragment jurnalFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.jurnalFragment = jurnalFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_jurnal, null);

        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelJurnal fp = (ModelJurnal) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
        menuItemHolder.status_buku_jurnal.setText(fp.getStatus_buku_jurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(300, 300)
                .centerCrop()
                .fitCenter()
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailJurnalActivity.class);
                intent.putExtra("Id",((ModelJurnal) recyclerViewItems.get(position)).getId());
                intent.putExtra("Judul",((ModelJurnal) recyclerViewItems.get(position)).getJudulJurnal());
                intent.putExtra("Pengarang",((ModelJurnal) recyclerViewItems.get(position)).getPengarangJurnal());
                intent.putExtra("Tahun_terbit",((ModelJurnal) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                intent.putExtra("Urlimages",((ModelJurnal) recyclerViewItems.get(position)).getUrlimagesJurnal());
                intent.putExtra("Urlpdf",((ModelJurnal) recyclerViewItems.get(position)).getUrlpdf());
                intent.putExtra("status_buku_admin",((ModelJurnal) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        public TextView judul;
        public TextView pengarang;
        public TextView tahun_terbit;
        public ImageView urlimages;
        public LinearLayout lineLayout;
        public TextView status_buku_jurnal;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul      = (TextView) itemLayoutView.findViewById(R.id.judul_jurnal);
            pengarang       = (TextView) itemLayoutView.findViewById(R.id.pengarang_jurnal);
            tahun_terbit     = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_jurnal);
            urlimages     = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_jurnal);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_jurnal);
            status_buku_jurnal     = (TextView) itemLayoutView.findViewById(R.id.status_buku_jurnal_pengguna);


        }
    }


}
