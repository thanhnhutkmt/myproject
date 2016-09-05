package nhutlt.androidapp.filerevealer.adapter;

import java.util.List;

import nhutlt.androidapp.simplemediaplayer.R;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListFileAdapter extends BaseAdapter {
	private Context context;
	private List<String> sizes, names, types, createddates;
	private int layout;
	private boolean[] selectItem;
	private boolean checkMode;
	
	public MyListFileAdapter(Context context, 
			int itemLayout, List<String> name, List<String> type,
							List<String> createddate, List<String> size,
							boolean[] selectItem, boolean checkMode) {
		this.context = context;
		this.names = name;
		this.types = type;
		this.createddates = createddate;
		this.sizes = size;
		this.layout = itemLayout;
		if (selectItem == null || selectItem.length == 0) {
			this.selectItem = new boolean[name.size()];
		} else {
			this.selectItem = selectItem;
		}
		this.checkMode = checkMode;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pos = position;
		LayoutInflater inflater = (LayoutInflater) this.context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View fileView;
			final TextView name;
			final TextView type;
			final TextView size;
			final TextView createddate;
			final ImageView checkbox;
			
		if(convertView == null) {
			fileView = inflater.inflate(layout, null);
			name = (TextView) fileView.findViewById(R.id.name);
			size = (TextView) fileView.findViewById(R.id.size);			
			type = (TextView) fileView.findViewById(R.id.type);
			createddate = (TextView) fileView.findViewById(R.id.createddate);
			checkbox = (ImageView) fileView.findViewById(R.id.checkbox);		
		} else {
			name = (TextView) convertView.findViewById(R.id.name);
			size = (TextView) convertView.findViewById(R.id.size);			
			type = (TextView) convertView.findViewById(R.id.type);
			createddate = (TextView) convertView.findViewById(R.id.createddate);
			checkbox = (ImageView) convertView.findViewById(R.id.checkbox);
			fileView = (View) convertView;
		}
		
		String nameOfFile = names.get(position);
		String sizeOfFile = sizes.get(position);
		String typeOfFile = types.get(position);
		String createddateOfFile = createddates.get(position);
				
//		Log.v("NhutLT",	"listed postion " + position + " " + tempName);
		name.setText(nameOfFile);
		size.setText(sizeOfFile);
		type.setText(typeOfFile);
		createddate.setText(createddateOfFile);
		if(checkMode) {
			checkbox.setVisibility(ImageView.VISIBLE);
			checkbox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectItem[pos] = !selectItem[pos];
					if (selectItem[pos]) {
						name.setTextColor(context.getResources().getColor(R.color.selectcolor));
						size.setTextColor(context.getResources().getColor(R.color.selectcolor));
						type.setTextColor(context.getResources().getColor(R.color.selectcolor));
						createddate.setTextColor(context.getResources().getColor(R.color.selectcolor));
						checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));
					} else {
						name.setTextColor(context.getResources().getColor(R.color.black));
						size.setTextColor(context.getResources().getColor(R.color.black));
						type.setTextColor(context.getResources().getColor(R.color.black));
						createddate.setTextColor(context.getResources().getColor(R.color.black));
						checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
					}
				}
			});
		} else {
			checkbox.setVisibility(ImageView.GONE);
		}
		
		if (selectItem[position]) {
			name.setTextColor(context.getResources().getColor(R.color.selectcolor));
			size.setTextColor(context.getResources().getColor(R.color.selectcolor));
			type.setTextColor(context.getResources().getColor(R.color.selectcolor));
			createddate.setTextColor(context.getResources().getColor(R.color.selectcolor));
			checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_check));
		} else {
			name.setTextColor(context.getResources().getColor(R.color.black));
			size.setTextColor(context.getResources().getColor(R.color.black));
			type.setTextColor(context.getResources().getColor(R.color.black));
			createddate.setTextColor(context.getResources().getColor(R.color.black));
			checkbox.setImageDrawable(context.getResources().getDrawable(R.drawable.checkbox_uncheck));
		}
		return fileView;
	}

	public int getCount() {
		return names.size();
	}

	public Object getItem(int position) {
		Log.v("NhutLT",	"listed postion " + position);
		return null;
	}

	public long getItemId(int position) {
		Log.v("NhutLT",	"listed postion " + position);
		return 0;
	}
}
