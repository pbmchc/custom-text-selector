package com.example.piotrek.customtextselector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;

public class CheckActivity extends AppCompatActivity {

    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();

        edt = (EditText) findViewById(R.id.edtShow);
        Spanned spanned;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            spanned = Html.fromHtml((intent.getStringExtra("text")), Html.FROM_HTML_MODE_COMPACT);
        } else {
            spanned = Html.fromHtml((intent.getStringExtra("text")));
        }
        edt.setText(spanned);

        if (intent.getIntExtra("flag",0) == 0) {
            ForegroundColorSpan[] fcspans = edt.getText().getSpans(0, spanned.length(), ForegroundColorSpan.class);
            for (ForegroundColorSpan fcs : fcspans) {

                int color  = fcs.getForegroundColor();
                BackgroundColorSpan cs = new BackgroundColorSpan(Color.argb(80, Color.red(color), Color.green(color), Color.blue(color)));
                edt.getText().setSpan(cs, edt.getText().getSpanStart(fcs), edt.getText().getSpanEnd(fcs), 1);
                edt.getText().removeSpan(fcs);
            }
        }
        else
        {
            UnderlineSpan[] styleSpen = edt.getText().getSpans(0, spanned.length(), UnderlineSpan.class);
            for (UnderlineSpan s : styleSpen)
            {
                ClickableSpan cs = new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        edt.getText().removeSpan(this);
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.bgColor= Color.argb(120,255, 117, 90);
                    }
                };
                edt.getText().setSpan(cs, edt.getText().getSpanStart(s), edt.getText().getSpanEnd(s), 1);
                edt.getText().removeSpan(s);
                edt.setMovementMethod(LinkMovementMethod.getInstance());

            }

        }
    }
}
