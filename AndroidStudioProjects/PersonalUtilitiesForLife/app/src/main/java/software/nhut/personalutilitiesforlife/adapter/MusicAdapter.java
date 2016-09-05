package software.nhut.personalutilitiesforlife.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import software.nhut.personalutilitiesforlife.KaraokeActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.data.Music;
import util.MyStringFormater;

/**
 * Created by Nhut on 6/6/2016.
 */
public class MusicAdapter extends ArrayAdapter {
    List<Music> listData;
    int rowID;
    Activity context;
    Boolean likeList;
    Filter musicFilter;

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

    @Override
    public Filter getFilter() {
        if (musicFilter == null)
            musicFilter = new MusicFilter(listData);
        return musicFilter;
    }

    private class MusicFilter extends Filter {
        private ArrayList<Music> sourceObjects;

        public MusicFilter(List<Music> list) {
            sourceObjects = new ArrayList<Music>();
            synchronized (this) {
                sourceObjects.addAll(list);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resultFilter = new FilterResults();
            String filterSeq = MyStringFormater.removeAccent(constraint.toString()).toLowerCase().trim();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<Music> filter = new ArrayList<Music>();
                if (Music.SORTBY == Music.SORTBY_MA) {
                    for (Music song : sourceObjects) {
                        if (song.getMaSo().toLowerCase().contains(filterSeq))
                            filter.add(song);
                    }
                } else if (Music.SORTBY == Music.SORTBY_NAME) {
                    for (Music song : sourceObjects) {
                        if (MyStringFormater.removeAccent(song.getTenBaiHat().split("\n")[0]).toLowerCase().trim().contains(filterSeq))
                            filter.add(song);
                    }
                } else if (Music.SORTBY == Music.SORTBY_AUTHOR) {
                    for (Music song : sourceObjects) {
                        if (MyStringFormater.removeAccent(song.getTenTacGia()).toLowerCase().trim().contains(filterSeq))
                            filter.add(song);
                    }
                }
                resultFilter.count = filter.size();
                resultFilter.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    resultFilter.values = sourceObjects;
                    resultFilter.count = sourceObjects.size();
                }
            }
            return resultFilter;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            ArrayList<Music> filtered = (ArrayList<Music>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((Music) filtered.get(i));
            notifyDataSetInvalidated();
            Log.i("My Tag", System.currentTimeMillis() + " : set true finishsearching");
            ((KaraokeActivity)context).setFinishSearching(true);
        }
    }
}
