package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.crypto.spec.IvParameterSpec;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CipherData;
import software.nhut.personalutilitiesforlife.layout.CircleMenuLayout;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyCheckSum;
import util.MyCipher;
import util.MyDialog;
import util.MyFileIO;

public class DongBoActivity extends Activity {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts;
    private int[] mItemImgs = new int[] {R.drawable.changepassword,
            R.drawable.export_data, R.drawable.output_rar,
            R.drawable.megasync, R.drawable.wifiexport};
//            R.drawable.home_mbank_6_normal };

    private String[] dataPaths = new String[4];
    String currentPath;
    String syncFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sync);
        currentPath = getApplicationInfo().dataDir + AppConstant.FS;
        syncFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + AppConstant.FS + AppConstant.SYNC;
        dataPaths[0] = currentPath + AppConstant.THUMUC_QUANLY_CUOCHEN;
        dataPaths[1] = currentPath + AppConstant.THUMUC_QUANLY_TINNHANDANHBA;
        dataPaths[2] = currentPath + AppConstant.THUMUC_BANDO;
        dataPaths[3] = currentPath + "databases";

        mItemTexts = new String[mItemImgs.length];
        for (int i = 0; i < mItemTexts.length; i++) mItemTexts[i] = "";

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                if (pos == 0) {
                    changePassword();
                } else if (pos == 1) {
                    exportWithEncrypted();
                } else if (pos == 2) {
                    exportWithRar();
                } else if (pos == 3) {
                    uploadToMEGASync();
                } else if (pos == 4) {
                    exportByWifi();
                }
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });
    }

    private void exportByWifi() {

    }

    private void uploadToMEGASync() {

    }

    private void exportWithRar() {

    }

    private void changePassword() {
        final EditText txtPass[] = new EditText[3];
        for (int i = 0; i < txtPass.length; i++) {
            txtPass[i] = new EditText(DongBoActivity.this);
            txtPass[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        MyDialog.showChangePasswordDiaLog(DongBoActivity.this, txtPass, new InputData() {
            @Override public void inputData(String s) {} @Override public void inputData(String s, int color) {}
            @Override public void inputData(String... s) {} @Override public void inputData(List<String> s) {}
            @Override public void inputData(DialogInterface dialog) {
                if (txtPass[0].getText().toString().equals
                        (MyAssetsAndPreferences.getStringFromPreferences(DongBoActivity.this, AppConstant.PASSWORD))) {
                    if (txtPass[1].getText().toString().equals(txtPass[2].getText().toString())) {
                        MyAssetsAndPreferences.saveToPreferences(DongBoActivity.this, AppConstant.PASSWORD, txtPass[1].getText().toString());
                        dialog.dismiss();
                    } else Toast.makeText(DongBoActivity.this, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DongBoActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                    txtPass[0].setText("");
                }
            }
        });
    }

    public void exportWithEncrypted(){
        MyFileIO.clearFolderContent(currentPath + "tempp", true);
        String key = "some keys";
        String outputPath = syncFolder + AppConstant.FS + "ewe";
        MyFileIO.makeFolder(outputPath);
        FilenameFilter fnf = new FilenameFilter() {
            @Override public boolean accept(File dir, String filename) {
                return (!dir.getName().equals("databases") || filename.endsWith(".sqlite"));
            }
        };
        for (String s : dataPaths) {
            File fc[] = new File(s).listFiles(fnf);
            if (fc != null)
                for (File f : fc) {
                    MyCipher.encryptFile(key, outputPath + AppConstant.FS + f.getName() + ".e", f.getAbsolutePath());
//                    MyCipher.decryptFile(key, outputPath + AppConstant.FS + f.getName() + ".d", outputPath + AppConstant.FS + f.getName() + ".e");
//                    boolean result = MyCheckSum.compareFile(new File(f.getAbsolutePath()), new File(outputPath + AppConstant.FS + f.getName() + ".d"));
//                    Log.i("MyTag", f.getName() + " encrypt -> decrypt -> idential " + ((result) ? "OK" : "NO"));
                }
        }
    }


}
