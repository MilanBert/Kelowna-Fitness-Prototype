package com.example.step4test;

import static java.lang.Math.round;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<Goal> itemList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView, trendText;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.itemTextView);
            trendText = view.findViewById(R.id.trendTextView);
        }
        public TextView getTextView() {
            return textView;
        }
    }
    public RecycleAdapter(ArrayList<Goal> itemList) {
        this.itemList = itemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        float trend = itemList.get(position).getTrend();
        String item = itemList.get(position).getName();
        String trendString = (trend>0?"+":"-")+ getTrendPercentage(trend);
        holder.textView.setText(item);
        holder.trendText.setText(trendString);
        holder.trendText.setTextColor(mapFloatToColor(trend));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public String getTrendPercentage(float trend){
        String output = round(trend*100)+"%";
        return output;
    }
    public static int mapFloatToColor(float value) {
        value = Math.max(-1, Math.min(1, value));
        // If value is negative increase the red component
        if (value < 0) {
            int red = (int) ((1 + value) * 255);
            return Color.rgb(red, 0, 0); // more red, no green or blue
        } else if (value > 0) {
            // If value is positive, we increase the green component
            int green = (int) (55+value * 200);
            return Color.rgb(0, green, 0); // more green, no red or blue
        } else {
            return Color.rgb(128, 128, 128); // grey
        }
    }

}
