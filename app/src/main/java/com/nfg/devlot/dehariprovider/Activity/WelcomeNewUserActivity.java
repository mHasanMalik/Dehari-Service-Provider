package com.nfg.devlot.dehariprovider.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nfg.devlot.dehariprovider.R;

public class WelcomeNewUserActivity extends AppCompatActivity implements View.OnClickListener{

    Button      createNewAccountBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new_user);

        createView();
        initializeObjects();

        createNewAccountBtn.setOnClickListener(this);
    }

    private void createView()
    {
        createNewAccountBtn = (Button) findViewById(R.id.continueButton_newUser_xml);
    }

    private void initializeObjects()
    {

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.continueButton_newUser_xml)
        {
            startActivity(new Intent(getApplicationContext(),NewUserSignUpActivity.class));
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        }
    }
}
