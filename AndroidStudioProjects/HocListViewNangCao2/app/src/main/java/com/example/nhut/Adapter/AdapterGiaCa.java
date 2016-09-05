package com.example.nhut.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nhut.hoclistviewnangcao2.R;

import java.util.List;

/**
 * Created by Nhut on 6/1/2016.
 */
public class AdapterGiaCa extends ArrayAdapter {

    Activity context;
    int Resource;
    String []gia;
    int []hinh;

    public AdapterGiaCa(Activity context, int resource, String []gia, int []hinh) {
        super(context, resource, gia);
        this.context = context;
        this.Resource = resource;
        this.gia = gia;
        this.hinh = hinh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.Resource, null);
        ImageView imgFlag = (ImageView) row.findViewById(R.id.imgQuocKy);
        EditText txtMuaTM = (EditText) row.findViewById(R.id.txtMuaTM);
        EditText txtMuaCK = (EditText) row.findViewById(R.id.txtMuaCK);
        EditText txtBanTM = (EditText) row.findViewById(R.id.txtBanTM);
        EditText txtBanCK = (EditText) row.findViewById(R.id.txtBanCK);

        imgFlag.setImageResource(hinh[position]);
        String []giaCa = gia[position].split(",");
        txtMuaTM.setText(giaCa[0]);
        txtBanTM.setText(giaCa[1]);
        txtMuaCK.setText(giaCa[2]);
        txtBanCK.setText(giaCa[3]);
        return row;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public int getResource() {
        return Resource;
    }

    public void setResource(int resource) {
        Resource = resource;
    }

    public String[] getGia() {
        return gia;
    }

    public void setGia(String[] gia) {
        this.gia = gia;
    }
}
