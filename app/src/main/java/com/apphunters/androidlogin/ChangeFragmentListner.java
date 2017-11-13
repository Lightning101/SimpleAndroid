package com.apphunters.androidlogin;

/**
 * Created by sean on 11/14/17.
 */

public interface ChangeFragmentListner {

    public void updateFragment(ChangeFragmentListner chg);

    public void addChangeFragmentRequestor(ChangeFragmentRequestor chg);

    public void removeChangeFragmentRequestor(ChangeFragmentRequestor chg);
}
