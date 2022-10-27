package com.syahmizul.mycovid.ui.traceregister;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.syahmizul.mycovid.databinding.FragmentTraceregisterRegisterBinding;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class TraceRegisterRegisterFragment extends Fragment {

    private FragmentTraceregisterRegisterBinding binding;

    public boolean SanityChecks()
    {
        if
        (
            binding.facilityNameTextbox.getText().toString().equals("") ||
            binding.facilityAddressTextbox.getText().toString().equals("")
        )
        {
            Snackbar.make(binding.getRoot(),"Please fill all the blanks!", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void SendData(final String FacilityName, final String FacilityAddress){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/insertfacilityinfo.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("status").equals("SUCCESS"))
                    {
                        Snackbar.make(binding.getRoot(),"Successfully registered your facility to the server.Generating QR Code.",Snackbar.LENGTH_SHORT).show();
                        QRGEncoder qrgEncoder = new QRGEncoder(jsonObject.toString(), null, QRGContents.Type.TEXT, 256);
                        QRGSaver qrgSaver = new QRGSaver();
                        String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
                        String imageName = "QRCode-MyCovid-" + jsonObject.getString("name");
                        qrgSaver.save(savePath,imageName , qrgEncoder.getBitmap(), QRGContents.ImageType.IMAGE_JPEG);
                        Snackbar.make(binding.getRoot(),"Generated QR Code at " + savePath + imageName,Snackbar.LENGTH_SHORT).show();
                    }
                    else
                        Snackbar.make(binding.getRoot(),jsonObject.getString("status"),Snackbar.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(binding.getRoot(),error.toString(),Snackbar.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", FacilityName);
                MyData.put("address",FacilityAddress);
                MyData.put("icnum", MainActivity.sharedPref.getString("CurrentICNumber",""));
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        binding = FragmentTraceregisterRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.qrGenerateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SanityChecks())
                            SendData(binding.facilityNameTextbox.getText().toString(),binding.facilityAddressTextbox.getText().toString());
                    }
                });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
