package com.nfg.devlot.dehariprovider.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfg.devlot.dehariprovider.Adapters.ReviewAdapter;
import com.nfg.devlot.dehariprovider.Helpers.NetworkChecker;
import com.nfg.devlot.dehariprovider.Models.ReviewModel;
import com.nfg.devlot.dehariprovider.Models.URL;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.Session.UserSession;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerProfileActivity extends AppCompatActivity implements View.OnClickListener
{

    ImageView                call_btn,
                             message_btn;
    TextView                 userName_editText,
                             userLocaton_editText;
    RatingBar                ratingBar;
    CircleImageView          profileImage;
    RequestQueue             requestQueue;
    String                   sentId,
                             sentName,
                             sentPhone,
                             sentLocation,
                             sentRating,
                             sentImagePath;
    ArrayList<ReviewModel>   reviewModelArrayList = null;
    ReviewAdapter            adapterReviewObject;
    RecyclerView             recyclerViewReview;
    LinearLayoutManager      layoutManager;
    Button                   hireNowButton;
    NetworkChecker           network;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        createView();
        initializeObject();

        /**
         *
         *  Diabling Message and Phone button here
         *
         *  message_btn.setVisibility(View.INVISIBLE);
         *  call_btn.setVisibility(View.INVISIBLE);
         * */

        message_btn.setVisibility(View.INVISIBLE);
        call_btn.setVisibility(View.INVISIBLE);

        /**
         *
         *  Retrieving intent values here
         *
         * */

        if(getIntent()!=null)
        {
            if(getIntent().hasExtra("sid") &&
                    getIntent().hasExtra("name") &&
                    getIntent().hasExtra("phone") &&
                    getIntent().hasExtra("location") &&
                    getIntent().hasExtra("averagerating") &&
                    getIntent().hasExtra("imagepath"))
            {
                sentId          = getIntent().getStringExtra("sid");
                sentName        = getIntent().getStringExtra("name");
                sentPhone       = getIntent().getStringExtra("phone");
                sentLocation    = getIntent().getStringExtra("location");
                sentRating      = getIntent().getStringExtra("averagerating");
                sentImagePath   = getIntent().getStringExtra("imagepath");

                /**
                 *
                 *  Setting values to UI elements here
                 *
                 * */

                userName_editText.setText(sentName);
                userLocaton_editText.setText(sentLocation);
                ratingBar.setRating(Float.parseFloat(sentRating));

                downloadImage(profileImage,sentImagePath);
            }
        }


        /**
         *
         *  Communication with database goes here
         *  @func getAllReviews();
         *
         * */


        getAllReviews();


        /**
         *
         *  Setting OnClickHandlers heres
         *
         *  @code  hireNowButton.setOnClickListener(this);
         *         call_btn.setOnClickListener(this);
         *         message_btn.setOnClickListener(this);
         * */


        hireNowButton.setOnClickListener(this);
        call_btn.setOnClickListener(this);
        message_btn.setOnClickListener(this);


        /**
         *   Checking if the user is already hired or not here
         *   @func isHired();
         *
         * */

        isHired();

    }

    private void getAllReviews()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URL.GET_WORKER_REVIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /**
                 *
                 *  Capturing and parsing response here
                 *  @func capture_Response_for_reviews(String response);
                 *
                 * */

                capture_Response_for_reviews(response);

                /**
                 *
                 *  Setting data to adapter here
                 *  @code adapterReviewObject.UpdateRecord(ArrayList<ReviewModel> reviewModelArrayList);
                 *
                 * */

                adapterReviewObject.UpdateRecord(reviewModelArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"There Appears to be problem, Please try again later", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("sp_id",sentId);

                return  parameters;
            }
        };

        requestQueue.add(request);
    }

    private void capture_Response_for_reviews(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("ratings");

            for(int i=0;i<jsonArray.length();i++)
            {
                ReviewModel _refReviewModel     = new ReviewModel();
                JSONObject  reviews             = jsonArray.getJSONObject(i);

                _refReviewModel.setServiceProviderRatingId(reviews.getString("spr_id"));
                _refReviewModel.setServiceSeekerId(reviews.getString("ss_id"));
                _refReviewModel.setServiceProviderId(reviews.getString("sp_id"));
                _refReviewModel.setReviewerName(reviews.getString("name"));
                _refReviewModel.setRating(reviews.getString("rating"));
                _refReviewModel.setComment(reviews.getString("comment"));
                _refReviewModel.setTime(reviews.getString("time"));

                reviewModelArrayList.add(_refReviewModel);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void downloadImage(ImageView profileView, String imagePath)
    {
        Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(profileView);
    }

    private void initializeObject()
    {
        requestQueue            = Volley.newRequestQueue(this);
        reviewModelArrayList    = new ArrayList<>();
        adapterReviewObject     = new ReviewAdapter(this,reviewModelArrayList);
        network                 = new NetworkChecker(this);
        layoutManager           = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewReview.setLayoutManager(layoutManager);
        recyclerViewReview.setAdapter(adapterReviewObject);
    }

    private void createView()
    {
        call_btn                = (ImageView)       findViewById(R.id.callIcon_ImageView_workerProfile_xml);
        message_btn             = (ImageView)       findViewById(R.id.messageIcon_imageView_workerProfile_xml);
        userName_editText       = (TextView)        findViewById(R.id.workerName_textView_workerProfile_xml);
        userLocaton_editText    = (TextView)        findViewById(R.id.userLocation_textView_workerProfile_xml);
        ratingBar               = (RatingBar)       findViewById(R.id.workerRating_ratingBar_workerProfile_xml);
        profileImage            = (CircleImageView) findViewById(R.id.workerProfile_circleImageView_workerProfile_xml);
        recyclerViewReview      = (RecyclerView)    findViewById(R.id.reviewRecyclerView_workerProfile_xml);
        hireNowButton           = (Button)          findViewById(R.id.hireNow_btn_workerProfile_xml);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.hireNow_btn_workerProfile_xml)
        {
            message_btn.setVisibility(View.VISIBLE);
            call_btn.setVisibility(View.VISIBLE);
            hireNowButton.setVisibility(View.INVISIBLE);

        }
        if(v.getId() == R.id.callIcon_ImageView_workerProfile_xml)
        {
            makeCall();
        }
        if(v.getId() == R.id.messageIcon_imageView_workerProfile_xml)
        {
            sendSMS();
        }
    }

    private void makeCall()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {

            Log.d("Test->PhoneCall",UserSession.uPhone);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+UserSession.uPhone));
            startActivity(intent);
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},100);
        }

    }

    private void sendSMS()
    {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("address", UserSession.uPhone);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {*/
            Log.d("Test->PhoneSms",UserSession.uPhone);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + UserSession.uPhone));
            startActivity(intent);
        //}
    }

    private void isHired()
    {
        if(!network.haveNetworkConnection())
        {
            Toast.makeText(getApplicationContext(),"No Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL.CHECK_IF_PROVIDER_HIRED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.contains("true"))
                {
                    message_btn.setVisibility(View.VISIBLE);
                    call_btn.setVisibility(View.VISIBLE);
                    hireNowButton.setVisibility(View.INVISIBLE);
                }
                else if(response.contains("false"))
                {
                    message_btn.setVisibility(View.INVISIBLE);
                    call_btn.setVisibility(View.INVISIBLE);
                    hireNowButton.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"There Appears to be a problem, Please try again later",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("sp_id",sentId);

                return parameters;
            }
        };

        requestQueue.add(request);

    }
}
