package com.rtmillerprojects.giftideareminder.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/29/2016.
 */
public class TagAdapter extends RecyclerView.Adapter {
    private ArrayList<String> tags;
    private Context context;
    private CheckBox tag;
    private DatabaseHelper db;
    private ArrayList<Contact> allContacts;

    public TagAdapter(ArrayList<String> tags, Context context) {
        /*
        db = DatabaseHelper.getInstance(context);
        allContacts = db.getAllContacts();
        for (Contact contact : allContacts) {
            contactNames.add(contact.getName());
        }
        */
        this.tags = tags;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }



    @Override public int getItemCount() {
        return tags.size();
    }
}