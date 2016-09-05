package nhutlt.demo.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

public class SlidingDrawer_exampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlandscape, R.layout.mainportrait);
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#setContentView(int)
	 */
	public void setContentView(int landscapelayoutResID, int portraitlayoutResID) {
		WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		if (wm != null) {
			int orientation = wm.getDefaultDisplay().getOrientation();
			if (orientation == 0) {
				super.setContentView(portraitlayoutResID);
			} else if (orientation == 1) {
				super.setContentView(landscapelayoutResID);
			}
		}
	}
}