package com.sweden4all.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.sweden4all.R;

public class MTextView extends android.support.v7.widget.AppCompatTextView {

    public MTextView(Context context) {
        super(context);
        init(context, null);
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public MTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs);
//    }

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

    public void showViewWithAnimation() {
        this.setAlpha(0);
        this.setVisibility(View.VISIBLE);
        this.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);


                    }
                });
    }

    public void hideViewWithAnimation() {

        this.animate()
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        MTextView.this.setVisibility(View.GONE);
                    }
                });
    }

}
