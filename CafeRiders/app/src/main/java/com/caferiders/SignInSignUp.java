package com.caferiders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignInSignUp extends Activity implements View.OnClickListener{

    private TextView terms_conditions;
    private Button signup,signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);
        init();
    }

    public void init() {
        terms_conditions = (TextView) findViewById(R.id.terms_conditions);
        signup = (Button) findViewById(R.id.signup);
        signin = (Button) findViewById(R.id.signin);
        terms_conditions.setPaintFlags(terms_conditions.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.signup) {
            Intent i = new Intent(this,SignUp.class);
            startActivity(i);
        } else  if(view.getId() == R.id.signin) {
            Intent i = new Intent(this,LogIn.class);
            startActivity(i);
        }
    }
}

