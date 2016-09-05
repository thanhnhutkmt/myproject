package de.passsy.multitouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class TestButton extends Button {

	public TestButton(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		Log.v("tag", "I get touched");
		setText("I recive a MotionEvent");
		if (event.getAction() == MotionEvent.ACTION_UP) {
			setText("I can recive Move events outside of my View");
		}
		return super.onTouchEvent(event);
	}
}
