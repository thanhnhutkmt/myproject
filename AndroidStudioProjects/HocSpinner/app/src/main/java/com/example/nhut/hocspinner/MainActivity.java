package com.example.nhut.hocspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nhut.model.NhanVien;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtTen, txtSoNgay;
    Button btnXacNhan;

    Spinner spThu;
    ArrayList<String> dsThu;
    ArrayAdapter<String> adapterThu;

    ListView lvCongTac;
    ArrayList<String> arrCongTac;
    ArrayAdapter<String> adapterCongTac;

    int lastSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXacNhanCongTac();
            }
        });
        spThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Bạn chọn " + dsThu.get(position), Toast.LENGTH_LONG).show();
                lastSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void xuLyXacNhanCongTac() {
        if(lastSelected == -1) {
            Toast.makeText(MainActivity.this, "Bạn chưa chọn thứ", Toast.LENGTH_LONG).show();
            return;
        }
        NhanVien nv = new NhanVien(txtTen.getText().toString(),
                dsThu.get(lastSelected),
                Integer.parseInt(txtSoNgay.getText().toString()));
        Toast.makeText(MainActivity.this, nv.toString(),Toast.LENGTH_LONG).show();

        arrCongTac.add(nv.toString());
        adapterThu.notifyDataSetChanged();
    }

    private void addControls() {
        txtTen = (EditText) findViewById(R.id.txtTen);
        txtSoNgay = (EditText) findViewById(R.id.txtSoNgay);
        btnXacNhan = (Button) findViewById(R.id.btnXacNhan);

        spThu = (Spinner) findViewById(R.id.spThu);
        dsThu = new ArrayList<>();
        dsThu.add("Thứ 2"); dsThu.add("Thứ 3"); dsThu.add("Thứ 4");
        dsThu.add("Thứ 5"); dsThu.add("Thứ 6"); dsThu.add("Thứ 7");
        dsThu.add("Chủ Nhật");
        adapterThu = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsThu
        );
        adapterThu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThu.setAdapter(adapterThu);

        lvCongTac = (ListView) findViewById(R.id.lvCongTac);
        arrCongTac = new ArrayList<String>();
        adapterThu = new ArrayAdapter<String>(
            MainActivity.this,
            android.R.layout.simple_list_item_1,
            arrCongTac
        );
        lvCongTac.setAdapter(adapterThu);
    }
}
