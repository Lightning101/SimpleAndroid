package com.apphunters.androidlogin;

import android.app.Fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;


public class SignIn extends PortFragmentClass {

    ArrayList<ChangeFragmentListner> crl;

    String fragmentName;


    @Override
    public void addChangeFragmentListner(ChangeFragmentListner chg) {
        crl.add(chg);
    }

    @Override
    public void requestFragmentChange() {

        for(ChangeFragmentListner ch: crl)
        {
            ch.updateFragment(this);
        }
    }

    @Override
    public void removeChangeFragmentListner(ChangeFragmentListner chg) {
        crl.remove(chg);
    }

    @Override
    public String getFragmentName() {
        return this.fragmentName;
    }

    @Override
    public void setFragmentName(String name) {
        this.fragmentName = name;
    }

    EditText userNameView;
    EditText passwordView;
    TextView help;
    Button signinView;
    Button signupView;
    boolean inProgress;
    ProgressBar progressbar;
    FrameLayout root;

    public SignIn() {
        crl = new ArrayList<>();
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


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userNameView = (EditText) getView().findViewById(R.id.username);
        passwordView = (EditText) getView().findViewById(R.id.password);
        signinView = (Button) getView().findViewById(R.id.signin);
        signupView = (Button) getView().findViewById(R.id.signup);
        progressbar = (ProgressBar) getView().findViewById(R.id.Progressbar);
        root = (FrameLayout) getView().findViewById(R.id.SigninFrameLayout);
        help = (TextView) getView().findViewById(R.id.help);
        signinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignClicked(view);
            }
        });
        signupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpClicked(view);
            }
        });

    }


    public void onSignUpClicked(View v)
    {

        setFragmentName("signup");
        requestFragmentChange();
    }


    public void onSignClicked(View v)
    {
        help.setVisibility(View.GONE);
        help.setText("");
        String username = userNameView.getText().toString();
        String password = userNameView.getText().toString();


        if( username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            if (!inProgress) {
                inProgress = true;
                View basesign = root.getChildAt(1);
                View baseprog = root.getChildAt(0);
                root.removeAllViews();
                root.addView(basesign);
                root.addView(baseprog);

                new GetUserDetials("mongodb://root:sean1996@ds159254.mlab.com:59254/test1.user", progressbar).execute(username);

            }

        }else{

            help.setText("Password Or Username is Invalid");
            help.setVisibility(View.VISIBLE);

        }

    }


    public void updateLogin(Document document)
    {

        String username = userNameView.getText().toString();
        String password = passwordView.getText().toString();

        View basesign = root.getChildAt(0);
        View baseprog = root.getChildAt(1);
        root.removeAllViews();
        root.addView(baseprog);
        root.addView(basesign);


        if( username != null && !username.isEmpty() && password != null && !password.isEmpty()) {

            //System.out.println("herer "+ document.getString("username")+ "  "+document.getString("password"));
            //System.out.println("herer "+username+ "  "+password);

            if(username.equals(document.getString("username")) && password.equals(document.getString("password")) )
            {

                //String temp = " congradulations you are now loged in " +document.getString("first_name")+ "  "+document.getString("last_name");
                //help.setText(temp);
                //help.setVisibility(View.VISIBLE);
                Intent tent = new Intent(getActivity(),Home.class);
                tent.putExtra("detials",document.getString("first_name")+ "  "+document.getString("last_name"));
                startActivity(tent);
            }else
            {
                help.setText("Password Or Username is Incorrect");
                help.setVisibility(View.VISIBLE);
                inProgress = false;

            }
        }else
        {

            help.setText("Password Or Username is Invalid");
            help.setVisibility(View.VISIBLE);
            inProgress = false;

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
            updateLogin(document);
        }


    }



}