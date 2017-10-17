package com.sweden4all.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class AppointmentsPagerAdapter extends FragmentPagerAdapter {

    private static final int TABS_COUNT = 3;

    private final ArrayList<Fragment> mListFragments = new ArrayList<>();
    private final ArrayList<String> mListFragmentTitles = new ArrayList<>();

    public AppointmentsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragmentTitles.get(position);
    }

    public void addFragment(Fragment fg, String title) {
        mListFragments.add(fg);
        mListFragmentTitles.add(title);
        Log.i("AppointmentsPagerAdapter", "Fragment added: " + mListFragments.size());
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }
}