package com.example.myapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KoleksiBukuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KoleksiBukuFragment extends Fragment {


    public static final String TAG = "MYTAG";
    RequestQueue QUEUE;
    String URLHTTP;

    String Idrak = "";

    private List<Object> mRecyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    RecyclerView rv;

    private GridLayoutManager gridLayoutManager;
    private RecyclerView myRecyclerview;


    public static KoleksiBukuFragment newInstance() {
        KoleksiBukuFragment koleksiBukuFragment = new KoleksiBukuFragment();
        return koleksiBukuFragment;
    }

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final  View rootView = inflater.inflate(R.layout.fragment_koleksi_buku,container,false);

        rv = (RecyclerView)rootView.findViewById(R.id.recyclerView_kolesib);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter    = new AdabterKoleksiBuku(getContext(),mRecyclerViewItems,this);
        QUEUE = Volley.newRequestQueue(getContext());

        User user = SharedPrefmanager.getInstance(getContext()).getUser();
        URLHTTP = getResources().getString(R.string.urlkoleksibuku)+'/'+user.getId();
        httpGET(URLHTTP);

        return rootView;
    }

    public void httpGET(String url)
    {
        //2 KOLOM
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(gridLayoutManager);

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
                int id_rak = jo_inside.getInt("id_rak");
                int id = jo_inside.getInt("id");
                String judul = jo_inside.getString("judul");
                String pengarang = jo_inside.getString("pengarang");
                String tahun_terbit = jo_inside.getString("tahun_terbit");
                String urlimages = jo_inside.getString("urlimages");
                String urlpdf = jo_inside.getString("urlpdf");

                ModelKoleksiBuku modelKoleksiBuku = new ModelKoleksiBuku(id,id_rak, judul, pengarang, tahun_terbit, urlimages, urlpdf);
                mRecyclerViewItems.add(modelKoleksiBuku);
            }

            rv.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}