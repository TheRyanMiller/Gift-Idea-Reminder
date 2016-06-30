package com.rtmillerprojects.giftideareminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.ui.EditEventActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/29/2016.
 */
public class AgendaItemAdapter extends RecyclerView.Adapter<AgendaItemAdapter.AgendaItemViewHolder>{

    private ArrayList<AgendaItem> agendaItems;
    private Context context;

    public AgendaItemAdapter(ArrayList<AgendaItem> agendaItems, Context context) {
        this.agendaItems = agendaItems;
        this.context = context;
    }

    public static class AgendaItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.agenda_item_title) TextView agendaItemTitle;
        @Bind(R.id.agenda_item_subheader) TextView subHeader;
        @Bind(R.id.agenda_row_photo) ImageView agendaPhoto;
        Context context;
        ArrayList<AgendaItem> agendaItems;

        public AgendaItemViewHolder(View v, Context context, ArrayList<AgendaItem> agendaItems) {
            super(v);
            ButterKnife.bind(this, v);
            this.context = context;
            this.agendaItems = agendaItems;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            AgendaItem event = agendaItems.get(position);
            Toast.makeText(context,event.getTitle().toString(),Toast.LENGTH_SHORT).show();
            int recordId;
            recordId = (int) event.getId();
            Intent intent = new Intent(context, EditEventActivity.class);
            intent.putExtra("recordId",recordId);
            intent.putExtra("isNew",false);
            context.startActivity(intent);
        }
    }

    @Override
    public AgendaItemAdapter.AgendaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_agenda_item, parent, false);
        AgendaItemViewHolder vh = new AgendaItemViewHolder(v, context, agendaItems);
        return vh;
    }

    @Override
    public void onBindViewHolder(AgendaItemViewHolder holder, int position) {
        AgendaItem agendaItem = agendaItems.get(position);
        holder.agendaItemTitle.setText(agendaItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return agendaItems.size();
    }


}
