package com.apphunters.androidlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        text = (TextView) findViewById(R.id.home_text);
        text.setText("Welcome  "+getIntent().getExtras().getString("detials"));
    }
}
