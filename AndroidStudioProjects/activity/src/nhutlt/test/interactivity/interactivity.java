package nhutlt.test.interactivity;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class interactivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.v("onCreate act1", "STT 1");   
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        
        Log.v("onCreate act1", "STT 2");  
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        
        Log.v("onCreate act1", "STT 4");
        Intent next = new Intent(interactivity.this, interactivity1.class);
        startActivity(next);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        
        Intent next = new Intent(interactivity.this, interactivity1.class);
        startActivity(next);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.v("onCreate act1", "STT 3"); 
        Intent next = new Intent(interactivity.this, interactivity1.class);
        startActivity(next);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        
        Log.v("onCreate act1", "STT 2"); 
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        
        Intent next = new Intent(interactivity.this, interactivity1.class);
        startActivity(next); 
    }
    
    
}