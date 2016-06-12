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
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.ui.EditEventActivity;
import com.rtmillerprojects.giftideareminder.ui.NameValuePair;
import com.rtmillerprojects.giftideareminder.util.DatabaseHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ryan on 5/29/2016.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private ArrayList<String> tags;
    private Context context;
    private ArrayList<NameValuePair> nameIdPairs;

    public TagAdapter(ArrayList<NameValuePair> nameIdPairs, Context context){
        this.context = context;
        this.nameIdPairs = nameIdPairs;
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
        holder.tagCheckBox.setText(nameIdPairs.get(position).name);
    }

    @Override public int getItemCount() {
        return nameIdPairs.size();
    }
    public NameValuePair getTagPair(int i) {
        return nameIdPairs.get(i);
    }
}