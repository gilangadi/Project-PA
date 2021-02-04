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

public class AdabterStatusPengembalian extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> recyclerViewItems;
    private final Context mContext;
    StatusKembaliFragment statusKembaliFragment;

    String id_user = "";
    String Idrak ="";

    public AdabterStatusPengembalian(Context context, List<Object> recyclerViewItems, StatusKembaliFragment statusKembaliFragment) {

        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.statusKembaliFragment = statusKembaliFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_status_pengembalian, null);
        return new AdabterStatusPengembalian.MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final AdabterStatusPengembalian.MenuItemViewHolder menuItemHolder = (AdabterStatusPengembalian.MenuItemViewHolder) holder;
        holder.itemView.getContext();
        final ModelPengembalian fp = (ModelPengembalian) recyclerViewItems.get(position);

        menuItemHolder.judul.setText(fp.getJudulJurnal());
        menuItemHolder.pengarang.setText(fp.getPengarangJurnal());
        menuItemHolder.tahun_terbit.setText(fp.getTahun_TerbitJurnal());
        Glide.with(mContext)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + fp.getUrlimagesJurnal())
                .override(900, 250)
                .into(menuItemHolder.urlimages);

        menuItemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());

                CharSequence[] dialogItem = {"Detail","Delete"};
                builder.setTitle(((ModelPengembalian) recyclerViewItems.get(position)).getJudulJurnal());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(mContext,DetailStatusKembaliActivity.class);
                                intent.putExtra("Id",((ModelPengembalian) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Judul",((ModelPengembalian) recyclerViewItems.get(position)).getJudulJurnal());
                                intent.putExtra("Pengarang",((ModelPengembalian) recyclerViewItems.get(position)).getPengarangJurnal());
                                intent.putExtra("Tahun_terbit",((ModelPengembalian) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                                intent.putExtra("Urlimages",((ModelPengembalian) recyclerViewItems.get(position)).getUrlimagesJurnal());
                                intent.putExtra("Urlpdf",((ModelPengembalian) recyclerViewItems.get(position)).getUrlpdf());
                                mContext.startActivity(intent);
                                break;

                            case 1:
                                deletepengembalian(((ModelPengembalian) recyclerViewItems.get(position)).getId());
                                break;
                        }
                    }

                    //DELETE KOLEKSI BUKU
                    private void deletepengembalian(int id) {
                        final String URLs = "http://192.168.43.183/apiperpus/public/api/deletepengembalian/";
                        RequestQueue queue = Volley.newRequestQueue(menuItemHolder.itemView.getContext());
                        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URLs+id,
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

        MenuItemViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            judul = (TextView) itemLayoutView.findViewById(R.id.judul_pengguna_status_pengembalian);
            pengarang = (TextView) itemLayoutView.findViewById(R.id.pengarang_pengguna_status_pengembalian);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_pengguna_status_pengembalian);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_pengguna_status_pengembalian);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_pengguna_pengembalian);

        }
    }

}
