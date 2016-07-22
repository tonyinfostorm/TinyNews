package com.tonyinfostorm.tinynews.adapter;


import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;

import com.tonyinfostorm.tinynews.Network.URL;
import com.tonyinfostorm.tinynews.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by higer on 2016/7/18.
 */
public class NewsPageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    public NewsPageAdapter(android.app.FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        for(int i=0;i<getCount();i++){
            fragmentList.add(i, null);
        }

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = fragmentList.get(position);
        if(fragment == null){
            fragment = new NewsFragment(getPageTitle(position).toString());
            fragmentList.set(position, fragment);
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return URL.news_items.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return URL.news_items[position];
    }


}
