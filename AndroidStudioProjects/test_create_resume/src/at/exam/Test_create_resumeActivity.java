package at.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Test_create_resumeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView box = (TextView) findViewById(R.id.text);
        box.setText("onCreate");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        TextView box = (TextView) findViewById(R.id.text);
        box.setText(box.getText().toString() + "onResume");
        
    }
    

}