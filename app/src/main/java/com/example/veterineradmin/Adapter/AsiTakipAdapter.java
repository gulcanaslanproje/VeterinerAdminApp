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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.R;
import com.example.veterineradmin.RestApi.ManagerAll;
import com.example.veterineradmin.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiTakipAdapter extends RecyclerView.Adapter<AsiTakipAdapter.ViewHolder> {

    List<PetAsiTakipModel> list;
    Context context;
    Activity activity;

    public AsiTakipAdapter(List<PetAsiTakipModel> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asitakiplayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.asiTakipPetName.setText(list.get(position).getPetisim().toString());
        holder.asiTakipBilgiText.setText(list.get(position).getKadi().toString() + " isimli kullanıcının " + list.get(position).getPetisim().toString()
                + " isimli petinin " + list.get(position).getAsiisim() + " aşısı yapılacaktır...");

        Picasso.get().load(list.get(position).getPetresim().toString()).resize(150, 200).into(holder.asiTakipImage);

        holder.asiTakipOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asiOnayla(list.get(position).getAsiid().toString(),position);
            }
        });
        holder.asiTakipCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Onaylama(list.get(position).getAsiid().toString(),position);

            }
        });
        holder.asiTakipAraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ara(list.get(position).getTelefon().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView asiTakipPetName, asiTakipBilgiText;
        CircleImageView asiTakipImage;
        ImageView asiTakipOkButton, asiTakipCancelButton, asiTakipAraButton;

        public ViewHolder(View itemView) {
            super(itemView);
            asiTakipPetName = (TextView) itemView.findViewById(R.id.asiTakipPetName);
            asiTakipBilgiText = (TextView) itemView.findViewById(R.id.asiTakipBilgiText);
            asiTakipImage = (CircleImageView) itemView.findViewById(R.id.asiTakipImage);
            asiTakipOkButton = (ImageView) itemView.findViewById(R.id.asiTakipOkButton);
            asiTakipCancelButton = (ImageView) itemView.findViewById(R.id.asiTakipCancelButton);
            asiTakipAraButton = (ImageView) itemView.findViewById(R.id.asiTakipAraButton);

        }
    }


    public void deleteToList(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void ara(String callNum) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + callNum));
        activity.startActivity(intent);

    }

    public void asiOnayla(String id, final int position) {
        Call<AsiOnaylaModel> req = ManagerAll.getInstance().asiOnayla(id);
        req.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                deleteToList(position);
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void Onaylama(String id, final int position) {
        Call<AsiOnaylaModel> req = ManagerAll.getInstance().asiOnayla(id);
        req.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                deleteToList(position);
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });
    }
}
