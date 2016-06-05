package com.rtmillerprojects.giftideareminder.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.listener.FabClickListener;
import com.rtmillerprojects.giftideareminder.model.Contact;

import java.util.ArrayList;

/**
 * Created by Ryan on 5/21/2016.
 */
public class GiftsFragment extends BaseFragment implements FabClickListener{

    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> contacts;

    public GiftsFragment() {
        // Required empty public constructor
    }

    public static GiftsFragment newInstance() {
        GiftsFragment fragment = new GiftsFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        if(container==null) {
            return null;
        }
        return inflater.inflate(R.layout.gifts_fragment, container, false);
    }

    @Override
    public void fabClickAction() {
        Toast.makeText(ACA,"WE ARE IN GIFTS",Toast.LENGTH_SHORT).show();
    }
}
