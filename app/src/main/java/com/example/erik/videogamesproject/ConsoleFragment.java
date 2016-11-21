package com.example.erik.videogamesproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Marco on 18/10/2016.
 */

public class ConsoleFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] tabIcons = {
            R.drawable.last_release,
            R.drawable.best_seller,
            R.drawable.top_rated
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_fragment, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabsLayout);
        tabLayout.setupWithViewPager(viewPager);
        getPageIcon();


        return v;
    }

    public void getPageIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new LastReleaseConsole());
        viewPagerAdapter.addFragment(new TopSellerConsole());
        viewPagerAdapter.addFragment(new TopRatedConsole());
        viewPager.setAdapter(viewPagerAdapter);

    }

}
