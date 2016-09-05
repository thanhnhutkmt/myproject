package util;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.data.CuocHen;
import software.nhut.personalutilitiesforlife.data.ViTri;

/**
 * Created by Nhut on 6/23/2016.
 */
public class MyData {
    public static <T> void removeDuplication(List<T> list) {
        int listLength;
        if (list != null && list.size() > 0) listLength = list.size();
        else return;
        for (int i = 0; i < listLength; i++) {
            T ch = list.get(i);
            for (int j = i + 1; j < listLength; j++) {
                if (ch.toString().equals(list.get(j).toString())) {
                    list.remove(j);
                }
            }
        }
    }
    /*
        return : number of CuocHen of listToAdd added to list.
     */
    public static <T> int addAllWithoutDuplication(List<T> list,
                                               List<T> listToAdd,
                                               boolean removeDuplicationIn_listToAdd) {
        int listLength, listToAddLength, start = 0;
        boolean notDuplicated = false;
        if (list == null) list = new ArrayList<T>();
        if (listToAdd == null || listToAdd.size() == 0) return start;
        listLength = list.size();
        listToAddLength = listToAdd.size();
        if (removeDuplicationIn_listToAdd) removeDuplication(listToAdd);
        for (int i = 0; i < listToAddLength; i++) {
            T ch = listToAdd.get(i);
            notDuplicated = true;
            for (int j = start; j < (listLength + start); j++) {
                if (ch.toString().equals(list.get(j).toString())) {
                    notDuplicated = false;
                    break;
                }
            }
            if (notDuplicated) {
                list.add(0, ch);
                start++;
            }
        }
        return start;
    }

//    public static ViTri[] convertListToArray(List<ViTri> list) {
//        ViTri array[] = new ViTri[list.size()];
//        int i = 0;
//        for (ViTri vt : list) {
//            array[i++] = vt;
//        }
//        return array;
//    }

    public static LatLng[] convertListToArray(List<LatLng> list) {
        LatLng array[] = new LatLng[list.size()];
        int i = 0;
        for (LatLng vt : list) {
            array[i++] = vt;
        }
        return array;
    }
}
