package com.recoder.medibox;

import android.content.Context;
import android.util.AttributeSet;

public class F_SquareButton extends android.support.v7.widget.AppCompatImageButton {

    public F_SquareButton(Context context) {
        super(context);
    }

    public F_SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public F_SquareButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        width = Math.min(width, height);
        height = width;
        setMeasuredDimension(width, height);
    }
}