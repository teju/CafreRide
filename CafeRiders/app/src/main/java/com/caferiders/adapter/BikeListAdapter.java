package com.caferiders.adapter;

/**
 * Created by Khushvinders on 15-Nov-16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.caferiders.BikeDetail;
import com.caferiders.R;


public class BikeListAdapter extends RecyclerView.Adapter<BikeListAdapter.MyViewHolder> {
    private final Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout bike_item;

        public MyViewHolder(View view) {
            super(view);
            bike_item = (LinearLayout)view.findViewById(R.id.bike_item);
        }
    }


    public BikeListAdapter(Context context) {
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bike_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bike_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,BikeDetail.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}