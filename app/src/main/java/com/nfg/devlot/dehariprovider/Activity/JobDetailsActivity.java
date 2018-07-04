package com.nfg.devlot.dehariprovider.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfg.devlot.dehariprovider.Models.JobModel;
import com.nfg.devlot.dehariprovider.Models.URL;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.Session.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    RequestQueue            requestQueue;
    String                  jobId = "";
    ArrayList<JobModel>     jobArrayList = null;

    CircleImageView         circleImageView;
    TextView                seekerName;
    TextView                seekingService;
    TextView                locationOfSeeker;
    Button                  acceptOfferButton;
    Button                  declineOfferButton;

    int                     index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        /**
         *
         *  Creating Widgets here
         *  @func createWidget();
         *
         * */

        createWidget();


        /**
         *
         *  Initializing Objects here
         *  @func IntializingObjects();
         *
         * */

        IntializingObjects();



        /**
         *
         *  Retreiving Intent value
         *  @func retreiveIntent();
         *
         * */

        retreiveIntent();

        /**
         *
         *  Retreiving Job Information againt Job id
         *  @func retreiveJobInformation();
         *
         * */

        retreiveJobInformation();

        /**
         *
         *  Setting OnClickListeners for buttons here
         *  @code  acceptOfferButton   .setOnClickListener(this);
         *  @code  declineOfferButton  .setOnClickListener(this);
         *
         * */

        acceptOfferButton   .setOnClickListener(this);
        declineOfferButton  .setOnClickListener(this);
    }

    private void createWidget()
    {
        seekerName          = (TextView)        findViewById(R.id.workerName_textView_jobdetails_xml);
        seekingService      = (TextView)        findViewById(R.id.workerProfession_textView_jobdetails_xml);
        locationOfSeeker    = (TextView)        findViewById(R.id.userLocation_textView_jobdetails_xml);
        acceptOfferButton   = (Button)          findViewById(R.id.accept_button_jobdetails_xml);
        declineOfferButton  = (Button)          findViewById(R.id.decline_button_jobdetails_xml);
        circleImageView     = (CircleImageView) findViewById(R.id.workerProfile_circleImageView_jobdetails_xml);
    }

    private void IntializingObjects()
    {
        requestQueue = Volley.newRequestQueue(this);
        jobArrayList =  new ArrayList<>();

    }

    private void retreiveJobInformation()
    {
        StringRequest   request = new StringRequest(Request.Method.POST, URL.GET_JOB_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.contains("norecord"))
                {
                    return;
                }

                /**
                 *
                 * Capturing Response from server here
                 * @func capture_response(String response);
                 *
                 * */

                capture_response(response);


                /**
                 *
                 *  Showing information on display
                 *  @func showInformation();
                 *
                 * */

                showInformation();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"There appears to be a problem, Please try again", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();

                parameters.put("j_id",jobId);

                return parameters;
            }
        };

        requestQueue.add(request);
    }



    private void showInformation()
    {
        if(jobArrayList.size() > 0)
        {
            seekingService  .setText("I want to hire you for " + jobArrayList.get(0).getSeekingService());
            seekerName      .setText(jobArrayList.get(0).getSeekerName());
            locationOfSeeker.setText(jobArrayList.get(0).getLocation());
        }
    }


    private void capture_response(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray  jsonArray  = jsonObject.getJSONArray("job");

            for (int i=0; i<jsonArray.length();i++)
            {
                JSONObject job    = jsonArray.getJSONObject(i);

                JobModel   _refModel = new JobModel();

                _refModel.setJ_id(job.getString("j_id"));
                _refModel.setS_id(job.getString("s_id"));
                _refModel.setSs_id(job.getString("ss_id"));
                _refModel.setSp_id(job.getString("sp_id"));
                _refModel.setStarting_time(job.getString("start_time"));
                _refModel.setEnding_time(job.getString("ending_time"));
                _refModel.setLocation(job.getString("location"));
                _refModel.setStatus(job.getString("status"));
                _refModel.setAdditional_charges(job.getString("additional_charges"));


                _refModel.setSeekerName(job.getString("seeker"));
                _refModel.setSeekingService(job.getString("service"));
                _refModel.setSeekerFcToken(job.getString("token").trim());

                jobArrayList.add(_refModel);
            }


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private void retreiveIntent()
    {
        if(getIntent().hasExtra("jobid"))
        {
            jobId   =  getIntent().getStringExtra("jobid");
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.accept_button_jobdetails_xml)
        {
            /**
             *
             *  Sending a Accepting message back to Service seeker
             *  @func SendMessageToSeeker(String message);
             *
             * */

            SendMessageToServer(UserSession.uname + " has Declined your offer", "declined");

        }
        else if(view.getId() == R.id.decline_button_jobdetails_xml)
        {
            /**
             *
             *  Sending a Decling message back to Service seeker
             *  @func SendMessageToSeeker(String message);
             *
             * */

            SendMessageToServer(UserSession.uname + " has Accepted your offer", "accepted");

        }
    }

    private void SendMessageToServer(final String message, final String type)
    {
        StringRequest   request = new StringRequest(Request.Method.POST, URL.SEND_JOB_NOTIFICATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Cannot send Decline message to seeker", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parameters = new HashMap<String, String>();

                parameters.put("message", message);
                parameters.put("token"  , jobArrayList.get(0).getSeekerFcToken());
                parameters.put("sp_id"  , jobArrayList.get(0).getSp_id());
                parameters.put("ss_id"  , jobArrayList.get(0).getSs_id());
                parameters.put("s_id"   , jobArrayList.get(0).getS_id());
                parameters.put("type"   , type);
                parameters.put("j_id"   , jobArrayList.get(0).getJ_id());


                return parameters;
            }
        };

        requestQueue.add(request);
    }
}
