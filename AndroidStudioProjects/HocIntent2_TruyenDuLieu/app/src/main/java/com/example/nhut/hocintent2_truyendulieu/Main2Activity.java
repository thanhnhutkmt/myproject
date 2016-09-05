package com.example.nhut.hocintent2_truyendulieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nhut.model.SinhVien;

public class Main2Activity extends AppCompatActivity {

    TextView txtKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addControls();
    }

    private void addControls() {
        txtKetQua = (TextView) findViewById(R.id.txtKetQua);
        Intent i = getIntent();
        boolean kieuBoolean = i.getBooleanExtra("Kieu_boolean", false);
        char kieuChar = i.getCharExtra("Kieu_char", 'w');
        int kieuInt = i.getIntExtra("Kieu_int", 0);
        double kieuDouble = i.getDoubleExtra("Kieu_double", 0.0);
        String kieuChuoi = i.getStringExtra("Kieu_chuoi");

        SinhVien sv = (SinhVien) i.getSerializableExtra("SINHVIEN");

        txtKetQua.setText(  "Kiểu boolean " + kieuBoolean + "\n" +
                            "Kiểu char " + kieuChar + "\n" +
                            "Kiểu int " + kieuInt + "\n" +
                            "Kiểu double " + kieuDouble + "\n" +
                            "Kiểu string " + kieuChuoi + "\n" +
                            "Kiểu đối tượng " + sv
        );

//        intent.putExtra("Kieu_boolean", true);
//        intent.putExtra("Kieu_charn", 'x');
//        intent.putExtra("Kieu_int", 100);
//        intent.putExtra("Kieu_double", 15.99);
//        intent.putExtra("Kieu_chuoi", "Topica edumall");
    }
}
