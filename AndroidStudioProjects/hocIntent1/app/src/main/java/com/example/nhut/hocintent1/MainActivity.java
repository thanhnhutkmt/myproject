package com.example.nhut.hocintent1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyMo1ManHinh();
            }
        });
    }

    public void xuLyMo1ManHinh() {
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(i);
    }
}
