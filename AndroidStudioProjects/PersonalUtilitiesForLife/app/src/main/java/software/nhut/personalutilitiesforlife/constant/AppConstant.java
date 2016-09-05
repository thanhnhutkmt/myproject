package software.nhut.personalutilitiesforlife.constant;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nhut on 6/15/2016.
 */
public class AppConstant {
    public static final int CHIEUDAI_TIEUDE_NGAN_CUOCHEN = 80;
    public static final int CHIEUDAI_NOIDUNG_NGAN_CUOCHEN = 120;
    public static final int QUANLYCUOCHEN_NEW_REQUESTCODE = 10;
    public static final int QUANLYCUOCHEN_EDIT_REQUESTCODE = 11;
    public static final int QUANLYCUOCHEN_VIEW_REQUESTCODE = 12;
    public static final int CHITIETCUOCHEN_OPENMAP_REQUESTCODE = 13;
    public static final int CHITIETCUOCHEN_RESULTCODE = 20;
    public static final int CHITIETCUOCHEN_OPENMAP_RESULTCODE = 21;
    public static final String CHITIETCUOCHEN_CHUOIGUI_CUOCHEN = "cuochen";
    public static final String QUANLYCUOCHEN_CHUOIGUI_CUOCHEN = "cuochen";
    public static final String THUMUC_QUANLY_CUOCHEN = "QuanLyCuocHen";
    public static final String THUMUC_QUANLY_TINNHANDANHBA = "QuanLyTinNhanDanhBa";
    public static final String THUMUC_BANDO = "BanDo";
    public static final String DANHSACH_VITRI_BANDO = "dsvitri.bando";
    public static final String DATEFORMAT = "dd MMM yyyy";
    public static final String TIMEFORMAT = "HH:mm";
    public static final String FULLTIMEFORMAT = "HH:mm EEE\ndd MMM yyyy";
    public static final String FULLTIMEFORMAT_WITHOUTNEWLINE = "HH:mm EEE dd MMM yyyy";
    public static final String FULLTIMEFORMAT_WITHOUTDAYINWEEK = "HH:mm dd MMM yyyy";
    public static final int SECONDSOF7DAY = 7 * 24 * 3600 * 1000;
    public static final int SECONDSOF3DAY = 3 * 24 * 3600 * 1000;
    public static final String TEMPNAME = "temp.tmp";
    public static final String DATAGTDG = "gtdg.dat";
    public static final String ASSETSMUSIC = "music";
    public static final String ASSETSFONT = "font";
    public static final String ASSETSDBSQLITEFOLDER = "dbsqlite";
    public static final String FS = System.getProperty("file.separator");
    public static final LatLng HCMLOCATION = new LatLng(10.7765189, 106.7010057);
    public static final int ZOOM_WORLDLEVEL = 2; //1;
    public static final int ZOOM_CONTINENTLEVEL = 5;
    public static final int ZOOM_CITYLEVEL = 10;
    public static final int ZOOM_STREETLEVEL = 15;
    public static final int ZOOM_BUILDINGLEVEL = 20;
    public static final String STRINGALARMID = "stringAlarmID";
    public static final String PASSWORD = "TinnhanPassword";
    public static final String RINGTONENAME = "MessageRingTone.uri";
    public static final String THUMUC_BAOGIA = "BaoGiaViTinh";
    public static final String SPAM_LIST = "Spam.list";

    //constant for util
    public static final String DBNAME = "db";
    public static final String GMAPKEY = "AIzaSyDcVdsbY0QqL3OcadbSkgsUo8BtQ1ze-_c";

}

