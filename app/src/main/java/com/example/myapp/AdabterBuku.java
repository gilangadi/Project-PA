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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdabterBuku extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> recyclerViewItems = new ArrayList<>();
//    private final Context mContext;
    BukuFragment bukuFragment;

    public AdabterBuku() {

    }

//    public AdabterBuku(Context context, List<Object> recyclerViewItems,BukuFragment bukuFragment) {
//
//        this.mContext = context;
//        this.recyclerViewItems = recyclerViewItems;
//        this.bukuFragment = bukuFragment;
//    }

    public void send(List<Object> list) {
        recyclerViewItems.clear();
        recyclerViewItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buku, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelBuku fp = (ModelBuku) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
        menuItemHolder.status_buku_jurnal.setText(fp.getStatus_buku_jurnal());
        Glide.with(menuItemHolder.itemView.getContext())
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(300, 300)
                .centerCrop()
                .fitCenter()
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menuItemHolder.itemView.getContext(),DetailBukuActivity.class);
                intent.putExtra("Id",((ModelBuku) recyclerViewItems.get(position)).getId());
                intent.putExtra("Judul",((ModelBuku) recyclerViewItems.get(position)).getJudulJurnal());
                intent.putExtra("Pengarang",((ModelBuku) recyclerViewItems.get(position)).getPengarangJurnal());
                intent.putExtra("Tahun_terbit",((ModelBuku) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                intent.putExtra("Urlimages",((ModelBuku) recyclerViewItems.get(position)).getUrlimagesJurnal());
                intent.putExtra("Urlpdf",((ModelBuku) recyclerViewItems.get(position)).getUrlpdf());
                intent.putExtra("status_buku_admin",((ModelBuku) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                menuItemHolder.itemView.getContext().startActivity(intent);
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
            judul      = (TextView) itemLayoutView.findViewById(R.id.judul_buku);
            pengarang       = (TextView) itemLayoutView.findViewById(R.id.pengarang_buku);
            tahun_terbit     = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_buku);
            urlimages     = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_buku);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_buku);
            status_buku_jurnal     = (TextView) itemLayoutView.findViewById(R.id.status_buku_jurnal_pengguna);

        }
    }
}
