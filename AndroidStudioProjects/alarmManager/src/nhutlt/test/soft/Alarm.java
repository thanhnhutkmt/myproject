package nhutlt.test.soft;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends Service {

	@Override
	public void onCreate() {

		Toast.makeText(this, "10 seconds gone.", Toast.LENGTH_LONG).show();
		Log.v("NhutLT", "thanks");
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

}
