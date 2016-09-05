package com.example.nhut.hocdatepickertimepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageButton btnDate, btnTime;
    TextView txtDate, txtTime;
    Calendar calendar;
    SimpleDateFormat sdfDate, sdfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDatePicker();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTimePicker();
            }
        });
    }

    private void xuLyTimePicker() {
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

    private void xuLyDatePicker() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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

    private void addControls() {
        btnDate = (ImageButton) findViewById(R.id.btnDate);
        btnTime = (ImageButton) findViewById(R.id.btnTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);

        calendar = Calendar.getInstance();
        sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        sdfTime = new SimpleDateFormat("HH:mm:ss");

        txtDate.setText(sdfDate.format(calendar.getTime()));
        txtTime.setText(sdfTime.format(calendar.getTime()));

    }
}
