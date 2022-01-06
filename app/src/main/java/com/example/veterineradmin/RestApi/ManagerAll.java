package com.example.veterineradmin.RestApi;


import com.example.veterineradmin.Models.AsiOnaylaModel;
import com.example.veterineradmin.Models.CevaplaModel;
import com.example.veterineradmin.Models.KampanyaEkleModel;
import com.example.veterineradmin.Models.KampanyaModel;
import com.example.veterineradmin.Models.KampanyaSilModel;
import com.example.veterineradmin.Models.KullanicilarModel;
import com.example.veterineradmin.Models.PetAsiTakipModel;
import com.example.veterineradmin.Models.PetModel;
import com.example.veterineradmin.Models.SoruModel;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<List<KampanyaModel>> getKampanya() {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return x;
    }

    public Call<KampanyaEkleModel> addKampanya(String baslik, String icerik, String imageString) {
        Call<KampanyaEkleModel> x = getRestApi().addKampanya(baslik, icerik, imageString);
        return x;
    }

    public Call<KampanyaSilModel> kampanyaSil(String id) {
        Call<KampanyaSilModel> x = getRestApi().kampanyaSil(id);
        return x;
    }

    public Call<List<PetAsiTakipModel>> getPetAsiTakip(String tarih) {
        Call<List<PetAsiTakipModel>> x = getRestApi().getPetAsiTakip(tarih);
        return x;
    }

    public Call<AsiOnaylaModel> asiOnayla(String id) {
        Call<AsiOnaylaModel> x = getRestApi().asiOnayla(id);
        return x;
    }

    public Call<AsiOnaylaModel> asiiptal(String id) {
        Call<AsiOnaylaModel> x = getRestApi().asiiptal(id);
        return x;
    }

    public Call<List<SoruModel>> getSoru() {
        Call<List<SoruModel>> x = getRestApi().getSoru();
        return x;
    }

    public Call<CevaplaModel> cevapla(String musid, String soruid, String text) {
        Call<CevaplaModel> x = getRestApi().cevapla(musid, soruid, text);
        return x;
    }

    public Call<List<KullanicilarModel>> getKullanicilar() {
        Call<List<KullanicilarModel>> x = getRestApi().getKullanicilar();
        return x;
    }

    public Call<List<PetModel>> getPets(String id) {
        Call<List<PetModel>> x = getRestApi().getPets(id);
        return x;
    }
}
