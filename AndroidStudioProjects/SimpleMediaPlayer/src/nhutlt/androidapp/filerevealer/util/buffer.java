/**
 * 
 */
package nhutlt.androidapp.filerevealer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import nhutlt.androidapp.filerevealer.adapter.MyListFileAdapter;
import nhutlt.androidapp.simplemediaplayer.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author nhutlt
 * 
 */
public class buffer {
	private List<File> bufferCopy;
	private List<File> bufferCut;
	private List<File> bufferDelete;
	public static final int Copy = 0;
	public static final int Cut = 1;
	public static final int Delete = 2;
	private final int BUFFERSIZE = 524288;
	private Context context;
	private boolean dialogChoice;

	public buffer(Context context) {
		bufferCopy = new ArrayList<File>();
		bufferCut = new ArrayList<File>();
		bufferDelete = new ArrayList<File>();
		this.context = context;
	}

	public File pop(int commandType) {
		switch (commandType) {
		case Copy:
			if (bufferCopy.size() > 0) {
				return bufferCopy.remove(bufferCopy.size() - 1);
			} else {
				return null;
			}
		case Cut:
			if (bufferCut.size() > 0) {
				return bufferCut.remove(bufferCut.size() - 1);
			} else {
				return null;
			}
		case Delete:
			if (bufferDelete.size() > 0) {
				return bufferDelete.remove(bufferDelete.size() - 1);
			} else {
				return null;
			}
		default:
			return null;
		}
	}

	public int push(File input, int commandType) {
		switch (commandType) {
		case Copy:
			if (bufferCopy != null) {
				bufferCopy.add(input);
				return bufferCopy.indexOf(input);
			} else {
				return -1;
			}
		case Cut:
			if (bufferCut != null) {
				bufferCut.add(input);
				return bufferCut.indexOf(input);
			} else {
				return -1;
			}
		case Delete:
			if (bufferDelete != null) {
				bufferDelete.add(input);
				return bufferDelete.indexOf(input);
			} else {
				return -1;
			}
		default:
			return -1;
		}
	}

	public void copy(String path) throws IOException {
		
		while (bufferCopy.size() > 0) {
			File f = pop(Copy);
			FileInputStream fIn = new FileInputStream(f);
			final File newFile = new File(path + "/" + f.getName());

			if (newFile.exists()) {
				// continue;
				AlertDialog.Builder builder = new Builder(context);
				builder.setTitle(R.string.buffer_copy_confirmdialog_title);
				builder.setMessage(String.format(context.getResources()
						.getString(R.string.buffer_copy_confirmdialog_content),
						f.getAbsolutePath()));
				builder.setPositiveButton(
						R.string.buffer_copy_confirmdialog_buttonyes,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								boolean backup = false;
								int indexBackup = 1;
								File backupFile;
								do {
									backupFile = new File(newFile
											.getAbsolutePath() + "bk" + indexBackup);
									if (backupFile.exists()) {
										backup = true;
										indexBackup++;
									} else {
										backup = false;
									}
								} while (backup);
								newFile.renameTo(backupFile);
								newFile.delete();
								try {
									newFile.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
								dialogChoice = false;
								dialog.cancel();
							}
						});
				builder.setNegativeButton(
						R.string.buffer_copy_confirmdialog_buttonno,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialogChoice = true;
								dialog.cancel();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			} else {
				Log.v("NhutLT", newFile.getAbsolutePath());
				newFile.createNewFile();
				if (!newFile.canWrite()) {
					Log.v("NhutLT", "File can not write.");
					continue;
				}
			}
			
			if (dialogChoice) {
				dialogChoice = false;
				continue;
			}
			Log.v("NhutLT", "new File " + newFile.getAbsolutePath());
			FileOutputStream fOut = new FileOutputStream(newFile);
			byte[] fileBuffer;
			int length = (int) f.length();
			if (length > BUFFERSIZE) {
				do {
					fileBuffer = new byte[BUFFERSIZE];
					fIn.read(fileBuffer);
					fOut.write(fileBuffer);
					length -= BUFFERSIZE;
				} while (length > BUFFERSIZE);
			}

			fileBuffer = new byte[length];
			fIn.read(fileBuffer);
			fOut.write(fileBuffer);
			
			fOut.flush();			
			fOut.close();
			Log.v("NhutLT", "finish write. writen bytes :" + newFile.length() + "name " + newFile.getName());
			fIn.close();			
		}
	}

	public void cut(String path) {
		while (bufferCut.size() > 0) {
			File f = pop(Cut);
			f.renameTo(new File(path + "/" + f.getName()));
		}
	}

	public void delete() {
		while (bufferDelete.size() > 0) {
			File f = pop(Delete);
			f.delete();
		}
	}
	
	public void clear() {
		bufferCopy.clear();
		bufferCut.clear();
		bufferDelete.clear();
	}
	
	public void show() {
		final Dialog bufferInfo = new Dialog(context);
		bufferInfo.setContentView(R.layout.bufferlayout);
		bufferInfo.setTitle(R.string.buffer_infobox_title);
		Button close = (Button) bufferInfo.findViewById(R.id.close);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bufferInfo.cancel();
			}
		});
		
		LinearLayout layoutCopy = (LinearLayout) bufferInfo.findViewById(R.id.copylist);
		LinearLayout layoutCut = (LinearLayout) bufferInfo.findViewById(R.id.cutlist);
		LinearLayout layoutDelete = (LinearLayout) bufferInfo.findViewById(R.id.deletelist);
		ListView listCopy = (ListView) layoutCopy.findViewById(R.id.listfile);
		ListView listCut = (ListView) layoutCut.findViewById(R.id.listfile);
		ListView listDelete = (ListView) layoutDelete.findViewById(R.id.listfile);
		setContentForListView(listCopy, bufferCopy);
		setContentForListView(listCut, bufferCut);
		setContentForListView(listDelete, bufferDelete);
		bufferInfo.show();
	}
	
	private void setContentForListView(ListView list, List<File> listFile) {
		if (list == null || listFile == null || listFile.size() == 0) {
			return;
		}
		Method[] method = bufferCopy.get(0).getClass().getMethods();
		List<String[]> info = FileInfoUtility.getFolderInfo(listFile,
				method[28], method[29]);
		List<String> listName = other.convertArray(info.get(0));
		List<String> listType = other.convertArray(info.get(1));
		List<String> listDate = other.convertArray(info.get(2));
		List<String> listSize = other.convertArray(info.get(3));
		list.setAdapter(new MyListFileAdapter(context, R.layout.fileitem,
				listName, listType, listDate, listSize, null, false));
	}
}