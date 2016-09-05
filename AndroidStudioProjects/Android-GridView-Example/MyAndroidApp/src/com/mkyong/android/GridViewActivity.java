package com.mkyong.android;

import com.mkyong.android.adapter.ImageAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewActivity extends Activity {

	GridView gridView;

	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
			"Windows", "Blackberry" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gridView = (GridView) findViewById(R.id.gridView1);

		gridView.setAdapter(new ImageAdapter(this, loadImage(), MOBILE_OS));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(
						getApplicationContext(),
						((TextView) v.findViewById(R.id.grid_item_label))
								.getText(), Toast.LENGTH_SHORT).show();

			}
		});

	}
	
	 private Bitmap[] loadImage() {
         final String[] columns = { MediaStore.Images.Media.DATA,
                 MediaStore.Images.Media._ID };
         final String orderBy = MediaStore.Images.Media._ID;
         final String whereClause = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                 + " = ?";  // bucket name means the name of the container of the images
         final String[] imageDirectories = {"Camera"};
         
         
         Cursor imagecursor = managedQuery(
                 MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                 whereClause, imageDirectories, orderBy);
         int mNumberOfImage = imagecursor.getCount();
         Bitmap[] thumbNails = new Bitmap[mNumberOfImage];
         
         // get the index number of the ID column
         int imageColumnIndex = imagecursor
                 .getColumnIndex(MediaStore.Images.Media._ID);
         
         for (int i = 0; i < mNumberOfImage; i++) {
         	// image cursor point to row number i
             imagecursor.moveToPosition(i);
             // get the index of the image at the row number i
             int id = imagecursor.getInt(imageColumnIndex);
             // get the index number of the DATA column
             int dataColumnIndex = imagecursor
                     .getColumnIndex(MediaStore.Images.Media.DATA);
             // 
             thumbNails[i] = MediaStore.Images.Thumbnails.getThumbnail(
                     getApplicationContext().getContentResolver(), id,
                     MediaStore.Images.Thumbnails.MICRO_KIND, null);
         }

         imagecursor.close();
         return thumbNails;
     }
}