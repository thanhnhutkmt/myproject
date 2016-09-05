package nhutlt.example.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Notification_exampleActivity extends Activity implements OnClickListener{
	private final int notificationID = 9;
	private NotificationManager nm;
	private Notification n;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button cnb = (Button) findViewById(R.id.call_notification);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);		
        cnb.setOnClickListener(this);
        nm.cancel(notificationID);	        
    }

	@Override
	public void onClick(View v) {
		String body = "Hi, this is my notification";
        Intent intent = new Intent(this, notification_next_page.class);	
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		
		n = new Notification(R.drawable.ic_launcher, body, System.currentTimeMillis());
		n.setLatestEventInfo(this, "You have notification", body, pi);
		n.defaults = Notification.DEFAULT_ALL;
		n.flags = Notification.FLAG_AUTO_CANCEL;
		
		nm.notify(notificationID, n);	
	}
		
}