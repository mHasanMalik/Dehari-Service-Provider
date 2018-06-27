package com.nfg.devlot.dehariprovider.Activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nfg.devlot.dehariprovider.Helpers.InputValidation;
import com.nfg.devlot.dehariprovider.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputEditText   newPassword_editText,
                        confirmPassword_editText;

    TextInputLayout     newPassword_textView,
                        confirmPassword_textView;

    Button              continue_btn;
    InputValidation _refInputValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        createView();
        initializeObject();
    }

    private void initializeObject()
    {
        _refInputValidation = new InputValidation(this);
    }

    private void createView()
    {
        continue_btn             = (Button) findViewById(R.id.update_button_changePassword_xml);

        newPassword_editText     = (TextInputEditText) findViewById(R.id.newPassword_editText_forgotPassword_xml);
        confirmPassword_editText = (TextInputEditText) findViewById(R.id.confirmPassword_editText_forgotPassword_xml);

        newPassword_textView     = (TextInputLayout) findViewById(R.id.newPassword_textView_forgotPassword_xml);
        confirmPassword_textView = (TextInputLayout) findViewById(R.id.confirmPassword_textView_forgotPassword_xml);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.update_button_changePassword_xml)
        {
            if(checkInput())
            {
                /**
                 *
                 * COMMUNICATE WITH DATABASE HERE
                 *
                 * */

            }
        }
    }

    private boolean checkInput()
    {
        if(!_refInputValidation.isInputEditTextFilled(confirmPassword_editText, confirmPassword_textView, "Re-enter New Password"))
        {
            return false;
        }
        else if(!_refInputValidation.isInputEditTextFilled(newPassword_editText, confirmPassword_textView, "Enter New Password"))
        {
            return false;
        }
        else if(!_refInputValidation.isInputEditTextMatches(confirmPassword_editText, newPassword_editText, confirmPassword_textView, "Entered Passwords do not match"))
        {
            return false;
        }

        return true;
    }
}
