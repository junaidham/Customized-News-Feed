package tech.ducletran.customizednewsfeedapp.OtherResource;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tech.ducletran.customizednewsfeedapp.Fragments.ArtNewsFracment;
import tech.ducletran.customizednewsfeedapp.Fragments.MusicNewsFracment;
import tech.ducletran.customizednewsfeedapp.Fragments.TechNewsFracment;
import tech.ducletran.customizednewsfeedapp.Fragments.TravelNewsFracment;

public class CategoryAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Technology","Daily Art","Travel","Music"};

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
           return new MusicNewsFracment();
       }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
