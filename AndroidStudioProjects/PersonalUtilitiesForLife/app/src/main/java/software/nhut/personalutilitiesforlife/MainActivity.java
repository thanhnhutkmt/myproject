package software.nhut.personalutilitiesforlife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.internal.zzaba;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.ViTri;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyCheckSum;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginFirebase();
//        uploadFile();
        askForLogin();
    }

    private void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(AppConstant.BUCKETNAME);
        Uri file = Uri.fromFile(new File("/storage/emulated/0/zalo/abc.jpg"));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file); // start the upload task
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Upload OK!", Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.i("MyTag", "Download link " + downloadUrl);
            }
        });
    }

    private void loginFirebase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = InstanceID.getInstance(MainActivity.this).getToken("model-ruler-136223","");
                    Log.i("MyTag", "info : instance ID " + InstanceID.getInstance(MainActivity.this).getId()
                            + " , token " + token);
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConstant.Topic_ID);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        startService(new Intent(MainActivity.this, MyFirebaseMessagingService.class));

//        FirebaseMessaging fm = FirebaseMessaging.getInstance();
//        fm.send(new RemoteMessage.Builder(AppConstant.SENDER_ID + "@gcm.googleapis.com")
//                .setMessageId(Integer.toString((int) (Math.random() * 1000)))
//                .addData("my_message", "Nhut : Hello World")
//                .addData("my_action","SAY_HELLO")
//                .build());


        // Write a message to the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        String timestamp = MyDateTime.getDateString(System.currentTimeMillis(), AppConstant.FULLTIMEFORMATWITHSECOND_WITHOUTNEWLINE);
        myRef.child(AppConstant.UID_FIREBASE).child(timestamp).setValue("Login is run!", new DatabaseReference.CompletionListener() {
            @Override public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) Toast.makeText(MainActivity.this, databaseError.toString() + "\n", Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child(AppConstant.UID_FIREBASE).addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Log.i("MyTag", dataSnapshot.getValue().toString());
                }
            }
            @Override public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) Toast.makeText(MainActivity.this, "Signed-in.", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(MainActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.signInWithEmailAndPassword(AppConstant.USERNAME_FIREBASE, AppConstant.PASS_FIREBASE).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) Toast.makeText(MainActivity.this, "Error sign-in", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(MainActivity.this, "Sign-in successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null && mAuth != null) {
            mAuth.signOut();
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
            MyDialog.showNewPasswordDiaLog(MainActivity.this, new View[]{txtPass[0]}, new InputData() {
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
