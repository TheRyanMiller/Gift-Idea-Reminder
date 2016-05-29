package com.rtmillerprojects.giftideareminder.adapter;

/**
 * Created by Ryan on 5/29/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rtmillerprojects.giftideareminder.ui.ContactsFragment;
import com.rtmillerprojects.giftideareminder.ui.AgendaFragment;
import com.rtmillerprojects.giftideareminder.ui.GiftsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainTabsAdapter extends FragmentPagerAdapter {

    public final int AGENDA_FRAGMENT = 0;
    public final int CONTACTS_FRAGMENT = 1;
    public final int GIFTS_FRAGMENT = 2;


    public MainTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment pagerFragment = null;
        switch (position) {
            case AGENDA_FRAGMENT:
                pagerFragment = AgendaFragment.newInstance();
                break;

            case CONTACTS_FRAGMENT:
                pagerFragment = ContactsFragment.newInstance();
                break;

            case GIFTS_FRAGMENT:
                pagerFragment = GiftsFragment.newInstance();
                break;
        }
        return pagerFragment;
    }

    @Override public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Agenda";
            case 1:
                return "Contacts";
            case 2:
                return "Gifts";
        }
        return null;
    }
}