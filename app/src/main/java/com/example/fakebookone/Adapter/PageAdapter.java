package com.example.fakebookone.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fakebookone.Fragment.FragmentChat;
import com.example.fakebookone.Fragment.FragmentHome;
import com.example.fakebookone.Fragment.FragmentProfile;

public class PageAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numbTabs) {
        super(fm);
        this.numTabs = numbTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FragmentChat();
            case 1:
                return new FragmentHome();
            case 2:
                return new FragmentProfile();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
