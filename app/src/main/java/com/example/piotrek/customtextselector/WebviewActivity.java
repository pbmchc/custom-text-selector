package com.example.piotrek.customtextselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {


    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(getString(R.string.sample_text), "text/html; charset='utf-8'" , "UTF-8");
    }

    private ActionMode mActionMode = null;

    @Override
    public void onActionModeStarted(ActionMode mode) {
        if (mActionMode == null) {
            mActionMode = mode;
            Menu menu = mode.getMenu();
            menu.clear();
            mode.getMenuInflater().inflate(R.menu.menu, menu);
        }
        super.onActionModeStarted(mode);
    }

    public void onContextualMenuItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blue:
                break;
            case R.id.green:
                break;
            case R.id.red:
                break;
            default:
                break;
        }

        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        mActionMode = null;
        super.onActionModeFinished(mode);
    }
}
