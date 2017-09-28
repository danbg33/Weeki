package com.pap.iteso.weeki;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaNN on 26/09/2017.
 */

public class SectionsPageAdapter  extends FragmentPagerAdapter{

    private final List<Fragment> mFragmetnList = new ArrayList<>();
    private final List<String> mFragment_names = new ArrayList<>();

   public SectionsPageAdapter(FragmentManager fm){
       super(fm);
   }

    public void addFragment(Fragment fragment, String title){
        mFragmetnList.add(fragment);
        mFragment_names.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragment_names.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmetnList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmetnList.size();
    }
}
