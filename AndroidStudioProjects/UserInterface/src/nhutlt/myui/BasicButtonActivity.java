package nhutlt.myui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class BasicButtonActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(BasicButtonActivity.this,"hello, this is imagebutton", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void buttonaction(View v){
        Toast.makeText(this,"hello, this is label button", Toast.LENGTH_SHORT).show();
    }
}