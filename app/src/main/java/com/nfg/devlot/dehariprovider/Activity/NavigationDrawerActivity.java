package com.nfg.devlot.dehariprovider.Activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfg.devlot.dehariprovider.Fragment.MainMenuFragment;
import com.nfg.devlot.dehariprovider.Fragment.SettingMenuFragment;
import com.nfg.devlot.dehariprovider.Helpers.FirebaseInstance;
import com.nfg.devlot.dehariprovider.Models.URL;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.Session.UserSession;
import java.util.HashMap;
import java.util.Map;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        createDrawerAndNavigationStuff();

        call_main_menu_fragment();


        /**
         *
         * Initializing Objects here
         * @func initializeObject();
         *
         * */

        initializeObject();

    }

    private void initializeObject()
    {
        requestQueue = Volley.newRequestQueue(this);
    }

    private void call_main_menu_fragment()
    {
        MainMenuFragment _refMainMenu = new MainMenuFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentController,_refMainMenu);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification)
        {
            /*NotificationsMenuFragment _refFragment = new NotificationsMenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentController,_refFragment);
            fragmentTransaction.commit();*/
        }
        else if (id == R.id.nav_settings)
        {
            SettingMenuFragment _refSettingMenu = new SettingMenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentController,_refSettingMenu);
            fragmentTransaction.commit();
        }
        else if(id == R.id.nav_jobs)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createDrawerAndNavigationStuff()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


        /**
         *
         * Checking access token and uploading on server
         *
         * @func CheckTokenStatus();
         *
         * */

        CheckTokenStatus();
    }
    private void CheckTokenStatus()
    {
        if(!FirebaseInstance.token.trim().equals(""))
        {
            UploadOnServer();
        }
    }

    private void UploadOnServer()
    {
        StringRequest request =  new StringRequest(Request.Method.POST, URL.UPDATE_ACCESS_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.contains("updated"))
                {
                    return;
                }
                else if(response.contains("failed"))
                {
                    UploadOnServer();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parameters = new HashMap<String, String>();

                parameters.put("id"     , UserSession.uid);
                parameters.put("token"  , FirebaseInstance.token.trim());

                return parameters;
            }
        };

        requestQueue.add(request);
    }

}
