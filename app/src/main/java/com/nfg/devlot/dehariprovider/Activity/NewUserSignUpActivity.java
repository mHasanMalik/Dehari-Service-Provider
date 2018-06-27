package com.nfg.devlot.dehariprovider.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.nfg.devlot.dehariprovider.Models.UserModel;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.SQL.DatabaseHelper;
import com.nfg.devlot.dehariprovider.Session.UserSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText   name_editText,
                        email_editText,
                        password_editText,
                        confirmPassword_editText;

    TextInputLayout     name_textView,
                        email_textView,
                        password_textView,
                        confirmPassword_textView;

    Button              continueButton;
    //ProgressBar         progressBar;
    RequestQueue        requestQueue;
    InputValidation     _refInputValidation;
    CircleImageView     profileImage;
    DatabaseHelper      _refLocalDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_sign_up);

        createView();
        initializeObject();

        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.continue_button_userSignUp_xml)
        {
            if(checkInput())
            {
                /**
                 *
                 *  Disabling continue_btn here
                 *  @code continueButton.setEnabled(false);
                 *
                 * */

                continueButton.setEnabled(false);

                /**
                 *
                 *  Communication With database goes here
                 *  @func InsertNewUser();
                 *
                 * */

                InsertNewUser();

            }
        }
    }

    private boolean checkInput()
    {
        if(!_refInputValidation.isInputEditTextFilled(name_editText,name_textView,"Enter Name"))
        {
            return false;
        }
        if(!_refInputValidation.isInputEditTextFilled(password_editText,password_textView,"Enter Password"))
        {
            return false;
        }
        if(!_refInputValidation.isInputEditTextFilled(email_editText,email_textView,"Enter Email"))
        {
            return false;
        }
        if(!_refInputValidation.isInputEditTextFilled(confirmPassword_editText,confirmPassword_textView,"Enter confirm Password"))
        {
            return false;
        }
        if(!_refInputValidation.isInputEditTextMatches(password_editText,confirmPassword_editText,confirmPassword_textView,"Entered Passwords Do not match"))
        {
            return false;
        }
        if(!_refInputValidation.isValidPassword(password_editText,password_textView,"Password not secured",getApplicationContext()))
        {
            return false;
        }

        return true;
    }

    private void InsertNewUser()
    {
        StringRequest request = new StringRequest(Request.Method.POST, URL.INSERT_USER, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {
                /**
                 *
                 *  Enabling continue_btn here
                 *  @code continueButton.setEnabled(false);
                 *
                 * */

                continueButton.setEnabled(true);

                if(response.contains("phone_exists"))
                {
                    Toast.makeText(getApplicationContext(),"This phone number is already taken, Please choose different phone number", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(response.contains("email_exist"))
                {
                    Toast.makeText(getApplicationContext(),"This Email Address is already taken, Please choose different phone number", Toast.LENGTH_LONG).show();
                    return;
                }


                /**
                 *
                 *  Capturing Response from Server here
                 *  @func capture_response(String response);
                 *
                 * */

                capture_response(response);


                /**
                 *
                 *  Navigating to Main Menu Screen here
                 *  @destin NavigationDrawerActivity.class
                 *
                 * */

                finish();
                startActivity(new Intent(getApplicationContext(),NavigationDrawerActivity.class));
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                finish();

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                /**
                 *
                 *  Enabling continue_btn here
                 *  @code continueButton.setEnabled(false);
                 *
                 * */

                continueButton.setEnabled(true);

                Toast.makeText(getApplicationContext(),"There Appear to be a problem, Please try again later",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("name"     , name_editText     .getText().toString().trim());
                parameters.put("email"    , email_editText    .getText().toString().trim());
                parameters.put("password" , password_editText .getText().toString().trim());
                parameters.put("phone"    , UserSession.uPhone);

                return parameters;
            }
        };

        requestQueue.add(request);

    }

    private void capture_response(String response)
    {
        try
        {
            JSONObject jsonObject   = new JSONObject(response);
            JSONArray jsonArray     = jsonObject.getJSONArray("providerByRating");

            UserModel _refModel     = new UserModel();

            _refModel.setSs_id        (jsonArray.getJSONObject(0).getString("sp_id"));
            _refModel.setName         (jsonArray.getJSONObject(0).getString("name"));
            _refModel.setEmail        (jsonArray.getJSONObject(0).getString("email"));
            _refModel.setPhone_number (jsonArray.getJSONObject(0).getString("phone_number"));

            /**
             *
             *  Setting Session Variables here
             *  @code is written below
             *
             * */


            UserSession.uid         = _refModel.getUid();
            UserSession.uname       = _refModel.getName();
            UserSession.uemail      = _refModel.getEmail();
            UserSession.uPhone      = _refModel.getPhone_number();

            /**
             *
             *  Inserting into Local Database for session code goes here
             *  @func DatabaseHelper.insertUser(UserModel user);
             * */

            _refLocalDbHelper.insertUser(_refModel);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void initializeObject()
    {
        requestQueue             = Volley.newRequestQueue(this);
        _refInputValidation      = new InputValidation(this);
        _refLocalDbHelper        = new DatabaseHelper(this);
    }

    private void createView()
    {
        profileImage             = (CircleImageView)   findViewById(R.id.profilePicture_userSignUp_xml);

        name_editText            = (TextInputEditText) findViewById(R.id.name_editText_userSignUp_xml);
        email_editText           = (TextInputEditText) findViewById(R.id.email_editText_userSignUp_xml);
        password_editText        = (TextInputEditText) findViewById(R.id.password_editText_userSignUp_xml);
        confirmPassword_editText = (TextInputEditText) findViewById(R.id.confirmPassword_editText_userSignUp_xml);

        name_textView            = (TextInputLayout)  findViewById(R.id.name_textView_userSignUp_xml);
        email_textView           = (TextInputLayout)  findViewById(R.id.email_textView_userSignUp_xml);
        password_textView        = (TextInputLayout)  findViewById(R.id.password_textView_userSignUp_xml);
        confirmPassword_textView = (TextInputLayout)  findViewById(R.id.confirmPassword_textView_userSignUp_xml);

        continueButton           = (Button)           findViewById(R.id.continue_button_userSignUp_xml);

        //progressBar              = (ProgressBar) findViewById(R.id.progressBar_forgotPassword_xml);
    }
}
