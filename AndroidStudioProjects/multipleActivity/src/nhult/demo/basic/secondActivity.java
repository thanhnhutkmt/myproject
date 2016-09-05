package nhult.demo.basic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class secondActivity extends Activity {
	EditText etResultCode, etPhone;
	String requestCode;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondscreen);
		
		String requestCode = Integer.toString(getIntent().getIntExtra("requestCode", 0));
		etResultCode = (EditText) findViewById(R.id.resultCode);
		TextView tv = (TextView) findViewById(R.id.requestCode);
		Button btBack = (Button) findViewById(R.id.back);
		Button btCall = (Button) findViewById(R.id.call);
		Button btWebSite = (Button) findViewById(R.id.website);
		etPhone = (EditText) findViewById(R.id.telephone);

		tv.setText("Request Code : " + requestCode);
		btBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int resultCode;
				String stringResultCode = etResultCode.getText().toString();
				Intent firstscreen = new Intent(secondActivity.this, firstActivity.class);
				
				if (stringResultCode == null || stringResultCode.isEmpty()) {
					resultCode = 0;
				} else {
					resultCode = Integer.parseInt(stringResultCode);
				}
				setResult(resultCode, firstscreen);
				finish();
			}
		});

		btCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent call = new Intent(Intent.ACTION_CALL);
				call.setData(Uri.parse("tel:" + etPhone.getText().toString()));
                startActivity(call);
			}
		});
		
		btWebSite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent openWeb = new Intent(
						Intent.ACTION_VIEW, Uri.parse("http://www.5giay.vn"));
				startActivity(openWeb);
			}
		});
	}
}
