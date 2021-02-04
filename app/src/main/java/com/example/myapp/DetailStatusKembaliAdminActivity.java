package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailStatusKembaliAdminActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    public static final String URLs = "http://192.168.43.183/apiperpus/public/api/pengembalian";

    private TextView jjudul, jpengarang, jtahunterbit,textViewNama,textViewNim, textViewProdi, textViewCreated_at;
    private ImageView jimg;

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mCLayout;
    private Button Buttonpdf;
    private Button mButton;
    private Button buttonKoleksi;
    private Button alertButton;
    boolean isNotificActive = false;
    int notifID = 33;


    private String UrlGambar;
    private String UrlPdf;

    String idUser = "";
    String Idrak = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_kembali_admin);

        jjudul = (TextView) findViewById(R.id.judul_detail_status_admin_kembali);
        jpengarang = (TextView) findViewById(R.id.pengarang_detail_status_admin_kembali);
        jtahunterbit = (TextView) findViewById(R.id.tahun_terbit_detail_status_admin_kembali);
        jimg = (ImageView) findViewById(R.id.image_detail_status_admin_kembali);

        textViewNama = (TextView) findViewById(R.id.nama_peminjam_kembali);
        textViewNim = (TextView) findViewById(R.id.nim_peminjam_kembali);
        textViewProdi = (TextView) findViewById(R.id.prodi_peminjam_kembali);
        textViewCreated_at = (TextView) findViewById(R.id.tanggal_peminjam_kembali);

        //pasing detail jurnal
        Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Pengarang = intent.getStringExtra("Pengarang");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        String Nama = intent.getStringExtra("nama");
        String Nim = intent.getStringExtra("nim");
        String Prodi = intent.getStringExtra("prodi");
        String Tanggal_pinjam = intent.getStringExtra("created_at");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");

        Idrak = String.valueOf(intent.getIntExtra("Id", 0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailStatusKembaliAdminActivity.this).getId());

        Toast.makeText(DetailStatusKembaliAdminActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();
        UrlGambar = ("http://192.168.43.183/apiperpus/public/uploads/" + Urlimages);
        UrlPdf = ("http://192.168.43.183/apiperpus/public/pdf/" + Urlpdf);

        jjudul.setText(Judul);
        jpengarang.setText(Pengarang);
        jtahunterbit.setText(Tahun_terbit);
        textViewNama.setText(Nama);
        textViewNim.setText(Nim);
        textViewProdi.setText(Prodi);
        textViewCreated_at.setText(Tanggal_pinjam);


        Glide.with(this)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailStatusKembaliAdminActivity.this;
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
}