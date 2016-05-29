package com.rtmillerprojects.giftideareminder.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.model.Contact;
import java.util.ArrayList;

/**
 * Created by Ryan on 5/29/2016.
 */
public class ContactsFragment extends BaseFragment{

    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> contacts;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        if(container==null) {
            return null;
        }
        return inflater.inflate(R.layout.fragment2_layout, container, false);
    }


}
