package com.performance.ua.performancelab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sergey on 4/22/16.
 */
public class CanvasApiView extends View {

    public CanvasApiView(Context context) {
        super(context);
    }

    public CanvasApiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasApiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint myPaint = new Paint();
    private final int MARGIN = 100;
    private final int SIZE = MARGIN + 400;
    private static final int STROKE_WIDTH = 10;

    private final int N = 4;
    private final int shift = 50;
    private int leftMargin;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        alternative(canvas);
        // Invalidate the whole view. Doing this calls onDraw() if the view is visible.
        invalidate();
    }

    private void alternative(Canvas canvas) {
        for (int i = 0; i < N; i++) {
            canvas.save();

            myPaint.setColor(Color.RED / (i + 1));
            myPaint.setStrokeWidth(STROKE_WIDTH);

            leftMargin = MARGIN + i * shift;

            if (i != N - 1) {
                canvas.clipRect(leftMargin, MARGIN, leftMargin + shift, SIZE);
            }

            canvas.drawRect(MARGIN + i * shift, MARGIN, SIZE + i * shift, SIZE, myPaint);
            canvas.restore();
        }
    }


}
