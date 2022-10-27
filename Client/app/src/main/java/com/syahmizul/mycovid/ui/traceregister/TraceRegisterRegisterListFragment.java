package com.syahmizul.mycovid.ui.traceregister;

import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.material.snackbar.Snackbar;
import com.syahmizul.mycovid.MainActivity;
import com.syahmizul.mycovid.databinding.CardviewRegistertraceLayoutBinding;
import com.syahmizul.mycovid.databinding.CardviewTracesLayoutBinding;
import com.syahmizul.mycovid.databinding.FragmentTraceregisterRegisterlistBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class TraceRegisterRegisterListFragment extends Fragment
{
    private FragmentTraceregisterRegisterlistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTraceregisterRegisterlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        GetData(MainActivity.sharedPref.getString("CurrentICNumber",""));
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void GetData(final String ICNumber){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/getuserfacilityregisters.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {
                    /*System.out.println(response);*/
                    JSONArray json = new JSONArray(response);

                    /*System.out.println(json.toString());*/
                    for(int i = json.length() - 1;i>=0;i--)
                    {
                        JSONArray innerArray = json.getJSONArray(i);
                        TextView textView = new TextView(getContext());
                        textView.setText(innerArray.toString());

                        CardviewRegistertraceLayoutBinding CardBinding = CardviewRegistertraceLayoutBinding.inflate(getLayoutInflater(), binding.cardList, false);
                        CardBinding.facilityname.setText(innerArray.getString(1));
                        CardBinding.facilityaddress.setText(innerArray.getString(2));
                        binding.cardList.addView(CardBinding.dataViewCardViewLayout);
                    }






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
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("icnum", ICNumber);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

}
