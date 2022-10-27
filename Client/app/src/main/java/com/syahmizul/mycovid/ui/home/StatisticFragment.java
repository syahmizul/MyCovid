package com.syahmizul.mycovid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.syahmizul.mycovid.databinding.CardviewRegistertraceLayoutBinding;
import com.syahmizul.mycovid.databinding.FragmentHomeStatisticsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class StatisticFragment extends Fragment {

    private FragmentHomeStatisticsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeStatisticsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        GetCases();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void GetCases(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/getcovidstats.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {
                    /*System.out.println(response);*/
                    JSONArray json = new JSONArray(response);

                    /*System.out.println(json.toString());*/
                    binding.caseDateLabel.setText(binding.caseDateLabel.getText() + json.getString(0)); //date probably won't change its index
                    binding.newCaseText.setText(json.getString(1)); //TODO:get these index dynamically since we don't know if they'll update the ordering of the column.
                    binding.activeCaseText.setText(json.getString(4));
                    binding.recoveredCaseText.setText(json.getString(3));




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {
            /*protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("icnum", ICNumber);
                return MyData;
            }*/
        };


        MyRequestQueue.add(MyStringRequest);
    }

}
