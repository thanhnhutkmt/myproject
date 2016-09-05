package util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Nhut on 7/14/2016.
 */
public class MyMedia {
    public static void playSound(Context context, int index) {
        // Chay file nhac hieu ung
        MediaPlayer mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset(); // fix bug app show warning "W/MediaPlayer: mediaplayer went away with unhandled events"
                mp.release();
                mp = null;
            }
        });
        try {
            String []listMusic = context.getAssets().list("music");
            index = (index > (listMusic.length - 1)) ? listMusic.length - 1 : (index < 0) ? 0 : index;
            AssetFileDescriptor afd = context.getAssets().openFd("music"
                    + System.getProperty("file.separator") + listMusic[index]);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }
}
