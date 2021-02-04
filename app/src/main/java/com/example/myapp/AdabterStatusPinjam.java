package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdabterStatusPinjam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusPinjamFragment statusPinjamFragment;

    public AdabterStatusPinjam(Context context, List<Object> recyclerViewItems, StatusPinjamFragment statusPinjamFragment) {
        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusPinjamFragment = statusPinjamFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pinjam, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelStatusPinjam fp = (ModelStatusPinjam) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(900, 250)
                .into(menuItemHolder.urlimages);
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

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_jurnal);
            pengarang = (TextView) itemLayoutView.findViewById(R.id.pengarang_jurnal);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_jurnal);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_jurnal);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_pinjam);


        }
    }
}
