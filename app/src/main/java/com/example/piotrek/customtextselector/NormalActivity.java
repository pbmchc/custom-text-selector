package com.example.piotrek.customtextselector;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NormalActivity extends AppCompatActivity {

    EditText edt;
    Button viewButton;
    Button clearButton;
    Button copyButton;
    String selectedWords = "";
    String selectedText = "";
    int start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        edt = (EditText) findViewById(R.id.edtSelect);
        viewButton = (Button) findViewById(R.id.addButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        copyButton = (Button) findViewById(R.id.copyButton);
        
        edt.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                menu.clear();
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu, menu);
                start = edt.getSelectionStart();
                end = edt.getSelectionEnd();
                selectedText = edt.getText().toString().substring(start,end).toLowerCase();
                BackgroundColorSpan[] bcs = edt.getText().getSpans(start, end, BackgroundColorSpan.class);
                int i = bcs.length;
                if (i==0) {
                    menu.removeItem(R.id.remove);
                }
                else {
                    int spanEnd = edt.getText().getSpanEnd(bcs[0]);
                    int spanStart = edt.getText().getSpanStart(bcs[0]);
                    edt.setSelection(spanStart, spanEnd);
                    if (bcs[0].getBackgroundColor() == Color.argb(80, 255, 148, 130))
                        menu.removeItem(R.id.red);
                    else if (bcs[0].getBackgroundColor() == Color.argb(80, 84, 126, 204))
                        menu.removeItem(R.id.blue);
                    else
                        menu.removeItem(R.id.green);
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                CharacterStyle cs;
                int spanEnd=0;
                int spanStart=0;
                BackgroundColorSpan[] bcs = edt.getText().getSpans(edt.getSelectionStart(), edt.getSelectionEnd(), BackgroundColorSpan.class);
                if (bcs.length != 0)
                {
                    spanEnd = edt.getText().getSpanEnd(bcs[0]);
                    spanStart = edt.getText().getSpanStart(bcs[0]);
                }
                start = edt.getSelectionStart();
                end = edt.getSelectionEnd();

                switch (menuItem.getItemId()) {

                    case R.id.remove:
                        String fullSelection = edt.getText().toString().substring(spanStart, spanEnd).toLowerCase();
                        edt.getText().removeSpan(bcs[0]);
                        if (selectedWords.contains(fullSelection))
                            selectedWords = selectedWords.replace(fullSelection + ";", "");
                        actionMode.finish();
                        return true;

                    case R.id.red:
                        cs = new BackgroundColorSpan(Color.argb(80, 255, 148, 130));
                        if (bcs.length != 0)
                        {
                            edt.getText().removeSpan(bcs[0]);
                            edt.getText().setSpan(cs, spanStart,spanEnd, 1);
                        }
                        else{
                            selectedText = edt.getText().toString().substring(start, end).toLowerCase();
                            selectedWords += selectedText + ";";
                            edt.getText().setSpan(cs, start,end, 1);
                        }
                        actionMode.finish();
                        return true;
                    case R.id.blue:
                        cs = new BackgroundColorSpan(Color.argb(80, 84, 126, 204));
                        if (bcs.length != 0)
                        {
                            edt.getText().removeSpan(bcs[0]);
                            edt.getText().setSpan(cs, spanStart,spanEnd, 1);
                        }
                        else{
                            selectedText = edt.getText().toString().substring(start, end).toLowerCase();
                            selectedWords += selectedText + ";";
                            edt.getText().setSpan(cs, start, end, 1);
                        }
                        actionMode.finish();
                        return true;
                    case R.id.green:
                        cs = new BackgroundColorSpan(Color.argb(80, 55, 178, 125));
                        if (bcs.length != 0)
                        {
                            edt.getText().removeSpan(bcs[0]);
                            edt.getText().setSpan(cs, spanStart,spanEnd, 1);
                        }
                        else{
                            selectedText = edt.getText().toString().substring(start, end).toLowerCase();
                            selectedWords += selectedText + ";";
                            edt.getText().setSpan(cs, start,end, 1);
                        }
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
                Toast.makeText(NormalActivity.this, selectedWords, Toast.LENGTH_LONG).show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedWords = "";
                //problem with disappearing accent color after the clearing
                //edt.getText().clearSpans();
                BackgroundColorSpan[] spans = edt.getText().getSpans(0, edt.getText().length(), BackgroundColorSpan.class);
                for (BackgroundColorSpan bcs : spans)
                    edt.getText().removeSpan(bcs);
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NormalActivity.this, CheckActivity.class);
                BackgroundColorSpan[] bcspans = edt.getText().getSpans(0, edt.getText().length(), BackgroundColorSpan.class);
                SpannableString spannableString = new SpannableString(edt.getText());
                for (BackgroundColorSpan bcs : bcspans)
                {
                    ForegroundColorSpan fcs = new ForegroundColorSpan(bcs.getBackgroundColor());
                    spannableString.setSpan(fcs, edt.getText().getSpanStart(bcs), edt.getText().getSpanEnd(bcs), 1);
                    spannableString.removeSpan(bcs);
                }
                String s;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    s = Html.toHtml(spannableString, Html.FROM_HTML_MODE_COMPACT);
                } else {
                    s = Html.toHtml(spannableString);
                }
                intent.putExtra("text", s);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }
}
