package com.syahmizul.mycovid.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
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
import com.syahmizul.mycovid.databinding.FragmentLoginBinding;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment
{
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private View root;
    public void SendData(final String ICNumber,
                         final String Password){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "http://20.212.17.195/login.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                if(response.equals("SUCCESS"))
                {
                    Snackbar.make(root,"Successfully logged in.Redirecting you to the home page now.",Snackbar.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                    MainActivity.editor.putBoolean("isLoggedIn",true);
                    MainActivity.editor.putString("CurrentICNumber",ICNumber);
                    MainActivity.editor.apply();
                    navController.navigate(R.id.menu_nav_home);
                    NavGraph navGraph = navController.getGraph();
                    navGraph.setStartDestination(R.id.menu_nav_home);
                    navController.setGraph(navGraph);
                }
                else
                    Snackbar.make(root,response,Snackbar.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(binding.getRoot(),error.toString(),Snackbar.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("icnum", ICNumber);
                MyData.put("password",Password);
                return MyData;
            }
        };


        MyRequestQueue.add(MyStringRequest);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();




        binding.btnLogin.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SendData(binding.ICNumTextBox.getText().toString(),binding.passwordTextbox.getText().toString());
                }
            }
        );
        binding.btnRegister.setOnClickListener(
        new View.OnClickListener()
           {
               @Override
               public void onClick(View v)
               {
                   NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                   navController.navigate(R.id.menu_nav_register);

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
