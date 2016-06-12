package com.rtmillerprojects.giftideareminder.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rtmillerprojects.giftideareminder.R;

/**
 * Created by Ryan on 6/11/2016.
 */
public class TagDialog extends DialogFragment{
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.banner_edit_tag, null);
        TextView myText = (TextView) dialogLayout.findViewById(R.id.edit_tag_text);
        CharSequence[] items = new CharSequence[]{"hell","gucci","hell","gucci","hell","gucci","hell","gucci","hell","gucci","hell","gucci","hell","gucci"};
        boolean[] checkedItems ={false,true,false,true,false,true,false,true,false,true,false,true,false,true};
        builder.setMultiChoiceItems(items,
                checkedItems,
                new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //do something
                    }
                })
                .setView(dialogLayout)
                //.setMessage("This is my dialog")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
