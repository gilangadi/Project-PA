package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextNama, editTextNim, editTextPassword;
    AutoCompleteTextView textInputLayoutspin, editTextProdi, editTextStatus;
    ProgressBar progressBar;

    private AutoCompleteTextView dropdown;
    private AutoCompleteTextView dropdown1;
    private static final String URLs = "http://192.168.43.183/apiperpus/public/register";

    ConnectivityManager conMgr;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //// CEK KONEKSI INTERNET
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        if (SharedPrefmanager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this,PenggunaActivity.class));
            return;
        }

        textInputLayoutspin = findViewById(R.id.prodiregister);
        dropdown = findViewById(R.id.prodiregister);

        textInputLayoutspin = findViewById(R.id.statusregister);
        dropdown1 = findViewById(R.id.statusregister);

        String[] items = new String[]{
                "Tenik Informatika",
                "Teknik Mesin",
                "Menegemen Bisnis Pariwisata",
                "Agribisnis",
                "Teknik Sipil",
                "Manufaktur Kapal",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RegisterActivity.this,R.layout.support_simple_spinner_dropdown_item,
                items
        );
        dropdown.setAdapter(adapter);

        //SPINNER STATUS
        String[] items1 = new String[]{
                "pengguna",
        };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                RegisterActivity.this,R.layout.support_simple_spinner_dropdown_item,
                items1
        );
        dropdown1.setAdapter(adapter1);

        editTextNama = (TextInputEditText) findViewById(R.id.namaregister);
        editTextNim = (TextInputEditText) findViewById(R.id.nimregister);
        editTextProdi = (AutoCompleteTextView) findViewById(R.id.prodiregister);
        editTextStatus = (AutoCompleteTextView) findViewById(R.id.statusregister);
        editTextPassword = (TextInputEditText) findViewById(R.id.passwordregister);

        findViewById(R.id.loginsudah).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    public void register(View view) {
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

        //CEK KONEKSI INTERNET
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        //VALIDASI
        if (editTextNama.getText().toString().length() == 0) {
            editTextNama.setError("Silakan masukkan nama");
            editTextNama.requestFocus();
            return;
        }
        if (editTextNim.getText().toString().length() == 0) {
            editTextNim.setError("Silakan masukkan NIM");
            editTextNim.requestFocus();
            return;
        }
        if (editTextProdi.getText().toString().length() == 0) {
            editTextProdi.setError("Silakan masukkan prodi");
            editTextProdi.requestFocus();
            return;
        }
        if (editTextPassword.getText().toString().length() == 0) {
            editTextPassword.setError("Silakan masukkan password");
            editTextPassword.requestFocus();
            return;
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APPLOG", response);
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APPLOG", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", editTextNama.getText().toString().trim());
                params.put("nim", editTextNim.getText().toString().trim());
                params.put("prodi", editTextProdi.getText().toString().trim());
                params.put("status", editTextStatus.getText().toString().trim());
                params.put("password", editTextPassword.getText().toString().trim());
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
                showAlert(jsonObj.get("result").toString());
            } else {
                showAlert(jsonObj.get("result").toString());
            }
        } catch (JSONException e) {
        }
    }

    public void showAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Silahkan login!");
        builder.setTitle(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#2574A9"));
    }
}