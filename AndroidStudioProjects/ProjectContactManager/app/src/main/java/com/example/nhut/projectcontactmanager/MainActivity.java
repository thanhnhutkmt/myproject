package com.example.nhut.projectcontactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nhut.adapter.ContactAdapter;
import com.example.nhut.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ContactAdapter contactAdapter;
    List<Contact> listData;
    ListView lvDanhBa;
    Button btnLuu;
    EditText txtTen, txtSoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtTen = (EditText) findViewById(R.id.txtName);
        txtSoPhone = (EditText) findViewById(R.id.txtPhone);
        btnLuu = (Button) findViewById(R.id.btnLuuDanhBa);

        listData = new ArrayList<Contact>();
        contactAdapter = new ContactAdapter(
            MainActivity.this,
            R.layout.item,
            listData
        );
        lvDanhBa = (ListView) findViewById(R.id.lvDanhBa);
        lvDanhBa.setAdapter(contactAdapter);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c = new Contact(txtTen.getText().toString(), txtSoPhone.getText().toString());
                listData.add(c);
                contactAdapter.notifyDataSetChanged();
            }
        });
    }
}
