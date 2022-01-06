package com.example.veterineradmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.veterineradmin.Adapter.AsiTakipAdapter;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiTakipFragment extends Fragment {

    private View view;
    private DateFormat format;
    private Date date;
    private String reportDate;
    private ChangeFragments changeFragments;
    private RecyclerView asiTakipRecylerView;
    private AsiTakipAdapter asiTakipAdapter;
    List<PetAsiTakipModel> petAsiTakipList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_takip, container, false);
        tanimla();
        istekAt(reportDate);
        return view;
    }

    private void tanimla() {
        changeFragments = new ChangeFragments(getContext());
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        reportDate =format.format(date);
        asiTakipRecylerView =(RecyclerView)view.findViewById(R.id.asiTakipRecylerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        asiTakipRecylerView.setLayoutManager(layoutManager);
        petAsiTakipList =new ArrayList<>();
    }

    public void  istekAt(String tarih)
    {
        Call<List<PetAsiTakipModel>> req = ManagerAll.getInstance().getPetAsiTakip(tarih);
        req.enqueue(new Callback<List<PetAsiTakipModel>>() {
            @Override
            public void onResponse(Call<List<PetAsiTakipModel>> call, Response<List<PetAsiTakipModel>> response) {
                if(response.body().get(0).isTf())
                {
                    Toast.makeText(getContext(), "Bugün "+response.body().size()+" pate aşı yapılacaktır", Toast.LENGTH_LONG).show();

                    petAsiTakipList = response.body();
                    asiTakipAdapter= new AsiTakipAdapter(petAsiTakipList,getContext(),getActivity());
                    asiTakipRecylerView.setAdapter(asiTakipAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Bugün aşı yapılacak pate bulunamadı", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());

                }
            }

            @Override
            public void onFailure(Call<List<PetAsiTakipModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });

    }
}






















