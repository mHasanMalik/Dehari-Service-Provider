package com.nfg.devlot.dehariprovider.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.SQL.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button              continueWithMobile_btn;
    LoginButton         loginButton;
    CallbackManager     callbackManager;
    DatabaseHelper      _refLocalDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createView();
        initializeObject();

        continueWithMobile_btn.setOnClickListener(this);
        loginButton.setOnClickListener(this);


        /**
         *
         *  Checking if the user is already signed in here
         *  @func isSessionExists();
         * */

        if(isSessionExists())
        {
            startActivity(new Intent(getApplicationContext(),NavigationDrawerActivity.class));
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            finish();
        }


    }

    private boolean isSessionExists()
    {
        if(_refLocalDbHelper.checkUser())
        {
            return true;
        }

        return false;
    }


    private void createView()
    {
        continueWithMobile_btn    = (Button) findViewById(R.id.continueWithPhoneNumber_button_main_xml);
        loginButton               = (LoginButton) findViewById(R.id.login_button);
        callbackManager           = CallbackManager.Factory.create();

    }


    private void initializeObject()
    {
        _refLocalDbHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.continueWithPhoneNumber_button_main_xml)
        {
            startActivity(new Intent(getApplicationContext(),EnterPhoneActivity.class));
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        }
        else if(v.getId() == R.id.login_button)
        {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
        startActivity(intent);
    }
}
