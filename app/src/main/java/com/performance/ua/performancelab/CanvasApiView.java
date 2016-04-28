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

    private final int N = 4;
    private final int shift = 50;
    private Paint myPaint = new Paint();
    private final int MARGIN = 100;
    private final int SIZE = MARGIN + 400;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cardX;
        int shiftI;
        for (int i = 0; i < N; i++) {
            // Each card is laid out a little to the right of the previous one.
            myPaint.setColor(Color.RED / (i + 1));
            myPaint.setStrokeWidth(10);
            canvas.save();
            shiftI = i * shift;
            cardX =  shiftI + MARGIN;
            if (i != N - 1) {
                canvas.clipRect(cardX, MARGIN, cardX + shift, SIZE);
            }
            canvas.drawRect(cardX, MARGIN, SIZE + shiftI, SIZE, myPaint);
            //Or just draw needed rectangles
            canvas.restore();
        }
        // Invalidate the whole view. Doing this calls onDraw() if the view is visible.
        invalidate();
    }

}
