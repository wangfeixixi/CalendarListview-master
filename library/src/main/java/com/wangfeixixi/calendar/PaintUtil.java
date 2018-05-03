package com.wangfeixixi.calendar;

import android.graphics.Paint;
import android.support.annotation.ColorInt;

/**
 * Created by xuany on 2018/5/3.
 */

public class PaintUtil {
    //圆环
    public static Paint getCircleBg(@ColorInt int color) {
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        return paint;
    }

}
