package com.syahmizul.mycovid;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

/*import com.syahmizul.mycovid.databinding.ActivityMainBinding;*/
import com.syahmizul.mycovid.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    public static MainActivity activityInstance;
    private AppBarConfiguration mAppBarConfiguration;
    private MainActivityBinding binding;
    public static SharedPreferences sharedPref ;
    public static SharedPreferences.Editor editor;
    /*private LoginLayoutBinding binding;*/ // Use the NavGraph instead

    private int permissionCounter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstance = this;
        checkPermission();
        binding = MainActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor     = sharedPref.edit();

        BottomNavigationView navigationView = binding.bottomNavigation;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_nav_home/*, R.id.menu_nav_statistic*/,R.id.menu_nav_trace,R.id.menu_nav_list,R.id.menu_nav_traceRegister,R.id.loginFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this,navController);

        /*binding.gLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.loginFragment);
                NavGraph navGraph = navController.getGraph();
                navGraph.setStartDestination(R.id.loginFragment);
                navController.setGraph(navGraph);
                editor.putBoolean("isLoggedIn",false);
                editor.putString("CurrentICNumber","");
                editor.apply();
            }
        });*/
        // This callback will only be called when MyFragment is at least Started.
        /*OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed()
            {
                // Handle the back button event
                if(!sharedPref.getBoolean("isLoggedIn",false)
                        &&
                        (
                            navController.getCurrentDestination().getId() == R.id.menu_nav_home ||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_gallery ||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_slideshow
                        )
                    )
                {
                    editor.putString("CurrentICNumber","");
                    navController.navigate(R.id.loginFragment);
                    NavGraph navGraph = navController.getGraph();
                    navGraph.setStartDestination(R.id.loginFragment);
                    navController.setGraph(navGraph);
                    finishAffinity();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);*/
    // The callback can be enabled or disabled here or in handleOnBackPressed()

        if(sharedPref.getBoolean("isLoggedIn",false))
        {
            System.out.println("Already logged in,setting home fragment as start destination");
            navController.navigate(R.id.menu_nav_home);
            NavGraph navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.menu_nav_home);
            navController.setGraph(navGraph);
        }

        navigationView.setVisibility(View.GONE);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments)
            {
                if( destination.getId() == R.id.menu_nav_home ||
                    destination.getId() == R.id.menu_nav_trace ||
                    destination.getId() == R.id.menu_nav_list ||
                    destination.getId() == R.id.menu_nav_traceRegister /*||
                    destination.getId() == R.id.menu_nav_statistic*/)
                {
                    navigationView.setVisibility(View.VISIBLE);

                }
                else
                {

                    navigationView.setVisibility(View.GONE);
                }

                if(destination.getId() == R.id.loginFragment)
                {
                    editor.putBoolean("isLoggedIn",false);
                    editor.putString("CurrentICNumber","");
                    editor.apply();
                }

                if(!sharedPref.getBoolean("isLoggedIn",false)
                        &&
                        (
                            navController.getCurrentDestination().getId() == R.id.menu_nav_home             ||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_trace            ||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_list             ||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_traceRegister    /*||
                            navController.getCurrentDestination().getId() == R.id.menu_nav_statistic*/
                        )
                )
                {
                    editor.putString("CurrentICNumber","");
                    navController.navigate(R.id.loginFragment);
                    NavGraph navGraph = navController.getGraph();
                    navGraph.setStartDestination(R.id.loginFragment);
                    navController.setGraph(navGraph);
                }



            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public void checkPermission()
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null)
            {
                ActivityCompat.requestPermissions(MainActivity.this, info.requestedPermissions, permissionCounter);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        System.out.println("OnSupportNavigateUp Fired");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}