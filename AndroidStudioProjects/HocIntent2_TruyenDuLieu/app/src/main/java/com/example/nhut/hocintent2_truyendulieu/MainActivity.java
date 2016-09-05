package com.example.nhut.hocintent2_truyendulieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nhut.model.SanPham;
import com.example.nhut.model.SinhVien;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void xuLyMoVaGuiDuLieu(View view) {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("Kieu_boolean", true);
        intent.putExtra("Kieu_char", 'x');
        intent.putExtra("Kieu_int", 100);
        intent.putExtra("Kieu_double", 15.99);
        intent.putExtra("Kieu_chuoi", "Topica edumall");

        SinhVien topica = new SinhVien(1, "Topica Excellent!");
        intent.putExtra("SINHVIEN", topica);

        startActivity(intent);
    }

    public void xuLyMoVaGuiDuLieubundle(View view) {
        Bundle b = new Bundle();
        b.putInt("X", 111);
        b.putDouble("D", 114.115);
        SanPham coca = new SanPham(1, "Coca co lalala", 2000.0);
        b.putSerializable("sanpham", coca);

        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        intent.putExtra("Bundle", b);

        startActivity(intent);
    }
}
