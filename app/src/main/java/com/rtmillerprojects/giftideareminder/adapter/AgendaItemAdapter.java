package com.rtmillerprojects.giftideareminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private AdapterView.OnItemClickListener listener;

    public static class AgendaItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.agenda_item_title) TextView agendaItemTitle;
        @Bind(R.id.agenda_item_subheader) TextView subHeader;
        @Bind(R.id.agenda_row_photo) ImageView agendaPhoto;

        public AgendaItemViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final AgendaItem item, final AdapterView.OnItemClickListener listener) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public AgendaItemAdapter(ArrayList<AgendaItem> agendaItems, Context context, AdapterView.OnItemClickListener listener) {
        this.agendaItems = agendaItems;
        this.context = context;
        this.listener = listener;
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
        holder.bind(agendaItems.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return agendaItems.size();
    }


}
