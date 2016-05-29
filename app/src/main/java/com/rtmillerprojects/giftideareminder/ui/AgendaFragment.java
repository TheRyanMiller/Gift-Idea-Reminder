package com.rtmillerprojects.giftideareminder.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtmillerprojects.giftideareminder.R;

/**
 * Created by Ryan on 5/21/2016.
 */
public class AgendaFragment extends BaseFragment {

    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        if(container==null) {
            return null;
        }
        return inflater.inflate(R.layout.agenda_fragment, container, false);
    }

}
