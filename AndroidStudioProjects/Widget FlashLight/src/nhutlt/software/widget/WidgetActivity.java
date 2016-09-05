package nhutlt.software.widget;

import nhutlt.software.widget.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetActivity extends AppWidgetProvider {
	public static WidgetActivity Widget = null;
	public static Context context;
	public static AppWidgetManager appWidgetManager;
	public static int appWidgetIds[];
	private static Camera mCam;
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"EEE dd MMM yyyy HH:mm:ss");
	private static boolean mTimeDisplay = true;
	private static boolean mStatusLight = false;
	private static TelephonyManager mPhone;
	private static myphonesignal mSignalChecker;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		if (null == context)
			context = WidgetActivity.context;
		if (null == appWidgetManager)
			appWidgetManager = WidgetActivity.appWidgetManager;
		if (null == appWidgetIds)
			appWidgetIds = WidgetActivity.appWidgetIds;

		WidgetActivity.Widget = this;
		WidgetActivity.context = context;
		WidgetActivity.appWidgetManager = appWidgetManager;
		WidgetActivity.appWidgetIds = appWidgetIds;

		Log.i("PXR", "onUpdate");
		// Toast.makeText(context, "onUpdate", Toast.LENGTH_SHORT).show();

		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}

	}

	public static class myphonesignal extends PhoneStateListener {
		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength) {
			super.onSignalStrengthsChanged(signalStrength);
			Vibrator vibrator = (Vibrator) context
					.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(3000);
			File signalLog = new File(Environment.getExternalStorageDirectory() + File.separator + "SignalLog.txt");
			try {
				if (!signalLog.exists()) {
					signalLog.createNewFile();
				}
				if (signalLog.canWrite()) {
					FileOutputStream fOut = new FileOutputStream(signalLog, true);
					OutputStreamWriter osw = new OutputStreamWriter(fOut); 
					osw.write(Integer.toString(signalStrength.getGsmSignalStrength()));
					osw.flush();
					osw.close();
				    System.out.println("file created: " + signalLog);
				} else {
					Toast.makeText(context, "write failed", Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mStatusLight = false;
			WidgetActivity.Widget.onUpdate(context, appWidgetManager,
					appWidgetIds);
			mHandler.sendMessageDelayed(new Message(), 1000);
		}
	};

	static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {

		CharSequence text = format.format(new Date());
		Intent intent = new Intent(context, UpdateService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,
				intent, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		remoteViews.setOnClickPendingIntent(R.id.LinearLayout01, pendingIntent);
		remoteViews.setTextViewText(R.id.widget_textview, text);
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

		if (mStatusLight) {
			if (mCam != null) {
				mCam.stopPreview();
				mCam.release();
				mCam = null;
			} else {
				mCam = Camera.open(CameraInfo.CAMERA_FACING_BACK);
				Parameters camParam = mCam.getParameters();
				camParam.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCam.setParameters(camParam);
				mCam.startPreview();
			}
		}

		if (mTimeDisplay) {
			mHandler.sendMessageDelayed(new Message(), 1000);
			mTimeDisplay = false;
			mSignalChecker = new myphonesignal();
			mPhone = (TelephonyManager) context
					.getSystemService(context.TELEPHONY_SERVICE);
			mPhone.listen(mSignalChecker,
					PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		}
		// Tell the widget manager

	}

	public static class UpdateService extends Service {
		@Override
		public void onStart(Intent intent, int startId) {
			mStatusLight = true;
			WidgetActivity.Widget.onUpdate(context, appWidgetManager,
					appWidgetIds);
			mStatusLight = false;
			String text = null;
			if (mCam != null) {
				text = "Light On";
			} else {
				text = "Light Off";
			}
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
	}

}