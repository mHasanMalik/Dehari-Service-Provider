package com.nfg.devlot.dehariprovider.Activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class ChangePasswordMenuActivity extends AppCompatActivity implements View.OnClickListener {


    TextInputLayout     currentPassword_textView,
                        newPassword_textView,
                        confirmPassword_textView;

    TextInputEditText   currentPassword_editText,
                        newPassword_editText,
                        confirmPassword_editText;

    InputValidation     _refInputValidation;
    Button              update_btn;
    ImageView           backButton;
    ProgressBar         progressBar;
    RequestQueue        requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_menu);

        createWidget();
        initializeObject();

        progressBar.setVisibility(View.INVISIBLE);

        update_btn.setOnClickListener(this);
    }



    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.update_button_changePassword_xml)
        {
            if(!_refInputValidation.isInputEditTextFilled(currentPassword_editText,currentPassword_textView,"Enter Current Password"))
            {
                return;
            }
            if(!_refInputValidation.isInputEditTextFilled(newPassword_editText,newPassword_textView,"Enter new Password"))
            {
                return;
            }
            if(!_refInputValidation.isInputEditTextFilled(confirmPassword_editText,confirmPassword_textView,"Enter confirm Password"))
            {
                return;
            }
            if(!_refInputValidation.isInputEditTextMatches(newPassword_editText,confirmPassword_editText,confirmPassword_textView,"Entered Passwords Do not match"))
            {
                return;
            }
            if(!_refInputValidation.isValidPassword(newPassword_editText,newPassword_textView,"Password not secured",getApplicationContext()))
            {
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            /**
             *
             *  Communiction With Database goes here
             *  @func UpdatePassword();
             *
             * */
            
            UpdatePassword();

        }
        else if(view.getId() == R.id.backButton_changePassword_xml)
        {
            finish();
        }
    }

    private void UpdatePassword()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URL.UPDATE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);
                if(response.contains("updated"))
                {
                    Toast.makeText(getApplicationContext(),"Password Updated Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(response.contains("failed"))
                {
                    Toast.makeText(getApplicationContext(),"There appears to be a problem, Please try again later",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"There appears to be a problem",Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("uid", UserSession.uid);
                parameters.put("old_password",currentPassword_editText.getText().toString().trim());
                parameters.put("new_password",newPassword_editText.getText().toString().trim());

                return parameters;
            }
        };

        requestQueue.add(request);
    }

    private void initializeObject()
    {
        _refInputValidation     = new InputValidation(this);
        requestQueue            = Volley.newRequestQueue(this);
    }

    private void createWidget()
    {
        currentPassword_textView    = (TextInputLayout)  findViewById(R.id.currentPassword_textView_newPassword_xml);
        newPassword_textView        = (TextInputLayout)  findViewById(R.id.newPassword_textView_newPassword_xml);
        confirmPassword_textView    = (TextInputLayout)  findViewById(R.id.confirmPassword_textView_newPassword_xml);

        currentPassword_editText    = (TextInputEditText)findViewById(R.id.currentPassword_editText_newPassword_xml);
        newPassword_editText        = (TextInputEditText)findViewById(R.id.newPassword_editText_newPassword_xml);
        confirmPassword_editText    = (TextInputEditText)findViewById(R.id.confirmPassword_editText_newPassword_xml);

        update_btn                  = (Button)           findViewById(R.id.update_button_changePassword_xml);
        backButton                  = (ImageView)        findViewById(R.id.backButton_changePassword_xml);
        progressBar                 = (ProgressBar)      findViewById(R.id.progressBar_changedPassword_xml);
    }
}
