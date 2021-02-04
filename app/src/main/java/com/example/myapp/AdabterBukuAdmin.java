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

public class AdabterBukuAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    BukuAdminFragment bukuAdminFragment;

    public AdabterBukuAdmin(Context context, List<Object> recyclerViewItems,BukuAdminFragment bukuAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.bukuAdminFragment = bukuAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buku_admin, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final AdabterBukuAdmin.MenuItemViewHolder menuItemHolder = (AdabterBukuAdmin.MenuItemViewHolder) holder;
        final ModelBukuAdmin fp = (ModelBukuAdmin) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnalAdmin());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnalAdmin());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnalAdmin());
        menuItemHolder.status_buku_jurnal.setText(fp.getStatus_buku_jurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnalAdmin())
                .override(300, 300)
                .centerCrop()
                .fitCenter()
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailBukuAdminActivity.class);
                intent.putExtra("Id",((ModelBukuAdmin) recyclerViewItems.get(position)).getId());
                intent.putExtra("Judul",((ModelBukuAdmin) recyclerViewItems.get(position)).getJudulJurnalAdmin());
                intent.putExtra("Pengarang",((ModelBukuAdmin) recyclerViewItems.get(position)).getPengarangJurnalAdmin());
                intent.putExtra("Tahun_terbit",((ModelBukuAdmin) recyclerViewItems.get(position)).getTahun_TerbitJurnalAdmin());
                intent.putExtra("Urlimages",((ModelBukuAdmin) recyclerViewItems.get(position)).getUrlimagesJurnalAdmin());
                intent.putExtra("Urlpdf",((ModelBukuAdmin) recyclerViewItems.get(position)).getUrlpdfAdmin());
                intent.putExtra("Status_buku_jurnal",((ModelBukuAdmin) recyclerViewItems.get(position)).getStatus_buku_jurnal());
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
            judul      = (TextView) itemLayoutView.findViewById(R.id.judul_buku_admin);
            pengarang       = (TextView) itemLayoutView.findViewById(R.id.pengarang_buku_admin);
            tahun_terbit     = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_buku_admin);
            urlimages     = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_buku_admin);
            status_buku_jurnal     = (TextView) itemLayoutView.findViewById(R.id.status_buku_jurnal_admin);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_buku_admin);

        }
    }
}
