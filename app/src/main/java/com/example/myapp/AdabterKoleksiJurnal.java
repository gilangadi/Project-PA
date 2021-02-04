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

public class AdabterKoleksiJurnal extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Object> recyclerViewItems;
    private final Context mContext;
    KoleksiJurnalFragment koleksiJurnalFragment;

    String idUser = "";
    String Idrak = "";


    public AdabterKoleksiJurnal(Context context, List<Object> recyclerViewItems, KoleksiJurnalFragment koleksiJurnalFragment) {
        this.mContext = context;
        this.recyclerViewItems = recyclerViewItems;
        this.koleksiJurnalFragment = koleksiJurnalFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_koleksi_jurnal, null);
        return new MenuItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
        final ModelKoleksiJurnal fp = (ModelKoleksiJurnal) recyclerViewItems.get(position);

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

                CharSequence[] dialogItem = {"Detail Buku","Delete Koleksi Buku"};
                builder.setTitle(((ModelKoleksiJurnal) recyclerViewItems.get(position)).getJudulJurnal());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(mContext,DetailKoleksiJurnalActivity.class);
                                intent.putExtra("Id_rak",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getId_rak());
                                intent.putExtra("Id",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getId());
                                intent.putExtra("Judul",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getJudulJurnal());
                                intent.putExtra("Pengarang",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getPengarangJurnal());
                                intent.putExtra("Tahun_terbit",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getTahun_TerbitJurnal());
                                intent.putExtra("Urlimages",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getUrlimagesJurnal());
                                intent.putExtra("Urlpdf",((ModelKoleksiJurnal) recyclerViewItems.get(position)).getUrlpdf());
                                mContext.startActivity(intent);
                                break;

                            case 1:
                              deleteKoleksiBuku(((ModelKoleksiJurnal) recyclerViewItems.get(position)).getId());
                                break;
                        }
                    }

                     //  DELETE KOLEKSI jurnal
                    private void deleteKoleksiBuku(int id) {
                        final String URLs = "http://192.168.43.183/apiperpus/public/api/rakkoleksijurnal/";
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
            judul = (TextView) itemLayoutView.findViewById(R.id.judul_koleksi_jurnal);
            pengarang = (TextView) itemLayoutView.findViewById(R.id.pengarang_koleksi_jurnal);
            tahun_terbit = (TextView) itemLayoutView.findViewById(R.id.tahun_terbit_koleksi_jurnal);
            urlimages = (ImageView) itemLayoutView.findViewById(R.id.thumbnail_koleksi_jurnal);
            lineLayout = (LinearLayout) itemLayoutView.findViewById(R.id.linier_koleksi_jurnal);
        }
    }
}
