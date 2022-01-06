package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineradmin.Models.PetModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.util.List;


public class KullaniciPetlerFragmenti extends Fragment {
    private String musid;
    private Bundle bundle;
    private ChangeFragments changeFragments;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_kullanici_fragmenti, container, false);
        tanimla();
        getPets(musid);
        return view;
    }

    public void tanimla()
    {
        musid =getArguments().get("userid").toString();
        changeFragments = new ChangeFragments(getContext());

    }

    public void  getPets(String id)
    {
        Call<List<PetModel>> req= ManagerAll.getInstance().getPets(id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if (response.body().get(0).isTf()) {
                    Toast.makeText(getContext(), "Kullanıcıya ait "+response.body().size() +" pet bulunmakta", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Kullanıcıya ait pey bulunamadı", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
