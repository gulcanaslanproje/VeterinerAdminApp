package com.example.veterineradmin.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder> {

    List<KampanyaModel> list;
    Context context;
    Activity activity;

    public KampanyaAdapter(List<KampanyaModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity =activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kampanya_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.kampanyaLayoutBaslik.setText(list.get(position).getBaslik().toString());
        holder.kampanyaLayoutText.setText(list.get(position).getText().toString());
        Picasso.get().load(list.get(position).getResim().toString()).resize(150, 200).into(holder.kampanyaLayoutpetimage);

        holder.kampanyaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silKampanya(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kampanyaLayoutBaslik, kampanyaLayoutText;
        CircleImageView kampanyaLayoutpetimage;
        CardView kampanyaCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaLayoutBaslik = (TextView) itemView.findViewById(R.id.kampanyaLayoutBaslik);
            kampanyaLayoutText = (TextView) itemView.findViewById(R.id.kampanyaLayoutText);
            kampanyaLayoutpetimage = (CircleImageView) itemView.findViewById(R.id.kampanyaLayoutpetimage);
            kampanyaCardView = (CardView) itemView.findViewById(R.id.kampanyaCardView);
        }
    }


    public void silKampanya(final int position) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanyasillayout, null);
        Button kampanyaSilTamamButton = (Button) view.findViewById(R.id.kampanyaSilTamamButton);
        Button kampanyaSilIptalButton = (Button) view.findViewById(R.id.kampanyaSilIptalButton);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();


        kampanyaSilTamamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kampanyaSil(list.get(position).getId(),position);
                alertDialog.cancel();
            }
        });
        kampanyaSilIptalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void kampanyaSil(final String id, final int position) {
        Call<KampanyaSilModel> req = ManagerAll.getInstance().kampanyaSil(id);

        req.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {

                if(response.body().isTf())
                {
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();
                    deleteToList(position);
                }
                else
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
    public  void deleteToList(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
