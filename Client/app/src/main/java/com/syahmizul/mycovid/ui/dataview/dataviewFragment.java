package com.syahmizul.mycovid.ui.dataview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ViewGroupBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.syahmizul.mycovid.MainActivity;
import com.syahmizul.mycovid.R;
import com.syahmizul.mycovid.databinding.CardviewTracesLayoutBinding;
import com.syahmizul.mycovid.databinding.FragmentDataviewBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class dataviewFragment extends Fragment {

    private dataviewViewModel slideshowViewModel;
    private FragmentDataviewBinding binding;

    public void GetData(final String ICNumber){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/getusertrace.php"; // <----enter your post url here
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

                        CardviewTracesLayoutBinding CardBinding = CardviewTracesLayoutBinding.inflate(getLayoutInflater(), binding.cardList, false);
                        CardBinding.textView7.setText(innerArray.getString(3));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date date = dateFormat.parse(innerArray.getString(2));
                        dateFormat.applyPattern("MMM d, yyyy h:mm:ss a");
                        CardBinding.textView8.setText(dateFormat.format(date));
                        /*View CardView = View.inflate(getContext(),R.layout.cardview_traces_layout, binding.cardList);*/

                        /*ViewDataBinding innerbinding = DataBindingUtil.bind(view);
                        innerbinding.*/
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(dataviewViewModel.class);

        binding = FragmentDataviewBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        GetData(MainActivity.sharedPref.getString("CurrentICNumber",""));



        /*CardView cardView = new CardView(getContext());

        cardView.*/
        /*final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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