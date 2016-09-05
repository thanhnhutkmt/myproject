package com.example.nhut.hocintent2_truyendulieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    TextView txtKetQuaBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        addControls();
    }

    private void addControls() {
        txtKetQuaBundle = (TextView) findViewById(R.id.txtKetQuaBundle);
        Bundle b = getIntent().getBundleExtra("Bundle");

        txtKetQuaBundle.setText(
                "X = " + b.getInt("X") + "\n" +
                "D = " + b.getDouble("D") + "\n" +
                "SP = " + b.getSerializable("sanpham")
        );
    }
}
