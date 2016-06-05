package com.rtmillerprojects.giftideareminder.ui;

import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.rtmillerprojects.giftideareminder.R;
import com.rtmillerprojects.giftideareminder.adapter.MainTabsAdapter;
import com.rtmillerprojects.giftideareminder.listener.FabClickListener;
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

    int[] colorIntArray = {R.color.fabagenda,R.color.fabcontact,R.color.fabgift};
    int[] iconIntArray = {R.drawable.calendar,R.drawable.contacts80,R.drawable.gift96};


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

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        //Toolbar Stuff
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ACA.setSupportActionBar(toolbar);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        ACA.getSupportActionBar().setTitle("Programmatic title");

        //View Pager stuff
        mainTabsAdapter = new MainTabsAdapter(getChildFragmentManager());
        viewPager.setAdapter(mainTabsAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageSelected(int position) {
                animateFab(position);
            }
            @Override public void onPageScrollStateChanged(int state) {
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFrag = mainTabsAdapter.getPrimaryItem();
                FabClickListener myFrag = (FabClickListener) currentFrag;
                myFrag.fabClickAction();
                currentFrag.getView();
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



        return rootView;
    }
    
    @Override public void onDetach() {
        super.onDetach();
        listener = null;
    }

    protected void animateFab(final int position) {
        fab.clearAnimation();
        // Scale down animation
        ScaleAnimation shrink =  new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon
                fab.setBackgroundTintList(getResources().getColorStateList(colorIntArray[position]));
                fab.setImageDrawable(ResourcesCompat.getDrawable(ACA.getResources(),iconIntArray[position],null));

                // Scale up animation
                ScaleAnimation expand =  new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
    }

}
