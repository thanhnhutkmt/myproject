package com.example.nhut.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nhut.hocgridview1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 6/1/2016.
 */
public class AdapterHoa extends ArrayAdapter{
    Activity context;
    int row;
    ArrayList<Integer> listHinh;

    public AdapterHoa(Activity context, int row, ArrayList<Integer> listHinh) {
        super(context, row, listHinh);
        this.context = context;
        this.row = row;
        this.listHinh = listHinh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.row, null);
        ImageView imgHinh = (ImageView) row.findViewById(R.id.imgHinh);
        EditText txtSoLuong = (EditText) row.findViewById(R.id.txtSoLuong);
        Button btnMua = (Button) row.findViewById(R.id.btnMua);

        txtSoLuong.setText("0");
        imgHinh.setImageResource(listHinh.get(position).intValue());
        return row;
    }

    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public ArrayList<Integer> getListHinh() {
        return listHinh;
    }

    public void setListHinh(ArrayList<Integer> listHinh) {
        this.listHinh = listHinh;
    }
}
