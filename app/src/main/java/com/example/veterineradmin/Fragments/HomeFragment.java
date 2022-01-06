package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.veterineradmin.Adapter.VeterinerSoruAdapter;
import com.example.veterineradmin.R;
import com.example.veterineradmin.Utils.ChangeFragments;

public class HomeFragment extends Fragment {

    private LinearLayout kampanyaLayout,asiTakipLayout,soruLayout,kullanicilarLayout;
    private  View view;
    private ChangeFragments changeFragments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        tanimVeTiklama();
        return  view;
    }

    private void tanimVeTiklama() {
        kampanyaLayout = (LinearLayout)view.findViewById(R.id.kampanyaLayout);
        asiTakipLayout = (LinearLayout)view.findViewById(R.id.asiTakipLayout);
        soruLayout= (LinearLayout)view.findViewById(R.id.soruLayout);
        kullanicilarLayout= (LinearLayout)view.findViewById(R.id.kullanicilarLayout);

        changeFragments = new ChangeFragments(getContext());

        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KampanyaFragment());
            }
        });

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new AsiTakipFragment());
            }
        });

        soruLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new SoruFragment());

            }
        });

        kullanicilarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KullanicilarFragment());

            }
        });
    }
}
