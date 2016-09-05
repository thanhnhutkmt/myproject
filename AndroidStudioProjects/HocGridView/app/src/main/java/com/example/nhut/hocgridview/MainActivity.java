package com.example.nhut.hocgridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.nhut.adapter.HinhAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gvHinh;
    List<Integer> arrHinh;
    HinhAdapter adapterHinh;

    Button btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void addControls() {
        btnThoat = (Button) findViewById(R.id.btnExit);
        gvHinh = (GridView) findViewById(R.id.gvHinh);
        arrHinh = new ArrayList<Integer>();
        arrHinh.add(R.drawable.hinh1); arrHinh.add(R.drawable.hinh2);
        arrHinh.add(R.drawable.hinh3); arrHinh.add(R.drawable.hinh4);
        arrHinh.add(R.drawable.hinh5); arrHinh.add(R.drawable.hinh6);
        arrHinh.add(R.drawable.hinh7); arrHinh.add(R.drawable.hinh8);
        arrHinh.add(R.drawable.hinh9); arrHinh.add(R.drawable.hinh10);
        arrHinh.add(R.drawable.hinh11); arrHinh.add(R.drawable.hinh12);
        adapterHinh = new HinhAdapter(
                MainActivity.this,
                R.layout.item,
                arrHinh
        );
        gvHinh.setAdapter(adapterHinh);
    }
}
