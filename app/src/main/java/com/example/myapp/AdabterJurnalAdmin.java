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

public class AdabterJurnalAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    JurnalAdminFragment jurnalAdminFragment;

    public AdabterJurnalAdmin(Context context, List<Object> recyclerViewItems,JurnalAdminFragment jurnalAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.jurnalAdminFragment = jurnalAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_jurnal_admin, null);

        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final AdabterJurnalAdmin.MenuItemViewHolder menuItemHolder = (AdabterJurnalAdmin.MenuItemViewHolder) holder;
        final ModelJurnalAdmin fp = (ModelJurnalAdmin) recyclerViewItems.get(position);

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
                Intent intent = new Intent(mContext,DetailJurnalAdminActivity.class);
                intent.putExtra("Id",((ModelJurnalAdmin) recyclerViewItems.get(position)).getId());
                intent.putExtra("Judul",((ModelJurnalAdmin) recyclerViewItems.get(position)).getJudulJurnal());
                intent.putExtra("Pengarang",((ModelJurnalAdmin) recyclerViewItems.get(position)).getPengarangJurnal());
                intent.putExtra("Tahun_terbit",((ModelJurnalAdmin) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                intent.putExtra("Urlimages",((ModelJurnalAdmin) recyclerViewItems.get(position)).getUrlimagesJurnal());
                intent.putExtra("Urlpdf",((ModelJurnalAdmin) recyclerViewItems.get(position)).getUrlpdf());
                intent.putExtra("Status_buku_jurnal",((ModelJurnalAdmin) recyclerViewItems.get(position)).getStatus_buku_jurnal());
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
        public TextView status_buku_jurnal;
        public LinearLayout lineLayout;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul      = (TextView) itemLayoutView.findViewById(R.id.judul_jurnal_admin);
            pengarang       = (TextView) itemLayoutView.findViewById(R.id.pengarang_jurnal_admin);
            tahun_terbit     = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_jurnal_admin);
            urlimages     = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_jurnal_admin);
            status_buku_jurnal     = (TextView) itemLayoutView.findViewById(R.id.status_buku_jurnal_admin);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_jurnal_admin);
        }
    }
}
