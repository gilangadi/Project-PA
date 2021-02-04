package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
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

public class DetailStatusPinjamAdminMainActivity extends AppCompatActivity {

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
    int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_status_pinjam_admin_main);

        jjudul = (TextView) findViewById(R.id.judul_detail_status_admin);
        jpengarang = (TextView) findViewById(R.id.pengarang_detail_status_admin);
        jtahunterbit = (TextView) findViewById(R.id.tahun_terbit_detail_status_admin);
        jimg = (ImageView) findViewById(R.id.image_detail_status_admin);

        textViewNama = (TextView) findViewById(R.id.nama_peminjam);
        textViewNim = (TextView) findViewById(R.id.nim_peminjam);
        textViewProdi = (TextView) findViewById(R.id.prodi_peminjam);
        textViewCreated_at = (TextView) findViewById(R.id.tanggal_peminjam);

        //pasing detail pinjam
        final Intent intent = getIntent();
        id_user = intent.getIntExtra("Id_user", 0);
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
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailStatusPinjamAdminMainActivity.this).getId());

        Toast.makeText(DetailStatusPinjamAdminMainActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();
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
        mActivity = DetailStatusPinjamAdminMainActivity.this;

        mButton = (Button) findViewById(R.id.button_create_kembalian);

        //BUTOON PENGEMBALIAN
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Konfimasi peminjaman!");
                // Show a message on alert dialog
                builder.setMessage("Buku pinjaman akan tersimpan pada rak!");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailStatusPinjamAdminMainActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        pengembalian(String.valueOf(id_user), Idrak);//wes

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailStatusPinjamAdminMainActivity.this, "Anda membatalkan peminjaman!", Toast.LENGTH_SHORT).show();
                    }
                });
                // Create the alert dialog
                AlertDialog dialog = builder.create();
                // Finally, display the alert dialog
                dialog.show();
                // Get the alert dialog buttons reference
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                // Change the alert dialog buttons text and background color
                positiveButton.setTextColor(Color.parseColor("#2574A9"));
//                positiveButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
                negativeButton.setTextColor(Color.parseColor("#2574A9"));
//                negativeButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
            }
        });
    }

//    CREATE PENGEMBALIAN
    public void pengembalian(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailStatusPinjamAdminMainActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailStatusPinjamAdminMainActivity.this,"Pengembalian berhasil di input!",Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailStatusPinjamAdminMainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);
                params.put("id_rak", id_rak);
                return params;
            }
        };
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
}