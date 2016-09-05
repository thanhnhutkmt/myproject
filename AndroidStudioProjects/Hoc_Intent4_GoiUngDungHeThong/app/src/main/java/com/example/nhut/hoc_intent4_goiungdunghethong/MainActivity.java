package com.example.nhut.hoc_intent4_goiungdunghethong;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtSoPhone, txtTinNhan;
    Button btnQuaySo, btnGoiLuon, btnNhanTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnQuaySo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyQuaySo();
            }
        });
        btnGoiLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiLuon();
            }
        });
        btnNhanTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyNhanTin();
            }
        });
    }

    private void xuLyNhanTin() {
        final SmsManager smsManager = SmsManager.getDefault();
        Intent intent = new Intent("ACTION_MSG_SENT");
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Send OK";
                if(result != Activity.RESULT_OK) {
                    msg = "Send failed";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("ACTION_MSG_SENT"));
        smsManager.sendTextMessage(txtSoPhone.getText().toString(),
                null, txtTinNhan.getText().toString(), pendingIntent, null);
    }

    private void xuLyGoiLuon() {
        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + txtSoPhone.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void xuLyQuaySo() {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + txtSoPhone.getText().toString()));
        startActivity(intent);
    }

    private void addControls() {
        txtSoPhone = (EditText) findViewById(R.id.txtSoPhone);
        txtTinNhan = (EditText) findViewById(R.id.txtTinNhan);
        btnQuaySo = (Button) findViewById(R.id.btnQuaySo);
        btnGoiLuon = (Button) findViewById(R.id.btnGoiLuon);
        btnNhanTin = (Button) findViewById(R.id.btnNhanTin);
    }
}
