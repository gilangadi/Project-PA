package com.example.myapp;

import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

public class BukuAdminFragment extends Fragment {

    public static final String TAG = "MYTAG";
    RequestQueue QUEUE;
    String URLHTTP;

    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    RecyclerView rv;

    private SearchView searchView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView myRecyclerview;

    SwipeRefreshLayout swipeRefreshLayout;

    public static BukuAdminFragment newInstance()
    {
        BukuAdminFragment bukuAdminFragment = new BukuAdminFragment();
        return bukuAdminFragment;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    //SEARCHVIEW
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);

        //2 KOLOM
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(gridLayoutManager);

        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = new SearchView(getContext());
        searchView.setQueryHint("Cari buku");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mRecyclerViewItems.clear();
                searchbuku(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        item.setActionView(searchView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View rootView = inflater.inflate(R.layout.fragment_buku_admin,container,false);
        setHasOptionsMenu(true);

        rv = (RecyclerView)rootView.findViewById(R.id.recyclerView_buku_admin);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter    = new AdabterBukuAdmin(getContext(),mRecyclerViewItems,this);
        QUEUE = Volley.newRequestQueue(getContext());
        URLHTTP = getResources().getString(R.string.urlserverbuku);
        httpGET(URLHTTP);
        return rootView;
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
        });
//        QUEUE.getCache().clear();
        QUEUE.add(stringRequest);
    }

    public void parsingData(String jsonData)
    {
        try {
            JSONObject obj = new JSONObject(jsonData);
            JSONArray m_jArry = obj.getJSONArray("data");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int id = jo_inside.getInt("id");
                String judul = jo_inside.getString("judul");
                String pengarang = jo_inside.getString("pengarang");
                String tahun_terbit = jo_inside.getString("tahun_terbit");
                String urlimages = jo_inside.getString("urlimages");
                String urlpdf = jo_inside.getString("urlpdf");
                String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");

                ModelBukuAdmin modelBukuAdmin = new ModelBukuAdmin(id, judul, pengarang, tahun_terbit, urlimages,urlpdf,status_buku_jurnal);
                mRecyclerViewItems.add(modelBukuAdmin);
            }
            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void searchbuku (String query){
        String UrlSearch = "http://192.168.43.183/apiperpus/public/api/searchbuku/"+query;
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
                                String judul = jo_inside.getString("judul");
                                String pengarang = jo_inside.getString("pengarang");
                                String tahun_terbit = jo_inside.getString("tahun_terbit");
                                String urlimages = jo_inside.getString("urlimages");
                                String urlpdf = jo_inside.getString("urlpdf");
                                String status_buku_jurnal = jo_inside.getString("status_buku_jurnal");

                                ModelBukuAdmin modelBukuAdmin = new ModelBukuAdmin(id, judul, pengarang, tahun_terbit, urlimages,urlpdf,status_buku_jurnal);
                                mRecyclerViewItems.add(modelBukuAdmin);
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
        });
//        QUEUE.getCache().clear();
        QUEUE.add(stringRequest);
    }
    @Override
    public void onResume() {
        super.onResume();
        httpGET(URLHTTP);
    }
}