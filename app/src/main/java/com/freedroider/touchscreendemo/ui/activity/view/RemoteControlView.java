package com.freedroider.touchscreendemo.ui.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RemoteControlView extends View {

    public interface OnRemoteControlListener {
        void onSizeReady(int width, int height);

        void onMove(float angle, double distance);
    }

    private static final float TOUCH_TOLERANCE = 4;

    private final Path circlePath = new Path();
    private final Paint circlePaint = new Paint();
    private final Paint paint = new Paint();
    private final Path path = new Path();
    private float x, y;
    private boolean ready;
    private OnRemoteControlListener controlListener;

    public RemoteControlView(Context context) {
        super(context);
        init();
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RemoteControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawPath(circlePath, circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                up();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ready = true;
        updateSizeReady();
    }

    public void setControlListener(@Nullable OnRemoteControlListener controlListener) {
        this.controlListener = controlListener;
        updateSizeReady();
    }

    private void updateSizeReady() {
        if (controlListener != null && ready) {
            controlListener.onSizeReady(getWidth(), getHeight());
        }
    }

    private void init() {
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);
    }

    private void start(float x, float y) {
        path.reset();
        path.moveTo(x, y);
        this.x = x;
        this.y = y;
    }

    private void move(float x, float y) {
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        float angel = (float) (Math.atan2(y - this.y, x - this.x));
        double distance = Math.hypot(dx, dy);
        if (controlListener != null) {
            controlListener.onMove(angel, distance);
        }
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(this.x, this.y, (x + this.x) / 2, (y + this.y) / 2);
            this.x = x;
            this.y = y;
            circlePath.reset();
            circlePath.addCircle(this.x, this.y, 30, Path.Direction.CW);
        }
    }

    private void up() {
        circlePath.reset();
        path.reset();
    }
}
