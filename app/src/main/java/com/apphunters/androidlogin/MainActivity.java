package com.apphunters.androidlogin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity  {

    FrameLayout fragframe ;
    FragmentManager fragmgr;
    FragmentTransaction fragtrns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmgr = getFragmentManager();
        fragtrns = fragmgr.beginTransaction();
        fragframe = (FrameLayout) findViewById(R.id.fragmentframe);
        //if the android is being restarted
        if(savedInstanceState != null)
            return;

        SignIn signInFrag = new SignIn();

        //just incase fragment was called
        signInFrag.setArguments(getIntent().getExtras());

        //adding fragment to container with tag
        fragtrns.add(R.id.fragmentframe,signInFrag,"signin").commit();

    }


}
