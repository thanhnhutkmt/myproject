package software.nhut.personalutilitiesforlife.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nhut.model.Music;
import com.example.nhut.projectkaraoke.R;

import java.util.List;

/**
 * Created by Nhut on 6/6/2016.
 */
public class MusicAdapter extends ArrayAdapter {
    List<Music> listData;
    int rowID;
    Activity context;
    Boolean likeList;

    public MusicAdapter(Activity context, int rowID, List<Music> listData) {
        super(context, rowID, listData);
        this.listData = listData;
        this.rowID = rowID;
        this.context = context;
        this.likeList = null;
    }

    public MusicAdapter(Activity context, int rowID, List<Music> listData, boolean likeList) {
        super(context, rowID, listData);
        this.listData = listData;
        this.rowID = rowID;
        this.context = context;
        this.likeList = likeList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.rowID, null);
        TextView txtMaSo = (TextView) row.findViewById(R.id.txtMaSo);
        TextView txtTenBaiHat = (TextView) row.findViewById(R.id.txtTenBaiHat);
        TextView txtTacGia = (TextView) row.findViewById(R.id.txtTacGia);
        final ImageButton btnLike = (ImageButton) row.findViewById(R.id.btnLike);
        final ImageButton btnDislike = (ImageButton) row.findViewById(R.id.btnDislike);

        Music song = listData.get(position);
        txtMaSo.setText(song.getMaSo());
        txtTenBaiHat.setText(song.getTenBaiHat());
        txtTacGia.setText(song.getTenTacGia());
        if (song.isYeuThich()) {
            btnLike.setVisibility(View.INVISIBLE);
            btnDislike.setVisibility(View.VISIBLE);
        } else {
            btnLike.setVisibility(View.VISIBLE);
            btnDislike.setVisibility(View.INVISIBLE);
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.get(position).setYeuThich(true);
                btnLike.setVisibility(View.INVISIBLE);
                btnDislike.setVisibility(View.VISIBLE);
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.get(position).setYeuThich(false);
                btnLike.setVisibility(View.VISIBLE);
                btnDislike.setVisibility(View.INVISIBLE);
                if(likeList != null) {
//                    listData.remove(position);
//                    notifyDataSetChanged();
                    remove(listData.get(position));
                }
            }
        });

        return row;
    }

    public List<Music> getListData() {
        return listData;
    }

    public void setListData(List<Music> listData) {
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
