package software.nhut.personalutilitiesforlife;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.FingerPrintHandler;
import util.MyAssetsAndPreferences;
import util.MyDialog;
import com.pantech.fingerscan.FingerScanHelper;
import com.pantech.fingerscan.FingerScanUtils;
import com.pantech.fingerscan.OnTimeoutListener;
import com.pantech.fingerscan.OnVerifyListener;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

public class XacThucActivity extends AppCompatActivity {
    private TextView txtCambienvantay_xacthuc, txtMatKhau_xacthuc;
    private ImageView imgFingerPrint_xacthuc, imgPassword_xacthuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc);
        if(((TelephonyManager)getSystemService(TELEPHONY_SERVICE)).getPhoneType()==TelephonyManager.PHONE_TYPE_NONE){
            //No calling functionality
            Toast.makeText(XacThucActivity.this, R.string.Toast_nophonenetwork_xacthuc_kiemtrachucnangphone
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        txtCambienvantay_xacthuc = (TextView) findViewById(R.id.txtCambienvantay_xacthuc);
        txtMatKhau_xacthuc = (TextView) findViewById(R.id.txtMatKhau_xacthuc);
        imgFingerPrint_xacthuc = (ImageView) findViewById(R.id.imgFingerPrint_xacthuc);
        imgPassword_xacthuc = (ImageView) findViewById(R.id.imgPassword_xacthuc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) authenticateUser();
        else authenticateUserCompat();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void authenticateUser() {
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            CheckFingerPrint();
        } else checkPassword();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void CheckFingerPrint() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(AppConstant.PASSWORD,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(AppConstant.PASSWORD, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            new FingerPrintHandler(this).startAuth((FingerprintManager) getSystemService(FINGERPRINT_SERVICE),
                    new FingerprintManager.CryptoObject(cipher));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPassword() {
        txtCambienvantay_xacthuc.setVisibility(View.GONE);
        imgFingerPrint_xacthuc.setVisibility(View.GONE);
        final EditText txtPass = new EditText(this);
        txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        MyDialog.showPasswordDiaLog(this, new View[]{txtPass}, new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (txtPass.getText().toString().equals
                            (MyAssetsAndPreferences.getStringFromPreferences(XacThucActivity.this, AppConstant.PASSWORD)))
                        passCheck();
                    else {
                        Toast.makeText(XacThucActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                        txtPass.setText("");
                    }
                }
            }, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
    }

    private void passCheck() {
        int notificationID = getIntent().getIntExtra("NotificationID", -1);
        Intent i = new Intent(XacThucActivity.this, QuanLyTinNhanActivity.class);
        i.putExtra("NotificationID", notificationID);
        startActivity(i);
        finish();
    }

    private void authenticateUserCompat() {
        Log.i("Mytag", "Vendor : " + android.os.Build.MANUFACTURER);
        if (android.os.Build.MANUFACTURER.equals("Samsung")) {
            checkFingerPrintSamsung();
        } else if (android.os.Build.MANUFACTURER.equals("PANTECH")) {
            checkFingerPrintSkyPantech();
        } else if (android.os.Build.MANUFACTURER.equals("Lenovo")) {
            checkFingerPrintLenovo();
        } else checkPassword();
    }

    private void checkFingerPrintLenovo() {
        if (FingerScanUtils.isDeviceSupportFingerScan(this) && FingerScanUtils.isEnrolled(this)) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            checkFPSP();
        } else
            checkPassword();
    }

    private void checkFingerPrintSkyPantech() {
        if (FingerScanUtils.isDeviceSupportFingerScan(this) && FingerScanUtils.isEnrolled(this)) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            checkFPSP();
        } else
            checkPassword();
    }

    private void checkFPSP() {
        try {
            final FingerScanHelper mFingerScanHelper = new FingerScanHelper(this);
            OnVerifyListener v = new OnVerifyListener() {
                @Override  public void onVerified(boolean b) {
                    if (b) {
                        passCheck();
                        mFingerScanHelper.stopRequestVerify();
                    } else {
                        txtCambienvantay_xacthuc.setTextColor(Color.RED);
                        txtCambienvantay_xacthuc.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txtCambienvantay_xacthuc.setTextColor(Color.BLACK);
                            }
                        }, 3000);
                    }
                }
            };
            OnTimeoutListener t = new OnTimeoutListener() {
                @Override public void onTimeouted() {
                    Toast.makeText(XacThucActivity.this,
                            R.string.fingerprint_sensor_requestFingerPrintScan_xacthucactivity, Toast.LENGTH_SHORT).show();
                }
            };
            mFingerScanHelper.requestVerify(v);
            mFingerScanHelper.setOnTimeoutListener(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkFingerPrintSamsung() {
        Spass spass = new Spass();
        boolean isFingerPrintSupported = true;
        try {
            spass.initialize(XacThucActivity.this);
        } catch (Exception e) {
            isFingerPrintSupported = false;
            e.printStackTrace();
        }
        if (isFingerPrintSupported && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT)
                && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_FINGER_INDEX)
                && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_AVAILABLE_PASSWORD)) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            checkFPSS();
        } else checkPassword();
    }

    private void checkFPSS() {
        final SpassFingerprint spassFingerprint = new SpassFingerprint(XacThucActivity.this);
        SpassFingerprint.IdentifyListener identifyListener = new SpassFingerprint.IdentifyListener() {
            @Override public void onFinished(int eventStatus) {
                if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                    passCheck();
                } else if (eventStatus == SpassFingerprint.STATUS_TIMEOUT_FAILED) {
                    Toast.makeText(XacThucActivity.this,
                            R.string.fingerprint_sensor_requestFingerPrintScan_xacthucactivity, Toast.LENGTH_SHORT).show();
                } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED
                        && eventStatus == SpassFingerprint.STATUS_QUALITY_FAILED) {
                    txtCambienvantay_xacthuc.setTextColor(Color.RED);
                    txtCambienvantay_xacthuc.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtCambienvantay_xacthuc.setTextColor(Color.BLACK);
                        }
                    }, 3000);
                }
            }
            @Override public void onReady() {}
            @Override public void onStarted() {}
            @Override public void onCompleted() {}
        };
        spassFingerprint.startIdentify(identifyListener);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public TextView getTxtCambienvantay_xacthuc() {
        return txtCambienvantay_xacthuc;
    }
}
