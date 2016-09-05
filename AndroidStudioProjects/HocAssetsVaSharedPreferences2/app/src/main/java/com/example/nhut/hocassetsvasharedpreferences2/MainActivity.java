package com.example.nhut.hocassetsvasharedpreferences2;

import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogIn, btnExit;
    EditText txtUserName, txtPass;
    final String DATANAME = "Save";
    final String USERNAME = "SaveUserName";
    final String PASSWORD = "SavePassWord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Log in successfully!", Toast.LENGTH_LONG).show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.exit(0); // lenh nay se khong goi onPause()
                finish();
            }
        });
    }

    private void addControls() {
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnExit = (Button) findViewById(R.id.btnExit);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPass = (EditText) findViewById(R.id.txtPass);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "onResume() successfully!", Toast.LENGTH_LONG).show();
        SharedPreferences sp = getSharedPreferences(DATANAME, MODE_PRIVATE);
        txtUserName.setText(sp.getString(USERNAME, ""));
        txtPass.setText(sp.getString(PASSWORD, ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "onPause() successfully!", Toast.LENGTH_LONG).show();
        SharedPreferences sp = getSharedPreferences(DATANAME, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(USERNAME, txtUserName.getText().toString().trim());
        e.putString(PASSWORD, txtPass.getText().toString().trim());
        e.commit();
    }
}
