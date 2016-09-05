package com.example.nhut.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhut.hoclistviewnangcao.R;
import com.example.nhut.model.DanhBa;

import java.util.List;

/**
 * Created by Nhut on 5/31/2016.
 */
public class DanhBaAdapter extends ArrayAdapter<DanhBa> {
    //Man hinh su dung layout nay
    Activity context;
    //Layout cho tung dong muon hien thi
    int resources;
    //danh sach nguon du lieu muon hien thi len giao dien
    List<DanhBa> objects;
    private String smsContent;

    public DanhBaAdapter(Activity context, int resource, List<DanhBa> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resources = resource;
        this.objects = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resources, null);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtPhone = (TextView) row.findViewById(R.id.txtSoDienThoai);
        ImageButton btnCall = (ImageButton) row.findViewById(R.id.btnCall);
        ImageButton btnSMS = (ImageButton) row.findViewById(R.id.btnSMS);
        ImageButton btnChiTiet = (ImageButton) row.findViewById(R.id.btnChiTiet);

        //Tra ve danh ba hien tai muon ve
        final DanhBa danhba = this.objects.get(position);
        txtTen.setText(danhba.getTen());
        txtPhone.setText(danhba.getPhone());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiDIen(danhba);
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGuiSMS(danhba);
            }
        });
        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXemChiTiet(danhba);
            }
        });

        return row;
    }

    private void xuLyGuiSMS(final DanhBa db) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Nhập nội dung tin nhắn");

        // Set up the input
        final EditText input = new EditText(this.context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNo = db.getPhone();
                String smsContent = input.getText().toString();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, smsContent, null, null);
                    Toast.makeText(getContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                smsContent = "";
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void xuLyGoiDIen(DanhBa db) {
        PackageManager pm = this.context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            //has Telephony features.
            Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + db.getPhone()));
            try {
                if (in.resolveActivity(pm) != null) {
                    this.context.startActivity(in);
                }
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this.context, "Lỗi xảy ra :\n" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.context, "Thiết bị này không gọi điện được !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void xuLyXemChiTiet(DanhBa db) {
        Toast.makeText(this.context, "Bạn chọn : " + db.getTen(), Toast.LENGTH_LONG).show();
    }
}

