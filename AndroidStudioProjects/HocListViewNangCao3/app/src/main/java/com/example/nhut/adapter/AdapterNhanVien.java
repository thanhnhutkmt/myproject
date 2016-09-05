package com.example.nhut.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhut.hoclistviewnangcao3.R;

import java.util.List;

/**
 * Created by Nhut on 6/1/2016.
 */
public class AdapterNhanVien extends ArrayAdapter {
    Activity context;
    int resource;
    List<String> listNhanVien;

    public AdapterNhanVien(Activity context, int resource, List<String> listNhanVien) {
        super(context, resource, listNhanVien);
        this.context = context;
        this.resource = resource;
        this.listNhanVien = listNhanVien;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        ImageView imgNV = (ImageView) row.findViewById(R.id.imgNhanVien);
        TextView txtNV = (TextView) row.findViewById(R.id.txtNhanVien);
        CheckBox chkNV = (CheckBox) row.findViewById(R.id.chkNhanVien);

        String []thongTinNhanVien = listNhanVien.get(position).split(",");
        txtNV.setText(thongTinNhanVien[0]);
        imgNV.setImageResource(Integer.parseInt(thongTinNhanVien[1]));
        chkNV.setChecked(Boolean.parseBoolean(thongTinNhanVien[2]));

        chkNV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String nv = listNhanVien.remove(position).replace(
                        "," + Boolean.toString(!isChecked),
                        "," + Boolean.toString(isChecked));
                listNhanVien.add(position, nv);
            }
        });

        return row;
    }

    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public List<String> getListNhanVien() {
        return listNhanVien;
    }

    public void setNhanVien(List<String> listNhanVien) {
        this.listNhanVien = listNhanVien;
    }
}
