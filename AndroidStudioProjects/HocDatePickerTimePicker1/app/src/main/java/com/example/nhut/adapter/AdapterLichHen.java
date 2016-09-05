package com.example.nhut.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhut.hocdatepickertimepicker1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.nhut.hocdatepickertimepicker1.R.id.btnDateItem;
import static com.example.nhut.hocdatepickertimepicker1.R.id.txtDate;

/**
 * Created by Nhut on 6/2/2016.
 */
public class AdapterLichHen extends ArrayAdapter{
    Activity context;
    int row;
    List<String> listCuocHen; // tencuohen,ghichu,ngay,gio,sothutu
    ArrayList<String> listLastSelected;

    public AdapterLichHen(Activity context, int row, List listCuocHen, ArrayList<String> listLastSelected) {
        super(context, row, listCuocHen);
        this.context = context;
        this.row = row;
        this.listCuocHen = listCuocHen;
        this.listLastSelected = listLastSelected;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.row, null);
        ImageButton btnDate = (ImageButton) row.findViewById(R.id.btnDateItem);
        ImageButton btnTime = (ImageButton) row.findViewById(R.id.btnTimeItem);
        TextView txtDate = (TextView) row.findViewById(R.id.txtDateItem);
        TextView txtTime = (TextView) row.findViewById(R.id.txtTimeItem);
        TextView txtContent = (TextView) row.findViewById(R.id.txtNoiDung);

        String []cuocHen = this.listCuocHen.get(position).split(",");
        cuocHen[0] = cuocHen[0].replace((char)31, ',');
        cuocHen[1] = cuocHen[1].replace((char)31, ',');
        txtContent.setText(cuocHen[0] + "\n" + cuocHen[1]);
        txtDate.setText(cuocHen[2]);
        txtTime.setText(cuocHen[3]);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLastSelected.clear();
                listLastSelected.add(0, listCuocHen.get(position));
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<String> getListCuocHen() {
        return listCuocHen;
    }

    public void setListCuocHen(List<String> listCuocHen) {
        this.listCuocHen = listCuocHen;
    }
}
