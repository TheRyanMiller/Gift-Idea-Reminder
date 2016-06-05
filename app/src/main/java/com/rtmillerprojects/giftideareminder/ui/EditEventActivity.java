package com.rtmillerprojects.giftideareminder.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.rtmillerprojects.giftideareminder.R;

/**
 * Created by Ryan on 6/5/2016.
 */
public class EditEventActivity extends AppCompatActivity{

    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event2);
        toolbar = (Toolbar) findViewById(R.id.editEventToolbar);
        Bundle extras = getIntent().getExtras();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add an event");
        }

    }
}
