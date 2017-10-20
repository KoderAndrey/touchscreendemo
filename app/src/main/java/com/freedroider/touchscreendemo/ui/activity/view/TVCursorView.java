package com.freedroider.touchscreendemo.ui.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.utils.ImageUtils;
import com.freedroider.touchscreendemo.utils.SystemUtils;

public class TVCursorView extends View {
    private static final float SIZE = 8f;

    private final Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private float x, y;
    private boolean ready;
    private int controlWidth = -1;
    private int controlHeight = -1;
    private float widthRatio = 1.0f;
    private float heightRatio = 1.0f;
    private float top, bottom, start, end;

    public TVCursorView(Context context) {
        super(context);
        init();
    }

    public TVCursorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TVCursorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        bitmapWidth = (int) SystemUtils.convertDpToPixel(getContext(), SIZE);
        bitmapHeight = (int) SystemUtils.convertDpToPixel(getContext(), SIZE);
        bitmap = ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.cursor,
                bitmapWidth, bitmapHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ready = true;
        x = (float) (getWidth() / 2) - (float) (bitmapWidth);
        y = (float) (getHeight() / 2) - (float) (bitmapHeight / 2);
        this.top = - bitmap.getHeight() / 2;
        this.start = -bitmap.getWidth() / 2;
        this.bottom = getWidth() - bitmap.getHeight() / 2;
        this.end = getHeight() - bitmap.getHeight() / 2;
        calculateRatio();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public void calculateRatio(int width, int height) {
        controlWidth = width;
        controlHeight = height;
        if (ready) {
            calculateRatio();
        }
    }

    public void move(float angle, double distance) {
        x = (float) (x + Math.cos(angle) * distance * widthRatio);
        y = (float) (y + Math.sin(angle) * distance * heightRatio);
        y = y > top ? (y > bottom ? bottom : y) : top;
        x = x > start ? (x > end ? end : x) : start ;
        invalidate();
    }

    private void calculateRatio() {
        if (controlWidth == -1 && controlHeight == -1) return;
        final int width = getWidth();
        final int height = getHeight();
        widthRatio = (float) width / controlWidth;
        heightRatio = (float) height / controlHeight;
    }
}
