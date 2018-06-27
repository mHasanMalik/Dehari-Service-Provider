package com.nfg.devlot.dehariprovider.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nfg.devlot.dehariprovider.Activity.ChangePasswordMenuActivity;
import com.nfg.devlot.dehariprovider.Activity.MainActivity;
import com.nfg.devlot.dehariprovider.R;
import com.nfg.devlot.dehariprovider.Session.UserSession;
import java.util.ArrayList;

public class SettingMenuFragment extends Fragment implements View.OnClickListener {

    TextView            name_textView,
                        email_textView,
                        mobileNumber_textView,
                        language_textView,
                        logout_textView;

    RelativeLayout      language_btn,
                        rate_btn,
                        termAndCondition_btn,
                        logout_btn;

    TextView            change_btn;
    RelativeLayout      change_btn_rel;
    LinearLayout        change_btn_lin;
    ImageView           change_tbn_img;
    //Toolbar             toolbar;
    ArrayList<String>   allLanguages;


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        assert inflater != null;
        View view = inflater.inflate(R.layout.fragment_setting_menu, container, false);

        createView(view);
        initializeObject();


        /**
         *
         *  Setting Toolbar Home Up here
         *  @func SetToolBarHomeUp();
         *
         * */

        //SetToolBarHomeUp();


        name_textView.setText(UserSession.uname);
        email_textView.setText(UserSession.uemail);
        mobileNumber_textView.setText(UserSession.uPhone);

        language_btn.setOnClickListener(this);
        rate_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);

        change_btn.setOnClickListener(this);
        change_btn_rel.setOnClickListener(this);
        change_btn_lin.setOnClickListener(this);
        change_tbn_img.setOnClickListener(this);
        termAndCondition_btn.setOnClickListener(this);

        return view;
    }

   /* private void SetToolBarHomeUp()
    {

        assert ((AppCompatActivity)getActivity()) != null;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if(((AppCompatActivity) getActivity()).getSupportActionBar()!=null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
            toolbar.setTitleTextColor(Color.WHITE);

            if((((AppCompatActivity) getActivity()).getSupportActionBar())!=null)
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }*/

    private void initializeObject()
    {

    }

    private void createView(View view)
    {
        name_textView           = (TextView)       view.findViewById(R.id.profileName_textView_setting_xml);
        email_textView          = (TextView)       view.findViewById(R.id.profileEmail_textView_setting_xml);
        mobileNumber_textView   = (TextView)       view.findViewById(R.id.profileMobileNumber_textView_setting_xml);
        language_textView       = (TextView)       view.findViewById(R.id.profileLanguage_textView_setting_xml);
        logout_textView         = (TextView)       view.findViewById(R.id.logout_textView_setting_xml);
        change_btn              = (TextView)       view.findViewById(R.id.changePassword_textView_setting_xml);

        language_btn            = (RelativeLayout) view.findViewById(R.id.LanguageLayout_relative_setting_xml);
        rate_btn                = (RelativeLayout) view.findViewById(R.id.rateLayout_relative_setting_xml);
        logout_btn              = (RelativeLayout) view.findViewById(R.id.logout_relative_setting_xml);
        change_btn_rel          = (RelativeLayout) view.findViewById(R.id.forthLayout_relativeLayout_setting_xml);
        termAndCondition_btn    = (RelativeLayout) view.findViewById(R.id.aboutLayout_relative_setting_xml);

        change_btn_lin          = (LinearLayout)   view.findViewById(R.id.forthLayout_linearLayout_setting_xml);

        change_tbn_img          = (ImageView)      view.findViewById(R.id.changePassword_imageView_setting_xml);

        //toolbar                 = (Toolbar)        view.findViewById(R.id.toolbar);
    }

    private void showTermsAndConditions()
    {
        final  AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(getActivityNotNull(),android.R.style.Theme_Material_Dialog_Alert);
        }
        else
        {
            builder = new AlertDialog.Builder(getActivityNotNull());
        }

        builder.setTitle("Not Avaiable")
                .setMessage("This option awaits Team approval")
                .setPositiveButton("Close", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void showRateUsDialog()
    {
        final AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(getActivityNotNull(),android.R.style.Theme_Material_Dialog_Alert);

        }
        else
        {
            builder = new AlertDialog.Builder(getActivityNotNull());
        }
        builder.setTitle("Not Available")
                .setMessage("This option is disabled by Senior Developer")
                .setPositiveButton("Close", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    @Override
    public void onClick(View view)
    {
        if(view.getId() ==  R.id.LanguageLayout_relative_setting_xml)
        {
            Toast.makeText(getActivity(),"Multi Language support has been disabled by Senior Developer",Toast.LENGTH_SHORT).show();
            //showSelectLanguageDialog();
        }
        else if(view.getId() ==  R.id.rateLayout_relative_setting_xml)
        {
            showRateUsDialog();
        }
        else if(view.getId() ==  R.id.changePassword_textView_setting_xml)
        {
            Intent intent = new Intent(getActivity(),ChangePasswordMenuActivity.class);
            startActivity(intent);
        }
        else if(view.getId() ==  R.id.forthLayout_relativeLayout_setting_xml)
        {
            Intent intent = new Intent(getActivity(),ChangePasswordMenuActivity.class);
            startActivity(intent);
        }
        else if(view.getId() ==  R.id.forthLayout_linearLayout_setting_xml)
        {
            Intent intent = new Intent(getActivity(),ChangePasswordMenuActivity.class);
            startActivity(intent);
        }
        else if(view.getId() ==  R.id.changePassword_imageView_setting_xml)
        {
            Intent intent = new Intent(getActivity(),ChangePasswordMenuActivity.class);
            startActivity(intent);
        }
        else if(view.getId() ==  R.id.aboutLayout_relative_setting_xml)
        {
            showTermsAndConditions();
        }
        else if(view.getId() == R.id.logout_relative_setting_xml)
        {
            resetUserSessions();
            Intent intent   = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        }
    }

    private void resetUserSessions()
    {
        UserSession.uid ="-1";
        UserSession.uname="";
        UserSession.uemail="";
        UserSession.uPhone="";

    }


    protected FragmentActivity getActivityNotNull()  {
        if(getActivity()!=null)
        {
            return super.getActivity();
        }
        else
        {
            throw new RuntimeException("Get Activity is Null");
        }
    }


}
