/**
 * 
 */
package nhutlt.androidapp.dialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import nhutlt.androidapp.filerevealer.adapter.MyListFileAdapter;
import nhutlt.androidapp.filerevealer.util.FileInfoUtility;
import nhutlt.androidapp.filerevealer.util.buffer;
import nhutlt.androidapp.filerevealer.util.other;
import nhutlt.androidapp.simplemediaplayer.Mediaplayer;
import nhutlt.androidapp.simplemediaplayer.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author nhutlt
 * 
 */
public class Fileselector extends Activity {
//	private TextView namelabel, sizelabel, typelabel, createddatelabel, path;
	private TextView path;
	private ImageView checkboxheader;
	private ListView listFile;
	private String rootPath, sdCardPath;
	private File currentFolder;
	private File[] currentFolderContent;
	private Context context;
	private buffer fileCache;
	private final int UP = 0;
	private final int DOWN = 1;
	private boolean[] selectItem;
	private boolean checkMode;
	private boolean checkboxheaderstate;
//	private List<String> playlistName, playlistSize, playlistPath, playlistType, playlistDuraton;
	private ArrayList<String> newSongsName, newSongsSize, newSongsPath, newSongsType;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surfscreen);
		context = this;
		newSongsName = new ArrayList<String>();
		newSongsSize = new ArrayList<String>();
		newSongsPath = new ArrayList<String>();
		newSongsType = new ArrayList<String>();
		checkboxheader = (ImageView) findViewById(R.id.checkbox);
		path = (TextView) findViewById(R.id.path);
		fileCache = new buffer(context);
		checkboxheader.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkboxheaderstate) {
					selectNoneDisplay();
				} else {
					selectAllDisplay();
				}				
			}
		});
		checkboxheader.setVisibility(ImageView.GONE);
		sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		currentFolder = Environment.getRootDirectory();
		rootPath = currentFolder.getAbsolutePath();
		currentFolderContent = currentFolder.listFiles();
		path.setText(rootPath);
		selectItem = new boolean[currentFolderContent.length];

		Method[] method = currentFolder.getClass().getMethods();
		List<String[]> info = FileInfoUtility.getFolderInfo(currentFolder,
				method[28], method[29]);
		List<String> listName = other.convertArray(info.get(0));
		List<String> listType = other.convertArray(info.get(1));
		List<String> listDate = other.convertArray(info.get(2));
		List<String> listSize = other.convertArray(info.get(3));

		listFile = (ListView) findViewById(R.id.listfile);
		listFile.setAdapter(new MyListFileAdapter(this, R.layout.fileitem,
				listName, listType, listDate, listSize, selectItem, checkMode));
		listFile.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				if (checkMode) {
					ImageView checkbox = (ImageView) v.findViewById(R.id.checkbox);
					if (!checkbox.isPressed()) {
						changeDirectory(DOWN, position);
					}
				} else {
					changeDirectory(DOWN, position);
				}
			}
		});

		listFile.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int position, long id) {
				final int pos = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(R.string.surfscreen_selectmenu_title)
						.setCancelable(true)
						.setNegativeButton(
								R.string.surfscreen_selectmenu_choice_cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				builder.setItems(
						getResources().getStringArray(R.array.choices),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								switch (item) {
								case 0:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button paste");
									if (currentFolderContent[pos].isDirectory()) {
										try {
											fileCache.copy(currentFolderContent[pos].getAbsolutePath());
										} catch (IOException e) {
											e.printStackTrace();
										}
										fileCache.cut(currentFolderContent[pos].getAbsolutePath());
									}
									break;
								case 1:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button copy");
									fileCache.push(currentFolderContent[pos],
											buffer.Copy);
									break;
								case 2:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button cut");
									fileCache.push(currentFolderContent[pos],
											buffer.Cut);
									break;
								case 3:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button rename");
									if (currentFolderContent[pos].canWrite()) {
										final Dialog renameDialog = new Dialog(
												context);
										renameDialog.setTitle(getResources().getString(R.string.surfscreen_renamedialog_title));
										renameDialog
												.setContentView(R.layout.renamedialog);
										final TextView oldName = (TextView) renameDialog
												.findViewById(R.id.oldname);
										final EditText newName = (EditText) renameDialog
												.findViewById(R.id.newname);
										final Button change = (Button) renameDialog
												.findViewById(R.id.change);
										final Button cancel = (Button) renameDialog
												.findViewById(R.id.cancel);
										
										oldName.setText(currentFolderContent[pos].getName());										
										change.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												String name = newName.getText()
														.toString();
												if (name != null
														&& name.trim().length() > 0) {
													String newPath = path
															.getText()
															.toString()
															+ "/" + name;
													currentFolderContent[pos]
															.renameTo(new File(
																	newPath));
												}
												renameDialog.cancel();
												updateDisplay();
											}
										});
										cancel.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												renameDialog.cancel();
											}
										});
										renameDialog.show();
									} else {
										Toast.makeText(
												context,
												getResources()
														.getString(
																R.string.surfscreen_renamedialog_warnmessage),
												Toast.LENGTH_SHORT).show();
									}
									break;
								case 4:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button delete");
									if (currentFolderContent[pos].canWrite()) {
										if (currentFolderContent[pos].delete()) {
											updateDisplay();
											Toast.makeText(
													context,
													getResources()
															.getString(
																	R.string.surfscreen_delete_resultsuccessmessage),
													Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(
													context,
													getResources()
															.getString(
																	R.string.surfscreen_delete_resulterrormessage),
													Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(
												context,
												getResources()
														.getString(
																R.string.surfscreen_delete_accesserrormessage),
												Toast.LENGTH_SHORT).show();
									}									
									break;
								case 5:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button properties");

									break;
								case 6:
									Log.v("NhutLT",
											currentFolderContent[pos].getName()
													+ " button Add to playlist");
									String name = currentFolderContent[pos].getName();
									newSongsName.add(name);
									newSongsSize.add(FileInfoUtility.changeSizeFormat(Long
											.toString(currentFolderContent[pos]
													.length())));
									newSongsPath.add(currentFolderContent[pos].getAbsolutePath());
									if (name.contains(".")) {
										String extension = name.substring(name.lastIndexOf('.') + 1, name.length());
										newSongsType.add(extension);
									} else {
										newSongsType.add(context.getResources().getString(R.string.Noextension));
									}									
									break;
								}
							}
						});

				AlertDialog alert = builder.create();
				alert.show();
				return true;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (!currentFolder.exists()) {
			changeDirectory(UP, 0);
		}
		newSongsName.clear();
		newSongsSize.clear();
		newSongsPath.clear();
		newSongsType.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		String currentPath = path.getText().toString();
		if (currentPath.equals(rootPath) || currentPath.equals(sdCardPath)) {
			Intent next = new Intent(getApplicationContext(), Mediaplayer.class);
			next.putStringArrayListExtra("playlistNameo",
					(ArrayList<String>) this.getIntent()
							.getStringArrayListExtra("playlistName"));
			
			next.putStringArrayListExtra("playlistSizeo",
					(ArrayList<String>) this.getIntent()
							.getStringArrayListExtra("playlistSize"));
			
			next.putStringArrayListExtra("playlistPatho",
					(ArrayList<String>) this.getIntent()
							.getStringArrayListExtra("playlistPath"));
			
			next.putStringArrayListExtra("playlistTypeo",
					(ArrayList<String>) this.getIntent()
							.getStringArrayListExtra("playlistType"));
			
			next.putStringArrayListExtra("playlistDurationo",
					(ArrayList<String>) this.getIntent()
							.getStringArrayListExtra("playlistDuration"));
			
			next.putStringArrayListExtra("playlistName",
					(ArrayList<String>) newSongsName);
			next.putStringArrayListExtra("playlistSize",
					(ArrayList<String>) newSongsSize);
			next.putStringArrayListExtra("playlistPath",
					(ArrayList<String>) newSongsPath);
			next.putStringArrayListExtra("playlistType",
					(ArrayList<String>) newSongsType);

			startActivity(next);
		} else {
			changeDirectory(UP, 0);
		}
	}

	private void updateDisplay() {
		Method[] method = currentFolder.getClass().getMethods();
		Log.v("NhutLT", currentFolder.getName() + " " + currentFolder.canRead());
		List<String[]> info = FileInfoUtility.getFolderInfo(currentFolder,
				method[28], method[29]);
		List<String> listName = other.convertArray(info.get(0));
		List<String> listType = other.convertArray(info.get(1));
		List<String> listDate = other.convertArray(info.get(2));
		List<String> listSize = other.convertArray(info.get(3));
		listFile.setAdapter(new MyListFileAdapter(context, R.layout.fileitem,
				listName, listType, listDate, listSize, selectItem, checkMode));
	}

	private void changeDirectory(int direction, int position) {
		if (direction == UP) {
			String oneLevelUpPath = FileInfoUtility
					.getOneLevelUpFolderPath(path.getText().toString());
			currentFolder = new File(oneLevelUpPath);
			if (!currentFolder.exists()) {
				currentFolder = new File(rootPath);
			}
			path.setText(oneLevelUpPath);
			currentFolderContent = currentFolder.listFiles();
		} else { // DOWN
			if (currentFolderContent[position].isDirectory()) {
				if (currentFolderContent[position].canRead()) {
					currentFolder = currentFolderContent[position];
					path.setText(currentFolder.getAbsolutePath());
					currentFolderContent = currentFolder.listFiles();
				} else {
					Toast.makeText(context, getResources().getString
							(R.string.surfscreen_openfolder_accesserrormessage),
							Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				return;
			}
		}
		checkboxheaderstate = false;
		checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
		selectItem = new boolean[currentFolderContent.length];				
		updateDisplay();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.filemenu, menu);		
		
		return super.onCreateOptionsMenu(menu);		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int size = currentFolderContent.length;
		switch (item.getItemId()) {
		//Select submenu
	        case R.id.selectall:
	        	selectAllDisplay();
	            return true;
	        case R.id.selectmanyfiles:
	        	if (item.getTitle().toString().equals(getResources().getString(R.string.menu_select_manyfileson))) {
	        		checkMode = true;
	        		updateDisplay();
	        		checkboxheader.setVisibility(ImageView.VISIBLE);
	        		item.setTitle(getResources().getString(R.string.menu_select_manyfilesoff));
	        	} else {
	        		checkMode = false;
	        		updateDisplay();
	        		checkboxheader.setVisibility(ImageView.GONE);
	        		item.setTitle(getResources().getString(R.string.menu_select_manyfileson));
	        	}
	            return true;
	        case R.id.selectnone:
	        	selectNoneDisplay();
	            return true;
	        case R.id.selectrevert:
	        	selectReverseDisplay();
	            return true;
	    //Buffer submenu
	        case R.id.bufferremove:
	        	fileCache.clear();
	        	return true;
	        case R.id.buffershow:
	        	fileCache.show();
	        	return true;	
	        case R.id.showselectedsongs:
	        	showSelectedMediaFiles(this);
	        	return true;
	    //File submenu
	        case R.id.filecopy:
	        	selectItem = new boolean[size];
	        	updateDisplay();
	        	for (int index = 0; index < size; index++) {
	        		if (selectItem[index]) {
	        			fileCache.push(currentFolderContent[index], buffer.Copy);
	        		}
	        	}
	        	return true;
	        case R.id.filecut:
	        	selectItem = new boolean[size];
	        	updateDisplay();
	        	for (int index = 0; index < size; index++) {
	        		if (selectItem[index]) {
	        			fileCache.push(currentFolderContent[index], buffer.Cut);
	        		}
	        	}
	        	return true;
	        case R.id.filedelete:
	        	selectItem = new boolean[size];
	        	updateDisplay();
	        	for (int index = 0; index < size; index++) {
	        		if (selectItem[index]) {
	        			fileCache.push(currentFolderContent[index], buffer.Delete);
	        		}
	        	}
	        	return true;
	    //Location submenu
	        case R.id.root:
				currentFolder = new File(rootPath);
				path.setText(currentFolder.getAbsolutePath());
				currentFolderContent = currentFolder.listFiles();
				checkboxheaderstate = false;
				checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
				selectItem = new boolean[currentFolderContent.length];	
				updateDisplay();
	        	return true;
	        case R.id.sdcard:
	        	String sdCardState = Environment.getExternalStorageState();
	        	Log.v("NhutLT", "sdcard state " + sdCardState);
	        	if (!sdCardState.equals(Environment.MEDIA_MOUNTED)) {
	        		Toast.makeText(context, R.string.menu_location_sdcard_errormessage, Toast.LENGTH_LONG).show();
	        		return true;
	        	}
				currentFolder = new File(sdCardPath);				
				currentFolderContent = currentFolder.listFiles();
				if (currentFolderContent == null) {
					currentFolder = new File(rootPath);				
					currentFolderContent = currentFolder.listFiles();
				}
				path.setText(currentFolder.getAbsolutePath());
				checkboxheaderstate = false;
				checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
				selectItem = new boolean[currentFolderContent.length];	
				updateDisplay();	        	
	        	return true;	        	
	        default:
	            return super.onOptionsItemSelected(item);
		}
    }
	
	private boolean[] setSelectAll(int size) {
		boolean[] arraySelectAll = new boolean[size];
		for(int index = 0; index < size; index++) {
			arraySelectAll[index] = true;
		}
		return arraySelectAll;
	}
	
	private boolean[] reverseSelect(boolean[] selectItem) {
		int size = selectItem.length;
		for(int index = 0; index < size; index++) {
			selectItem[index] = !selectItem[index];
		}
		return selectItem;
	}
	
	private void selectAllDisplay() {
		selectItem = setSelectAll(currentFolderContent.length);
		checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));		
		checkboxheaderstate = true;
		updateDisplay();
	}
	
	private void selectNoneDisplay() {
    	selectItem = new boolean[currentFolderContent.length];
    	checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
    	checkboxheaderstate = false;
    	updateDisplay();
	}
	
	private void selectReverseDisplay() {
		int size = selectItem.length;
		boolean allFalse = true;
		boolean allTrue = true;
		for(int index = 0; index < size; index++) {
			if (selectItem[index]) {
				allFalse = false;
				break;
			}
		}
		for(int index = 0; index < size; index++) {
			if (!selectItem[index]) {
				allTrue = false;
				break;
			}
		}
		if (allFalse) {
			selectAllDisplay();
		} else if (allTrue){
			selectNoneDisplay();
		} else {
			selectItem = reverseSelect(selectItem);
	    	checkboxheader.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
	    	checkboxheaderstate = false;
			updateDisplay();
		}
	}
	
	private void showBuffer(Context context) {
		Dialog bufferDialog = new Dialog(context);
		bufferDialog.setContentView(R.layout.bufferlayout);
		ListView listCopy = (ListView) bufferDialog.findViewById(R.id.copylist);
		ListView listCut = (ListView) bufferDialog.findViewById(R.id.cutlist);
		ListView listDelete = (ListView) bufferDialog.findViewById(R.id.deletelist);
				
		bufferDialog.show();
	}
	
	private void showSelectedMediaFiles(Context context) {
		
	}
	
	
}

