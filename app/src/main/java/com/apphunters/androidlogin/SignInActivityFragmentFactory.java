package com.apphunters.androidlogin;

import android.app.Fragment;

/**
 * Created by sean on 11/14/17.
 */

public class SignInActivityFragmentFactory {


    public SignInActivityFragmentFactory()
    {

    }

    public PortFragmentClass getFragment(String fragmentName)
    {

            if(fragmentName.equals("signin"))
                return new SignIn();
            else if(fragmentName.equals("signup") )
                return new SignUp();
            else
                return null;

    }

}