package com.example.nhut.hocautocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String []arrTinhThanh;
    ArrayAdapter<String> adapterTinhThanh;
    AutoCompleteTextView autoTinhThanh;

    Button btnXacNhan;
    EditText txtTen;
    TextView txtKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXacNhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = txtTen.getText().toString() + " - " + autoTinhThanh.getText().toString();
                txtKetQua.setText(s);
            }
        });
    }

    private void addControls() {
        btnXacNhan = (Button) findViewById(R.id.btnXacNhan);
        txtTen = (EditText) findViewById(R.id.txtTen);
        txtKetQua = (TextView) findViewById(R.id.txtKetQua);
        arrTinhThanh = getResources().getStringArray(R.array.arrTinhThanh);
        autoTinhThanh = (AutoCompleteTextView) findViewById(R.id.autoTinhThanh);
        adapterTinhThanh = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrTinhThanh
        );
        autoTinhThanh.setAdapter(adapterTinhThanh);
    }
}
