package nhutlt.text.example;

import android.R.anim;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class Serialanimation_imageActivity extends Activity {
	
	private final int SHOW_RESULT = 20;
	private final int DELAY_SHOWING = 4000;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageView anim_image = (ImageView) findViewById(R.id.animation_image);
        anim_image.setBackgroundResource(R.anim.serial_animation);
        
        AnimationDrawable ani = (AnimationDrawable) anim_image.getBackground();
        ani.setOneShot(true);
        ani.start();
        Log.v("NhutLT", "finish animation");
       // mHandler.sendEmptyMessageDelayed(SHOW_RESULT, DELAY_SHOWING);       
    }
}