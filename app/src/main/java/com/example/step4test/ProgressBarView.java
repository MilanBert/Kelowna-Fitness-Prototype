package com.example.step4test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ProgressBarView extends View {

    private float start, target, current;
    private int barWidth = 150;
    private Paint startPaint, targetPaint, currentPaint;

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        startPaint = new Paint();
        startPaint.setColor(Color.GRAY); // Set color for start value bar
        startPaint.setAntiAlias(true);

        targetPaint = new Paint();
        targetPaint.setColor(Color.GREEN); // Set color for target value bar
        targetPaint.setAntiAlias(true);

        currentPaint = new Paint();
        currentPaint.setColor(Color.rgb(0,200,10)); // Set color for current value bar
        currentPaint.setAntiAlias(true);
    }

    public void setProgressValues(float start, float target, float current) {
        this.start = start;
        this.target = target;
        this.current = current;
        invalidate(); // Redraw the view when values change
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate the maximum height for the bars
        float maxHeight = getHeight();
        float highest = Math.max(start,Math.max(target,current));
        float currentHeight,startHeight,targetHeight;
        // Calculate the height of each bar as a proportion of the max height
        if(start == highest){
           startHeight = maxHeight;
        }else{
          startHeight = (start / highest) * maxHeight;
        }
        if(current == highest){
          currentHeight = maxHeight;
        }else{
           currentHeight = (current / highest) * maxHeight;
        }
        if(target == highest){
            targetHeight = maxHeight;
        }else{
            targetHeight = (target/highest)* maxHeight;
        }


        // Draw the start, target, and current bars
        canvas.drawRoundRect(barWidth * 2 + 20, maxHeight - startHeight, barWidth  * 3 + 20 , maxHeight, 25, 25, startPaint); // Rounded bars
        canvas.drawRoundRect(0, maxHeight - targetHeight, barWidth , maxHeight, 25, 25, targetPaint);
        canvas.drawRoundRect(barWidth + 10, maxHeight - currentHeight, barWidth* 2 + 10, maxHeight, 25, 25, currentPaint);
    }
}
