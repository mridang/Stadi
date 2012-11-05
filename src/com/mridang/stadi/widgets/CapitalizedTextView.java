package com.mridang.stadi.widgets;

import android.content.Context; 
import android.util.AttributeSet; 
import android.view.ViewTreeObserver; 
import android.widget.TextView;

public class CapitalizedTextView extends TextView implements ViewTreeObserver.OnPreDrawListener {

    /*
     * @see android.widget.TextView#TextView(android.content.Context)
     */
    public CapitalizedTextView(Context context) {
        super(context);
    }

    /*
     * @see android.widget.TextView#TextView(android.content.Context, android.util.AttributeSet)
     */
    public CapitalizedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * @see android.widget.TextView#TextView(android.content.Context, android.util.AttributeSet, int)
     */
    public CapitalizedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*
     * @see android.widget.TextView#setText(java.lang.CharSequence, android.widget.TextView.BufferType)
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().toUpperCase(), type);
    }

}