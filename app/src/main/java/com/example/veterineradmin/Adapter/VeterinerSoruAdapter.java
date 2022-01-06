package com.example.veterineradmin.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.Models.SoruModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.ViewHolder> {

    List<SoruModel> list;
    Context context;
    Activity activity;

    public VeterinerSoruAdapter(List<SoruModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sorularitemlayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.soruKullaniciText.setText(list.get(position).getKadi());
        holder.soruSoruText.setText(list.get(position).getSoru());

        holder.soruAramaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon());
            }
        });
        holder.soruCevablaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cevaplaAlert(list.get(position).getMusid(), list.get(position).getSoruid(), list.get(position).getSoru().toString(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView soruKullaniciText, soruSoruText;
        ImageView soruCevablaButton, soruAramaButton;

        public ViewHolder(View itemView) {
            super(itemView);
            soruKullaniciText = (TextView) itemView.findViewById(R.id.soruKullaniciText);
            soruSoruText = (TextView) itemView.findViewById(R.id.soruSoruText);
            soruCevablaButton = (ImageView) itemView.findViewById(R.id.soruCevablaButton);
            soruAramaButton = (ImageView) itemView.findViewById(R.id.soruAramaButton);

        }
    }


    public void ara(String callNum) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + callNum));
        activity.startActivity(intent);

    }

    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void cevaplaAlert(final String musid, final String soruid, String soru, final int postion) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.soru_cevapla_alert_layout, null);

        final EditText cevaplaedittext = (EditText) view.findViewById(R.id.cevaplaedittext);
        Button cevaplabutton = (Button) view.findViewById(R.id.cevaplabutton);
        TextView cevaplanacakSoruText = (TextView) view.findViewById(R.id.cevaplanacakSoruText);
        cevaplanacakSoruText.setText(soru);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();
        cevaplabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                cevapla(musid, soruid, cevaplaedittext.getText().toString(), alertDialog, postion);

            }
        });
        alertDialog.show();
    }

    public void cevapla(String musid, String soruid, String text, final AlertDialog alertDialog, final int postion) {
        Call<CevaplaModel> req = ManagerAll.getInstance().cevapla(musid, soruid, text);
        req.enqueue(new Callback<CevaplaModel>() {
            @Override
            public void onResponse(Call<CevaplaModel> call, Response<CevaplaModel> response) {
                if (response.body().isTf()) {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                    deleteToList(postion);
                } else {
                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<CevaplaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }
}
