package com.example.nhut.hoclistviewnangcao3;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nhut.adapter.AdapterNhanVien;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtMaNV, txtTenNV;
    RadioButton radNam, radNu;
    Button btnNhapNV;

    ListView lvNhanVien;
    ArrayList<String> listNhanVien;
    AdapterNhanVien adapterNV;

    ImageView imgXoaHet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtMaNV = (EditText) findViewById(R.id.txtMaNV);
        txtTenNV = (EditText) findViewById(R.id.txtTenNV);
        radNam = (RadioButton) findViewById(R.id.rdNam); radNam.setChecked(true);
        radNu = (RadioButton) findViewById(R.id.rdNu); radNu.setChecked(false);
        btnNhapNV = (Button) findViewById(R.id.btnNhapNV);
        imgXoaHet = (ImageView) findViewById(R.id.imgXoaHet);

        lvNhanVien = (ListView) findViewById(R.id.lvNhanVien);
        listNhanVien = new ArrayList<String>();
        adapterNV = new AdapterNhanVien(
                MainActivity.this,
                R.layout.item,
                listNhanVien
        );
        lvNhanVien.setAdapter(adapterNV);
    }

    private void addEvents() {
        btnNhapNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nv = (radNam.isChecked()) ?
                        txtMaNV.getText().toString() + "-" + txtTenNV.getText().toString()
                        + "," + Integer.toString(R.drawable.male) + "," + "false"
                        :
                        txtMaNV.getText().toString() + "-" + txtTenNV.getText().toString()
                                + "," + Integer.toString(R.drawable.female) + "," + "false";
                listNhanVien.add(nv);
                adapterNV.notifyDataSetChanged();
            }
        });

        imgXoaHet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listNhanVien.size(); i++) {
                    if (listNhanVien.get(i).endsWith(",true")) {
                        String nv = listNhanVien.remove(i).replace(",true", ",false");
                        listNhanVien.add(i, nv);
                    }
                }
                adapterNV.notifyDataSetChanged();
            }
        });
    }
}
