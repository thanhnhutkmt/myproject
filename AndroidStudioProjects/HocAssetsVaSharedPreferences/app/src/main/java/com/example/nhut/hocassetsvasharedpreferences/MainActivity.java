package com.example.nhut.hocassetsvasharedpreferences;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView txtMyName;
    ListView lvDanhSachFont;
    String listFont[];
    String listMusic[];
    ArrayAdapter adapter;
    String tenLuuTru = "luutru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        lvDanhSachFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                xuLyFontChu(position);
            }
        });
    }

    private void xuLyFontChu(int position) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font" + System.getProperty("file.separator") + listFont[position]);
        txtMyName.setTypeface(typeface);
        // Luu font da thiet dat
        SharedPreferences sp = getSharedPreferences(tenLuuTru, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("FONTCHU", "font" + System.getProperty("file.separator") + listFont[position]);
        e.commit();

        int listMusicLength = listMusic.length;
        int listFontLength = listFont.length;
        int i;
        if (listFontLength == listMusicLength) i = position;
        else {
            int temp = (listFontLength > listMusicLength)
                    ? listFontLength / listMusicLength
                    : listMusicLength / listFontLength;
            int temp1 = position / temp;
            i = (temp1 > (listMusicLength - 1)) ? listMusicLength - 1 : temp1;
            playSound(i);
//            play();
        }
    }

    private void playSound(int index) {
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
            AssetFileDescriptor afd = getAssets().openFd("music" + System.getProperty("file.separator") + listMusic[index]);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    private void play(int index) {
        MediaPlayer m = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("music" + System.getProperty("file.separator") + "tada.wav");
            m.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength()
            );
            afd.close();
            m.prepare();
            m.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addControls() {
        txtMyName = (TextView) findViewById(R.id.txtMyName);
        lvDanhSachFont = (ListView) findViewById(R.id.lvDanhSachFont);
        try {
            listFont = getAssets().list("font");
            listMusic = getAssets().list("music");
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter(
            MainActivity.this,
            android.R.layout.simple_list_item_1,
            listFont
        );
        lvDanhSachFont.setAdapter(adapter);

        SharedPreferences sp = getSharedPreferences(tenLuuTru, MODE_PRIVATE);
        String definedFont = sp.getString("FONTCHU", null);
        if(definedFont == null) return;
        Typeface tf = Typeface.createFromAsset(getAssets(), definedFont);
        txtMyName.setTypeface(tf);
    }
}
