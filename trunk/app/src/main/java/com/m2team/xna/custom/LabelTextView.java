package com.m2team.xna.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.m2team.xna.R;
import com.m2team.xna.utils.Common;

/**
 * Created by Hoang Minh on 2/24/2015.
 */
public class LabelTextView extends TextView {

    public LabelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LabelTextView, 0, 0);
        int integer = typedArray.getInteger(R.styleable.LabelTextView_fontStyle, 1);
        initFont(integer);
    }

    private void initFont(int fontStyle) {
        String font = Common.ROBOTO_REGULAR;
        switch (fontStyle) {
            case 0:
                font = Common.ROBOTO_BOLD;
                break;
            case 2:
                font = Common.ROBOTO_LIGHT;
                break;
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Common.FONT_PATH + font);
        setTypeface(tf, 1);
    }
}
