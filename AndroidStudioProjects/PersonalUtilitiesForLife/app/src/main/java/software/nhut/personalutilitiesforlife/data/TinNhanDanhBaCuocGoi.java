package software.nhut.personalutilitiesforlife.data;

import android.content.Context;
import android.graphics.Color;
import android.provider.CallLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.MyDateTime;
import util.MyPhone;

/**
 * Created by Nhut on 7/24/2016.
 */
public class TinNhanDanhBaCuocGoi implements Comparable, Serializable{
    public static final int SORTBYGROUP = 1;
    public static final int SORTBYTIME = 2;
    public static final int SORTBYCONTACT = 3;
    public static final int SORTBYTYPE = 4;
    public static final boolean REVERSE = true;
    public static final boolean NONREVERSE = false;
    public static final int UNKNOWN = 0; //Unknown/other
    public static final int SENT = 1; //outgoing Msg/Call
    public static final int RECEIVED = 2; //incoming Msg/Call
    public static final int DRAFT = 3; //draft Msg/Missed incoming call

    private String content;
    private List<Integer> groupNumber = new ArrayList<Integer>(); // all contact
    private int color = Color.BLACK;
    private boolean selected = false;
    private List<String> listInfo = new ArrayList<>();
    private int type = UNKNOWN;
    private long time;

    public static boolean reverseSort = NONREVERSE;
    public static int sortBy = SORTBYCONTACT;

    public TinNhanDanhBaCuocGoi(String content, String phone, int color,
            int type, long time, List<Integer> groupNumber, List<String> listInfo) {
        this.content = content;
        this.color = color;
        this.type = type;
        this.time = time;
        this.listInfo = listInfo;
        this.groupNumber = groupNumber;
    }

    public TinNhanDanhBaCuocGoi(String content, List<Integer> groupNumber, int color, boolean isSelected) {
        this.content = content;
        this.groupNumber.addAll(groupNumber);
        this.color = color;
        this.selected = isSelected;
        initializeListInfo(null);
    }

    public TinNhanDanhBaCuocGoi(String content, List<Integer> groupNumber, int color) {
        this.content = content;
        this.groupNumber.addAll(groupNumber);
        this.color = color;
        initializeListInfo(null);
    }

    public TinNhanDanhBaCuocGoi(int type, String content) {
        this.content = content;
        this.type = type;
        initializeListInfo(null);
    }

    public TinNhanDanhBaCuocGoi(String content, int color) {
        this.content = content;
        this.color = color;
        initializeListInfo(null);
    }

    public TinNhanDanhBaCuocGoi(String content) {
        this.content = content;
        initializeListInfo(null);
    }

    public TinNhanDanhBaCuocGoi(String content, List<String> listInfo) {
        this.content = content;
        this.listInfo = listInfo;
    }

    public TinNhanDanhBaCuocGoi(String content, List<String> listInfo, List<Integer> groupNumber) {
        this.content = content;
        this.listInfo = listInfo;
        if (groupNumber != null && groupNumber.size() > 0 && groupNumber.get(0) != 0) setGroupNumber(groupNumber);
    }

    public TinNhanDanhBaCuocGoi(int type, String content, String phoneNumber, long time) {
        this.content = content;
        this.type = type;
        this.time = time;
        initializeListInfo(phoneNumber);
    }

    public TinNhanDanhBaCuocGoi(String content, String phoneNumber) {
        this.content = content;
        initializeListInfo(phoneNumber);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(List<Integer> groupNumber) {
        boolean duplicate = false;
//        if (this.groupNumber.size() == 1 && this.groupNumber.get(0) == 0) {
//            this.groupNumber.set(0, groupNumber.get(0));
//        } else {
            for (int gn : this.groupNumber)
                if (gn == groupNumber.get(0)) {
                    duplicate = true;
                    break;
                }
            if (!duplicate) this.groupNumber.add(groupNumber.get(0));
//        }
        Collections.sort(this.groupNumber);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(Object another) {
        TinNhanDanhBaCuocGoi other = (TinNhanDanhBaCuocGoi) another;
        int result = 0;
        if (sortBy == SORTBYCONTACT) {
            int i = this.getContent().toLowerCase().compareTo(other.getContent().toLowerCase());
            result = (i > 0) ? 1 : (i == 0) ? 0 : -1;
        } else if (sortBy == SORTBYTIME) {
            result = (this.time > other.getTime()) ? 1 : (this.time == other.getTime()) ? 0 : -1;
        } else if (sortBy == SORTBYGROUP) {
            result = (this.getGroupNumber().get(0) > other.getGroupNumber().get(0)) ? 1 :
                    (this.getGroupNumber().get(0) == other.getGroupNumber().get(0)) ? 0 : -1;
        } else if (sortBy == SORTBYTYPE) {
            result = (this.getType() > other.getType()) ? 1 :
                    (this.getType() == other.getType()) ? 0 : -1;
        }
        return (reverseSort) ? result * -1 : result;
    }

    public List<String> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<String> listInfo) {
        this.listInfo = listInfo;
    }

    private void initializeListInfo(String phoneNumber) {
        for (int i = 0; i < MyPhone.HINTID.length; i++) this.listInfo.add(i, "");
        if (phoneNumber != null) this.listInfo.set(1, phoneNumber);
    }

    public int getType() {
        return type;
    }

    private static final int []CALLTYPE_NAME = {R.string.unknown_quanlytinnhan_typename,
            R.string.outgoing_quanlytinnhan_calltypenam,
            R.string.incoming_quanlytinnhan_calltypename,
            R.string.missed_quanlytinnhan_calltypename};
    public static String getCallTypeName(int type, Context context) {
        int i = (type == CallLog.Calls.OUTGOING_TYPE) ? 1 : (type == CallLog.Calls.INCOMING_TYPE) ? 2 :
                (type == CallLog.Calls.MISSED_TYPE) ? 3 : 0;
        return context.getResources().getString(CALLTYPE_NAME[i]);
    }

    private static final int []MESSAGETYPE_NAME = {R.string.unknown_quanlytinnhan_typename,
            R.string.Sendto_quanlytinnhan_messagetypename,
            R.string.Receivemessage_quanlytinnhan_messagetypename,
            R.string.Draftmessage_quanlytinnhan_messagetypename};
    public static String geMessageTypeName(int type, Context context) {
        int i = (type == SENT) ? 1 : (type == RECEIVED) ? 2 : (type == DRAFT) ? 3 : 0;
        return context.getResources().getString(MESSAGETYPE_NAME[i]);
    }

    public static int getTypeByCallLog(int type) {
        return (type == CallLog.Calls.OUTGOING_TYPE) ? SENT : (type == CallLog.Calls.INCOMING_TYPE) ? RECEIVED :
                (type == CallLog.Calls.MISSED_TYPE) ? DRAFT : UNKNOWN;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return this.listInfo.get(1);
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return content;
    }
}