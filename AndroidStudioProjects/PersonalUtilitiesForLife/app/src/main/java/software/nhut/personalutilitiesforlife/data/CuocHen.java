package software.nhut.personalutilitiesforlife.data;

import android.app.Activity;
import android.app.PendingIntent;
import android.text.TextUtils;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.MyAssetsAndPreferences;
import util.MyDateTime;

/**
 * Created by Nhut on 6/15/2016.
 */
public class CuocHen implements Serializable, Comparable{
    private String tieuDe, tieudDeNgan;
    private String noiDung, noiDungNgan;
    private Date time;
    private String gioThuNgayThangNam;
    private boolean tat;
    private int alarmID;

    private List<String> listDiaDiem;
    private List<String> listTinNhan;
    private List<String> listNguoiLienHe;

    public CuocHen() {
        this.tieuDe = "";
        this.noiDung = "";
        this.time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.FULLTIMEFORMAT);
        this.gioThuNgayThangNam = sdf.format(this.time);
        this.listDiaDiem = new ArrayList<String>();
        this.listTinNhan = new ArrayList<String>();
        this.listNguoiLienHe = new ArrayList<String>();
        this.tat = false;
    }

    public CuocHen(String tieuDe, String noiDung, String gioThuNgayThangNam, List<String> listDiaDiem,
                   List<String> listTinNhan, List<String> listNguoiLienHe, boolean tat) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.time = MyDateTime.getTime(gioThuNgayThangNam, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.FULLTIMEFORMAT);
        this.gioThuNgayThangNam = sdf.format(this.time);
        this.tat = tat;
        if(listDiaDiem != null) this.listDiaDiem = listDiaDiem;
        else this.listDiaDiem = new ArrayList<String>();
        if(listTinNhan != null) this.listTinNhan = listTinNhan;
        else this.listTinNhan = new ArrayList<String>();
        if(listNguoiLienHe != null) this.listNguoiLienHe = listNguoiLienHe;
        else this.listNguoiLienHe = new ArrayList<String>();

        if (tieuDe.length() < AppConstant.CHIEUDAI_TIEUDE_NGAN_CUOCHEN)
            tieudDeNgan = tieuDe;
        else
            tieudDeNgan = tieuDe.substring(0, AppConstant.CHIEUDAI_TIEUDE_NGAN_CUOCHEN);

        if (noiDung.length() < AppConstant.CHIEUDAI_NOIDUNG_NGAN_CUOCHEN)
            noiDungNgan = noiDung;
        else
            noiDungNgan = noiDung.substring(0, AppConstant.CHIEUDAI_NOIDUNG_NGAN_CUOCHEN);
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getTieuDeNgan() {
        return tieudDeNgan;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public String getNoiDungNgan() {
        return noiDungNgan;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getGioThuNgayThangNamGon() {
        return this.gioThuNgayThangNam;
    }

    public String getGioThuNgayThangNam() {
        return this.gioThuNgayThangNam.replace("\n", " ");
    }

    public void setGioThuNgayThangNam(String gioThuNgayThangNam) {
        Date d = MyDateTime.getTime(gioThuNgayThangNam, AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstant.FULLTIMEFORMAT);
        this.gioThuNgayThangNam = sdf.format(d);
    }

    public boolean isTat() {
        return tat;
    }

    public void setTat(boolean tat) {
        this.tat = tat;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<String> getListDiaDiem() {
        return listDiaDiem;
    }

    public void setListDiaDiem(List<String> listDiaDiem) {
        this.listDiaDiem = listDiaDiem;
    }

    public List<String> getListTinNhan() {
        return listTinNhan;
    }

    public void setListTinNhan(List<String> listTinNhan) {
        this.listTinNhan = listTinNhan;
    }

    public List<String> getListNguoiLienHe() {
        return listNguoiLienHe;
    }

    public void setListNguoiLienHe(List<String> listNguoiLienHe) {
        this.listNguoiLienHe = listNguoiLienHe;
    }

    @Override
    public int compareTo(Object another) {
        long time = MyDateTime.getTimeInMiliSeconds(this.getGioThuNgayThangNamGon(), AppConstant.FULLTIMEFORMAT);
        CuocHen ch = (CuocHen) another;
        long anotherTime = MyDateTime.getTimeInMiliSeconds(ch.getGioThuNgayThangNamGon(), AppConstant.FULLTIMEFORMAT);
        if (time > anotherTime) {
            return 1;
        } else if (time < anotherTime){
            return -1;
        } else {
            return 0;
        }
    }

    public int getAlarmID() {
        return alarmID;
    }

    public static void removeSavedAlarmID(Activity a, int id) {
        String stringID = MyAssetsAndPreferences.getStringFromPreferences(a, AppConstant.STRINGALARMID);
        if (stringID.length() == 0) return;
        else {
            List<String> listID = Arrays.asList(stringID.split(","));
            for (int i = 0; i < listID.size(); i++) {
                if (id == Integer.parseInt(listID.get(i))) {
                    listID.remove(i);
                    break;
                }
            }
            StringBuilder sidbuilder = new StringBuilder();
            for (String sid : listID) sidbuilder.append(sid + ",");
            MyAssetsAndPreferences.saveToPreferences(a, AppConstant.STRINGALARMID, sidbuilder.toString());
        }
    }

    public boolean setAlarmID(Activity a, int alarmID) {
        if (isAlarmIDOK(a, alarmID, true)) {
            this.alarmID = alarmID;
            return true;
        } else return false;
    }

    public static boolean isAlarmIDOK(Activity a, int id, boolean saveID) {
        String stringID = MyAssetsAndPreferences.getStringFromPreferences(a, AppConstant.STRINGALARMID);
        if (stringID.length() == 0) {
            if (saveID) {
                stringID = Integer.toString(id) + ",";
                MyAssetsAndPreferences.saveToPreferences(a, AppConstant.STRINGALARMID, stringID);
            }
            return true;
        }
        List<String> listID = Arrays.asList(stringID.split(","));
        for (String sid : listID) {
            if (id == Integer.parseInt(sid)) return false;
        }
        if (saveID) {
            stringID += Integer.toString(id) + ",";
            MyAssetsAndPreferences.saveToPreferences(a, AppConstant.STRINGALARMID, stringID);
        }
        return true;
    }

    @Override
    public String toString() {
        return "CuocHen{" +
                "tieuDe='" + tieuDe + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", gioThuNgayThangNam='" + gioThuNgayThangNam + '\'' +
                ", listDiaDiem=" + TextUtils.join("\t", listDiaDiem) +
                ", listTinNhan=" + TextUtils.join("\t", listTinNhan) +
                ", listNguoiLienHe=" + TextUtils.join("\t", listNguoiLienHe) +
                '}';
    }
}
