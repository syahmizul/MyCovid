
package com.syahmizul.mycovid.ui.traceregister;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.zxing.WriterException;
import com.syahmizul.mycovid.MainActivity;
import com.syahmizul.mycovid.R;
import com.syahmizul.mycovid.databinding.FragmentScannerBinding;
import com.syahmizul.mycovid.databinding.FragmentTraceregisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;



public class TraceRegisterFragment extends Fragment
{
    private TraceViewModel traceViewModel;
    private FragmentTraceregisterBinding binding;
    private static final int NUM_PAGES = 2;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;



    /*@Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }*/




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        final Activity activity = getActivity();

        binding = FragmentTraceregisterBinding .inflate(inflater, container, false);
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
                        tab.setText("Register");
                        break;
                    case 1:
                        tab.setText("Registered list");
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
                    returnFragment = new TraceRegisterRegisterFragment();
                    break;
                case 1:
                    returnFragment = new TraceRegisterRegisterListFragment();
                    break;
            }
            return returnFragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

