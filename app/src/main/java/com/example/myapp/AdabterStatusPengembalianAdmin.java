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

public class AdabterStatusPengembalianAdmin extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusKembaliAdminFragment statusKembaliAdminFragment;

    String id_user = "";
    String Idrak ="";

    public AdabterStatusPengembalianAdmin(Context context, List<Object> recyclerViewItems, StatusKembaliAdminFragment statusKembaliAdminFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusKembaliAdminFragment = statusKembaliAdminFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pengembalian_admin, null);
        return new AdabterStatusPengembalianAdmin.MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdabterStatusPengembalianAdmin.MenuItemViewHolder menuItemHolder = (AdabterStatusPengembalianAdmin.MenuItemViewHolder) holder;
        holder.itemView.getContext();
        final ModelAdminStatusKembali fp = (ModelAdminStatusKembali) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
//        menuItemHolder.status_buku_jurnal.setText(fp.getStatus_buku_jurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(900, 250)
                .into(menuItemHolder.urlimages);

        menuItemHolder.nama.setText(fp.getNama());
        menuItemHolder.nim.setText(fp.getNim());
        menuItemHolder.prodi.setText(fp.getProdi());

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                CharSequence[] dialogItem = {"Detail","Delete"};
                builder.setTitle(((ModelAdminStatusKembali) recyclerViewItems.get(position)).getJudulJurnal());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){

                            case 0:
                                Intent intent = new Intent(mContext,DetailStatusKembaliAdminActivity.class);
                                intent.putExtra("Id",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Judul",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getJudulJurnal());
                                intent.putExtra("Pengarang",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getPengarangJurnal());
                                intent.putExtra("Tahun_terbit",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                                intent.putExtra("Urlimages",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getUrlimagesJurnal());
                                intent.putExtra("Urlpdf",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getUrlpdf());
                                intent.putExtra("status_buku_jurnal",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getStatus_buku_jurnal());
                                intent.putExtra("nama",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getNama());
                                intent.putExtra("nim",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getNim());
                                intent.putExtra("prodi",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getProdi());
                                intent.putExtra("created_at",((ModelAdminStatusKembali) recyclerViewItems.get(position)).getCreated_at());
                                mContext.startActivity(intent);
                                break;

                            case 1:
                                deleteKoleksiBuku(((ModelAdminStatusKembali) recyclerViewItems.get(position)).getId());
                                break;
                        }
                    }

                    //DELETE PENGEMBALIAN
                    private void deleteKoleksiBuku(int id) {
                        final String URLs = "http://192.168.43.183/apiperpus/public/api/rakkoleksibuku/"+id;
                        RequestQueue queue = Volley.newRequestQueue(menuItemHolder.itemView.getContext());
                        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URLs,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("APPLOG", response);
                                        Toast.makeText(menuItemHolder.itemView.getContext(),response,Toast.LENGTH_SHORT).show();
                                        parseJson(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("APPLOG", error.toString());
                                        Toast.makeText(menuItemHolder.itemView.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                        queue.add(postRequest);
                    }

                    public void parseJson(String jsonStr) {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(jsonStr);
                            if (jsonObj.get("result").equals("result")) {
                            } else {
                            }
                        } catch (JSONException e) {
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
//        public TextView created_at;

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            judul = (TextView) itemLayoutView.findViewById(R.id.judul_admin_status_pengembalian);
            pengarang = (TextView) itemLayoutView.findViewById(R.id.pengarang_admin_status_pengembalian);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_admin_status_pengembalian);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_admin_status_pengembalian);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_admin_pengembalian);
            nama = (TextView) itemLayoutView.findViewById(R.id.namapengembalian);
            nim = (TextView) itemLayoutView.findViewById(R.id.nimpengembalian);
            prodi = (TextView) itemLayoutView.findViewById(R.id.prodipengembalian);
//            created_at = (TextView) itemLayoutView.findViewById(R.id.tanggalpengembalian);

        }
    }

}
