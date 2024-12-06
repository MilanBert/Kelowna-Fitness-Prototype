package com.example.step4test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

public class ProgressBarView extends View {

    private float start, target, current;
    private int barWidth = 300;
    private Paint startPaint, targetPaint, currentPaint;
    private int[] previousHeight = {50, 50, 50}; // Previous heights for smooth transition
    private static final long FRAME_DURATION = 1000 / 30; // 30 FPS -> ~33ms

    private final Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            // Invalidate the view to trigger onDraw
            invalidate();
            // Post the next frame at 30 FPS (30 frames per second)
            Choreographer.getInstance().postFrameCallbackDelayed(this, FRAME_DURATION);
        }
    };

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
        currentPaint.setColor(Color.rgb(0, 200, 10)); // Set color for current value bar
        currentPaint.setAntiAlias(true);

        // Start the frame callback to begin drawing at 30 FPS
        Choreographer.getInstance().postFrameCallback(frameCallback);
    }

    public void setProgressValues(float start, float target, float current) {
        this.start = start;
        this.target = target;
        this.current = current;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate the maximum height for the bars
        float maxHeight = getHeight();
        float highest = Math.max(start, Math.max(target, current));
        float currentHeight, startHeight, targetHeight;

        // Calculate the height of each bar as a proportion of the max height
        startHeight =  (start == highest) ? maxHeight : 60+(start / highest) * maxHeight;
        currentHeight = (current == highest) ? maxHeight : (current / highest) * maxHeight;
        targetHeight = (target == highest) ? maxHeight : (target / highest) * maxHeight;

        // Draw the bars with rounded corners
        canvas.drawRoundRect(barWidth * 2 - 30, maxHeight - lerpHeight(targetHeight, 0),barWidth * 3 - 30 , maxHeight, 25, 25, targetPaint);
        canvas.drawRoundRect(barWidth - 20, maxHeight - lerpHeight(currentHeight, 1), barWidth * 2 - 20, maxHeight, 25, 25, currentPaint);
        canvas.drawRoundRect(0, maxHeight - lerpHeight(startHeight, 2),barWidth, maxHeight, 25, 25, startPaint);
    }

    public float lerpHeight(float value, int index) {
        float output = (previousHeight[index] + value) / 2;
        previousHeight[index] = (int) output;
        return output;
    }

    // Optionally, add a stop method to stop the frame callback when the view is no longer needed (e.g., when it's removed from the layout)
    public void stopFrameUpdates() {
        Choreographer.getInstance().removeFrameCallback(frameCallback);
    }
}
