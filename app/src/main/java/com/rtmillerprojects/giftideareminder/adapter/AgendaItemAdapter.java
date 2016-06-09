package com.rtmillerprojects.giftideareminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.model.AgendaItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/29/2016.
 */
public class AgendaItemAdapter extends RecyclerView.Adapter<AgendaItemAdapter.AgendaItemViewHolder>{

    private ArrayList<AgendaItem> agendaItems;
    private Context context;

    public static class AgendaItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.agenda_item_title) TextView agendaItemTitle;
        @Bind(R.id.agenda_item_subheader) TextView subHeader;
        @Bind(R.id.agenda_row_photo) ImageView agendaPhoto;

        public AgendaItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public AgendaItemAdapter(ArrayList<AgendaItem> agendaItems, Context context) {
        this.agendaItems = agendaItems;
        this.agendaItems = agendaItems;
    }

    @Override
    public AgendaItemAdapter.AgendaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_agenda_item, parent, false);
        AgendaItemViewHolder vh = new AgendaItemViewHolder(v);
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
