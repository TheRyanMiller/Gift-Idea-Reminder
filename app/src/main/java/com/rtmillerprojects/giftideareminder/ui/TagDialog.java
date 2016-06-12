package com.rtmillerprojects.giftideareminder.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.adapter.TagAdapter;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/11/2016.
 */
public class TagDialog extends DialogFragment{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> allContacts;
    private ArrayList<String> contactNames;
    private ArrayList<NameValuePair> contactPairs;
    private DatabaseHelper db;
    private String str;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.edit_tag_list, null);
        recyclerView = (RecyclerView) dialogLayout.findViewById(R.id.recycler_view_tags);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        contactNames = new ArrayList<>();
        db = DatabaseHelper.getInstance(getActivity());
        str="";
        contactPairs = new ArrayList<>();
        allContacts = db.getAllContacts();
        for (Contact contact : allContacts) {
            contactPairs.add((new NameValuePair(contact.getName(), (int) contact.getId())));
        }

        final TagAdapter tagAdapter = new TagAdapter(contactPairs, getActivity()); //Maybe need to pass in a bunch of contact names here?
        recyclerView.setAdapter(tagAdapter);
        recyclerView.setLayoutManager(layoutManager);



        builder .setView(dialogLayout)
                //.setMessage("This is my dialog")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View view = getView();
                        //RecyclerView recyclerView = (RecyclerView) (view != null ? view.findViewById(R.id.recycler_view_tags) : null);
                        int count = recyclerView.getAdapter().getItemCount();
                        CheckBox chkbox;
                        for(int i=0;i<count;i++){
                            chkbox = (CheckBox) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tagCheckBox);
                            if(chkbox.isChecked()) {
                                str = str + tagAdapter.getTagPair(i).value+ ", ";
                            }
                        }
                        str=str.substring(0,str.length()-2);
                        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
