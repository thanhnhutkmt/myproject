package com.mkyong.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mkyong.android.R;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private Bitmap[] images;
	private final String[] mobileValues;

	public ImageAdapter(Context context, Bitmap[] image, String[] mobile) {
		this.context = context;
		this.images = image;
		this.mobileValues = mobile;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.mobile, null);
/*
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(mobileValues[position]);

			// set image based on selected text


			String mobile = mobileValues[position];
			*/
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
			imageView.setImageBitmap(images[position]);

		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
