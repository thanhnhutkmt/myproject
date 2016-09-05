package software.nhut.personalutilitiesforlife.data;

/**
 * Created by Nhut on 6/6/2016.
 */
public class Music implements Comparable{
    public static final int SORTBY_MA = 0;
    public static final int SORTBY_NAME = 1;
    public static final int SORTBY_AUTHOR = 2;
    public static int SORTBY = SORTBY_NAME;
    private String maSo;
    private String tenBaiHat;
    private String tenTacGia;
    private boolean yeuThich;

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


    @Override
    public int compareTo(Object another) {
        Music anotherSong = (Music)another;
        if (SORTBY == SORTBY_MA) {
            long thisMa = Long.parseLong(this.maSo);
            long anotherMa = Long.parseLong(anotherSong.getMaSo());
            if (thisMa > anotherMa) {
                return 1;
            } else if (thisMa < anotherMa) {
                return -1;
            } else {
                return 0;
            }
        } else {
            int difference = (SORTBY == SORTBY_NAME) ?
                    this.tenBaiHat.compareTo(anotherSong.getTenBaiHat())
                    :
                    this.tenTacGia.compareTo(anotherSong.getTenTacGia()) /*(SORTBY == SORTBY_AUTHOR)*/
                    ;
            if (difference > 0) {
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
