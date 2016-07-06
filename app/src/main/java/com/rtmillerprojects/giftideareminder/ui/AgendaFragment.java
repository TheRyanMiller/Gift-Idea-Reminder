package com.rtmillerprojects.giftideareminder.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.AgendaItemAdapter;
import com.rtmillerprojects.giftideareminder.listener.FabClickListener;
import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/21/2016.
 */
public class AgendaFragment extends BaseFragment implements FabClickListener{

    private ArrayList<AgendaItem> agendaItems;
    @Bind(R.id.recycler_view) RecyclerView recyclerView;
    private DatabaseHelper db;
    private LinearLayoutManager layoutManager;
    private TextView noEventsText;

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
        View rootView = inflater.inflate(R.layout.agenda_fragment, container, false);
        ButterKnife.bind(this, rootView);
        //need to pull in agenda items from db
        noEventsText = (TextView) rootView.findViewById(R.id.noEventsText);
        db = DatabaseHelper.getInstance(ACA);
        agendaItems = db.getAllAgendaItems();

        layoutManager = new LinearLayoutManager(ACA);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        AgendaItemAdapter agendaItemAdapter = new AgendaItemAdapter(agendaItems,ACA);
        recyclerView.setAdapter(agendaItemAdapter);
        recyclerView.setLayoutManager(layoutManager);
        adjustEventVisibility(agendaItemAdapter);
        return rootView;
    }

    @Override
    public void fabClickAction() {
        Toast.makeText(ACA,"WE ARE IN AGENDA",Toast.LENGTH_SHORT).show();
        //startActivityForResult(new Intent(Intent., ContactsContract.Contacts.CONTENT_URI), RESULT_PICK_CONTACT);
        int recordId;
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        AgendaItem newEvent = new AgendaItem(null,null,null);
        recordId = (int) db.insertAgendaItem(newEvent);
        Intent intent = new Intent(ACA, EditEventActivity.class);
        intent.putExtra("recordId",recordId);
        intent.putExtra("isNew",true);
        startActivity(intent);
    }

    private void adjustEventVisibility(AgendaItemAdapter eventAdapter){
        int numEvents = eventAdapter.getItemCount();
        noEventsText.setText(""+numEvents);
        if(numEvents>0){
            noEventsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            noEventsText.setVisibility(View.VISIBLE);
        }
    }
}
