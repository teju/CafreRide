package com.caferiders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.caferiders.adapter.BikeListAdapter;

public class BikeList extends AppCompatActivity {

    private RecyclerView bike_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_list);
        initUI();
    }

    private void initUI() {
        bike_list = (RecyclerView) findViewById(R.id.bike_list);
        BikeListAdapter mAdapter = new BikeListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bike_list.setLayoutManager(mLayoutManager);
        bike_list.setItemAnimator(new DefaultItemAnimator());
        bike_list.setAdapter(mAdapter);

    }
}
