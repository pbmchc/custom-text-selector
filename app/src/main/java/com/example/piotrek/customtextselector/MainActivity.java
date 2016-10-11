package com.example.piotrek.customtextselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button backgroundBtn;
    Button clickableBtn;
    Button easyBtn;
    Button webBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundBtn = (Button) findViewById(R.id.backgroundBtn);
        clickableBtn = (Button) findViewById(R.id.clickableBtn);
        easyBtn = (Button) findViewById(R.id.easywayBtn);
        webBtn = (Button) findViewById(R.id.webBtn);

        backgroundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });

        clickableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClickableActivity.class);
                startActivity(intent);
            }
        });

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EasyActivity.class);
                startActivity(intent);
            }
        });

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                startActivity(intent);
            }
        });

    }

}
