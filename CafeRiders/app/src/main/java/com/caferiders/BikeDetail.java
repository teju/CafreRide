package com.caferiders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class BikeDetail extends AppCompatActivity implements View.OnClickListener{

    private TextView book_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_detail);
        initUI();
    }

    private void initUI() {
        book_now = (TextView)findViewById(R.id.book_now);
        book_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,ConfirmBooking.class);
        startActivity(i);
    }
}
