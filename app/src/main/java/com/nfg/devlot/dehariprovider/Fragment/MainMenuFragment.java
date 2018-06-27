package com.nfg.devlot.dehariprovider.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nfg.devlot.dehariprovider.Helpers.SectionsPageAdapter;
import com.nfg.devlot.dehariprovider.R;


public class MainMenuFragment extends Fragment {

    private ViewPager       _refViewPager;
            TabLayout       _refTabLayout;


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        createView(view);
        initializeObjects();

        setupViewPager(_refViewPager);
        _refTabLayout.setupWithViewPager(_refViewPager);
        _refTabLayout.setTabTextColors(Color.parseColor("#00d8ff"), Color.parseColor("#00d8ff"));


        return view;
    }


    private void setupViewPager(ViewPager pager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OngoingJobFragment(),"On Going Jobs");
        adapter.addFragment(new HistoryJobsFragment(),"History");
        pager.setAdapter(adapter);

    }

    private void createView(View view)
    {
        _refViewPager = (ViewPager) view.findViewById(R.id.frameViewPager);
        _refTabLayout = (TabLayout) view.findViewById(R.id.frameTabLayout);
    }

    private void initializeObjects()
    {

    }

}
