package com.example.myapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class PinjamFragment extends Fragment {

    View myFragment;

    private ViewPager viewPagerPinjam;
    private TabLayout tabLayoutPinjam;

    public PinjamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment =  inflater.inflate(R.layout.fragment_pinjam, container, false);


        viewPagerPinjam = myFragment.findViewById(R.id.view_pager_pinjam);
        tabLayoutPinjam = myFragment.findViewById(R.id.tab_layout_pinjam);

        return myFragment;
    }
    //Call onactivity create method


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPagerPinjam);
        tabLayoutPinjam.setupWithViewPager(viewPagerPinjam);

        tabLayoutPinjam.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        adabterpinjam.addFragment (new StatusPinjamFragment(), "Status Pinjam");
        adabterpinjam.addFragment (new StatusKembaliFragment(), "Status Kembali");

        viewPagerPinjam.setAdapter(adabterpinjam);
    }
}