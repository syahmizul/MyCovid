package com.syahmizul.mycovid.ui.traceregister;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.syahmizul.mycovid.R;
import com.syahmizul.mycovid.databinding.FragmentTraceregisterBinding;

public class TraceRegisterPagerActivity extends FragmentActivity
{
        private TraceViewModel traceViewModel;
        private FragmentTraceregisterBinding binding;
        /**
         * The number of pages (wizard steps) to show in this demo.
         */
        private static final int NUM_PAGES = 2;

        /**
         * The pager widget, which handles animation and allows swiping horizontally to access previous
         * and next wizard steps.
         */
        private ViewPager2 mPager;

        /**
         * The pager adapter, which provides the pages to the view pager widget.
         */
        private FragmentStateAdapter pagerAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(binding.getRoot());

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = binding.pager;
            pagerAdapter = new ScreenSlidePagerAdapter(this);
            mPager.setAdapter(pagerAdapter);
        }

        @Override
        public void onBackPressed() {
            if (mPager.getCurrentItem() == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                super.onBackPressed();
            } else {
                // Otherwise, select the previous step.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }




        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}
