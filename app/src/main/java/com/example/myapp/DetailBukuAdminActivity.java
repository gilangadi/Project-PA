package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class DetailBukuAdminActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

//    public static final String URLs = "http://192.168.43.183/apiperpus/public/api/pinjam";

    private TextView jjudul, jpengarang, jtahunterbit;
    private ImageView jimg;

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mCLayout;
    private Button Buttonpdf;
    private Button mButton;
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
        setContentView(R.layout.activity_detail_buku_admin);

        jjudul = (TextView) findViewById(R.id.judul_buku_detail_admin);
        jpengarang = (TextView) findViewById(R.id.pengarang_buku_detail_admin);
        jtahunterbit = (TextView) findViewById(R.id.tahun_terbit_buku_detail_admin);
        jimg = (ImageView) findViewById(R.id.image_buku_detail_admin);

        //pasing detail jurnal
        final Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Pengarang = intent.getStringExtra("Pengarang");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        Idrak = String.valueOf(intent.getIntExtra("Id", 0));

        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailBukuAdminActivity.this).getId());

        Toast.makeText(DetailBukuAdminActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();

        UrlGambar = ("http://192.168.43.183/apiperpus/public/uploads/" + Urlimages);
        UrlPdf = ("http://192.168.43.183/apiperpus/public/pdf/" + Urlpdf);

        jjudul.setText(Judul);
        jpengarang.setText(Pengarang);
        jtahunterbit.setText(Tahun_terbit);
        Glide.with(this)
                .load("http://192.168.43.183/apiperpus/public/uploads/" + Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimg);

        mContext = getApplicationContext();
        mActivity = DetailBukuAdminActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_buku_admin_admin);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf_admin);

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

        //BUTOON HAPUS
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing a new alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("Konfimasi hapus buku!");
                // Show a message on alert dialog
                builder.setMessage("Yakin untuk hapus buku!");
                // Set the positive button
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailBukuAdminActivity.this, "Anda menghapus buku!", Toast.LENGTH_SHORT).show();
                        hapusbuku(Idrak);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailBukuAdminActivity.this, "Anda membatalkan hapus buku!", Toast.LENGTH_SHORT).show();
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

    //HAPUS
    private void hapusbuku(final String idrak) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String URLsHapus = "http://192.168.43.183/apiperpus/public/api/rakadmin/"+idrak;
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, URLsHapus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
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

}