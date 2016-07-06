package com.rtmillerprojects.giftideareminder.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ryan on 5/28/2016.
 */
public class BaseFragment extends Fragment {
    protected AppCompatActivity ACA;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        ACA = (AppCompatActivity) context;

    }

    @Override public void onDetach() {
        super.onDetach();
        ACA = null;
    }

}
