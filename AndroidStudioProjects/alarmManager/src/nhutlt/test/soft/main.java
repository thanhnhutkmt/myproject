package nhutlt.test.soft;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends Activity {
	private Button set;
	private EditText hour;
	private EditText minute;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        set = (Button) findViewById(R.id.alarm);
        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        
        set.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
		        //get time
		        Date time = new Date(System.currentTimeMillis());
		        time.setHours(Integer.parseInt(hour.getText().toString()));
		        time.setMinutes(Integer.parseInt(minute.getText().toString()));
		        time.setSeconds(59);
		        Intent intent = new Intent(main.this, Alarm.class);		        		        
		        PendingIntent pendingIntent = PendingIntent.getBroadcast
		        		(main.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		        //set time
		        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		        am.cancel(pendingIntent);
		        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 10000, pendingIntent);	
		        Toast.makeText(main.this, "10 seconds gone.", Toast.LENGTH_LONG).show();
			}
		});
	}
}
