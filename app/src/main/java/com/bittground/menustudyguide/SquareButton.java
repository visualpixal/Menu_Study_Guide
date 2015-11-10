package com.bittground.menustudyguide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


public class SquareButton extends Button {
    public SquareButton(Context context) {
        super(context);
    }



        public SquareButton(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SquareButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
