package util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import software.nhut.personalutilitiesforlife.QuanLyTinNhanActivity;
import software.nhut.personalutilitiesforlife.XacThucActivity;

/**
 * Created by Nhut on 8/10/2016.
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback{
    private CancellationSignal cancellationSignal;
    private Activity activity;

    public FingerPrintHandler(Activity activity) {
        this.activity = activity;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override public void onAuthenticationError(int errMsgId, CharSequence errString) { }
    @Override public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {}
    @Override public void onAuthenticationFailed() {
        final TextView txt = ((XacThucActivity)activity).getTxtCambienvantay_xacthuc();
        txt.setTextColor(Color.RED);
        txt.postDelayed(new Runnable() {
            @Override public void run() {
                txt.setTextColor(Color.BLACK);
            }
        }, 3000);
    }
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Intent i = new Intent(activity, QuanLyTinNhanActivity.class);
        activity.startActivity(i);
        activity.finish();
    }
}
