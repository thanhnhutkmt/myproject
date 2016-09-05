package nhutlt.androidapp.simplemediaplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nhutlt.androidapp.dialog.Fileselector;
import nhutlt.androidapp.filerevealer.util.FileInfoUtility;
import nhutlt.androidapp.simplemediaplayer.adapter.listSongAdapter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.VideoView;

public class Mediaplayer extends Activity implements OnClickListener{
	private List<String> playlistPath, playlistName, playlistSize, playlistDuraton, playlistType;
	private Button playBT, pauseBT, stopBT, repeatBT, nextBT, prevBT;
	private Button miniBT, fullScreenBT;
	private MediaPlayer mp;
	private int currentSong;
	private boolean loop;
	private VideoView video;
	private SurfaceHolder sHolder;
	private Context context;
	private NotificationManager nManager;
	private static final int UNIQUEID = 121355115; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediaplayer);
		context = this;
		mp = new MediaPlayer();
		playlistPath = new ArrayList<String>();
		playlistName = new ArrayList<String>();
		playlistSize = new ArrayList<String>();
		playlistDuraton = new ArrayList<String>();
		playlistType = new ArrayList<String>();
		playBT = (Button) findViewById(R.id.play);
		playBT.setOnClickListener(this);
		pauseBT = (Button) findViewById(R.id.pause);
		pauseBT.setOnClickListener(this);
		stopBT = (Button) findViewById(R.id.stop);
		stopBT.setOnClickListener(this);
		repeatBT = (Button) findViewById(R.id.repeat);
		repeatBT.setOnClickListener(this);
		nextBT = (Button) findViewById(R.id.next);
		nextBT.setOnClickListener(this);
		prevBT = (Button) findViewById(R.id.previous);
		prevBT.setOnClickListener(this);
		fullScreenBT = (Button) findViewById(R.id.fullscreen);
		fullScreenBT.setOnClickListener(this);
		miniBT = (Button) findViewById(R.id.minimize);
		miniBT.setOnClickListener(this);
		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		currentSong = 0;
		video = (VideoView) findViewById(R.id.video);
		sHolder = video.getHolder();
		sHolder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
		sHolder.setFixedSize(300, 300);
		sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mediaplayermenu, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent next;
		switch (item.getItemId()) {
		// Select open menu
//	        case R.id.sdcard:
//	        	next = new Intent(getApplicationContext(), Fileselector.class);
//	        	next.putExtra("path", Environment.getExternalStorageDirectory().getAbsolutePath());
//	        	startActivity(next);
//	            return true;
//	        case R.id.root:
//	        	next = new Intent(getApplicationContext(), Fileselector.class);
//	        	next.putExtra("path", Environment.getRootDirectory().getAbsolutePath());
//	        	startActivity(next);
//	            return true;
        case R.id.openmenu:
        	next = new Intent(getApplicationContext(), Fileselector.class);          	
			next.putStringArrayListExtra("playlistName", (ArrayList<String>) playlistName);
			next.putStringArrayListExtra("playlistSize", (ArrayList<String>) playlistSize);
			next.putStringArrayListExtra("playlistPath", (ArrayList<String>) playlistPath);
			next.putStringArrayListExtra("playlistType", (ArrayList<String>) playlistType);
			next.putStringArrayListExtra("playlistDuration", (ArrayList<String>) playlistDuraton);
			
        	startActivity(next);
            return true;
	    // Select playlist menu
	        case R.id.showcurrentplaylist:
//	        	showCurrentPlaylist();
	    		final Dialog playlist = new Dialog(context);
	    		playlist.setTitle(R.string.playlist_title);
	    		playlist.setContentView(R.layout.playlist);
	    		final ListView listSongs = (ListView) playlist.findViewById(R.id.listsongs);
	    		Button addBT = (Button) playlist.findViewById(R.id.add);
	    		Button closeBT = (Button) playlist.findViewById(R.id.close);
	    		Button removeBT = (Button) playlist.findViewById(R.id.remove);
	    		Button clearBT = (Button) playlist.findViewById(R.id.removeall);
	    		
	    		listSongs.setAdapter(new listSongAdapter(context,
	    				R.layout.songitem, playlistName, playlistType, playlistDuraton,
	    				playlistSize, currentSong));
	    		for (String filePath : playlistPath) {
	    			Log.v("NhutLT", "show playlist filepath " + filePath);
	    		}
	    		Log.v("NhutLT", "show playlist plp " + playlistPath.size());
	    		Log.v("NhutLT", "show playlist pln " + playlistName.size());
	    		Log.v("NhutLT", "show playlist plt " + playlistType.size());
	    		Log.v("NhutLT", "show playlist pld " + playlistDuraton.size());
	    		Log.v("NhutLT", "show playlist pls " + playlistSize.size());
	    		
	    		closeBT.setOnClickListener(new OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {
	    				playlist.dismiss();
	    			}
	    		});
	    		
	    		clearBT.setOnClickListener(new OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {
	    				playlistName.clear();
	    				playlistType.clear();
	    				playlistPath.clear();
	    				playlistSize.clear();
	    				playlistDuraton.clear();
	    	    		listSongs.setAdapter(new listSongAdapter(context,
	    	    				R.layout.songitem, playlistName, playlistType, playlistDuraton,
	    	    				playlistSize, currentSong));
	    			}
	    		});
	    		playlist.show();
	            return true;
	        case R.id.showallplaylistname:
	        	
	            return true;
	        case R.id.createplaylist:
	        	
	            return true;
	        case R.id.exitmenu:
	    		Display screen = getWindowManager().getDefaultDisplay();	
	    		ViewGroup.LayoutParams lp = video.getLayoutParams();
	    		
	        	if (lp.height == screen.getHeight() && lp.width == screen.getWidth()) {
	        		switchToNormalScreen();
	        		return true;
	        	}
	        	mp.stop();
	        	mp.reset();
	        	mp.release();
				Intent exit = new Intent(Intent.ACTION_MAIN);
				exit.addCategory(Intent.CATEGORY_HOME);
				exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exit);
				return true;
	        default :

	        	return true;
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// initialize mp again
		mp = new MediaPlayer();
		// Get new song data
		ArrayList<String> newSongsName = this.getIntent().getStringArrayListExtra("playlistName");
		ArrayList<String> newSongsSize = this.getIntent().getStringArrayListExtra("playlistSize");
		ArrayList<String> newSongsPath = this.getIntent().getStringArrayListExtra("playlistPath");
		ArrayList<String> newSongsDuration = new ArrayList<String>();
		int index = 0;
		if (newSongsPath != null) {
			for (String song : newSongsPath) {
				mp.reset();
				try {
					mp.setDataSource(newSongsPath.get(index));
					mp.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				newSongsDuration.add(FileInfoUtility.changeDurationFormat((mp.getDuration())));
				index++;
			}
		}
		mp.reset();
		ArrayList<String> newSongsType = this.getIntent().getStringArrayListExtra("playlistType");
		
		// get original playing playlist
		if (this.getIntent().getStringArrayListExtra("playlistNameo") != null) {
			playlistName = this.getIntent().getStringArrayListExtra("playlistNameo");	
		}
		if (this.getIntent().getStringArrayListExtra("playlistSizeo") != null) {
			playlistSize = this.getIntent().getStringArrayListExtra("playlistSizeo");
		}
		if (this.getIntent().getStringArrayListExtra("playlistPatho") != null) {
			playlistPath = this.getIntent().getStringArrayListExtra("playlistPatho");
		}
		if (this.getIntent().getStringArrayListExtra("playlistTypeo") != null) {
			playlistType = this.getIntent().getStringArrayListExtra("playlistTypeo");
		}
		if (this.getIntent().getStringArrayListExtra("playlistDurationo") != null) {
			playlistDuraton = this.getIntent().getStringArrayListExtra("playlistDurationo");	
		}
		
		// Add new song to current playlist
		
		if (newSongsPath != null && newSongsPath.size() > 0) {
			int numberOfNewSongs = newSongsPath.size();
			for (int index1 = 0; index1 < numberOfNewSongs; index1++) {
				if (!playlistPath.contains(newSongsPath.get(index1))) {
					playlistPath.add(newSongsPath.get(index1));
					if (newSongsDuration != null) {
						playlistDuraton.add(newSongsDuration.get(index1));
					}
					if (newSongsType != null) {
						playlistType.add(newSongsType.get(index1));
					}
					if (newSongsName != null) {
						playlistName.add(newSongsName.get(index1));
					}
					if (newSongsSize != null) {
						playlistSize.add(newSongsSize.get(index1));
					}
				}
				Log.v("NhutLT", "resume " + newSongsPath.get(index1));
			}
		}		
		
		// Turn off Notification
		nManager.cancel(UNIQUEID);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.play:				
				playMediaFile();
				break;
			case R.id.pause:
				mp.pause();
				break;
			case R.id.stop:
				mp.stop();
				mp.reset();
				break;
			case R.id.next:
				currentSong++;
				if (currentSong > playlistPath.size() - 1) {
					currentSong--;
				}
				playMediaFile();
				break;
			case R.id.previous:
				currentSong--;
				if (currentSong < 0) {
					currentSong++;
				}
				playMediaFile();
				break;
			case R.id.repeat:
				loop = !loop;
				mp.setLooping(loop);
				break;
			case R.id.fullscreen:
				switchToFullScreen();
				break;
			case R.id.minimize:
				Intent openSMPapp = new Intent(this, Mediaplayer.class);
				PendingIntent pOpenSMPApp = PendingIntent.getActivity(context, 0, openSMPapp, 0);
				Notification noti = new Notification(R.drawable.icon_notification,
						this.getResources().getString(R.string.app_name),
						System.currentTimeMillis() + 1000);
				String songName;
				if(playlistName.size() == 0) {
					songName = "";
				} else {
					songName = playlistName.get(currentSong);
				}
				noti.setLatestEventInfo(context, 
						getResources().getString(R.string.app_name), 
						songName, pOpenSMPApp);
				noti.defaults = Notification.DEFAULT_ALL;
				nManager.notify(UNIQUEID, noti);
				Intent exit = new Intent(Intent.ACTION_MAIN);
				exit.addCategory(Intent.CATEGORY_HOME);
				exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exit);
				break;
		}		
	}	
	
	private void showCurrentPlaylist() {

	}
	
	private void showAllPlaylists() {
		
	}
	
	private void createPlaylist() {
		
	}
	
	@SuppressLint("NewApi")
	private void playMediaFile() {
		if (playlistPath.size() > 0) {
			try {
				if (mp.isPlaying()) {
					mp.stop();
					mp.reset();
				}
				Log.v("NhutLT","stop & reset");
				if (playlistType.get(currentSong).equals("mp3")) {
					
				} else {
					mp.setDisplay(sHolder);
					mp.setScreenOnWhilePlaying(true);
					
				}
				mp.setDataSource(playlistPath.get(currentSong));
				Log.v("NhutLT", "currentSong " + currentSong);
				mp.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mp.start();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.v("NhutLT", "on paused _");		
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.v("NhutLT", "on stop _");
		super.onStop();
	}
	
	private void switchToFullScreen() {

		LinearLayout bta1 = (LinearLayout) findViewById(R.id.btarray1);
		LinearLayout bta2 = (LinearLayout) findViewById(R.id.btarray2);
		bta1.setVisibility(LinearLayout.GONE);
		bta2.setVisibility(LinearLayout.GONE);
		Display screen = getWindowManager().getDefaultDisplay();	
		ViewGroup.LayoutParams lp = video.getLayoutParams();
		lp.height = screen.getHeight();
		lp.width = screen.getWidth();
		sHolder.setFixedSize(screen.getHeight(), screen.getWidth());
	}
	
	private void switchToNormalScreen() {
		ViewGroup.LayoutParams lp = video.getLayoutParams();
		lp.height = 300;
		lp.width = 300;
		sHolder.setFixedSize(300, 300);
		LinearLayout bta1 = (LinearLayout) findViewById(R.id.btarray1);
		LinearLayout bta2 = (LinearLayout) findViewById(R.id.btarray2);
		bta1.setVisibility(LinearLayout.VISIBLE);
		bta2.setVisibility(LinearLayout.VISIBLE);

	}
}
