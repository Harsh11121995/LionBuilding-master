package com.dies.lionbuilding.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.ArrayList;


/**
 * Created by Kunal on 3/16/2017.
 */

public class TabingFragmentAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabtitals = new ArrayList<>();
    private final FragmentManager mFragmentManager;

    public void addFragments(Fragment fragments, String titals) {
        // Required empty public constructor
        this.fragments.add(fragments);
        this.tabtitals.add(titals);

    }

    public TabingFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        //return fragments.get(position);
        switch (position) {
            case 0:
//                ShoesFragment shoesFragment = new ShoesFragment();
//                return shoesFragment;
            case 1:
//                IroningFragment ironingFragment = new IroningFragment();
//                return ironingFragment;
            case 2:
//                RollPolishFragment rollPolish = new RollPolishFragment();
//                return rollPolish;
            case 3:
//                DryCleanFragment dryCleanFragment = new DryCleanFragment();
//                return dryCleanFragment;
            case 4:
//                WashAndIronFragment washAndIronFragment = new WashAndIronFragment();
//                return washAndIronFragment;
            case 5:
               //eturn washAndFoldFragment;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitals.get(position);
    }
}
