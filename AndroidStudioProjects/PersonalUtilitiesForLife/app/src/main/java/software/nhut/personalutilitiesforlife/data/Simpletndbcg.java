package software.nhut.personalutilitiesforlife.data;

import android.graphics.Color;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 8/16/2016.
 */
public class Simpletndbcg implements Serializable{
    private String content;
    private String phoneNumber;
    private List<Integer> groupNumber = new ArrayList<>();
    private int color;
    private int type;
    private long time;
    private List<String> listInfo = new ArrayList<>();

    public Simpletndbcg(TinNhanDanhBaCuocGoi tndbcg) {
        this.content = tndbcg.getContent();
        this.phoneNumber = tndbcg.getPhoneNumber();
        this.groupNumber = tndbcg.getGroupNumber();
        this.color = tndbcg.getColor();
        this.type = tndbcg.getType();
        this.time = tndbcg.getTime();
        this.listInfo = tndbcg.getListInfo();
    }

    public static TinNhanDanhBaCuocGoi convertSimpleToFull(Simpletndbcg s) {
        return new TinNhanDanhBaCuocGoi(s.getContent(), s.getPhoneNumber(), s.getColor(),
                s.getType(), s.getTime(), s.getGroupNumber(), s.getListInfo());
    }

    public String getContent() {
        return content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public List<Integer> getGroupNumber() {
        return groupNumber;
    }

    public List<String> getListInfo() {
        return listInfo;
    }
}
