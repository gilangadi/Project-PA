package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePenggunaActivity extends AppCompatActivity {

    TextView textViewId, textViewNama, textViewNim, textViewProdi, textViewStatus;
    Button buttonLogout;
    SharedPreferences sharedpreferences;
    String nama, id, nim;

    public final static String TAG_NAMA = "nama";
    public final static String TAG_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pengguna);

        if (!SharedPrefmanager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewNama = (TextView) findViewById(R.id.textViewNama);
        textViewNim = (TextView) findViewById(R.id.textViewNim);
        textViewProdi = (TextView) findViewById(R.id.textViewProdi);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);


        //getting the current user
        User user = SharedPrefmanager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewNama.setText(user.getNama());
        textViewNim.setText(user.getNim());
        textViewProdi.setText(user.getProdi());
        textViewStatus.setText(user.getStatus());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefmanager.getInstance(getApplicationContext()).logout();
                finish();
            }
        });

    }
}