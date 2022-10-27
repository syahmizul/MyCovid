package com.syahmizul.mycovid.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.syahmizul.mycovid.MainActivity;
import com.syahmizul.mycovid.R;
import com.syahmizul.mycovid.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public void GetData(final String ICNumber){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/getuserdata.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {
                    JSONObject json = new JSONObject(response);
                    binding.topNamelabel.setText(json.getString("fullname"));
                    binding.topIcnumber.setText(json.getString("icnum"));




                    String RiskStats = json.getString("riskstats");
                    binding.riskstats.setText(RiskStats);
                    switch(RiskStats)
                    {
                        case "Low Risk":
                            binding.symptomCard.setCardBackgroundColor(0xFF007BFF);
                            break;
                        case "Medium Risk":
                            binding.symptomCard.setCardBackgroundColor(0xFFFF9900);
                            break;
                        case "High Risk":
                            binding.symptomCard.setCardBackgroundColor(0xFFFF0000);
                            break;
                    }






                    int VaccineLevel = json.getInt("vaccinelevel");
                    switch(VaccineLevel)
                    {
                        case 1 :
                            binding.vaccineCard.setCardBackgroundColor(0xFFFFD500);
                            binding.vaccinestats.setText("Half Vaccinated");
                            break;
                        case 2:
                            binding.vaccineCard.setCardBackgroundColor(0xFF00FF55);
                            binding.vaccinestats.setText("Fully Vaccinated");
                            break;
                        default:
                            binding.vaccineCard.setCardBackgroundColor(0xFFFF0000);
                            binding.vaccinestats.setText("Not vaccinated");
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("icnum", ICNumber);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        GetData(MainActivity.sharedPref.getString("CurrentICNumber",""));
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*final TextView textView = binding.textHome;*/
        binding.newsPageWebview.loadUrl("https://covid-19.moh.gov.my/terkini");
        binding.newsPageWebview.setVerticalScrollBarEnabled(true);
        binding.newsPageWebview.setHorizontalScrollBarEnabled(true);
        binding.newsPageWebview.computeScroll();
        binding.webLinearlayout.setClipToOutline(true);

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textView4.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}