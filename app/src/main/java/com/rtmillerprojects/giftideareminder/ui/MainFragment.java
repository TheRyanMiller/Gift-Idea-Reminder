package com.rtmillerprojects.giftideareminder.ui;

import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.ContactsAdapter;
import com.rtmillerprojects.giftideareminder.adapter.MainTabsAdapter;
import com.rtmillerprojects.giftideareminder.listener.MainListener;

/**
 * Created by Ryan on 5/28/2016.
 */
public class MainFragment extends BaseFragment {

    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private MainListener listener;
    private MainTabsAdapter mainTabsAdapter;


    public MainFragment() {
        // Required empty public constructor
    }
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MainActivity) context;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //Toolbar Stuff
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        ACA.setSupportActionBar(toolbar);
        ACA.getSupportActionBar().setTitle("Capture Yo Gifts!");

        //View Pager stuff
        mainTabsAdapter = new MainTabsAdapter(getChildFragmentManager());
        viewPager.setAdapter(mainTabsAdapter);
        fab.hide();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override public void onPageSelected(int position) {
                if (position == 0) {fab.hide();}
                else {fab.show();}
            }
            @Override public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("").setIcon(R.drawable.calendar);
        tabLayout.getTabAt(1).setText("").setIcon(R.drawable.contacts80);
        tabLayout.getTabAt(2).setText("").setIcon(R.drawable.gift96);

        //Nav Drawer Stuff
        DrawerLayout drawer = listener.getDrawer();
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(ACA, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //FAB stuff
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Toast.makeText(ACA,"FAB has been clicked",Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }
    
    @Override public void onDetach() {
        super.onDetach();
        listener = null;
    }

}