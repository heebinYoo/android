package com.heebin.smartroute.view.custiomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.heebin.smartroute.R;
import com.heebin.smartroute.util.constants.NomalConstants;


public class ElementView extends View {

    private int mType= NomalConstants.station;

    public ElementView(Context context) {
        super(context);
    }
    public ElementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ElementView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ElementView, defStyle, 0);
        mType = a.getInt(
                R.styleable.ElementView_type,
                mType);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int contentWidth = getWidth();
        int contentHeight = getHeight();

        RectF rect = new RectF(0,0 , contentWidth, contentHeight);
        Paint paint = new Paint();

        switch (this.mType){
            case NomalConstants.startStation: {
                paint.setAntiAlias(true);
                paint.setColor(Color.RED);
                canvas.drawArc(rect, 150, 210, false, paint);
                break;
            }
            case NomalConstants.station: {
                paint.setAntiAlias(true);
                paint.setColor(Color.GRAY);
                canvas.drawRect(rect, paint);
                break;
            }
            case NomalConstants.endStation: {
                paint.setAntiAlias(true);
                paint.setColor(Color.BLUE);
                canvas.drawArc(rect, 150, 210, false, paint);
                break;
            }
            case NomalConstants.bus: {
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                canvas.drawArc(rect, 0, 360, false, paint);
                break;
            }
            case NomalConstants.user: {
                paint.setAntiAlias(true);
                paint.setColor(Color.GREEN);
                canvas.drawArc(rect, 0, 360, false, paint);
                break;
            }
        }



    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
        invalidate();
    }
}
