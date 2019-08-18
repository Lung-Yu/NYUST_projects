package com.developer.lungyu.ncyu_agricultural.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lungyu on 2016/11/14.
 */

public class UIPosition extends View {
    private final Paint paint;
    private static final long ANIMATION_DELAY = 500;

    private static final int VIEW_RANGE = 0;

    public UIPosition(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();

        //Resources resources = getResources();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect frame = CameraPreview.getInstance().GetFramingRectline();

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left + VIEW_RANGE, frame.bottom + VIEW_RANGE, paint);
        canvas.drawRect(frame.right - VIEW_RANGE, frame.top, width, frame.bottom + VIEW_RANGE, paint);
        canvas.drawRect(0, frame.bottom + VIEW_RANGE, width, height, paint);


        //postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }
}
