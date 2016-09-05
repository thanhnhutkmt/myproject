package com.example.nhut.hoc_intent3_truyenvanhandulieutrave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnXuLy;
    EditText txtA, txtB;
    TextView txtUSCLN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(txtA.getText().toString());
                int b = Integer.parseInt(txtB.getText().toString());
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("soa", a);
                i.putExtra("sob", b);
                startActivityForResult(i, 99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == 100) {
            int uscln = data.getIntExtra("uscln", -1);
            txtUSCLN.setText("USCLN là " + uscln);
        }
    }

    private void addControls() {
        btnXuLy = (Button) findViewById(R.id.btnXuLy);
        txtA = (EditText) findViewById(R.id.txtA);
        txtB = (EditText) findViewById(R.id.txtB);
        txtUSCLN = (TextView) findViewById(R.id.txtUSCLN);
    }
}
