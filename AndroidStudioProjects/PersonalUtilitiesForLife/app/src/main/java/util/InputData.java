package util;

import java.util.List;

/**
 * Created by Nhut on 7/21/2016.
 */
public interface InputData {
    void inputData(String s);
    void inputData(String... s);
    void inputData(List<String> s);
    void inputData(String s, int color);
}
