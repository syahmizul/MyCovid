package com.syahmizul.mycovid.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.syahmizul.mycovid.databinding.FragmentHomeDefaultBinding;
import com.syahmizul.mycovid.databinding.FragmentTraceregisterBinding;
import com.syahmizul.mycovid.ui.traceregister.TraceRegisterFragment;
import com.syahmizul.mycovid.ui.traceregister.TraceRegisterRegisterFragment;
import com.syahmizul.mycovid.ui.traceregister.TraceRegisterRegisterListFragment;

public class HomeDefaultFragment extends Fragment
{
    private FragmentHomeDefaultBinding binding;
    private static final int NUM_PAGES = 2;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }




        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returnFragment = null;
            switch(position)
            {
                case 0:
                    returnFragment = new HomeFragment();
                    break;
                case 1:
                    returnFragment = new StatisticFragment();
                    break;
            }
            return returnFragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        final Activity activity = getActivity();

        binding = FragmentHomeDefaultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mPager = binding.pager;
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity());
        mPager.setAdapter(pagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, mPager, true, new TabLayoutMediator.TabConfigurationStrategy(){


            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch(position)
                {
                    case 0:
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setText("Statistics");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
