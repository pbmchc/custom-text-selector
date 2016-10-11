package com.example.piotrek.customtextselector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClickableActivity extends AppCompatActivity {

    ClickableEditText edt;
    Button viewButton;
    Button clearButton;
    Button copyButton;
    TextView txt;
    String selectedWords = "";
    String selectedText = "";
    int start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clickable);
        edt = (ClickableEditText) findViewById(R.id.edtSelect);
        viewButton = (Button) findViewById(R.id.addButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        copyButton = (Button) findViewById(R.id.copyButton);
        txt = (TextView) findViewById(R.id.txtSelect);

        edt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                menu.clear();
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_click, menu);
                start = edt.getSelectionStart();
                end = edt.getSelectionEnd();
                int i = edt.getText().getSpans(start, end, ClickableSpan.class).length;
                if (i != 0)
                {
                    menu.clear();
                    return false;
                }
                else {
                    if (end > start)
                        selectedText = edt.getText().toString().substring(start, end).toLowerCase();
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                CharacterStyle cs;
                switch (menuItem.getItemId()) {

                    case R.id.selectable:
                        start = edt.getSelectionStart();
                        end = edt.getSelectionEnd();
                        cs = new ClickableSpan() {

                            @Override
                            public void onClick(View view) {
                                int start = edt.getText().getSpanStart(this);
                                int end = edt.getText().getSpanEnd(this);
                                selectedText = String.valueOf(edt.getText().subSequence(start,end));
                                selectedWords = selectedWords.replace(selectedText.toLowerCase(),"");
                                edt.getText().removeSpan(this);
                            }
                            @Override
                            public void updateDrawState(TextPaint ds) {
                                ds.bgColor=Color.argb(120,255, 117, 90);
                            }
                        };

                        edt.getText().setSpan(cs, start,end,1);
                        edt.setMovementMethod(LinkMovementMethod.getInstance());
                        selectedText = edt.getText().toString().substring(start, end).toLowerCase();
                        selectedWords += selectedText + " ";
                        actionMode.finish();
                        return true;

                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });


        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClickableActivity.this, selectedWords, Toast.LENGTH_LONG).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedWords = "";
                //problem with disappearing accent color after the clearing
                //edt.getText().clearSpans();
                ClickableSpan[] cspans = edt.getText().getSpans(0, edt.getText().length(), ClickableSpan.class);
                for (ClickableSpan cs : cspans)
                    edt.getText().removeSpan(cs);
                txt.setText("");
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ClickableActivity.this, CheckActivity.class);
                ClickableSpan[] cspans = edt.getText().getSpans(0, edt.getText().length(), ClickableSpan.class);
                SpannableString spannableString = new SpannableString(edt.getText());
                for (ClickableSpan cs : cspans)
                {
                    UnderlineSpan us = new UnderlineSpan();
                    spannableString.setSpan(us, edt.getText().getSpanStart(cs), edt.getText().getSpanEnd(cs), 1);
                    spannableString.removeSpan(cs);
                }
                String s;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    s = Html.toHtml(spannableString, Html.FROM_HTML_MODE_COMPACT);
                } else {
                    s = Html.toHtml(spannableString);
                }
                intent.putExtra("text", s);
                intent.putExtra("flag", 1);
                startActivity(intent);
            }
        });
    }
}
