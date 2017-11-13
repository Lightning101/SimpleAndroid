package com.apphunters.androidlogin;

/**
 * Created by sean on 11/14/17.
 */

public interface ChangeFragmentRequestor {


    public void addChangeFragmentListner(ChangeFragmentListner chg);

    public void requestFragmentChange();

    public void removeChangeFragmentListner(ChangeFragmentListner chg);

    public String getFragmentName();

    public void setFragmentName(String name);

}
