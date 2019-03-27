package com.caferiders;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

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
        int resId = R.anim.layout_animation_slide_right;
        bike_list = (RecyclerView) findViewById(R.id.bike_list);
        BikeListAdapter mAdapter = new BikeListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bike_list.setLayoutManager(mLayoutManager);
        bike_list.setAdapter(mAdapter);
        final Context context = bike_list.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);

        bike_list.setLayoutAnimation(controller);
        bike_list.getAdapter().notifyDataSetChanged();
        bike_list.scheduleLayoutAnimation();


    }
}
