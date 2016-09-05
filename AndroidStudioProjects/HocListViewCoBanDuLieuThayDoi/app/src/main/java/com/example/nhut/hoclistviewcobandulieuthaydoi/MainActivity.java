package com.example.nhut.hoclistviewcobandulieuthaydoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrTen;
    ArrayAdapter<String> adapterTen;
    ListView lvTen;

    Button btnLuu;
    EditText txtTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuuDuLieu();
            }
        });
        txtTen.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                xuLyLuuDuLieu();
                return true;
            }
            return false;
            }
        });
    }

    private void xuLyLuuDuLieu() {
        String ten = txtTen.getText().toString();
        if(ten != null && ten.trim().length() > 0) {
            arrTen.add(ten); //Them du lieu moi
            adapterTen.notifyDataSetChanged(); //Ra lenh listview cap nhat lai giao dien
            txtTen.setText("");
            txtTen.requestFocus();
        }
    }

    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        txtTen = (EditText) findViewById(R.id.txtTen);
        arrTen = new ArrayList<String>();
        lvTen = (ListView) findViewById(R.id.lvTen);
        adapterTen = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrTen
        );
        lvTen.setAdapter(adapterTen);
    }
}
