package com.example.nhut.hocsqlite;

import android.app.Activity;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhut.util.copyDBUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String DBNAME = "dbContact.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(
            MainActivity.this,
            copyDBUtil.copyDBFromAssets(this),
            Toast.LENGTH_LONG)
        .show();
    }
}
