/**
 * 
 */
package nhutlt.androidapp.simplemediaplayer.adapter;

import java.util.List;
import nhutlt.androidapp.simplemediaplayer.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author nhutlt
 * 
 */
public class listSongAdapter extends BaseAdapter {
	private Context context;
	private List<String> sizes, names, types, durations;
	private int layout, currentSong;

	public listSongAdapter(Context context, int itemLayout,
			List<String> name, List<String> type, List<String> durations,
			List<String> size, int currentSong) {
		this.context = context;
		this.names = name;
		this.types = type;
		this.durations = durations;
		this.sizes = size;
		this.layout = itemLayout;
		this.currentSong = currentSong;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		LayoutInflater inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View fileView;
		final TextView name;
		final TextView type;
		final TextView size;
		final TextView duration;

		if (convertView == null) {
			fileView = inflater.inflate(layout, null);
			name = (TextView) fileView.findViewById(R.id.name);
			size = (TextView) fileView.findViewById(R.id.size);
			type = (TextView) fileView.findViewById(R.id.type);
			duration = (TextView) fileView.findViewById(R.id.duration);
		} else {
			name = (TextView) convertView.findViewById(R.id.name);
			size = (TextView) convertView.findViewById(R.id.size);
			type = (TextView) convertView.findViewById(R.id.type);
			duration = (TextView) convertView.findViewById(R.id.duration);
			fileView = (View) convertView;
		}
		


		String nameOfFile = names.get(position);
		String sizeOfFile = sizes.get(position);
		String typeOfFile = types.get(position);
		String durationOfFile = durations.get(position);

		Log.v("NhutLT", "listed postion " + position + " " + nameOfFile);
		name.setText(nameOfFile);
		size.setText(sizeOfFile);
		type.setText(typeOfFile);
		duration.setText(durationOfFile);
		
		if (currentSong == position) {
			name.setTextColor(context.getResources().getColor(R.color.red));
			size.setTextColor(context.getResources().getColor(R.color.red));
			type.setTextColor(context.getResources().getColor(R.color.red));
			duration.setTextColor(context.getResources().getColor(R.color.red));
		} else {
			name.setTextColor(context.getResources().getColor(R.color.black));
			size.setTextColor(context.getResources().getColor(R.color.black));
			type.setTextColor(context.getResources().getColor(R.color.black));
			duration.setTextColor(context.getResources().getColor(R.color.black));
		}
		
		return fileView;
	}

	public int getCount() {
		return names.size();
	}

	public Object getItem(int position) {
		Log.v("NhutLT", "listed postion " + position);
		return null;
	}

	public long getItemId(int position) {
		Log.v("NhutLT", "listed postion " + position);
		return 0;
	}

}
