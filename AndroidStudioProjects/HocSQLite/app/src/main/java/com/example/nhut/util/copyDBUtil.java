package com.example.nhut.util;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhut.hocsqlite.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Nhut on 6/11/2016.
 */
public class copyDBUtil {
    public static String copyDBFromAssets(MainActivity a) {
        final String DBNAME = a.DBNAME;
        final String DBPATH = a.getApplicationInfo().dataDir + System.getProperty("file.separator") + "databases";
        final String DBNAMEWITHPATH =  DBPATH + System.getProperty("file.separator") + DBNAME;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        File f = new File(DBPATH);
        if (!f.exists()) f.mkdirs();
        f = new File(DBNAMEWITHPATH);
        try {
            if (!f.exists()) f.createNewFile();
            else return DBNAMEWITHPATH + " already existed!!";
            inputStream = a.getAssets().open(DBNAME);
            outputStream = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Copy DB successfully.";
    }
}
