package com.example.step4test;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<Goal> itemList;
    private int selectedPosition = -1; // To track the selected goal

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
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        float trend = itemList.get(position).getTrend();
        String item = itemList.get(position).getName();
        String trendString = getTrendPercentage(trend) + (trend > 0 ? " closer" : " further away");
        holder.textView.setText(item);
        holder.trendText.setText(trendString);
        holder.trendText.setTextColor(mapFloatToColor(trend));

        // Update the background color to highlight the selected item
        if (position == selectedPosition) {
            // Change the background color to highlight the selected item
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_item_background));
        } else {
            // Reset to default background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setSelected(position == selectedPosition); // Highlight selected item

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position; // Update selected position
            notifyDataSetChanged(); // Notify adapter to refresh the RecyclerView
            ProgressReport activity = (ProgressReport) holder.itemView.getContext();
            activity.updateSelectedGoalProgressBars(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public String getTrendPercentage(float trend){
        return round(trend * 100) + "%";
    }

    public static int mapFloatToColor(float value) {
        value = Math.max(-1, Math.min(1, value));
        if (value < 0) {
            int red = (int) ((1 + value) * 255);
            return Color.rgb(red, 0, 0); // more red, no green or blue
        } else if (value > 0) {
            int green = (int) (55 + value * 200);
            return Color.rgb(0, green, 0); // more green, no red or blue
        } else {
            return Color.rgb(128, 128, 128); // grey
        }
    }
}
