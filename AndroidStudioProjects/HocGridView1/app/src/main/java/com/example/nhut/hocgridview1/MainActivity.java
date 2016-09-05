package com.example.nhut.hocgridview1;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.nhut.adapter.AdapterHoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gvHoa;
    ArrayList<Integer> listHinh;
    AdapterHoa adapterHoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        gvHoa = (GridView) findViewById(R.id.gvHoa);
        listHinh = new ArrayList<Integer>();
        listHinh.add(R.drawable.hinh1); listHinh.add(R.drawable.hinh2); listHinh.add(R.drawable.hinh3);
        listHinh.add(R.drawable.hinh4); listHinh.add(R.drawable.hinh5); listHinh.add(R.drawable.hinh6);
        listHinh.add(R.drawable.hinh7); listHinh.add(R.drawable.hinh8); listHinh.add(R.drawable.hinh9);
        listHinh.add(R.drawable.hinh10); listHinh.add(R.drawable.hinh11); listHinh.add(R.drawable.hinh12);
        adapterHoa = new AdapterHoa(
            MainActivity.this,
            R.layout.item,
            listHinh
        );
        gvHoa.setAdapter(adapterHoa);
    }
}
