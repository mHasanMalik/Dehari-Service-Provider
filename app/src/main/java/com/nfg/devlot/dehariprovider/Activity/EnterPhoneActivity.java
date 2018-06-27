package com.nfg.devlot.dehariprovider.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nfg.devlot.dehariprovider.Helpers.InputValidation;
import com.nfg.devlot.dehariprovider.Models.URL;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.Session.UserSession;
import java.util.HashMap;
import java.util.Map;

public class EnterPhoneActivity extends AppCompatActivity implements View.OnClickListener{

    Button              continue_btn;
    TextInputLayout     phone_textView;
    TextInputEditText   phone_editText;
    InputValidation     _refInputValidation;
    RequestQueue        requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);

        createView();
        initializeObject();

        continue_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.continueButton_enterPhone_xml) {
            if (checkInput())
            {
                /**
                 *
                 * COMMUNICATION WITH DATABASE GOES HERE
                 *
                 * */

                StringRequest request = new StringRequest(Request.Method.POST, URL.CHECK_PHONE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        UserSession.uPhone      =   phone_editText.getText().toString().trim();
                        Log.d("Test->Phone", UserSession.uPhone);

                        if(response.contains("false"))
                        {
                            startActivity(new Intent(getApplicationContext(),WelcomeNewUserActivity.class));
                            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                        }
                        else if(response.contains("true"))
                        {
                            startActivity(new Intent(getApplicationContext(),EnterPasswordActivity.class));
                            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"There Appears to be problem, Please try again later",Toast.LENGTH_SHORT).show();

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();

                        parameters.put("phone",phone_editText.getText().toString().trim());
                        return parameters;
                    }
                };

                requestQueue.add(request);

            }
        }
    }



    private boolean checkInput()
    {
        if(!_refInputValidation.isInputEditTextFilled(phone_editText,phone_textView, "Enter Phone"))
        {
            return false;
        }
        else if(!_refInputValidation.isValidPhoneNumber(phone_editText, phone_textView,"Please Enter Valid Phone Number"))
        {
            return false;
        }

        return true;
    }

    private void initializeObject()
    {
        _refInputValidation = new InputValidation(this);
        requestQueue        = Volley.newRequestQueue(getApplicationContext());
    }

    private void createView()
    {
        continue_btn    = (Button)           findViewById(R.id.continueButton_enterPhone_xml);
        phone_editText  = (TextInputEditText)findViewById(R.id.phoneNumber_editText_enterPhone_xml);
        phone_textView  = (TextInputLayout)  findViewById(R.id.phoneNumber_textView_enterPhone_xml);
    }
}
