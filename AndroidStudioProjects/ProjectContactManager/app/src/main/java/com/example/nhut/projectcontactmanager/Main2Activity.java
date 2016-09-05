package com.example.nhut.projectcontactmanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    TextView txtThongTin;
    EditText txtNoiDung;
    ImageButton btnNhanTin;
    String ten, soPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtThongTin = (TextView) findViewById(R.id.txtNguoiNhan);
        txtNoiDung = (EditText) findViewById(R.id.txtNoiDung);
        btnNhanTin = (ImageButton) findViewById(R.id.btnNhanTin);
        Intent i = getIntent();
        ten = i.getStringExtra("ten");
        soPhone = i.getStringExtra("sophone");
        txtThongTin.setText(ten + "-" + soPhone);
    }

    private void addEvents() {
        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SmsManager smsManager = SmsManager.getDefault();
                Intent intent = new Intent("ACTION_MSG_SENT");
                final PendingIntent pendingIntent = PendingIntent.getBroadcast(Main2Activity.this, 0, intent, 0);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int result = getResultCode();
                        String msg = "Send OK";
                        if(result != Activity.RESULT_OK) {
                            msg = "Send failed";
                        }
                        Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter("ACTION_MSG_SENT"));
                smsManager.sendTextMessage(soPhone, null, txtNoiDung.getText().toString(),
                        pendingIntent, null);
            }
        });
    }
}
