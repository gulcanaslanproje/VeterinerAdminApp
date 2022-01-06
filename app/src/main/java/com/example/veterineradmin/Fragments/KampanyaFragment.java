package com.example.veterineradmin.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.veterineradmin.Activies.MainActivity;
import com.example.veterineradmin.Adapter.KampanyaAdapter;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.ChangeFragments;
import com.example.veterineradmin.Utils.Warnings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView kampanyaRecView;
    private List<KampanyaModel> kampanyaList;
    private KampanyaAdapter kampanyaAdapter;
    private ChangeFragments changeFragments;
    private Button kampanyaEkleButton, kampanyaImageEkleButton, kampanyaDetayEkleButton;
    private ImageView kampanyaEkleImageView;
    private EditText kampanyaBaslikEditText, kampanyaIcerikEditText;
    private Bitmap bitmap = null;
    private String imageString = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampamya, container, false);
        tanim();
        click();
        getKampanya();
        return view;
    }

    private void tanim() {
        kampanyaRecView = (RecyclerView) view.findViewById(R.id.kampanyaRecView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        kampanyaRecView.setLayoutManager(layoutManager);
        kampanyaList = new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        kampanyaEkleButton = (Button) view.findViewById(R.id.kampanyaEkleButton);

    }

    private void click() {
        kampanyaEkleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKampanya();
            }
        });

    }


    private void getKampanya() {
        Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();

        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {
                if (response.body().get(0).isTf()) {
                    if (response.isSuccessful()) {
                        kampanyaList = response.body();
                        kampanyaAdapter = new KampanyaAdapter(kampanyaList, getContext(),getActivity());
                        kampanyaRecView.setAdapter(kampanyaAdapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Kampanya yok...", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void addKampanya() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanyaeklelayout, null);
        kampanyaImageEkleButton = (Button) view.findViewById(R.id.kampanyaImageEkleButton);
        kampanyaBaslikEditText = (EditText) view.findViewById(R.id.kampanyaBaslikEditText);
        kampanyaIcerikEditText = (EditText) view.findViewById(R.id.kampanyaIcerikEditText);
        kampanyaEkleImageView = (ImageView) view.findViewById(R.id.kampanyaEkleImageView);
        kampanyaDetayEkleButton = (Button) view.findViewById(R.id.kampanyaDetayEkleButton);


        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        kampanyaImageEkleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        kampanyaDetayEkleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageToString().equals("") && !kampanyaBaslikEditText.getText().equals("") && !kampanyaIcerikEditText.getText().equals("")) {
                    kampanyaEkle(kampanyaBaslikEditText.getText().toString(), kampanyaIcerikEditText.getText().toString(), imageToString(), alertDialog);

                    kampanyaBaslikEditText.setText("");
                    kampanyaIcerikEditText.setText("");
                    kampanyaEkleImageView.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getContext(), "Tüm alanların doldurulması ve resim seçimi zorunludur...", Toast.LENGTH_LONG).show();
                }

            }
        });

        alertDialog.show();
    }

    private void galeriAc() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777 && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                kampanyaEkleImageView.setImageBitmap(bitmap);
                kampanyaEkleImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private String imageToString() {

        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
             imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
            return imageString;
        } else
            return imageString;
    }

    private void kampanyaEkle(String baslik, String icerik, String imageString, final AlertDialog alertDialog) {

        Call<KampanyaEkleModel> req = ManagerAll.getInstance().addKampanya(baslik, icerik, imageString);
        req.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {

                if (response.body().isTf()) {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    getKampanya();
                    alertDialog.cancel();
                } else {
                    Toast.makeText(getContext(), response.body().getSonuc(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
        /*  Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();

        req.enqueue(new Callback<List<KampanyaModel>>() {*/
    }
}
