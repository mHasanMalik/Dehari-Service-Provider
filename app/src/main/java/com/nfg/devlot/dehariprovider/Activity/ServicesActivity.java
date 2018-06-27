package com.nfg.devlot.dehariprovider.Activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfg.devlot.dehariprovider.Adapters.ServicesAdapter;
import com.nfg.devlot.dehariprovider.Models.ServicesModel;
import com.nfg.devlot.dehariprovider.Models.URL;
import com.nfg.devlot.dehariprovider.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicesActivity extends AppCompatActivity {

    RecyclerView                recyclerView;
    SwipeRefreshLayout          swipeRefreshLayout;
    LinearLayoutManager         layoutManager;
    RequestQueue                requestQueue;
    ArrayList<ServicesModel>    serviceArrayList;
    ServicesAdapter             adapter;
    Toolbar                     LocalToolbar;

    String selectedCategoryid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        createView();
        initializeObjects();

        /**
         *
         *
         * Setting Support Action Bar code goes here
         * @func SetSupportActionBarCode();
         * */

        SetSupportActionBarCode();

        /**
         *
         * Retrieving intent value here & Communication With database
         * @func retrievingIntentExtra();
         * */

        retrievingIntentExtra();

        /**
         *
         * Swipe to Refresh listener Code goes here
         * @func swipeToRefresh();
         * */

        swipeToRefresh();


    }

    private void SetSupportActionBarCode()
    {
        setSupportActionBar(LocalToolbar);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LocalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void swipeToRefresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(true);

                Handler handler =  new Handler();

                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        /**
                         *
                         * Communication with database goes here
                         * @func getServices();
                         *
                         * */

                        getServices();

                    }
                },3000);
            }
        });
    }

    private void retrievingIntentExtra()
    {
        if (getIntent().hasExtra("categoryId"))
        {
            if (!getIntent().getStringExtra("categoryId").isEmpty() && getIntent().getStringExtra("categoryId") != null)
            {

                selectedCategoryid = getIntent().getStringExtra("categoryId");
                /**
                 *
                 * COMMUNICATION WITH DATABASE GOES HERE
                 * @func get_services();
                 * */

                getServices();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Developer Check the Category Adapter. No Extras added", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getServices()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URL.GET_SERVICES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if (response.contains("noServices"))
                {
                    Toast.makeText(getApplicationContext(), "No Services avaiable yet. Please try again later", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 *
                 *  Capturing response and parsing data here
                 *  @func capture_response(String response);
                 *
                 * */

                capture_response(response);

                /**
                 *
                 *  Passing data to adapter here
                 *  @code adapter.UpdateRecord(ArrayList<ServiceMode> serviceArrayList);
                 *
                 * */

                adapter.UpdateRecord(serviceArrayList);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "There Appears to be a problem, Please try again later", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("c_id", selectedCategoryid);

                return parameters;
            }
        };

        requestQueue.add(request);
    }

    private void capture_response(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("services");

            for (int i=0; i<jsonArray.length();i++)
            {
                ServicesModel _refServicesModel = new ServicesModel();
                JSONObject services = jsonArray.getJSONObject(i);

                _refServicesModel.setServiceId(services.getString("s_id"));
                _refServicesModel.setServiceName(services.getString("name"));
                _refServicesModel.setCategoryId(services.getString("c_id"));
                _refServicesModel.setCategoryId(services.getString("base_rate"));
                _refServicesModel.setCategoryId(services.getString("hourly_rate"));
                _refServicesModel.setCategoryId(services.getString("estimated_time"));

                serviceArrayList.add(_refServicesModel);
            }
        }
        catch (JSONException e)
        {
            Toast.makeText(getApplicationContext(),"There is an Error parsing Services in Services Activity",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void initializeObjects()
    {
        requestQueue        = Volley.newRequestQueue(getApplicationContext());
        serviceArrayList    = new ArrayList<>();
        layoutManager       = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        adapter             = new ServicesAdapter(getApplicationContext(),serviceArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createView()
    {
        recyclerView        = (RecyclerView) findViewById(R.id.recyclcerView_services_xml);
        swipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh_services_xml);
        LocalToolbar        = (Toolbar) findViewById(R.id.toolbar);
    }
}
