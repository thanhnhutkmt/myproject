package com.example.nhut.hoclistviewnangcao2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nhut.Adapter.AdapterGiaCa;

public class MainActivity extends AppCompatActivity {

    ListView lvGia;
//    int []hinh;
//    String []gia;
    AdapterGiaCa giaCaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        int []hinh = {R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4,
                R.drawable.h5, R.drawable.h6, R.drawable.h7, R.drawable.h8,
                R.drawable.h9, R.drawable.h10, R.drawable.h11, R.drawable.h12,
                R.drawable.h13, R.drawable.h14, R.drawable.h15, R.drawable.h16
               };
        String []gia = {"1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345",
                "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345",
                "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345",
                "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345", "1234,4567,9101,2345",
              };
        lvGia = (ListView) findViewById(R.id.lvGiaCa);
        giaCaAdapter = new AdapterGiaCa(
            MainActivity.this,
            R.layout.item,
            gia,
            hinh
        );
        lvGia.setAdapter(giaCaAdapter);
    }

    private void addEvents() {

    }
}
