package software.nhut.personalutilitiesforlife.data;

/**
 * Created by Nhut on 6/6/2016.
 */
public class Music {
    String maSo;
    String tenBaiHat;
    String tenTacGia;
    boolean yeuThich;

    public Music(String maSo, String tenBaiHat, String tenTacGia, boolean yeuThich) {
        this.maSo = maSo;
        this.tenBaiHat = tenBaiHat;
        this.tenTacGia = tenTacGia;
        this.yeuThich = yeuThich;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public boolean isYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(boolean yeuThich) {
        this.yeuThich = yeuThich;
    }
}
