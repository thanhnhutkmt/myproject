package util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;

/**
 * Created by Nhut on 6/19/2016.
 */
public class MyFileIO {
    public static boolean saveData(List<?> list, String path, String fileName) {
        boolean result = true;
        ObjectOutput oo = null;
        String dataPath = path + System.getProperty("file.separator") + fileName;
        File f = new File(dataPath);
        if (f.exists()) f.delete();
        try {
            oo = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(f)));
            oo.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                oo.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public static List<?> loadData(String appPath, String fileName) {
        List<Object> listObject = null;
        ObjectInput oi = null;
        String dataFileNamePath = appPath + System.getProperty("file.separator") + fileName;
        if (dataFileNamePath == null || dataFileNamePath.trim().length() == 0) return listObject;
        try {
            oi = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(dataFileNamePath)));
            listObject = (List<Object>) oi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public static boolean clearFolderContent(String path) {
        boolean result = true;
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            File[] content = folder.listFiles();
            for (File f : content) {
                result = f.delete();
            }
        }
        return result;
    }

    public static boolean makeFolder(String folderName) {
        File folder = new File(folderName);
        if(!folder.exists()) return folder.mkdirs();
        else return true;
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

    public static String copyFile(String fileNameToSave, String path, InputStream assetsfFile) {
        final String DBNAMEWITHPATH =  path + System.getProperty("file.separator") + fileNameToSave;
        OutputStream outputStream = null;
        File f = new File(path);
        if (!f.exists()) f.mkdirs();
        f = new File(DBNAMEWITHPATH);
        try {
            if (!f.exists()) f.createNewFile();
            else return DBNAMEWITHPATH + " already existed!!";
            outputStream = new FileOutputStream(f);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = assetsfFile.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            assetsfFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Copy ok.";
    }

}
