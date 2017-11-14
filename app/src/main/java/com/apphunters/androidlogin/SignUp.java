package com.apphunters.androidlogin;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoServerException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;


public class SignUp extends PortFragmentClass {

    EditText firstnameView;
    EditText lastnameView;
    EditText usernameView;
    EditText eamilView;
    EditText passwordView;
    Button signupView;
    EditText help;
    String fragname;
    ArrayList<ChangeFragmentListner> chglst;
    boolean inprogress;
    public SignUp() {
        // Required empty public constructor
        chglst = new ArrayList<>();
        fragname ="";
        inprogress = false;
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

            firstnameView = (EditText) getView().findViewById(R.id.signup_firstname);
            lastnameView = (EditText) getView().findViewById(R.id.signup_lastname);
            usernameView = (EditText) getView().findViewById(R.id.signup_usrname);
            eamilView = (EditText) getView().findViewById(R.id.signup_email);
            passwordView = (EditText) getView().findViewById(R.id.signup_password);
            help = (EditText)getView().findViewById(R.id.signup_help);
            signupView = (Button) getView().findViewById(R.id.signup_button);
            signupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSignUpClicked(view);
                }
            });




    }

    @Override
    public void addChangeFragmentListner(ChangeFragmentListner chg) {
        chglst.add(chg);
    }

    @Override
    public void requestFragmentChange() {
        for(ChangeFragmentListner ch: chglst)
            ch.updateFragment(this);
    }

    @Override
    public void removeChangeFragmentListner(ChangeFragmentListner chg) {
        chglst.remove(chg);
    }

    @Override
    public String getFragmentName() {
        return this.fragname;
    }

    @Override
    public void setFragmentName(String name) {
        this.fragname = name;
    }




    public void onSignUpClicked(View v)
    {
        help.setVisibility(View.GONE);
        help.setText("");
        String temp ="Incorrect: ";

        if(firstnameView.getText().toString().isEmpty() || firstnameView.getText().toString() == null)
        {
           temp+= "First Name,";
        }else if(lastnameView.getText().toString().isEmpty() ||lastnameView.getText().toString() == null)
        {
            temp+= "last Name,";
        }else if(usernameView.getText().toString().isEmpty() ||usernameView.getText().toString() == null)
        {
            temp+= "User Name,";
        }else if(eamilView.getText().toString().isEmpty() ||eamilView.getText().toString() == null)
        {
            temp+= " Email,";
        }else if(passwordView.getText().toString().isEmpty() ||passwordView.getText().toString() == null)
        {
            temp+= "password,";
        }

        if(temp.equals("Incorrect: "))
        {
            if(!inprogress) {
                inprogress = true;
                Document doc = new Document();
                doc.put("first_name", firstnameView.getText().toString());
                doc.put("last_name", lastnameView.getText().toString());
                doc.put("username", usernameView.getText().toString());
                doc.put("email", eamilView.getText().toString());
                doc.put("password", passwordView.getText().toString());

                System.out.println(doc.toString());
                new UpdateUserDetials("mongodb://root:sean1996@ds159254.mlab.com:59254/test1.user").execute(doc);
            }
        }else
        {
            help.setText(temp);
            help.setVisibility(View.VISIBLE);

        }


    }


    public void onCompleted(boolean boo)
    {
        if(boo)
        {
            setFragmentName("signin");
            requestFragmentChange();

        }else
        {
            help.setText("Uable to add user to system");
            help.setVisibility(View.VISIBLE);
        }
        inprogress = false;
    }


    private class UpdateUserDetials extends AsyncTask<Document , Integer, Boolean>
    {

        MongoClientURI uri;
        MongoClient client;
        MongoDatabase db;


        public UpdateUserDetials(String mongoUrl)
        {

            this.uri = new MongoClientURI(mongoUrl);
            this.client = new MongoClient(uri);
            this.db = client.getDatabase(uri.getDatabase());

        }




        @Override
        protected Boolean doInBackground(Document... docs) {

            MongoCollection<Document> coll = db.getCollection(uri.getCollection());

            try {
                coll.insertOne(docs[0]);
            }
            catch (MongoServerException ex) {
                System.out.println("sadasdasds here 2");
                return false;
            }
            System.out.println("sadasdasds here 1");

            return true;

        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleted(aBoolean);
        }
    }

}
