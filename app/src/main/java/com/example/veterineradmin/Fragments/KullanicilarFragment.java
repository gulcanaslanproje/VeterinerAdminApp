package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineradmin.Adapter.UsersAdapter;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KullanicilarFragment extends Fragment {

    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView kullaniciRecyclerView;
    private List<KullanicilarModel> kullanicilarList;
    private UsersAdapter usersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kullanicilar, container, false);
        tanim();
        getKullanicilar();
        return view;
    }


    private void tanim() {
        changeFragments = new ChangeFragments(getContext());
        kullaniciRecyclerView = (RecyclerView) view.findViewById(R.id.kullaniciRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        kullaniciRecyclerView.setLayoutManager(layoutManager);
        kullanicilarList = new ArrayList<>();
    }

    private void getKullanicilar() {
        Call<List<KullanicilarModel>> req = ManagerAll.getInstance().getKullanicilar();

        req.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {
                if (response.body().get(0).isTf()) {

                        kullanicilarList = response.body();
                        usersAdapter = new UsersAdapter(kullanicilarList, getContext(), getActivity());
                        kullaniciRecyclerView.setAdapter(usersAdapter);
                    
                } else {
                    Toast.makeText(getContext(), "Sistemde kayıtlı kullanıcı yoktur...", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }


}
