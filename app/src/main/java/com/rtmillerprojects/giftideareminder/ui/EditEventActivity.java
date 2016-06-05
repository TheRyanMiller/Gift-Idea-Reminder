package com.rtmillerprojects.giftideareminder.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rtmillerprojects.giftideareminder.R;

/**
 * Created by Ryan on 6/5/2016.
 */
public class EditEventActivity extends AppCompatActivity{

    Toolbar toolbar;
    Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);
        spinner = (Spinner) findViewById(R.id.recurrence_spinner);
        toolbar = (Toolbar) findViewById(R.id.editEventToolbar);
        Bundle extras = getIntent().getExtras();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add an event");
        }
        ArrayAdapter<CharSequence> recurrenceOptionAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        recurrenceOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(recurrenceOptionAdapter);

    }
    public void onStart(){
        super.onStart();

        EditText selectedDate=(EditText) findViewById(R.id.selectedDate);

        if(selectedDate!=null){
            selectedDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View view, boolean hasfocus){
                    if(hasfocus){
                        DateDialog dialog=new DateDialog(view);
                        FragmentTransaction ft =getFragmentManager().beginTransaction();
                        dialog.show(ft, "DatePicker");

                    }
                }

            });
        }
    }


}
