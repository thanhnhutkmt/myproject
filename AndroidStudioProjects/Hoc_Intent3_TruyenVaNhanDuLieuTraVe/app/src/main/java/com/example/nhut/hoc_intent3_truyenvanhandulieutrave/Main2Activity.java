package com.example.nhut.hoc_intent3_truyenvanhandulieutrave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView txtAB;
    Button btnTinhUSCLN;
    int a, b;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnTinhUSCLN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyUSCLN();
            }
        });
    }

    private void xuLyUSCLN() {
        i.putExtra("uscln", TinhUSCLN());
        setResult(100, i);
        finish();
    }

    private int TinhUSCLN() {
        int ia = Math.abs(a);
        int ib = Math.abs(b);
        if (ia == 0 || ib == 0) return ia + ib;
        while(ia != ib) {
            if(ia > ib)
                ia = ia - ib;
            else
                ib = ib - ia;
        }
        return ia;
    }

    private void addControls() {
        txtAB = (TextView) findViewById(R.id.txtAB);
        btnTinhUSCLN = (Button) findViewById(R.id.btnTinhUSCLN);
        i = getIntent();
        a = i.getIntExtra("soa", 0);
        b = i.getIntExtra("sob", 0);
        String ab = "a = " + a + "; b = " + b;
        txtAB.setText(ab);
    }
}
