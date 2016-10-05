package software.nhut.personalutilitiesforlife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyCheckSum;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;

public class MainActivity extends AppCompatActivity {
    Query getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyDateTime.getDateString(System.currentTimeMillis(),
                AppConstant.FULLTIMEFORMATWITHSECOND_WITHOUTNEWLINE) + " message");

        myRef.setValue("Login is run!");
        getData = myRef.orderByKey();
        getData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        askForLogin();
    }

    private void askForLogin() {
        final EditText txtPass[] = new EditText[] {new EditText(this), new EditText(this)};
        txtPass[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (MyAssetsAndPreferences.getStringFromPreferences(MainActivity.this, AppConstant.PASSWORD).equals("")) {
            MyDialog.showNewPasswordDiaLog(this, txtPass, new InputData() {
                    @Override public void inputData(String s) {}
                    @Override public void inputData(DialogInterface dialog) {
                        if (txtPass[0].getText().toString().equals(txtPass[1].getText().toString())) {
                            MyAssetsAndPreferences.saveToPreferences(MainActivity.this, AppConstant.PASSWORD, txtPass[0].getText().toString());
                            dialog.dismiss();
                        } else Toast.makeText(MainActivity.this, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                    }
                    @Override public void inputData(String... s) {}
                    @Override public void inputData(List<String> s) {}
                    @Override public void inputData(String s, int color) {}
                });
        } else {
            MyDialog.showNewPasswordDiaLog(this, new View[]{txtPass[0]}, new InputData() {
                    @Override public void inputData(String s) {}
                    @Override public void inputData(DialogInterface dialog) {
                        if (txtPass[0].getText().toString().equals
                                (MyAssetsAndPreferences.getStringFromPreferences(MainActivity.this, AppConstant.PASSWORD)))
                            dialog.dismiss();
                        else {
                            Toast.makeText(MainActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                            txtPass[0].setText("");
                        }
                    }
                    @Override public void inputData(String... s) {}
                    @Override public void inputData(List<String> s) {}
                    @Override public void inputData(String s, int color) {}
                });
        }
    }

    public void moCuocHen(View view) {
        openActivity(QuanLyCuocHenActivity.class);
    }

    public void moKaraoke(View view) {
        openActivity(KaraokeActivity.class);
    }

    public void moTinNhan(View view) {
        openActivity(XacThucActivity.class);
    }

    public void moTroChoi(View view) {

    }

    public void moHinhPhim(View view) {

    }

    public void moGhiChu(View view) {

    }

    public void moDongBo(View view) {
        openActivity(DongBoActivity.class);
    }

    public void moBanDo(View view) {
        openActivity(BanDoActivity.class);
    }

    public void moDoiTien(View view) {
        openActivity(TiGiaActivity.class);
    }

    private void openActivity(Class<?> nextActivity) {
        Intent i = new Intent(this, nextActivity);
        startActivity(i);
    }
}
