package com.example.nhut.hoctabhost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabHost tabHost;

    Button btnCong, btnTru;
    EditText txtA, txtB, txtA1, txtB1;

    ListView lvLichSu;
    ArrayList<String> listLichSu;
    ArrayAdapter adapterLichSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCongTru(2);
            }
        });
        btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCongTru(1);
            }
        });
    }

    private void xuLyCongTru(int phepTinh) {
        String s = "";
        if(phepTinh == 1) {
            double a = Double.parseDouble(txtA1.getText().toString());
            double b = Double.parseDouble(txtB1.getText().toString());
            txtA1.setText("");
            txtB1.setText("");
            s = a + " - " + b + " = " + (a-b);
        } else if (phepTinh == 2) {
            double a = Double.parseDouble(txtA.getText().toString());
            double b = Double.parseDouble(txtB.getText().toString());
            txtA.setText("");
            txtB.setText("");
            s = a + " + " + b + " = " + (a+b);
        }
        listLichSu.add(s);
        adapterLichSu.notifyDataSetChanged();
    }

    private void addControls() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.plus1));
        tab1.setContent(R.id.tabCong);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.minus));
        tab2.setContent(R.id.tabTru);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("3.Lịch Sử");
        tab3.setContent(R.id.tabLichSu);
        tabHost.addTab(tab3);

        btnCong = (Button) findViewById(R.id.btnCong);
        btnTru = (Button) findViewById(R.id.btnTru);
        txtA = (EditText) findViewById(R.id.txtA);
        txtB = (EditText) findViewById(R.id.txtB);
        txtA1 = (EditText) findViewById(R.id.txtA1);
        txtB1 = (EditText) findViewById(R.id.txtB1);

        lvLichSu = (ListView) findViewById(R.id.lvLichSu);
        listLichSu = new ArrayList<String>();
        adapterLichSu = new ArrayAdapter(
            MainActivity.this,
            android.R.layout.simple_list_item_1,
            listLichSu
        );
        lvLichSu.setAdapter(adapterLichSu);
    }
}
