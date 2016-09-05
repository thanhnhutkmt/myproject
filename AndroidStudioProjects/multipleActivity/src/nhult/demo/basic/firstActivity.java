package nhult.demo.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class firstActivity extends Activity {
	EditText et;
	TextView tv;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstscreen);
		
		Button bt = (Button) findViewById(R.id.nextAct);
		tv = (TextView) findViewById(R.id.info);
		et = (EditText) findViewById(R.id.requestCode);

		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String stringRequestCode = et.getText().toString();
				int requestCode;
				if (stringRequestCode == null || stringRequestCode.isEmpty()) {
					requestCode = 0;
				} else {
					requestCode = Integer.parseInt(stringRequestCode);
				}
				Intent secondscreen = new Intent(firstActivity.this, secondActivity.class);
				startActivityForResult(secondscreen, requestCode);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#startActivityForResult(android.content.Intent, int)
	 */
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		intent.putExtra("requestCode", requestCode);
		super.startActivityForResult(intent, requestCode);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		tv.setText("Result Code : " + Integer.toString(resultCode));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("onPause", "app calls onPause");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.v("onStop", "app calls onStop");
	}
	
	
}
