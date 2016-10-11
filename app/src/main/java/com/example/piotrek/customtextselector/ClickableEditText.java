package com.example.piotrek.customtextselector;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Piotrek on 2016-10-09.
 */
public class ClickableEditText extends EditText {


    public ClickableEditText(final Context context) {
        super(context);
    }

    public ClickableEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickableEditText(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int startSelection = getSelectionStart();
        int endSelection = getSelectionEnd();
        if (startSelection != endSelection) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                final CharSequence text = getText();
                setText(null);
                setText(text);
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
