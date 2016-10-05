package software.nhut.personalutilitiesforlife;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.layout.CircleMenuLayout;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyDialog;

public class DongBoActivity extends Activity {
    private CircleMenuLayout mCircleMenuLayout;

    private String[] mItemTexts;
    private int[] mItemImgs = new int[] {R.drawable.changepassword};
//            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
//            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
//            R.drawable.home_mbank_6_normal };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sync);
        mItemTexts = new String[mItemImgs.length];
        for (int i = 0; i < mItemTexts.length; i++) mItemTexts[i] = "";

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                if (pos == 0) {
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
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });
    }
}
