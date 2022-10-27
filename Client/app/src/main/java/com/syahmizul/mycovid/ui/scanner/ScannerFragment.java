package com.syahmizul.mycovid.ui.scanner;

import static com.syahmizul.mycovid.Utils.Clamp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.syahmizul.mycovid.MainActivity;
import com.syahmizul.mycovid.R;

import com.syahmizul.mycovid.databinding.FragmentScannerBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class ScannerFragment extends Fragment {

    private ScannerViewModel scannerViewModel;
    private FragmentScannerBinding binding;
    public CodeScanner mCodeScanner;
    private ScaleGestureDetector mScaleDetector;
    private int mScaleFactor = 0;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void SendData(final String FacilityID, final String TracerIC){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/inserttrace.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                try {


                    if(response.equals("SUCCESS"))
                    {
                        Snackbar.make(binding.getRoot(),"Successfully checked-in !",Snackbar.LENGTH_SHORT).show();

                    }
                    else
                        Snackbar.make(binding.getRoot(),response,Snackbar.LENGTH_SHORT).show();
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
                MyData.put("tracerfacilityid",FacilityID);
                MyData.put("traceric", TracerIC);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        binding = FragmentScannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mScaleDetector = new ScaleGestureDetector(activity, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                mScaleFactor = (int) (scaleFactor < 1 ? mScaleFactor - scaleFactor : mScaleFactor + scaleFactor);
                mScaleFactor = (int) Clamp(mScaleFactor,0,30);
                mCodeScanner.setZoom(mScaleFactor);
                return true;
            }

        });

        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);// or CAMERA_FRONT or specific camera id
        mCodeScanner.setFormats(CodeScanner.TWO_DIMENSIONAL_FORMATS ); // list of type BarcodeFormat,

        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE); // or CONTINUOUS
        mCodeScanner.setScanMode(ScanMode.SINGLE);// or CONTINUOUS or PREVIEW
        mCodeScanner.setAutoFocusEnabled(true); // Whether to enable auto focus or not
        mCodeScanner.setFlashEnabled(false);// Whether to enable flash or not
        scannerView.setOnTouchListener((v, event) -> {
            mScaleDetector.onTouchEvent(event);
            return true;
        });
        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                ((Runnable) () -> Snackbar.make(root, "ERROR : " + error.toString(),
                        Snackbar.LENGTH_SHORT)
                        .show()).run();
            }
        });
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result)
            {
                ((Runnable) () -> {
                    if(root != null)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(result.getText());

                            if(jsonObject.getString("status").equals("SUCCESS"))
                            {
                                String FacilityID = jsonObject.getString("id");
                                String TracerIC = MainActivity.sharedPref.getString("CurrentICNumber","");

                                SendData(FacilityID,TracerIC);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    activity.runOnUiThread(() -> mCodeScanner.startPreview());

                }).run();

            }
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}