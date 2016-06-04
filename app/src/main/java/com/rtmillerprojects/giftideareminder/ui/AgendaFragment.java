package com.rtmillerprojects.giftideareminder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.listener.FabClickListener;

/**
 * Created by Ryan on 5/21/2016.
 */
public class AgendaFragment extends BaseFragment implements FabClickListener{

    public static AgendaFragment newInstance() {
        AgendaFragment fragment = new AgendaFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.agenda_fragment, container, false);
    }


    @Override
    public void fabClickListener() {
        Toast.makeText(ACA,"WE ARE IN AGENDA",Toast.LENGTH_SHORT).show();
    }
}
