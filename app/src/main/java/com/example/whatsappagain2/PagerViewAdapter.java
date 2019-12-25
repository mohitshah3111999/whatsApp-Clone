package com.example.whatsappagain2;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    Context context;

    public PagerViewAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new Fragment_Camera(context);
                break;
            case 1:
                fragment = new Fragment_Chats(context);
                break;
            case 2:
                fragment = new Fragment_Status(context);
                break;
            case 3:
                fragment = new Fragment_Calls(context);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
