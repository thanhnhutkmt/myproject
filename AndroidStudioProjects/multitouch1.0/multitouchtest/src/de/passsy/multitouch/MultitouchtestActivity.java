package de.passsy.multitouch;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

public class MultitouchtestActivity extends MultiTouchActivity {
	/** Called when the activity is first created. */

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private TestButton btn4;
	private SeekBar seekbar;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn1 = (Button) findViewById(R.id.button1);
		btn1.setText("My Multitouch is not enabled");
		btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnTouchListener(this);
		btn2.setText("My Multitouch is enabled");
		btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnTouchListener(this);
		btn3.setText("My Multitouch is enabled");
		btn4 = (TestButton) findViewById(R.id.button4);
		btn4.setOnTouchListener(this);
		addMoveOutsideEnabledViews(btn4);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnTouchListener(this);
		addMoveOutsideEnabledViews(seekbar);
	}
}