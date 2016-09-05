package com.example.nhut.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhut.hocgridview.R;

import java.util.List;

/**
 * Created by Nhut on 6/1/2016.
 */
public class HinhAdapter extends ArrayAdapter{
    Activity context;      // Activity shows the view which uses this adapter
    int resource;          // id of layout of every item
    List<Integer> objects; // list id of image

    public HinhAdapter(Activity context, int resource, List<Integer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);
        ImageView img = (ImageView) item.findViewById(R.id.imgHinh);
        img.setImageResource(this.objects.get(position));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Bạn chọn vị trí " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return item;
    }
}
