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

import java.util.HashMap;
import java.util.Map;

public class DetailJurnalActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;

    public static final String URLs = "http://192.168.43.183/apiperpus/public/api/pinjam";
    public static final String URLsKoleksijurnal = "http://192.168.43.183/apiperpus/public/api/koleksi";

    private TextView jjudul, jpengarang, jtahunterbit;
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
        setContentView(R.layout.activity_detail_jurnal);

        jjudul = (TextView) findViewById(R.id.judul_jurnal_detail);
        jpengarang = (TextView) findViewById(R.id.pengarang_jurnal_detail);
        jtahunterbit = (TextView) findViewById(R.id.tahun_terbit_jurnal_detail);
        jimg = (ImageView) findViewById(R.id.image_jurnal_detail);

        //pasing detail jurnal
        Intent intent = getIntent();
        String Judul = intent.getStringExtra("Judul");
        String Pengarang = intent.getStringExtra("Pengarang");
        String Tahun_terbit = intent.getStringExtra("Tahun_terbit");
        final String Urlimages = intent.getStringExtra("Urlimages");
        final String Urlpdf = intent.getStringExtra("Urlpdf");
        Idrak = String.valueOf(intent.getIntExtra("Id", 0));

        idUser = String.valueOf(SharedPrefmanager.getInstance(DetailJurnalActivity.this).getId());

        Toast.makeText(DetailJurnalActivity.this, "id user : " + idUser, Toast.LENGTH_SHORT).show();

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
        mActivity = DetailJurnalActivity.this;

        mButton = (Button) findViewById(R.id.button_pinjam_jurnal);
        buttonKoleksi = (Button) findViewById(R.id.button_koleksi_jurnal);
        Buttonpdf = (Button) findViewById(R.id.Button_pdf);

        //OPEN PDF
        Buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlPdf));
                startActivity(fileIntent);
            }
        });

        //BUTTON KOLEKSI
        buttonKoleksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                koleksijurnal(idUser, Idrak);
            }
        });

        //BUTOON PINJAM
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
                        Toast.makeText(DetailJurnalActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        transaksi(idUser, Idrak);

//                        scheduleNotification(getNotification("Anda telah melewati batas pengambilan buku"), 6000);

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailJurnalActivity.this, "Anda membatalkan peminjaman!", Toast.LENGTH_SHORT).show();
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

//    //NOTIVIKASI
//    public void scheduleNotification(Notification notification, int delay) {
//        final Intent notificationIntent = new Intent(DetailJurnalActivity.this, NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//    }

//    private Notification getNotification(String content) {
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("Notification Peminjaman");
//        builder.setContentText(content);
//        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
//        return builder.build();
//    }

    //CREATE KOLEKSI
    public void koleksijurnal(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailJurnalActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLsKoleksijurnal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailJurnalActivity.this, "Anda  menambahkan koleksi buku!", Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailJurnalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
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


    //CREATE PINJAM
    public void transaksi(final String id_user, final String id_rak) {
        RequestQueue queue = Volley.newRequestQueue(DetailJurnalActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        Toast.makeText(DetailJurnalActivity.this, "Anda melanjudkan peminjaman!", Toast.LENGTH_SHORT).show();
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                        Toast.makeText(DetailJurnalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
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