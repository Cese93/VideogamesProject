package com.example.erik.videogamesproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Erik on 21/10/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LastReleaseVideogames();
            case 1:
                return new TopSellerVideogames();
            case 2:
                return new TopRatedVideogames();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
