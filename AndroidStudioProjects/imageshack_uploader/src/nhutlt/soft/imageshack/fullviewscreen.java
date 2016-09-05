package nhutlt.soft.imageshack;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class fullviewscreen extends Activity {

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullviewscreen);
        ImageView iv = (ImageView)findViewById(R.id.imageview);
        VideoView vv = (VideoView) findViewById(R.id.videoview);
        
        Intent intent = this.getIntent();
        String filePath = "/sdcard/DCIM/Camera/" + intent.getStringExtra("FileName");
        int type = intent.getIntExtra("Type", -1);

        if (type == 0) {
            showImageFile(filePath);
            vv.setVisibility(VideoView.GONE);
            iv.setVisibility(ImageView.VISIBLE);
        } else if (type == 1) {
            playVideoFile(this, filePath);
            iv.setVisibility(ImageView.GONE);
            vv.setVisibility(VideoView.VISIBLE);
        }

    }

    public void showImageFile(String filePath) {
        ImageView iv = (ImageView)findViewById(R.id.imageview);
        FileInputStream in;
        BufferedInputStream buf;
        try 
        {
            in = new FileInputStream(filePath);
            buf = new BufferedInputStream(in, 1024);
            byte[] bMapArray= new byte[buf.available()];
            buf.read(bMapArray);
            Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
            iv.setImageBitmap(bMap);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }
        }
        catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }
    }
    
    public void playVideoFile(Context context, String filePath) {
    	getWindow().setFormat(PixelFormat.TRANSLUCENT);
    	VideoView vv = (VideoView) findViewById(R.id.videoview);
        //MediaController is the ui control howering above the video (just like in the default youtube player).
        vv.setMediaController(new MediaController(context));
        //assing a video file to the video holder
        vv.setVideoURI(Uri.parse(filePath));
        //get focus, before playing the video.
        vv.requestFocus();
        vv.start();
    }
    
    public void playAudioFile(String filePath) {
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(filePath);
            mp.prepare();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }
}
