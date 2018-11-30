package com.jdjr.courtcanteen.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.jdjr.courtcanteen.utils.TimeUtil;

import java.io.File;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class TimeTextView extends AppCompatTextView {
    private static final String FONT_DIGITAL_7;
    private Context context;
    private Paint paint;

    static {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("fonts");
        localStringBuilder.append(File.separator);
        localStringBuilder.append("digital-7.ttf");
        FONT_DIGITAL_7 = localStringBuilder.toString();
    }

    public TimeTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        this.context = paramContext;
        this.paint = new Paint();
        setTypeface(Typeface.createFromAsset(paramContext.getAssets(), FONT_DIGITAL_7));
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        setText(TimeUtil.getTimeString());
        invalidate();
    }
}
