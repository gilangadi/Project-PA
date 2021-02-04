package com.example.myapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdabterStatusPinjamAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusPinjamAdminFragment statusPinjamAdminFragment;

    String id_user = "";
    String Idrak ="";

    public AdabterStatusPinjamAdmin(Context context, List<Object> recyclerViewItems, StatusPinjamAdminFragment statusPinjamAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusPinjamAdminFragment = statusPinjamAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pinjam_admin, null);
        return new AdabterStatusPinjamAdmin.MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdabterStatusPinjamAdmin.MenuItemViewHolder menuItemHolder = (AdabterStatusPinjamAdmin.MenuItemViewHolder) holder;
        holder.itemView.getContext();
        final ModelAdminStatusPinjam fp = (ModelAdminStatusPinjam) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(900, 250)
                .into(menuItemHolder.urlimages);

        menuItemHolder.nama.setText(fp.getNama());
        menuItemHolder.nim.setText(fp.getNim());
        menuItemHolder.prodi.setText(fp.getProdi());
//        menuItemHolder.status.setText(fp.getStatus());

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                CharSequence[] dialogItem = {"Detail"};
                builder.setTitle(((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getJudulJurnal());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){

                            case 0:
                                Intent intent = new Intent(mContext,DetailStatusPinjamAdminMainActivity.class);
                                intent.putExtra("Id",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Id_user",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getId_user());
                                intent.putExtra("Judul",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getJudulJurnal());
                                intent.putExtra("Pengarang",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getPengarangJurnal());
                                intent.putExtra("Tahun_terbit",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                                intent.putExtra("Urlimages",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getUrlimagesJurnal());
                                intent.putExtra("Urlpdf",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getUrlpdf());
                                intent.putExtra("nama",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getNama());
                                intent.putExtra("nim",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getNim());
                                intent.putExtra("prodi",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getProdi());
                                intent.putExtra("created_at",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getCreated_at());
                                intent.putExtra("status_buku_jurnal",((ModelAdminStatusPinjam) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                mContext.startActivity(intent);
                                break;
                        }
                    }

                });
                builder.create().show();
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
        public TextView nama;
        public TextView nim;
        public TextView prodi;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_admin_status);
            pengarang = (TextView) itemLayoutView.findViewById(R.id.pengarang_admin_status);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_admin_status);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_admin_status);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_admin);
            nama = (TextView) itemLayoutView.findViewById(R.id.namapeminjam);
            nim = (TextView) itemLayoutView.findViewById(R.id.nimpeminjam);
            prodi = (TextView) itemLayoutView.findViewById(R.id.prodipeminjam);
        }
    }
}
