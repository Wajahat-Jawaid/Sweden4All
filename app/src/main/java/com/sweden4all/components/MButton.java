package com.sweden4all.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.sweden4all.R;

public class MButton extends android.support.v7.widget.AppCompatButton {

    public MButton(Context context) {
        super(context);
        init(context, null);
    }

    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MTextView);

            try {
                int font = a.getInt(R.styleable.MTextView_fontName, 9);
                if (font != -1) {
                    applyCustomFont(context, font);
                }
            } finally {
                a.recycle();
            }
        } else {
            applyCustomFont(context, 9);
        }
    }

    private void applyCustomFont(Context context, int fontIndex) {
        Typeface customFont = FontManager.getTypeface(context, fontIndex);
        setTypeface(customFont);
    }
}
