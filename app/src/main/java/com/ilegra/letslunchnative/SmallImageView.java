package com.ilegra.letslunchnative;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SmallImageView extends android.support.v7.widget.AppCompatImageView {
    public SmallImageView(Context context) {
        super(context);
        getLayoutParams().height = 80;
        setScaleType(ScaleType.CENTER_CROP);
        setBackgroundResource(R.color.colorAccent);
    }
}