package util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import software.nhut.personalutilitiesforlife.QuanLyCuocHenActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.adapter.MusicAdapter;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import software.nhut.personalutilitiesforlife.data.Music;

/**
 * Created by Nhut on 6/19/2016.
 */
public class MyFileIO {
    public static boolean saveData(List<?> list, String fullPath, String fileName) {
        if (list == null && list.size() == 0) return true;
        boolean result = true;
        ObjectOutput oo = null;
        String dataPath = fullPath + AppConstant.FS + fileName;
        File f = new File(dataPath);
        if (f.exists()) result = f.delete();
        if (!result) return result;
        try {
            oo = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(f)));
            oo.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (oo != null) oo.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public static List<?> loadData(String fullAppPath, String fileName) {
        List<Object> listObject = new ArrayList<Object>();
        ObjectInput oi = null;
        if (fullAppPath == null || fullAppPath.trim().length() == 0
                || fileName == null || fileName.trim().length() == 0) return listObject;
        String dataFileNamePath = fullAppPath + AppConstant.FS + fileName;
        try {
            oi = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(dataFileNamePath)));
            listObject.addAll((List<Object>) oi.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oi != null) oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listObject;
    }

    public static <T> boolean saveData(T data, String fileNameWithPath) {
        if (data == null) return true;
        boolean result = true;
        ObjectOutput oo = null;
        File f = new File(fileNameWithPath);
        if (f.exists()) result = f.delete();
        if (!result) return result;
        try {
            oo = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(f)));
            oo.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (oo != null) oo.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public static Object loadData(String fileNameWithPath) {
        Object data = null;
        ObjectInput oi = null;
        if (fileNameWithPath == null || fileNameWithPath.trim().length() == 0) return data;
        try {
            oi = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(fileNameWithPath)));
            data = oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oi != null) oi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static boolean writeStringFile(String fileNameWithPath, String content, boolean appendMode) {
        boolean result = true;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileNameWithPath, appendMode));
            writer.write(content);
            writer.flush();
        } catch ( IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public static String readStringFile(String fileNameWithPath) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(fileNameWithPath));
            String temp;
            while ((temp = reader.readLine()) != null) sb.append(temp).append(System.getProperty("line.separator"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static byte[] readByteFile(File file) {
        byte data[] = new byte[(int)file.length()];
        try {
            new FileInputStream(file).read(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeByteFile(File file, byte[] data, boolean append) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file, append);
            out.write(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean clearFolderContent(String path) {
        boolean result = true;
        File folder = new File(path);
        if (folder.exists()) {
            if (folder.isDirectory()) {
                File[] content = folder.listFiles();
                if (content == null) return true;
                for (File f : content) {
                    if (f.isDirectory()) clearFolderContent(f.getAbsolutePath());
                    else if (f.isFile()) result = f.delete();
                }
            } else if (folder.isFile()) folder.delete();
        }
        return result;
    }

    public static boolean clearFolderContent(String path, boolean deleteFolder) {
        boolean result = true;
        File folder = new File(path);
        if (folder.exists()) {
            if (folder.isDirectory()) {
                File[] content = folder.listFiles();
                if (content != null)
                    for (File f : content) {
                        if (f.isDirectory()) clearFolderContent(f.getAbsolutePath());
                        else if (f.isFile()) result = f.delete();
                    }
            } else if (folder.isFile()) folder.delete();
        }
        if (deleteFolder) result = folder.delete();
        return result;
    }

    public static boolean clearFolderStartWith(String path, String startWith) {
        boolean result = true;
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            for (File f : folder.listFiles()) {
                if (f.isDirectory() && f.getName().startsWith(startWith)) {
                    clearFolderContent(f.getAbsolutePath());
                    result = f.delete();
                }
            }
        }
        return result;
    }

    public static boolean makeFolder(String folderName) {
        File folder = new File(folderName);
        boolean result;
        result = !folder.exists();
        if(result) result = folder.mkdirs();
        else result = true;
        return result;
    }

    public static String getDataFileName(String path) {
        String dataName = null;
        String folderPath = path + System.getProperty("file.separator");
        File dataFolder = new File(path);
        String []arrayFiles = dataFolder.list();
        if (arrayFiles.length > 0) dataName = folderPath + arrayFiles[0];
        return dataName;
    }

    public static String[] getListDataFileName(String path) {
        String folderPath = path + System.getProperty("file.separator");
        File dataFolder = new File(path);
        return dataFolder.list();
    }

    class readWriteTask extends AsyncTask<Integer, Integer, Void> {
        private ProgressDialog pd;
        private List<?> list;
        private boolean writeTask;
        private Activity activity;
        private String path;
        private String fileName;
        public static final boolean WRITE = true;
        public static final boolean READ = false;

        readWriteTask(Activity a, List<?> list, boolean writeTask, String fileName) {
            pd = new ProgressDialog(a);
            pd.setMessage((writeTask) ?
                    activity.getResources().getString(R.string.ProgressDialog_GhiFile) :
                    activity.getResources().getString(R.string.ProgressDialog_DocFile));
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgress(0);
            this.list = list;
            this.writeTask = writeTask;
            this.fileName = fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setProgress(0);
            pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.setProgress(100);
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            if (writeTask) {
                pd.setProgress(values[0]);
                if (values[1] == 1)
                    Toast.makeText(activity,
                            R.string.Toast_GhiRaFile_BiLoi, Toast.LENGTH_LONG).show();
            } else {
                if (this.list != null) pd.setProgress(values[0]);
            }
        }

        @Override
        protected Void doInBackground(Integer... params) {
            if (writeTask) {
                boolean result = MyFileIO.saveData(this.list,
                        activity.getApplicationInfo().dataDir + System.getProperty("file.separator")
                                + AppConstant.THUMUC_QUANLY_CUOCHEN, fileName);
                publishProgress(100, (result) ? 0 : 1);
                SystemClock.sleep(200);
            } else {
                String path = activity.getApplicationInfo().dataDir + System.getProperty("file.separator")
                        + AppConstant.THUMUC_QUANLY_CUOCHEN;
                this.list = MyFileIO.loadData(path, fileName);
                publishProgress(100);
                SystemClock.sleep(200);
            }
            return null;
        }
    }

    public static String copyFile(String fileNameToSave, String path, InputStream assetsfFile, String md5) {
        final String DBNAMEWITHPATH =  path + System.getProperty("file.separator") + fileNameToSave;
        OutputStream outputStream = null;
        File f = new File(path);
        if (!f.exists()) f.mkdirs();
        f = new File(DBNAMEWITHPATH);
        try {
//            if (!f.exists()) f.createNewFile();
//            else return DBNAMEWITHPATH + " already existed!!";
            Log.d("My Tag", "original file size \t" + assetsfFile.available()/1024 + " KB");
            f.delete();
            f.createNewFile();
            outputStream = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = assetsfFile.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            Log.d("My Tag", "Copied file size \t" + f.length()/1024 + " KB");
            assetsfFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        if (MyCheckSum.checkMD5(md5, f)) return "Copy ok.";
        else return "Copy failed";
    }

    public static void copyFolder(String originalFolderPathWithName, String destinationFolderPathWithoutName, String destinationFolderName) {
        File origin = new File(originalFolderPathWithName);
        File destination = new File(destinationFolderPathWithoutName + AppConstant.FS + destinationFolderName);
        if (origin.isDirectory()) {
            destination.mkdir();
            for (File f : origin.listFiles()) if (f.isDirectory() || f.isFile()) copyFolder(f.getAbsolutePath() + AppConstant.FS + f.getName(),
                    destinationFolderPathWithoutName + AppConstant.FS + destinationFolderName, f.getName());
        }
        else if (origin.isFile()) try {MyFileIO.copyFile(origin.getName(), destinationFolderPathWithoutName + AppConstant.FS + destinationFolderName,
                        new FileInputStream(origin), MyCheckSum.calculateMD5(origin));} catch (Exception e) { e.printStackTrace();}
    }

    /*
        showConfirmBox
            Nbutton
                copy Asset to Phone
                read database
                Hien thi listview karaoke
            Pbutton
                Hien thi cac noi luu tru de chon
                    Mo thu muc da chon
                        liet ke noi dung thu muc
                        click vao thu muc cha
                            liet ke noi dung thu muc cha
                    Mo file da chon
                        copy Asset to Phone
                        read database
                        Hien thi listview karaoke
     */
    public static void showConfirmBox(final Activity activity, final String KARAOKELIST_DBNAME,
                                      final String KARAOKELIST_DBNAMEWITHPATH, final String KARAOKELIST_DBTABLENAME,
                                      final List<Music> listBaiHat, final MusicAdapter adapterMusicBaiHat) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.ConfirmDialog_TuaDe_karaoke);
        TextView txtContent = new TextView(activity);
        txtContent.setText(R.string.ConfirmDialog_NoiDung_karaoke);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        txtContent.setGravity(Gravity.CENTER_VERTICAL + Gravity.LEFT);
        txtContent.setLayoutParams(lp);
        txtContent.setPadding(20, 20, 20, 20);
        builder.setView(txtContent);
        builder.setNegativeButton(R.string.ConfirmDialog_NutNo_karaoke, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                processDefaultCase(activity, KARAOKELIST_DBNAME,
                        KARAOKELIST_DBNAMEWITHPATH, KARAOKELIST_DBTABLENAME,
                        listBaiHat, adapterMusicBaiHat);
            }
        });
        builder.setPositiveButton(R.string.ConfirmDialog_NutYes_karaoke, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               processChooseFile(activity, KARAOKELIST_DBNAME, KARAOKELIST_DBTABLENAME, listBaiHat, adapterMusicBaiHat);
            }
        });
        builder.show();
    }

    private static void processChooseFile(final Activity activity, final String KARAOKELIST_DBNAME,
                                          final String KARAOKELIST_DBTABLENAME, final List<Music> listBaiHat,
                                          final MusicAdapter adapterMusicBaiHat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.fileDialog_title_karaoke);
        final List<File> listSelectedFile = new ArrayList<File>();
        final List<String> listFileName = new ArrayList<String>();
        //Lay danh sach cac noi luu tru co the co
        listFileName.add(0, ""); // current path
        listFileName.add(1, ""); // parent path
        listFileName.addAll(listExternalStoragePath());

        // Set up the listview to show file
        ListView lvFile = new ListView(activity);
        final ArrayAdapter adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listFileName);
        lvFile.setAdapter(adapter);
        builder.setView(lvFile);
        final AlertDialog ad = builder.show();
        lvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                processSelectFileFolder(activity, listFileName, position, adapter, ad,
                        KARAOKELIST_DBTABLENAME, listBaiHat, adapterMusicBaiHat);
            }
        });
    }

    /*
     * Ham processBackFolder() :
     *  > quay len thu muc tren 1 cap
     *      > Tao file thu muc tren 1 cap
     *          > Neu con thu muc cap tren :
     *              > Lay chuoi thu muc tren 1 cap tai listFileName [1] dong 1 listview : duong_dan
     *              > bo ten_thu_muc cuoi cung va dau cach duong dan ra khoi duong_dan
     *              > Liet ke noi dung ra
     *              > cap nhat listFileName cua ListView cua dialog
     *                  > listFileName [0] dong 1 listview : duong_dan_da_cat_bo_thu_muc_hien_tai
     *                  > listFileName [1] duong_dan
     *                  > cac dong con lai noi dung thu muc
     *              > Cap nhat hien thi listview cua dialog
     *          > Neu khong con :
     *              > thoat ham
     */
    private static void processBackFolder(List<String> listFileName, ArrayAdapter adapter) {
        String parentPath = listFileName.get(0);
        File sf = new File(parentPath);
        int pos = parentPath.lastIndexOf(AppConstant.FS);
        if (pos > 0) { // con thu muc tren 1 cap
            String parentOfParentPath = parentPath.substring(0, pos);
            listFileName.clear();
            listFileName.add(0, parentOfParentPath);
            listFileName.add(1, parentPath);

            for (String fileName : sf.list()) listFileName.add(fileName);
            adapter.notifyDataSetChanged();
        } else { // het thu muc tren 1 cap
            return;
        }
    }

    /*
     * Ham processSelectFileFolder() :
        > tao chuoi : duong_dan/ten_folder_hay_file
        > tao bien thuoc lop File tu chuoi
        folder  > open list files
                    > liet ke noi dung
                    > cap nhat hien thi listview cua dialog
                        > cap nhat list cua listview :
                            > Xoa het list
                            > listFileName [0] dong 1 listview : duong_dan
                            > listFileName [1] duong_dan/folder_hien_tai
                            > cac dong con lai noi dung thu muc
                        > cap nhat hien thi adapter
        file    > dong dialog
                > chep file vao may
                    > goi ham chep file vao
                > doc database vao listview
                    > kiem tra co table can doc
                    > xoa het data cua list
                    > doc data vao list
                > cap nhat hien thi listview cua danh sach karaoke
     */
    private static void processSelectFileFolder(Activity activity, List<String> listFileName,
                                                int position, ArrayAdapter adapter, AlertDialog ad,
                                                String KARAOKELIST_DBTABLENAME, List<Music> listBaiHat,
                                                MusicAdapter adapterMusicBaiHat) {
        File selectedFile = null;
        if (position == 0) {
            processBackFolder(listFileName, adapter);
            return;
        }
        else if (position > 1) selectedFile = new File(listFileName.get(1) +
                                                AppConstant.FS + listFileName.get(position));
        else return;
        if (selectedFile.isDirectory()) {
            if (selectedFile.list() == null) Toast.makeText(activity,
                    R.string.ConfirmDialog_Toast_emptyfolder, Toast.LENGTH_SHORT).show();
            String currentPath = listFileName.get(1);
            listFileName.clear();
            listFileName.add(0, currentPath);
            try {
                listFileName.add(1, selectedFile.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String file : selectedFile.list()) {
                listFileName.add(file);
            }
            adapter.notifyDataSetChanged();
        } else if (selectedFile.isFile()) {
            ad.dismiss();
            boolean result;
            String resultString = null;
            try {
                resultString = MyFileIO.copyFile(selectedFile.getName(), activity.getApplicationInfo().dataDir
                        + AppConstant.FS + "databases", new FileInputStream(selectedFile),
                        MyCheckSum.calculateMD5(selectedFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = (resultString.equals("Copy ok.") || resultString.endsWith(" already existed!!"));
            if (result) {
                Toast.makeText(activity, R.string.ConfirmDialog_Toast_copyok_karaoke, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, R.string.ConfirmDialog_Toast_copyfail_karaoke, Toast.LENGTH_SHORT).show();
            }
            result = MySqlite.checkTableExisting(activity, selectedFile.getName(), KARAOKELIST_DBTABLENAME);
            if (!result) {
                Toast.makeText(activity, R.string.ConfirmDialog_Toast_NoTableFound, Toast.LENGTH_SHORT).show();
                return;
            }
            listBaiHat.clear();
            // MySqlite.read() cause wrong database become 12K
            List<String> db = MySqlite.read(activity, selectedFile.getName(),
                    KARAOKELIST_DBTABLENAME, null, null, null, null, null, null);
            db.remove(0); // Xoa dong chua ten cac cot
            for (String song : db) {
                String[] songInfo = song.split(MySqlite.LIMITER);
                Music m = new Music(songInfo[0], songInfo[1] + "\n" + songInfo[2],
                        songInfo[3], (songInfo[5].equals("0")) ? false : true);
                listBaiHat.add(m);
            }
            adapterMusicBaiHat.notifyDataSetChanged();
        }
    }

    private static List<String> listExternalStoragePath() {
        List<String> listPath = new ArrayList<>();
        File storage = new File("/storage");
        File mount = new File("/mnt");
        for (File device : storage.listFiles()) {
            try {
                listPath.add(device.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File device : mount.listFiles()) {
            try {
                listPath.add(device.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listPath;
    }

    private static void processDefaultCase(Activity activity, String KARAOKELIST_DBNAME,
                                           String KARAOKELIST_DBNAMEWITHPATH, String KARAOKELIST_DBTABLENAME,
                                            List<Music> listBaiHat, MusicAdapter adapterMusicBaiHat) {
        boolean result = MyAssetsAndPreferences.copyAssetsToPhone(activity, KARAOKELIST_DBNAME,
                activity.getApplicationInfo().dataDir + AppConstant.FS + "databases",
                KARAOKELIST_DBNAMEWITHPATH);
        if (result) {
            Toast.makeText(activity, R.string.ConfirmDialog_Toast_copyok_karaoke, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, R.string.ConfirmDialog_Toast_copyfail_karaoke, Toast.LENGTH_SHORT).show();
        }
        // MySqlite.read() cause wrong database become 12K
        List<String> db = MySqlite.read(activity, KARAOKELIST_DBNAME,
                KARAOKELIST_DBTABLENAME, null, null, null, null, null, null);
        db.remove(0); // Xoa dong chua ten cac cot
        for (String song : db) {
            String []songInfo = song.split(MySqlite.LIMITER);
            Music m = new Music(songInfo[0], songInfo[1] + "\n" + songInfo[2],
                    songInfo[3], (songInfo[5].equals("0")) ? false : true);
            listBaiHat.add(m);
        }
//        List<Music> a = new ArrayList<Music>(); a.addAll(listBaiHat);
//        listBaiHat.addAll(a);
//        Log.i("My Tag", "2.so luong " + listBaiHat.size());
        adapterMusicBaiHat.notifyDataSetChanged();
    }
}
