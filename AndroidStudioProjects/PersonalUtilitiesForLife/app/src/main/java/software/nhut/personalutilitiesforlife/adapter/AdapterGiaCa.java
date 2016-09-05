package software.nhut.personalutilitiesforlife.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import software.nhut.personalutilitiesforlife.R;
import util.Mydownloader;

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
        TextView txtMuaTM = (TextView) row.findViewById(R.id.txtMuaTM);
        TextView txtMuaCK = (TextView) row.findViewById(R.id.txtMuaCK);
        TextView txtBanTM = (TextView) row.findViewById(R.id.txtBanTM);
        TextView txtBanCK = (TextView) row.findViewById(R.id.txtBanCK);
        TextView txtTenLoaiTien = (TextView) row.findViewById(R.id.txtTenLoaiTien);

        try {
            txtTenLoaiTien.setText((position == 11) ? "SJC(1C)" : Mydownloader.MONEYTYPE[position]);
            imgFlag.setImageResource(hinh[position]);
            String[] giaCa = gia[position].split(",,,,");
            txtMuaTM.setText(giaCa[0]);
            txtBanTM.setText(giaCa[1]);
            txtMuaCK.setText(giaCa[2]);
            txtBanCK.setText(giaCa[3]);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            Log.e("MyTag", "Position " + position);
            e.printStackTrace();
        }
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
