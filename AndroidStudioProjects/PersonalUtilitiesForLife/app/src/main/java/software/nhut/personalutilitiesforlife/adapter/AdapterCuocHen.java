package software.nhut.personalutilitiesforlife.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import software.nhut.personalutilitiesforlife.QuanLyCuocHenActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import util.MyAnimation;
import util.MyDateTime;

/**
 * Created by Nhut on 6/15/2016.
 */
public class AdapterCuocHen extends ArrayAdapter {
    List<CuocHen> listCuocHen;
    int row;
    Activity context;

    public AdapterCuocHen(Activity context, int row, List<CuocHen> listCuocHen) {
        super(context, row, listCuocHen);
        this.listCuocHen = listCuocHen;
        this.row = row;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        final View row = inflater.inflate(this.row, null);
        TextView txtTieuDeNgan = (TextView) row.findViewById(R.id.txtTieuDeNgan);
        TextView txtNoiDungNgan = (TextView) row.findViewById(R.id.txtNoiDungNgan);
        TextView txtNgayGioThu = (TextView) row.findViewById(R.id.txtNgayGioThu);
        ImageView imgStar_itemlvcuochen = (ImageView) row.findViewById(R.id.imgStar_itemlvcuochen);
        CuocHen cuocHen = listCuocHen.get(position);
        txtTieuDeNgan.setText(cuocHen.getTieuDeNgan());
        txtNoiDungNgan.setText(cuocHen.getNoiDungNgan());
        txtNgayGioThu.setText(cuocHen.getGioThuNgayThangNamGon());
        imgStar_itemlvcuochen.setVisibility(View.INVISIBLE);
        this.context.registerForContextMenu(row);
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((QuanLyCuocHenActivity)context).setLastListViewItemSelected(position);
                ((QuanLyCuocHenActivity)context).setLastListViewItem(row);
                return false;
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyCuocHenActivity)context).setLastListViewItemSelected(position);
                ((QuanLyCuocHenActivity)context).setLastListViewItem(row);
                ((QuanLyCuocHenActivity)context).moCuocHen();
            }
        });
        setMyAnimation(row, cuocHen);
        return row;
    }

    public void setMyAnimation(final View row, CuocHen cuocHen) {
        Animation a = AnimationUtils.loadAnimation(this.context, R.anim.hieuunglistview);
        row.startAnimation(a);
        ImageView star = (ImageView) row.findViewById(R.id.imgStar_itemlvcuochen);
        LinearLayout ll = (LinearLayout) row.findViewById(R.id.starbox);
        if (cuocHen.isTat()) {
            row.setBackgroundColor(Color.RED);
            row.clearAnimation();
            ll.setVisibility(View.INVISIBLE);
            return;
        } else {
            ll.setVisibility(View.VISIBLE);
            row.setBackgroundColor(Color.WHITE);
        }
        Date currentDate = new Date(System.currentTimeMillis());
        long longCurrenDate = MyDateTime.setTimeToZero(currentDate);
        long next3Days = currentDate.getTime() + AppConstant.SECONDSOF3DAY;
        long next7Days = currentDate.getTime() + AppConstant.SECONDSOF7DAY;
        Date dateCuocHen = cuocHen.getTime();
        long longDateCuocHen = MyDateTime.setTimeToZero(dateCuocHen);

        if (longDateCuocHen <= next7Days && longDateCuocHen > next3Days) {
            animation7Days(row, star);
        } else if (longDateCuocHen <= next3Days && longDateCuocHen > longCurrenDate) {
            animation3Days(row, star);
        } else if (longDateCuocHen == longCurrenDate) {
            animationOnDays(row, star);
        } else if (longDateCuocHen < longCurrenDate){
            row.setBackgroundColor(Color.GRAY);
        } else {
            row.setBackgroundColor(Color.WHITE);
        }
    }

    private void animationOnDays(final View row, ImageView star) {
        MyAnimation.runAnimation(row, R.anim.tangkichthuoc1_3, true, this.context);
        row.setBackgroundColor(Color.rgb(158, 231, 255));
        star.setVisibility(View.INVISIBLE);
    }

    private void animation3Days(final View row, final ImageView star) {
        MyAnimation.runAnimation(row, R.anim.tangkichthuoc1_2, true, this.context);
        MyAnimation.runAnimation(star, R.anim.tanggiamkichthuocsao, true, this.context);
        MyAnimation.runAnimationDrawable(star, R.drawable.doimaungoisao);
    }

    private void animation7Days(final View row, ImageView star) {
        MyAnimation.runAnimation(row, R.anim.tangkichthuoc, true, this.context);
        MyAnimation.runAnimation(star, R.anim.xoayngoisao, true, this.context);
        MyAnimation.runAnimationDrawable(star, R.drawable.doimaungoisao);
    }

    public List<CuocHen> getListCuocHen() {
        return listCuocHen;
    }

    public void setListCuocHen(List<CuocHen> listCuocHen) {
        this.listCuocHen = listCuocHen;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}

