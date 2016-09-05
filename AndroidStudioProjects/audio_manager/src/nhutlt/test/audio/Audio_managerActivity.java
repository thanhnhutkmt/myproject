package nhutlt.test.audio;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class Audio_managerActivity extends Activity implements OnSeekBarChangeListener{
	private AudioManager am;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar1);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxV = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        
        sb.setMax(maxV);
        sb.setProgress(curV);
        
        sb.setOnSeekBarChangeListener(this);
    }    

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}