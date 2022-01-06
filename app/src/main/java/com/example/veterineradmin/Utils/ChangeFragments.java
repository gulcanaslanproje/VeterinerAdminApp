package com.example.veterineradmin.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.veterineradmin.R;

public class ChangeFragments {


    private Context context;

    public ChangeFragments(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment)
    {
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, fragment, "Fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

    public void changeWithParameters(Fragment fragment , String petid)
    {
        Bundle bundle = new Bundle();
        bundle.putString("userid",petid);
        fragment.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout, fragment, "Fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }
}
