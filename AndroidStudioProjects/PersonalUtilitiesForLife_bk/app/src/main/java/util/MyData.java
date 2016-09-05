package util;

import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.data.CuocHen;

/**
 * Created by Nhut on 6/23/2016.
 */
public class MyData {
    public static void removeDuplication(List<CuocHen> list) {
        int listLength;
        if (list != null && list.size() > 0) listLength = list.size();
        else return;
        for (int i = 0; i < listLength; i++) {
            CuocHen ch = list.get(i);
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
    public static int addAllWithoutDuplication(List<CuocHen> list,
                                               List<CuocHen> listToAdd,
                                               boolean removeDuplicationIn_listToAdd) {
        int listLength, listToAddLength, start = 0;
        boolean notDuplicated = false;
        if (list == null) list = new ArrayList<CuocHen>();
        if (listToAdd == null || listToAdd.size() == 0) return start;
        listLength = list.size();
        listToAddLength = listToAdd.size();
        if (removeDuplicationIn_listToAdd) removeDuplication(listToAdd);
        for (int i = 0; i < listToAddLength; i++) {
            CuocHen ch = listToAdd.get(i);
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
}
