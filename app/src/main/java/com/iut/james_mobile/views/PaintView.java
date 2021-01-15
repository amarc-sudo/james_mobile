package com.iut.james_mobile.dessiner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class PaintView extends View {

    public ViewGroup.LayoutParams params;
    private Path path = new Path();
    private Paint brush = new Paint();
    private Boolean reset = false;

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        brush.setAntiAlias(true);
        brush.setColor(Color.BLACK);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float pointX = motionEvent.getX();
        float pointY = motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }
        postInvalidate();
        return false;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, brush);
        if (reset) {
            path = new Path();
            canvas.drawColor(Color.WHITE);
            //canvas.restore();
            reset = false;
        }
    }

    public void resetSignature() {
        reset = true;
        this.postInvalidate();
    }


}
