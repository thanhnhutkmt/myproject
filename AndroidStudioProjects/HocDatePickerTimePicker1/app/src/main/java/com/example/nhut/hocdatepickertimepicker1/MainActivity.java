package com.example.nhut.hocdatepickertimepicker1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nhut.adapter.AdapterLichHen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static java.util.Calendar.MONTH;

public class MainActivity extends AppCompatActivity {

    ListView lvCuocHen;
    ArrayList<String> listCuocHen;
    AdapterLichHen adapterCuocHen;
    ArrayList<String> listLastSelected;

    Button btnLuu, btnXoaHet, btnXoa, btnThoat;
    TextView txtDate, txtTime, txtTenCuocHen, txtGhiChu, txtTuaDanhSach;
    ImageButton btnDate, btnTime;

    Calendar calendar;
    SimpleDateFormat sdfDate, sdfTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        lvCuocHen = (ListView) findViewById(R.id.lvLichHen);
        listCuocHen = new ArrayList<String>();
        listLastSelected = new ArrayList<String>();
        adapterCuocHen = new AdapterLichHen(
            MainActivity.this,
            R.layout.item,
            listCuocHen,
            listLastSelected
        );
        lvCuocHen.setAdapter(adapterCuocHen);

        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnXoaHet = (Button) findViewById(R.id.btnXoaHet);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnThoat = (Button) findViewById(R.id.btnThoat);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTenCuocHen = (TextView) findViewById(R.id.txtTenCuocHen);
        txtGhiChu = (TextView) findViewById(R.id.txtGhiChu);
        txtTuaDanhSach = (TextView) findViewById(R.id.txtTuaDanhSach);

        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnTime = (ImageButton) findViewById(R.id.btnTime);

        calendar = Calendar.getInstance();
        sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        sdfTime = new SimpleDateFormat("HH:mm:ss");

        txtDate.setText(sdfDate.format(calendar.getTime()));
        txtTime.setText(sdfTime.format(calendar.getTime()));
    }

    private void addEvents() {
        txtTuaDanhSach.setText("Danh sách cuộc hẹn - " + listCuocHen.size() + " cuộc hẹn");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ghiChu = txtGhiChu.getText().toString();
                String tenCuocHen = txtTenCuocHen.getText().toString();
                String rep = String.valueOf((char)31);
                tenCuocHen = tenCuocHen.replace(',', (char)31);
                ghiChu = ghiChu.replace(',', (char)31);
                String cuocHen =  tenCuocHen +
                                "," +  ghiChu +
                                "," + txtDate.getText().toString() +
                                "," + txtTime.getText().toString() +
                                "," + Integer.toString(listCuocHen.size());
                listCuocHen.add(listCuocHen.size(), cuocHen);
                txtTuaDanhSach.setText("Danh sách cuộc hẹn - " + listCuocHen.size() + " cuộc hẹn");
                Collections.sort(listCuocHen, new Comparator<String>() {
                    public int compare(String cuocHen1, String cuocHen2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String []thoiGianCuocHen1 = cuocHen1.split(",");
                        String []thoiGianCuocHen2 = cuocHen2.split(",");
                        Date d1 = null;
                        Date d2 = null;
                        try {
                            d1 = sdf.parse(thoiGianCuocHen1[2] + " " + thoiGianCuocHen1[3]);
                            d2 = sdf.parse(thoiGianCuocHen2[2] + " " + thoiGianCuocHen2[3]);
                        } catch (ParseException e) {
                            Toast.makeText(MainActivity.this, "error parse :" + e.getMessage(), Toast.LENGTH_LONG).show();
                            e.getStackTrace();
                        }
                        if (d1 == null || d2 == null) return 0;
                        else return d1.compareTo(d2);
                    }
                });
                adapterCuocHen.notifyDataSetChanged();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listLastSelected.size() <= 0) {
                    Toast.makeText(MainActivity.this, "Bạn chưa chọn cuộc hẹn nào để xóa", Toast.LENGTH_SHORT).show();
                } else {
                    String lastSelected = listLastSelected.remove(0);
                    for (int i = 0; i < listCuocHen.size(); i++) {
                        if (listCuocHen.get(i).equals(lastSelected)) {
                            listCuocHen.remove(i);
                            adapterCuocHen.notifyDataSetChanged();
                            break;
                        }
                    }
                    txtTuaDanhSach.setText("Danh sách cuộc hẹn - " + listCuocHen.size() + " cuộc hẹn");
                }
            }
        });

        btnXoaHet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listCuocHen.size() > 0) {
                    listCuocHen.clear();
                    txtTuaDanhSach.setText("Danh sách cuộc hẹn - " + listCuocHen.size() + " cuộc hẹn");
                    adapterCuocHen.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Danh sách cuộc hẹn trống rỗng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        calendar.set(year, monthOfYear, dayOfMonth);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        txtDate.setText(sdfDate.format(calendar.getTime()));
                    }
                };
                DatePickerDialog dpd = new DatePickerDialog(
                    MainActivity.this,
                    callBack,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        txtTime.setText(sdfTime.format(calendar.getTime()));
                    }
                };
                TimePickerDialog tpd = new TimePickerDialog(
                    MainActivity.this,
                    callBack,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                );
                tpd.show();
            }
        });
    }
}
