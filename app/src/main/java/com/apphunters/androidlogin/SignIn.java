package com.apphunters.androidlogin;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


public class SignIn extends Fragment {

    EditText userNameView;
    EditText passwordView;
    Button signinView;
    Button signupView;
    String transferedUsername;
    boolean inProgress;
    ProgressBar progressbar;
    FrameLayout root;

    public SignIn() {

    }

    // TODO: Rename and change types and number of parameters
    public static SignIn newInstance(String usrnm) {
        SignIn fragment = new SignIn();
        Bundle args = new Bundle();
        args.putString("arg1", usrnm);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState.containsKey("arg1"))
        {
            transferedUsername = savedInstanceState.getString("arg1");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        userNameView = (EditText) getView().findViewById(R.id.username);
        passwordView = (EditText) getView().findViewById(R.id.password);
        signinView = (Button) getView().findViewById(R.id.signin);
        signupView = (Button) getView().findViewById(R.id.signup);
        progressbar = (ProgressBar) getView().findViewById(R.id.Progressbar);
        root = (FrameLayout) getView().findViewById(R.id.SigninFrameLayout);
        if(transferedUsername!=null && !savedInstanceState.containsKey("username") && !savedInstanceState.containsKey("username"))
        {
            userNameView.setText(transferedUsername);
        }

        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState.containsKey("username") )
        {
            userNameView.setText(savedInstanceState.getString("username"));
        }

        if( savedInstanceState.containsKey("password"))
        {
            passwordView.setText(savedInstanceState.getString("password"));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!userNameView.getText().toString().isEmpty() && userNameView.getText() != null)
        {
            outState.putString("username",userNameView.getText().toString());
        }
        if(!passwordView.getText().toString().isEmpty() &&passwordView.getText() != null)
        {
            outState.putString("password",passwordView.getText().toString());
        }



    }


    public void onSignClicked(View v)
    {
        if(!inProgress)
        {
            inProgress= true;
            new GetUserDetials("",)
        }

    }




    private class GetUserDetials  extends AsyncTask<String,Integer,Document> {

        MongoClientURI uri;
        MongoClient client;
        MongoDatabase db;
        ProgressBar prgb;

        public GetUserDetials(String mongoUrl) {
            this.uri = new MongoClientURI(mongoUrl);
            this.client = new MongoClient(uri);
            this.db = client.getDatabase(uri.getDatabase());

        }

        public GetUserDetials(String mongoUrl, ProgressBar prgb)
        {
            this.uri = new MongoClientURI(mongoUrl);
            this.client = new MongoClient(uri);
            this.db = client.getDatabase(uri.getDatabase());

            this.prgb = prgb;
        }


        @Override
        protected Document doInBackground(String... strings) {

            MongoCollection<Document> coll = db.getCollection(uri.getCollection());
            Document user = coll.find(eq("username", strings[0])).first();
            if (user == null) {
                return null;
            } else {
                return user;
            }

        }

        @Override
        protected void onPostExecute(Document document)
        {
        }


    }



}