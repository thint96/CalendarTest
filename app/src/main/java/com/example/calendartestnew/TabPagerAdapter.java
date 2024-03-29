package com.example.calendartestnew;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by QuangND on 1/8/2018.
 */

public class TabPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private Context context;
    private List<Integer> list;

    public TabPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    public void addOnlyFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titleList.get(position);
    }

    public void setStatusList(List<Integer> list)
    {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
