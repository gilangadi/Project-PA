package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BukuAdminFragment bukuAdminFragment;
    private JurnalAdminFragment jurnalAdminFragment;
    private StatusAdminFragment statusAdminFragment;
    private InputBukuJurnalAdminFragment inputBukuJurnalAdminFragment;

    private Activity mActivity;

    FrameLayout frameLayout;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager_admin);
        tabLayout = findViewById(R.id.tab_layout_admin);

        bukuAdminFragment = new BukuAdminFragment();
        jurnalAdminFragment = new JurnalAdminFragment();
        statusAdminFragment = new StatusAdminFragment();
        inputBukuJurnalAdminFragment = new InputBukuJurnalAdminFragment();

        tabLayout.setupWithViewPager(viewPager);
        AdminActivity.ViewPagerAdabter viewPagerAdabter = new AdminActivity.ViewPagerAdabter(getSupportFragmentManager(), 0);
        viewPagerAdabter.addFragment(bukuAdminFragment, "Buku");
        viewPagerAdabter.addFragment(jurnalAdminFragment, "Jurnal");
        viewPagerAdabter.addFragment(statusAdminFragment, "Status");
        viewPagerAdabter.addFragment(inputBukuJurnalAdminFragment, "Input");

        viewPager.setAdapter(viewPagerAdabter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_buku);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_jurnal);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_rak);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_input);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment miFragment=null;
//        boolean fragmentSeleccionado=false;
//        int id = item.getItemId();
//        if (id == R.id.notivikasi) {
//            Intent intent = new Intent(PenggunaActivity.this, NotivikasiActivity.class);
//            Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
//            startActivity(intent);
//        }
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private class ViewPagerAdabter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentsTitle = new ArrayList<>();

        public ViewPagerAdabter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentsTitle.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)  {
            return fragmentsTitle.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit..!");
        builder.setIcon(R.drawable.ic_logout);
        builder.setMessage("Are you sure Exit..!");
        builder.setCancelable(false);
        // Set the positive button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AdminActivity.this, "you clicked on cencel", Toast.LENGTH_LONG).show();
            }
        });
        // Create the alert dialog
        AlertDialog dialog = builder.create();
        // Finally, display the alert dialog
        dialog.show();
        // Get the alert dialog buttons reference
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setTextColor(Color.parseColor("#2574A9"));
//                positiveButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));

        negativeButton.setTextColor(Color.parseColor("#2574A9"));
//                negativeButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.natives){
            Intent intent = new Intent(AdminActivity.this,NotivikasiActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.profil){
            Intent intent = new Intent(AdminActivity.this,ProfilePenggunaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}