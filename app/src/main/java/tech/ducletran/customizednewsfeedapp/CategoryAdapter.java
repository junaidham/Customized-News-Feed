package tech.ducletran.customizednewsfeedapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Technology","Daily Art","Travel","Others"};

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int i) {
       if (i==0) {
           return new TechNewsFracment();
       } else if (i==1) {
           return new ArtNewsFracment();
       } else if (i==2) {
           return new TravelNewsFracment();
       } else {
           return new OtherNewsFracment();
       }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
