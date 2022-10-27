package com.syahmizul.mycovid.ui.register;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;
import com.syahmizul.mycovid.R;
import com.syahmizul.mycovid.databinding.FragmentLoginBinding;
import com.syahmizul.mycovid.databinding.FragmentRegisterBinding;
import com.syahmizul.mycovid.ui.login.LoginViewModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment
{
    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    public ArrayList<String> StringChecksBeforeRegister()
    {
        ArrayList<String> stringToCheck = new ArrayList<>();
        stringToCheck.add(binding.fullnameTextbox.getText().toString());
        stringToCheck.add(binding.icnumTextbox.getText().toString());
        stringToCheck.add(binding.addressTextbox.getText().toString());
        stringToCheck.add(binding.ccpRegister.getFullNumberWithPlus());
        stringToCheck.add(binding.passwordTextbox.getText().toString());

        for (String str: stringToCheck) {
            if (str.equals(""))
            {
                Snackbar.make(binding.getRoot(),"Please fill all the blanks.",Snackbar.LENGTH_SHORT).show();
                return null;
            }

        }
        return stringToCheck;
    }

    public void SendData(final String FullName,
                          final String ICNumber,
                          final String Address,
                          final String PhoneNumber,
                          final String Password){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/register.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                if(response.equals("SUCCESS"))
                {
                    Snackbar.make(binding.getRoot(),"You have successfully registered.Redirecting you to the login page now.",Snackbar.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.loginFragment);
                }
                else
                    Snackbar.make(binding.getRoot(),response,Snackbar.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(binding.getRoot(),error.toString(),Snackbar.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("fullname", FullName);
                MyData.put("icnum", ICNumber);
                MyData.put("address",Address);
                MyData.put("phonenum",PhoneNumber);
                MyData.put("password",Password);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CountryCodePicker ccp = binding.ccpRegister;
        ccp.registerCarrierNumberEditText(binding.regPhonenum);

        binding.registerButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ArrayList<String> dataStrings = StringChecksBeforeRegister();
                        if(dataStrings == null)
                            return;

                        SendData(dataStrings.get(0),
                                dataStrings.get(1),
                                dataStrings.get(2),
                                dataStrings.get(3),
                                dataStrings.get(4));

                    }
                }
        );
        /*final TextView textView = binding.textSlideshow;
        loginViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
