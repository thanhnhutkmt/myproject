package com.example.nhut.hoclistviewnangcao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.nhut.adapter.DanhBaAdapter;
import com.example.nhut.model.DanhBa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhBa;
    ArrayList<DanhBa> dsDanhBa;
    DanhBaAdapter danhBaAdapter;
    ImageButton btnCall, btnSMS, btnDetail;

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
        lvDanhBa = (ListView) findViewById(R.id.lvDanhBa);
        dsDanhBa = new ArrayList<DanhBa>();
        dsDanhBa.add(new DanhBa(1, "Nguyễn Văn a", "0901523783"));
        dsDanhBa.add(new DanhBa(2, "Nguyễn Văn b", "0915443408"));
        dsDanhBa.add(new DanhBa(3, "Nguyễn Văn c", "0913425670"));
        dsDanhBa.add(new DanhBa(4, "Nguyễn Văn d", "*101#"));
        danhBaAdapter = new DanhBaAdapter(
                MainActivity.this,
                R.layout.item,
                dsDanhBa
        );
        lvDanhBa.setAdapter(danhBaAdapter);
    }
}
