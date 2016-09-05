package software.nhut.personalutilitiesforlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
    }

    private void addControls() {
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtInfo.setText("Version 1.0\n" +
                "Tác giả : Kỹ sư Lưu Thành Nhựt\n" +
                "All rights reserved 2016");
    }

    public void moCuocHen(View view) {
        Intent i = new Intent(MainActivity.this, QuanLyCuocHenActivity.class);
        startActivity(i);
    }

    public void moKaraoke(View view) {
        Intent i = new Intent(MainActivity.this, KaraokeActivity.class);
        startActivity(i);
    }

    public void moTinNhan(View view) {
        Intent i = new Intent(MainActivity.this, XacThucActivity.class);
        startActivity(i);
    }

    public void moTroChoi(View view) {

    }

    public void moHinhPhim(View view) {

    }

    public void moGhiChu(View view) {

    }

    public void moDongBo(View view) {

    }

    public void moBanDo(View view) {
        Intent i = new Intent(this, BanDoActivity.class);
        startActivity(i);
    }

    public void moDoiTien(View view) {
        Intent i = new Intent(this, TiGiaActivity.class);
        startActivity(i);
    }
}
