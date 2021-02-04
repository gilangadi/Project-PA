package com.example.myapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StatusPinjamAdminFragment extends Fragment {

    public static final String TAG = "MYTAG";
    RequestQueue QUEUE;
    String URLHTTP;

    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    RecyclerView rv;

    private GridLayoutManager gridLayoutManager;
    private RecyclerView myRecyclerview;

    //redfresh
    private SwipeRefreshLayout swipeRefreshLayout;

    public static StatusPinjamAdminFragment newInstance()
    {
        StatusPinjamAdminFragment statusPinjamAdminFragment = new StatusPinjamAdminFragment();
        return statusPinjamAdminFragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View rootView = inflater.inflate(R.layout.fragment_status_pinjam_admin,container,false);

        rv = (RecyclerView)rootView.findViewById(R.id.recyclerView_statuspinjam_admin);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter    = new AdabterStatusPinjamAdmin(getContext(),mRecyclerViewItems,this);
        QUEUE = Volley.newRequestQueue(getContext());
        URLHTTP = getResources().getString(R.string.urlsemuaminjam);
        httpGET(URLHTTP);

        return rootView;
    }


    //SEARCHVIEW
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = new SearchView(getContext());
        searchView.setQueryHint("Cari jurnal");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mRecyclerViewItems.clear();
                searchjurnal(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        item.setActionView(searchView);
    }

    public void httpGET(String url)
    {
        mRecyclerViewItems.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsingData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,"utf-8");
                    Log.d(TAG,"ERROR "+responseBody);
                }catch (UnsupportedEncodingException errorr){
                    Log.d(TAG,errorr.toString());
                }
            }
        }
        );
        QUEUE.add(stringRequest);
    }

    public void parsingData(String jsonData)
    {
        try {
            JSONArray obj = new JSONArray(jsonData);
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jo_inside = obj.getJSONObject(i);
                int id = jo_inside.getInt("id_rak");
                int id_user = jo_inside.getInt("id_user");
                String judul = jo_inside.getString("judul");
                String pengarang = jo_inside.getString("pengarang");
                String tahun_terbit = jo_inside.getString("tahun_terbit");
                String urlimages = jo_inside.getString("urlimages");
                String urlpdf = jo_inside.getString("urlpdf");
                String nama = jo_inside.getString("nama");
                String nim = jo_inside.getString("nim");
                String prodi = jo_inside.getString("prodi");
                String created_at = jo_inside.getString("created_at");
                String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");

                ModelAdminStatusPinjam modelAdminStatusPinjam = new ModelAdminStatusPinjam(id, id_user, judul, pengarang, tahun_terbit, urlimages, urlpdf, nama, nim, prodi,created_at,status_buku_jurnal);
                mRecyclerViewItems.add(modelAdminStatusPinjam);

            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void searchjurnal (String query){
        String UrlSearch = "http://192.168.43.183/apiperpus/public/api/search/"+query;
        QUEUE = Volley.newRequestQueue(getContext());
        mRecyclerViewItems.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSearch,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray m_jArry = null;
                        try {
                            m_jArry = new JSONArray(response);
                            for (int i = 0; i < m_jArry.length(); i++) {
                                JSONObject jo_inside = m_jArry.getJSONObject(i);
                                int id = jo_inside.getInt("id");
                                int id_user = jo_inside.getInt("id_user");
                                String judul = jo_inside.getString("judul");
                                String pengarang = jo_inside.getString("pengarang");
                                String tahun_terbit = jo_inside.getString("tahun_terbit");
                                String urlimages = jo_inside.getString("urlimages");
                                String urlpdf = jo_inside.getString("urlpdf");
                                String nama = jo_inside.getString("nama");
                                String nim = jo_inside.getString("nim");
                                String prodi = jo_inside.getString("prodi");
                                String created_at = jo_inside.getString("created_at");
                                String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");

                                ModelAdminStatusPinjam modelAdminStatusPinjam = new ModelAdminStatusPinjam(id,id_user, judul, pengarang, tahun_terbit, urlimages, urlpdf, nama, nim, prodi, created_at,status_buku_jurnal);
                                mRecyclerViewItems.add(modelAdminStatusPinjam);
                            }
                            rv.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,"utf-8");
                    Log.d(TAG,"ERROR "+responseBody);
                }catch (UnsupportedEncodingException errorr){
                    Log.d(TAG,errorr.toString());
                }
            }
        }
        );

        QUEUE.add(stringRequest);
    }

    public void clikcData(String datatitle)
    {
        Toast.makeText(getContext(),datatitle, Toast.LENGTH_SHORT).show();
    }
}