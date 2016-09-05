package nhutlt.demo.basic;

import nhutlt.demo.basic.object.MyUrlHistory;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class web_displayer extends Activity {
    private WebView mWebDisplay;
    private Button mLoad, mBack, mForward;
    private EditText mET;
    private MyUrlHistory mUrlStack;
    private static final int BACKBUTTON = 0;
    private static final int CONFIRM = 1;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);
        
        mWebDisplay = (WebView) findViewById(R.id.webpage);
        mLoad = (Button) findViewById(R.id.load);
        mBack = (Button) findViewById(R.id.back);
        mForward = (Button) findViewById(R.id.forward);
        mET = (EditText) findViewById(R.id.urlstring);
        mUrlStack = new MyUrlHistory();
        
        mWebDisplay.getSettings().setJavaScriptEnabled(true);
        mWebDisplay.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                mUrlStack.add(url);
                mForward.setEnabled(mUrlStack.getForwardStatus());
                mBack.setEnabled(mUrlStack.getBackStatus());
                mET.setText(mUrlStack.getBack());
                return true;
           }
        });
        mLoad.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
            	String urlFromStack = mUrlStack.getLastOut();
            	String urlInEditText = mET.getText().toString();
            	String url;
            	if (urlFromStack == null) {
            		url = urlInEditText;
            	} else {
            		url = urlFromStack;
            	}
                mWebDisplay.loadUrl(url);
            }
        });
        mBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mWebDisplay.loadUrl(mUrlStack.getBack());
			}
		});
        mForward.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mWebDisplay.loadUrl(mUrlStack.getForward());
			}
		});
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		showDialog(CONFIRM);
		if (mExit) {
			super.onBackPressed();
			this.finish();
		}
	}
	
	private boolean mExit = false;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder ad = new Builder(this);
		ad
		.setTitle("Confirm")
		.setMessage("Are you sure want to exit webpage?")
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				mExit = true;
				onBackPressed();
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				//mExit = false;				
			}
		});
		return (Dialog) ad.create();
	}

}
