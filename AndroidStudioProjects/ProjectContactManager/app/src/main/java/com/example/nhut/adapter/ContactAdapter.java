package com.example.nhut.adapter;

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
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhut.model.Contact;
import com.example.nhut.projectcontactmanager.Main2Activity;
import com.example.nhut.projectcontactmanager.MainActivity;
import com.example.nhut.projectcontactmanager.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Nhut on 6/8/2016.
 */
public class ContactAdapter extends ArrayAdapter {
    List<Contact> listData;
    int rowID;
    Activity context;

    public ContactAdapter(Activity context, int rowID, List<Contact> listData) {
        super(context, rowID, listData);
        this.listData = listData;
        this.rowID = rowID;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.rowID, null);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtPhone = (TextView) row.findViewById(R.id.txtPhone);
        ImageButton btnCall = (ImageButton) row.findViewById(R.id.btnCall);
        ImageButton btnSms = (ImageButton) row.findViewById(R.id.btnSms);
        ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);

        final Contact contact = listData.get(position);
        txtTen.setText(contact.getTen());
        txtPhone.setText(contact.getSoPhone());
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoi(contact.getSoPhone());
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySms(contact);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXoa(contact);
            }
        });

        return row;
    }

    private void xuLyXoa(Contact contact) {
        this.remove(contact);
    }

    private void xuLySms(Contact contact) {
        Intent intent = new Intent(this.context, Main2Activity.class);
        intent.putExtra("sophone", contact.getSoPhone());
        intent.putExtra("ten", contact.getTen());
        this.context.startActivity(intent);
    }

    private void xuLyGoi(String soPhone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + soPhone));
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.context.startActivity(intent);
    }

    public List<Contact> getListData() {
        return listData;
    }

    public void setListData(List<Contact> listData) {
        this.listData = listData;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}
