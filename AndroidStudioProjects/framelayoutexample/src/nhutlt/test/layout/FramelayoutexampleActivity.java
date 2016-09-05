package nhutlt.test.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FramelayoutexampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView topText = new TextView(this);       
        topText.setText("top Text of frame layout");
        topText.setGravity(Gravity.LEFT);
        
        TextView bottomText = new TextView(this);
        bottomText.setText("bottom Text of frame layout");
        bottomText.setGravity(Gravity.CENTER);
        
        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        fl.addView(bottomText);
        fl.addView(topText);
        setContentView(fl);
    }
}