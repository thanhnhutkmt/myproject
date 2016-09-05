package com.helloandroid.android.progressdialogexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

public class ProgressDialogExample extends Activity implements Runnable {

	private String pi_string;
	private TextView tv;
	private ProgressDialog pd;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		tv = (TextView) this.findViewById(R.id.main);
		tv.setText("Press any key to start calculation");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		pd = ProgressDialog.show(this, "Working..", "Calculating Pi", true,
				false);

		Thread thread = new Thread(this);
		thread.start();

		return super.onKeyDown(keyCode, event);
	}

	public void run() {
		pi_string = Pi.computePi(800).toString();
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			tv.setText(pi_string);

		}
	};

}