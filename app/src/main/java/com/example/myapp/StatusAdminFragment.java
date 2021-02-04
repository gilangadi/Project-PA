package com.example.myapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class StatusAdminFragment extends Fragment {

    View myFragment;

    private ViewPager viewPagerStatusAdmin;
    private TabLayout tabLayoutStatusadmin;

    public StatusAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragment =  inflater.inflate(R.layout.fragment_status_admin, container, false);
        viewPagerStatusAdmin = myFragment.findViewById(R.id.view_pager_status_admin);
        tabLayoutStatusadmin = myFragment.findViewById(R.id.tab_layout_status_admin);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPagerStatusAdmin);
        tabLayoutStatusadmin.setupWithViewPager(viewPagerStatusAdmin);

        tabLayoutStatusadmin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPagerPinjam) {
        SectionPagerAdabterPinjamPengguna adabterpinjam = new SectionPagerAdabterPinjamPengguna(getChildFragmentManager());

        adabterpinjam.addFragment (new StatusPinjamAdminFragment(), "Status Pinjam");
        adabterpinjam.addFragment (new StatusKembaliAdminFragment(), "Status Kembali");

        viewPagerPinjam.setAdapter(adabterpinjam);
    }
}