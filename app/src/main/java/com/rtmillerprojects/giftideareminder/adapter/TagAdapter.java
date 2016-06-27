package com.rtmillerprojects.giftideareminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.model.NameValueCheck;

import java.util.ArrayList;

/**
 * Created by Ryan on 5/29/2016.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private ArrayList<String> tags;
    private Context context;
    private ArrayList<NameValueCheck> nameValueCheck;

    public TagAdapter(ArrayList<NameValueCheck> nameValueCheck, Context context){
        this.context = context;
        this.nameValueCheck = nameValueCheck;
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public CheckBox tagCheckBox;
        private Context context;
        public TagViewHolder(View v, final Context context) {
            super(v);
            tagCheckBox = (CheckBox) v.findViewById(R.id.tagCheckBox);
            tagCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_tag, parent, false);
        TagViewHolder vh = new TagViewHolder(v, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        holder.tagCheckBox.setText(nameValueCheck.get(position).name);
        holder.tagCheckBox.setChecked(nameValueCheck.get(position).isChecked);
    }

    @Override public int getItemCount() {
        return nameValueCheck.size();
    }
    public NameValueCheck getTagPair(int i) {
        return nameValueCheck.get(i);
    }
}