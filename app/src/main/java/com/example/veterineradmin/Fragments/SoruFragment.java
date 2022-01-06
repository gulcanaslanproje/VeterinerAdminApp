package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.veterineradmin.Adapter.VeterinerSoruAdapter;
import com.example.veterineradmin.Models.SoruModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoruFragment extends Fragment {

    private View view;
    private RecyclerView soruRecyclerView;
    private List<SoruModel> list;
    VeterinerSoruAdapter veterinerSoruAdapter;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_soru, container, false);
        tanimla();
        istekAt();
        return view;
    }

    private void tanimla() {
        soruRecyclerView = (RecyclerView) view.findViewById(R.id.soruRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        soruRecyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());

    }

    public void istekAt() {
        Call<List<SoruModel>> req = ManagerAll.getInstance().getSoru();
        req.enqueue(new Callback<List<SoruModel>>() {
            @Override
            public void onResponse(Call<List<SoruModel>> call, Response<List<SoruModel>> response) {
                if (response.body().get(0).isTf()) {
                    list = response.body();
                    veterinerSoruAdapter = new VeterinerSoruAdapter(list, getContext(), getActivity());
                    soruRecyclerView.setAdapter(veterinerSoruAdapter);
                } else {
                    Toast.makeText(getContext(), "Soru yok...", Toast.LENGTH_LONG).show();
                     changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SoruModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
