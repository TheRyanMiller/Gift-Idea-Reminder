package com.rtmillerprojects.giftideareminder.ui;

import android.annotation.SuppressLint;
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

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.TagAdapter;
import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.model.Gift;
import com.rtmillerprojects.giftideareminder.model.NameValueCheck;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Ryan on 6/11/2016.
 */
public class TagDialog extends DialogFragment{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Contact> allContacts;
    private ArrayList<Gift> allGifts;
    private ArrayList<AgendaItem> allEvents;
    private ArrayList<Contact> selectedContacts;
    private ArrayList<Gift> selectedGifts;
    private ArrayList<AgendaItem> selectedEvents;
    private ArrayList<NameValueCheck> tagPairs;
    private ArrayList<NameValueCheck> selectedTagPairs;
    private DatabaseHelper db;
    private String str;
    private String recordType;
    private long recordId;
    private String tagType;
    private ArrayList<Integer> selectedTagIds;
    private TagAdapter tagAdapter;
    OnMyDialogResult mDialogResult;

    public TagDialog(){/*required public constructor*/}

    @SuppressLint("ValidFragment")
    public TagDialog(String tagType, String recordType, long recordId){
        this.tagType = tagType;
        this.recordType = recordType;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.edit_tag_list, null);
        recyclerView = (RecyclerView) dialogLayout.findViewById(R.id.recycler_view_tags);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //ABSTRACT THIS STUFF
        db = DatabaseHelper.getInstance(getActivity());
        tagPairs = new ArrayList<>();

        if(tagType == "Contact"){
            if(recordType=="Event") {
                allContacts = db.getAllContacts();
                selectedContacts = db.getContactTagsForEvent(recordId);
                //Compare against selected list
                for (Contact contact : allContacts) {
                    NameValueCheck tempNVC = new NameValueCheck(contact.getName(), (int) contact.getId());
                    for (Contact selContact : selectedContacts) {
                        if (contact.getId() == selContact.getId()) {
                            tempNVC.isChecked = true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }
            if(recordType=="Gift") {
                allContacts = db.getAllContacts();
                selectedContacts = db.getContactTagsForGift(recordId);
                //Compare against selected list
                for (Contact contact : allContacts) {
                    NameValueCheck tempNVC = new NameValueCheck(contact.getName(), (int) contact.getId());
                    for(Contact selContact : selectedContacts) {
                        if(contact.getId() == selContact.getId()) {
                            tempNVC.isChecked=true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }
        }

        if(tagType == "Gift"){
            if(recordType=="Event") {
                allGifts = db.getAllGifts();
                selectedGifts = db.getGiftTagsForContact(recordId);
                //Compare against selected list
                for (Gift gift : allGifts) {
                    NameValueCheck tempNVC = new NameValueCheck(gift.getName(), (int) gift.getId());
                    for(Gift selGift : selectedGifts) {
                        if(gift.getId() == selGift.getId()) {
                            tempNVC.isChecked=true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }
            if(recordType=="Contact") {
                allEvents = db.getAllAgendaItems();
                selectedEvents = db.getEventTagsForGift(recordId);
                //Compare against selected list
                for (AgendaItem event : allEvents) {
                    NameValueCheck tempNVC = new NameValueCheck(event.getTitle(), (int) event.getId());
                    for(AgendaItem selEvent : selectedEvents) {
                        if(event.getId() == selEvent.getId()) {
                            tempNVC.isChecked=true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }
        }

        if(tagType == "Event"){
            if(recordType=="Contact") {
                allEvents = db.getAllAgendaItems();
                selectedEvents = db.getEventTagsForContact(recordId);
                //Compare against selected list
                for (AgendaItem event : allEvents) {
                    NameValueCheck tempNVC = new NameValueCheck(event.getTitle(), (int) event.getId());
                    for(AgendaItem selEvent : selectedEvents) {
                        if(event.getId() == selEvent.getId()) {
                            tempNVC.isChecked=true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }
            if(recordType=="Gift") {
                allEvents = db.getAllAgendaItems();
                selectedEvents = db.getEventTagsForGift(recordId);
                //Compare against selected list
                for (AgendaItem event : allEvents) {
                    NameValueCheck tempNVC = new NameValueCheck(event.getTitle(), (int) event.getId());
                    for(AgendaItem selEvent : selectedEvents) {
                        if(event.getId() == selEvent.getId()) {
                            tempNVC.isChecked=true;
                            break;
                        }
                    }
                    tagPairs.add(tempNVC);
                }
            }

        }

        //FINISH ABSTRACTING STUFF
        tagAdapter = new TagAdapter(tagPairs, getActivity()); //Maybe need to pass in a bunch of contact names here?
        recyclerView.setAdapter(tagAdapter);
        recyclerView.setLayoutManager(layoutManager);
        str = "";


        builder .setView(dialogLayout)
                //.setMessage("This is my dialog")
                .setPositiveButton("OK", new OKListener())
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();

        return dialog;
    }

    private class OKListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if( mDialogResult != null ){
                int count = recyclerView.getAdapter().getItemCount();
                CheckBox chkbox;
                selectedTagIds = new ArrayList<>();
                for(int i=0;i<count;i++){
                    chkbox = (CheckBox) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tagCheckBox);
                    if(chkbox.isChecked()) {
                        selectedTagIds.add(tagAdapter.getTagPair(i).value);
                        str = str + tagAdapter.getTagPair(i).value+ ", ";
                    }
                }
                mDialogResult.dialogFinish(selectedTagIds);
            }
            TagDialog.this.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }
    public interface OnMyDialogResult{
        void dialogFinish(ArrayList<Integer> result);
    }
}
/*
* new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int count = recyclerView.getAdapter().getItemCount();
                        CheckBox chkbox;
                        selectedTagIds = new ArrayList<>();
                        for(int i=0;i<count;i++){
                            chkbox = (CheckBox) recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.tagCheckBox);
                            if(chkbox.isChecked()) {
                                selectedTagIds.add(tagAdapter.getTagPair(i).value);
                                str = str + tagAdapter.getTagPair(i).value+ ", ";
                            }
                        }
                        str=str.substring(0,str.length()-2);
                        //Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
                        if(tagType=="Contact"){
                            if(recordType=="Event"){
                                db.deleteContactTagsForEvent(recordId);
                                db.insertContactTagsForEvent(recordId, selectedTagIds);
                            }
                            if(recordType=="Gift"){
                                db.deleteContactTagsForGift(recordId);
                                db.insertContactTagsForGift(recordId, selectedTagIds);
                            }
                        }
                        if(tagType=="Event"){
                            if(recordType=="Contact"){
                                db.deleteEventTagsForContact(recordId);
                                db.insertEventTagsForContact(recordId, selectedTagIds);
                            }
                            if(recordType=="Gift"){
                                db.deleteEventTagsForGift(recordId);
                                db.insertEventTagsForGift(recordId, selectedTagIds);
                            }
                        }
                        if(tagType=="Gift"){
                            if(recordType=="Contact"){
                                db.deleteGiftTagsForContact(recordId);
                                db.insertGiftTagsForContact(recordId, selectedTagIds);
                            }
                            if(recordType=="Event"){
                                db.deleteGiftTagsForEvent(recordId);
                                db.insertGiftTagsForEvent(recordId, selectedTagIds);
                            }
                        }
                    }
                })
*
* */