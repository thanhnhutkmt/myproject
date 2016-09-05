package util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import software.nhut.personalutilitiesforlife.constant.AppConstant;

/**
 * Created by Nhut on 6/26/2016.
 */
public class MyAssetsAndPreferences {
    public static Typeface getFont(Activity a, int position) {
        try {
            return Typeface.createFromAsset(a.getAssets(), "font"
                    + System.getProperty("file.separator") + a.getAssets().list(AppConstant.ASSETSFONT)[position]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void playSound(Activity a, int index) {
        // Chay file nhac hieu ung
        MediaPlayer mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset(); // fix bug app show warning "W/MediaPlayer: mediaplayer went away with unhandled events"
                mp.release();
                mp = null;
            }
        });
        try {
            String []listMusic = a.getAssets().list(AppConstant.ASSETSMUSIC);
            AssetFileDescriptor afd = a.getAssets().openFd("music"
                    + System.getProperty("file.separator") + listMusic[index]);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    public static boolean copyAssetsToPhone(Activity a, String fileNameToSave, String path, String assetsFileWithPath) {
        String resultString = null;
        try {
            resultString = MyFileIO.copyFile(fileNameToSave, path, a.getAssets().open(assetsFileWithPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (resultString.equals("Copy ok.") || resultString.endsWith(" already existed!!")) ? true : false;
    }

    public static String getStringFromPreferences(Activity a, String key) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static float getFloatFromPreferences(Activity a, String key) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        return sp.getFloat(key, 0);
    }

    public static Set<String> getSetFromPreferences(Activity a, String key) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        return sp.getStringSet(key, null);
    }

    public static boolean saveToPreferences(Activity a, String key, String value) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(key, value);
        e.commit();
        return true;
    }

    public static boolean saveToPreferences(Activity a, String key, float value) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putFloat(key, value);
        e.commit();
        return true;
    }

    public static boolean saveToPreferences(Activity a, String key, Set<String> value) {
        SharedPreferences sp = a.getSharedPreferences(AppConstant.DBNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putStringSet(key, value);
        e.commit();
        return true;
    }

    public static List<String> getAssetsList(Activity a, String folder) {
        List<String> list = new ArrayList<>();
        String []file = null;
        try {
            file = a.getAssets().list(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String f : file) list.add(f);
        return list;
    }
}
