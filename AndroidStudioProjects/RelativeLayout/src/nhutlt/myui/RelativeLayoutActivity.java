package nhutlt.myui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class RelativeLayoutActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        EditText ed = new EditText(this);
        Button bt = new Button(this);
        LayoutParams lp1 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);    
        LayoutParams lp3 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp1.addRule(RelativeLayout.LEFT_OF, 200);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);        

        ed.setHint("input text here");
        ed.setLayoutParams(lp1);
        ed.setId(100);
        bt.setText("Click here");
        bt.setLayoutParams(lp2);
        bt.setId(200);
        
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(lp3);
        rl.addView(bt);
        rl.addView(ed);
        setContentView(rl);
    }
}
