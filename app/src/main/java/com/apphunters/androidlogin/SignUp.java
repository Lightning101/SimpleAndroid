package com.apphunters.androidlogin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends PortFragmentClass {


    public SignUp() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignUp newInstance() {
        SignUp fragment = new SignUp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addChangeFragmentListner(ChangeFragmentListner chg) {

    }

    @Override
    public void requestFragmentChange() {

    }

    @Override
    public void removeChangeFragmentListner(ChangeFragmentListner chg) {

    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void setFragmentName(String name) {

    }
}
