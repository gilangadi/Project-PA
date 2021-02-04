package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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

public class DetailKoleksiJurnalActivity extends AppCompatActivity {

    public static final String URLs = "http://192.168.43.183/apiperpus/public/api/pinjam";

    private TextView jjudulk, jpengarangk,jtahunterbitk,decrip;
    private ImageView jimgk;

    private Context mContext;
    private Activity mActivity;

    private LinearLayout mCLayout;
    private Button mButton;
    private Button Buttonpdf;

    private  String UrlGambar;
    private  String UrlPdf;

    String idUser = "";
    String Idrak ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_koleksi_jurnal);

        createNotivicationChanel();

        decrip = (TextView) findViewById(R.id.descrip_detail);

        jjudulk = (TextView) findViewById(R.id.judul_detail_koleksi_jurnal);
        jpengarangk = (TextView) findViewById(R.id.pengarang_detail_koleksi_jurnal);
        jtahunterbitk = (TextView) findViewById(R.id.tahun_terbit_detail_koleksi_jurnal);
        jimgk = (ImageView) findViewById(R.id.image_detail_koleksi_jurnal);

        //pasing detail jurnal
        Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Pengarang = intent.getStringExtra("Pengarang");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        String Urlimages = intent.getStringExtra("Urlimages");
        String Urlpdf = intent.getStringExtra("Urlpdf");

        Idrak = String.valueOf(intent.getIntExtra("Id_rak",0));
        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailKoleksiJurnalActivity.this).getId());
        Toast.makeText(DetailKoleksiJurnalActivity.this,"id user : "+idUser,Toast.LENGTH_SHORT).show();

        UrlGambar = ("http://192.168.43.183/apiperpus/public/uploads/"+Urlimages);
        UrlPdf = ("http://192.168.43.183/apiperpus/public/pdf/"+Urlpdf);

        jjudulk.setText(Judul);
        jpengarangk.setText(Pengarang);
        jtahunterbitk.setText(Tahun_terbit);
        Glide.with(this)
                .load("http://192.168.43.183/apiperpus/public/uploads/"+Urlimages)
                .centerCrop()
                .fitCenter()
                .into(jimgk);

        mContext = getApplicationContext();
        mActivity = DetailKoleksiJurnalActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_jurnalnya);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf);

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

//        BUTOON PINJAM
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
                        Toast.makeText(DetailKoleksiJurnalActivity.this,"Anda melanjudkan peminjaman!",Toast.LENGTH_SHORT).show();
                        transaksi(idUser,Idrak);

//                        //NOTIVIKASI PEMINJAMAN
//                        Intent intent = new Intent(Detail_Koleksi_Jurnal_Activity.this,ReminderBroadcastReceiver.class);
//                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Detail_Koleksi_Jurnal_Activity.this, 0,intent,0);
//                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                        long timeButtononClick = System.currentTimeMillis();
//                        long tenSeconInmillis = 10 * 5;
//                        Log.d("waktunya",""+timeButtononClick + tenSeconInmillis);
//                        alarmManager.set(AlarmManager.RTC_WAKEUP,
//                                15000,
//                                pendingIntent);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailKoleksiJurnalActivity.this,"Anda membatalkan peminjaman!",Toast.LENGTH_SHORT).show();
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

    //CREATE NOTIVIKASI
    private void createNotivicationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kemdsjhfkjh";
            String description = "Chanell notiv";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //CREATE PINJAM
    public void transaksi(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailKoleksiJurnalActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailKoleksiJurnalActivity.this,"Anda melanjudkan peminjaman!",Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailKoleksiJurnalActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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
