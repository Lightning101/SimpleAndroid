package com.apphunters.androidlogin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChangeFragmentListner  {

    FrameLayout fragframe ;
    FragmentManager fragmgr;
    SignInActivityFragmentFactory sigfact;
    ArrayList<ChangeFragmentRequestor> changReq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmgr = getFragmentManager();

        fragframe = (FrameLayout) findViewById(R.id.fragmentframe);
        //if the android is being restarted
        sigfact = new SignInActivityFragmentFactory();

        PortFragmentClass signInFrag = sigfact.getFragment("signin");

        signInFrag.setArguments(getIntent().getExtras());
        signInFrag.addChangeFragmentListner(this);




        //just incase fragment was called


        //adding fragment to container with tag
        fragmgr.beginTransaction().add(R.id.fragmentframe,signInFrag,"signin").commit();

    }



    @Override
    public void updateFragment(ChangeFragmentRequestor chg) {

        PortFragmentClass temp = sigfact.getFragment(chg.getFragmentName());
        fragmgr.beginTransaction().replace(R.id.fragmentframe,temp,"signup").addToBackStack(null).commit();

    }

    @Override
    public void addChangeFragmentRequestor(ChangeFragmentRequestor chg) {

    }

    @Override
    public void removeChangeFragmentRequestor(ChangeFragmentRequestor chg) {

    }
}
