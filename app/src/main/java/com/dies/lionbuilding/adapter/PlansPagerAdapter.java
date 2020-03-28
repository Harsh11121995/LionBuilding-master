package com.dies.lionbuilding.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dies.lionbuilding.fragment.DynamicFragment;


public class PlansPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Integer[] catid;
    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs, Integer[] catid) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.catid= catid;
    }
    @Override
    public Fragment getItem(int position) {
        return DynamicFragment.
                newInstance(catid[position],position);
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
